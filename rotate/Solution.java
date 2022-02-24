import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Long.parseLong(st.nextToken());
        long K = Long.parseLong(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        ArrayList<Long> arr = new ArrayList<>();
        ArrayList<Long> sums = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            arr.add(Long.parseLong(st.nextToken()));
            sums.add(0L);
        }

        long ans = 0;
        for (int i = 0; i < N; i++) {
            if (i < K) {
                for (int j = 0; j < N/K; j++) {
                    sums.set(i, sums.get(i)+arr.get((int)((i+K*j) % N)));
                }
            } else {
                sums.set(i, sums.get((int)(i-K))-arr.get((int)(i-K))+arr.get((int)((i-K+K*(N/K)) % N)));;
            }
            if (i == 0) ans = sums.get(i);
            else ans = Math.max(ans, sums.get(i));
        }
        System.out.println(ans);
    }
}
