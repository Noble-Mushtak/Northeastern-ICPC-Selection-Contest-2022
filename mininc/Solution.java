import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Long.parseLong(st.nextToken());
        long K = Long.parseLong(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        long arr[] = new long[(int)N];
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        long ans = 1;
        for (int i = 0; i < N; i++) {
            int idx = Arrays.binarySearch(arr, arr[i]+K);
            if (idx < 0) idx = -idx-1;
            ans = Math.max(ans, idx-i);
        }
        System.out.println(ans);
    }
}
