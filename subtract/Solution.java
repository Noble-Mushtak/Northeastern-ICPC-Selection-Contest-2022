import java.io.*;
import java.util.*;

public class Solution {
    static final int MAXN = 300010;

    static boolean isPerfectSquare(long x) {
        long sr = (long)Math.sqrt(x);
        return (sr*sr == x);
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        boolean isPrime[] = new boolean[MAXN];
        isPrime[0] = isPrime[1] = false;
        for (long p = 2; p < MAXN; p++) {
            isPrime[(int)p] = true;
        }
        ArrayList<Long> goodPrimes = new ArrayList<>();
        for (long p = 2; p < MAXN; p++) {
            if (isPrime[(int)p]) {
                if (isPerfectSquare(p-1)) {
                    goodPrimes.add(p);
                }
                for (long j = 2*p; j < MAXN; j += p) {
                    isPrime[(int)j] = false;
                }
            }
        }

        boolean dp[] = new boolean[MAXN];
        for (long g = 0; g < MAXN; g++) {
            dp[(int)g] = false;
        }
        for (long g = 0; g < MAXN; g++) {
            for (long p : goodPrimes) {
                if (p > g) break;
                if (!dp[(int)(g-p)]) {
                    dp[(int)g] = true;
                    break;
                }
            }
        }

        long T = Long.parseLong(st.nextToken());
        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            long N = Long.parseLong(st.nextToken());
            System.out.println(2-(dp[(int)N] ? 1 : 0));
        }
    }
}
