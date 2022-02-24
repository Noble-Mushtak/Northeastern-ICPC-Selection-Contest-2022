import java.io.*;
import java.util.*;

public class Solution {
    static final long MOD = 1000000007;

    //Based on https://github.com/kth-competitive-programming/kactl/blob/main/content/number-theory/euclid.h
    static class EuclidInfo {
        long x;
        long y;
        long d;
        EuclidInfo(long a, long b, long c) {
            this.x = a;
            this.y = b;
            this.d = c;
        }
    }
    static EuclidInfo euclid(long a, long b) {
        if (b == 0) return new EuclidInfo(1, 0, a);
        EuclidInfo curInf = euclid(b, a % b);
        long y = curInf.x;
        long x = curInf.y;
        long d = curInf.d;
        y -= a/b*x;
        return new EuclidInfo(x, y, d);
    }
    //Based on https://github.com/kth-competitive-programming/kactl/blob/main/content/number-theory/ModularArithmetic.h
    static long invert(long z) {
        EuclidInfo curInf = euclid(z, MOD);
        return (curInf.x + MOD) % MOD;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Long.parseLong(st.nextToken());
        long Q = Long.parseLong(st.nextToken());
        
        st = new StringTokenizer(br.readLine());
        long arr[] = new long[(int)N];
        long prod[] = new long[(int)(N+1)];
        prod[0] = 1;
        long numZeroes[] = new long[(int)(N+1)];
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
            if (arr[i] == 0) {
                prod[i+1] = 1;
                numZeroes[i+1] = numZeroes[i]+1;
            } else {
                prod[i+1] = (prod[i]*arr[i]) % MOD;
                numZeroes[i+1] = numZeroes[i];
            }
        }
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            int l = Integer.parseInt(st.nextToken());
            int r = Integer.parseInt(st.nextToken());
            if (numZeroes[l-1] == numZeroes[r]) {
                System.out.println((prod[r]*invert(prod[l-1])) % MOD);
            } else {
                System.out.println("0");
            }
        }
    }
}
