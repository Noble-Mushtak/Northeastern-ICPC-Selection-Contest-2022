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

    static long calcDist(Pair p1, Pair p2) {
        return (p1.first-p2.first)*(p1.first-p2.first) + (p1.second-p2.second)*(p1.second-p2.second);
    }

    //Based on https://github.com/kth-competitive-programming/kactl/blob/main/content/graph/SCC.h
    static int val[];
    static int comp[];
    static ArrayDeque<Integer> z = new ArrayDeque<>();
    static int Time;
    static int ncomps;

    static class StackFrame {
        boolean initFrame;
        int curJ, idx, low;
        StackFrame(int j) {
            this.initFrame = true;
            this.curJ = j;
            this.idx = 0;
            this.low = 0;
        }
        StackFrame(int j, int cidx, int clow) {
            this.initFrame = false;
            this.curJ = j;
            this.idx = cidx;
            this.low = clow;
        }
    }
    
    static int dfs(int v, ArrayList<Integer> g[]) {
        int res = 0;
        ArrayDeque<StackFrame> frames = new ArrayDeque<>();
        frames.addLast(new StackFrame(v));
        while (!frames.isEmpty()) {
            StackFrame curFrame = frames.pollLast();
            if (curFrame.initFrame) {
                Time++;
                val[curFrame.curJ] = Time;
                z.addLast(curFrame.curJ);
                res = Time;
                frames.addLast(new StackFrame(curFrame.curJ, 0, Time));
            } else {
                int low = Math.min(curFrame.low, res);
                boolean broke = false;
                for (int i = curFrame.idx; i < g[curFrame.curJ].size(); i++) {
                    int e = g[curFrame.curJ].get(i);
                    if (comp[e] < 0) {
                        if (val[e] > 0) {
                            low = Math.min(low, val[e]);
                        } else {
                            frames.addLast(new StackFrame(curFrame.curJ, i+1, low));
                            frames.addLast(new StackFrame(e));
                            broke = true;
                            break;
                        }
                    }
                }
                if (broke) continue;
                if (low == val[curFrame.curJ]) {
                    int x;
                    do {
                        x = z.pollLast();
                        comp[x] = ncomps;
                    } while (x != curFrame.curJ);
                    ncomps++;
                }
                val[curFrame.curJ] = low;
                res = low;
            }
        }
        return res;
    }
    static void scc(ArrayList<Integer> g[]) {
        int n = g.length;
        val = new int[n];
        comp = new int[n];
        for (int i = 0; i < n; i++) {
            val[i] = 0;
            comp[i] = -1;
        }
        Time = 0;
        ncomps = 0;
        for (int i = 0; i < n; i++) {
            if (comp[i] < 0) {
                dfs(i, g);
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Long.parseLong(st.nextToken());
        long K = Long.parseLong(st.nextToken());
        Pair points[] = new Pair[(int)N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            points[i] = new Pair(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()));
        }

        ArrayList<Integer> adj[] = new ArrayList[(int)N];
        for (int i = 0; i < N; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < N; i++) {
            ArrayList<Pair> dsts = new ArrayList<>();
            for (int j = 0; j < N; j++) {
                dsts.add(new Pair(calcDist(points[i], points[j]), j));
            }
            PriorityQueue<Pair> dstsQ = new PriorityQueue<>(dsts);
            Pair kBest[] = new Pair[(int)K];
            for (int j = 0; j < K; j++) {
                kBest[j] = dstsQ.poll();
            }
            long mi = dstsQ.peek().first;
            for (int j = 0; j < K; j++) {
                if (kBest[j].first < mi) {
                    adj[i].add((int)kBest[j].second);
                }
            }
        }

        scc(adj);
        if (ncomps == 1) {
            System.out.println(0);
            System.exit(0);
        }

        boolean inDegZero[] = new boolean[ncomps];
        boolean outDegZero[] = new boolean[ncomps];
        for (int i = 0; i < ncomps; i++) {
            inDegZero[i] = true;
            outDegZero[i] = true;
        }
        for (int i = 0; i < N; i++) {
            for (int j : adj[i]) {
                if (comp[i] != comp[j]) {
                    outDegZero[comp[i]] = false;
                    inDegZero[comp[j]] = false;
                }
            }
        }
        int inDegsZero = 0, outDegsZero = 0;
        for (int i = 0; i < ncomps; i++) {
            if (inDegZero[i]) inDegsZero++;
            if (outDegZero[i]) outDegsZero++;
        }
        System.out.println(Math.max(inDegsZero, outDegsZero));
    }
}
