import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Long.parseLong(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        long arr[] = new long[(int)N];
        PriorityQueue<Long> priQ = new PriorityQueue<>();
        long score = 0;
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
            priQ.add(arr[i]);
            score += arr[i];
        }
        while (priQ.size() > 1) {
            long a = priQ.poll();
            long b = priQ.poll();
            score += a+b;
            priQ.add(a+b);
        }
        System.out.println(score);
    }
}
