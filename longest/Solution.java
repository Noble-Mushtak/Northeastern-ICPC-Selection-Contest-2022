import java.io.*;
import java.util.*;

public class Solution {
    static class Pair implements Comparable<Pair> {
        long first, second;
        Pair(long a, long b) {
            this.first = a;
            this.second = b;
        }
        public int compareTo(Pair other) {
            int cmp1 = Long.compare(this.first, other.first);
            if (cmp1 != 0) return cmp1;
            return Long.compare(this.second, other.second);
        }
    }
    
    static class Edge {
        long start, end, val;
        Edge(long s, long e, long v) {
            this.start = s;
            this.end = e;
            this.val = v;
        }
    }

    static long addString(Map<String, Long> m, String s) {
        if (m.containsKey(s)) {
            return m.get(s);
        } else {
            int curSz = m.size();
            m.put(s, (long)curSz);
            return curSz;
        }
    }

    //Based on https://github.com/kth-competitive-programming/kactl/blob/main/content/graph/TopoSort.h
    static ArrayList<Long> topoSort(ArrayList<Long> gr[]) {
        long indeg[] = new long[gr.length];
        ArrayList<Long> ret = new ArrayList<>();
        for (ArrayList<Long> li : gr) {
            for (long x : li) {
                indeg[(int)x] += 1;
            }
        }
        ArrayDeque<Long> q = new ArrayDeque<>();
        for (long i = 0; i < gr.length; i++) {
            if (indeg[(int)i] == 0) q.addLast(i);
        }
        while (!q.isEmpty()) {
            long i = q.pollFirst();
            ret.add(i);
            for (long x : gr[(int)i]) {
                if (--indeg[(int)x] == 0) {
                    q.addLast(x);
                }
            }
        }
        return ret;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long N = Long.parseLong(st.nextToken());
        ArrayList<Edge> edges = new ArrayList<>();
        Map<String, Long> stringToNum = new TreeMap<>();
        for (long i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            long K = Long.parseLong(st.nextToken());
            String w = st.nextToken();
            String suff = w;
            if (w.length() > 10) {
                suff = w.substring(w.length()-10);
            }
            
            st = new StringTokenizer(br.readLine());
            for (long j = 0; j < K; j++) {
                String beg = st.nextToken();
                long begIdx = addString(stringToNum, beg);
                String end = suff;
                if (suff.length() < 10) {
                    int gap = 10-suff.length();
                    end = beg.substring(beg.length()-gap)+suff;
                }
                long endIdx = addString(stringToNum, end);
                edges.add(new Edge(begIdx, endIdx, w.length()));
            }
        }

        ArrayList<Long> adj[] = new ArrayList[stringToNum.size()];
        for (long i = 0; i < stringToNum.size(); i++) {
            adj[(int)i] = new ArrayList<Long>();
        }
        Map<Pair, Long> edgeWeights = new TreeMap<>();
        for (Edge curEdge : edges) {
            Pair curKey = new Pair(curEdge.start, curEdge.end);
            if (edgeWeights.containsKey(curKey)) {
                edgeWeights.put(curKey, Math.max(edgeWeights.get(curKey), curEdge.val));
            } else {
                adj[(int)curEdge.start].add(curEdge.end);
                edgeWeights.put(curKey, curEdge.val);
            }
        }
        ArrayList<Pair> revAdj[] = new ArrayList[adj.length];
        for (long i = 0; i < adj.length; i++) {
            revAdj[(int)i] = new ArrayList<Pair>();
        }
        for (Map.Entry<Pair, Long> curEntry : edgeWeights.entrySet()) {
            revAdj[(int)curEntry.getKey().second].add(new Pair(curEntry.getKey().first, curEntry.getValue()));
        }

        ArrayList<Long> ordering = topoSort(adj);
        if (ordering.size() < adj.length) {
            System.out.println(-1);
            System.exit(0);
        }
        long dp[] = new long[adj.length];
        long ans = 0;
        for (long v : ordering) {
            for (Pair pr : revAdj[(int)v]) {
                dp[(int)v] = Math.max(dp[(int)v], dp[(int)pr.first]+pr.second);
            }
            ans = Math.max(ans, dp[(int)v]);
        }
        System.out.println(10+ans);
    }
}
