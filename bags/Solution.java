import java.io.*;
import java.util.*;

public class Solution {
    static Random randomObj = new Random();

    //Based on https://github.com/Noble-Mushtak/cp-notebook/blob/main/content/data-structures/Treap.h
    static class TreapNode {
        long pri, value, sze, mn, mx;
        TreapNode left, right;
        TreapNode(long v) {
            this.pri = randomObj.nextLong();
            this.value = v;
            this.sze = 1;
            this.mn = v;
            this.mx = v;
            this.left = null;
            this.right = null;
        }
    }
    static class Pair {
        TreapNode left, right;
        Pair(TreapNode a, TreapNode b) {
            this.left = a;
            this.right = b;
        }
    }

    static long mn(TreapNode t) {
        if (t == null) return Long.MAX_VALUE;
        return t.mn;
    }

    static long mx(TreapNode t) {
        if (t == null) return Long.MIN_VALUE;
        return t.mx;
    }

    static long sze(TreapNode t) {
        if (t == null) return 0;
        return t.sze;
    }

    static void update(TreapNode t) {
        if (t == null) return;
        t.sze = sze(t.left)+sze(t.right)+1;
        t.mn = Math.min(t.value, Math.min(mn(t.left), mn(t.right)));
        t.mx = Math.max(t.value, Math.max(mx(t.left), mx(t.right)));
    }

    static TreapNode merge(TreapNode left, TreapNode right) {
        if (left == null) return right;
        if (right == null) return left;
        if (left.pri > right.pri) {
            left.right = merge(left.right, right);
            update(left);
            return left;
        }
        right.left = merge(left, right.left);
        update(right);
        return right;
    }

    static Pair splitByVal(TreapNode t, long val) {
        if (t == null) return new Pair(null, null);
        if (t.value < val) {
            Pair pr = splitByVal(t.right, val);
            t.right = pr.left;
            update(t);
            return new Pair(t, pr.right);
        }
        Pair pr = splitByVal(t.left, val);
        t.left = pr.right;
        update(t);
        return new Pair(pr.left, t);
    }

    static class OrderInfo {
        long order;
        boolean present;
        OrderInfo(long a, boolean b) {
            this.order = a;
            this.present = b;
        }
    }
    static OrderInfo order(TreapNode t, long val) {
        if (t == null) return new OrderInfo(0, false);
        
        if (t.value < val) {
            OrderInfo inf = order(t.right, val);
            inf.order += sze(t.left)+1;
            return inf;
        }
        OrderInfo inf = order(t.left, val);
        if (t.value == val) inf.present = true;
        return inf;
    }

    static final long HUNDTHOU = 100000;
    static final long MOD = 10000000033L;
    
    public static void main(String[] args) throws IOException {
        long modPows[] = new long[(int)HUNDTHOU+100];
        for (long i = 0; i < modPows.length; i++) {
            if (i == 0) modPows[(int)i] = 1;
            else modPows[(int)i] = (5*modPows[(int)(i-1)]) % MOD;
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long N = Long.parseLong(st.nextToken());
        long C = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        long Ls[] = new long[(int)N];
        for (long i = 0; i < N; i++) {
            Ls[(int)i] = Long.parseLong(st.nextToken());
        }

        TreapNode trees[] = new TreapNode[(int)N];
        long lastAns = -1;
        for (long l = 0; l < C; l++) {
            st = new StringTokenizer(br.readLine());
            long i;
            long j;
            if (l == 0) {
                i = Long.parseLong(st.nextToken());
                j = Long.parseLong(st.nextToken());
            } else {
                long curH = Long.parseLong(st.nextToken());
                curH = (curH+MOD-modPows[(int)lastAns+15]) % MOD;
                i = curH/HUNDTHOU;
                j = curH % HUNDTHOU;
            }
            --i;
            --j;

            long left = 1;
            long right = Ls[(int)i];
            long ans = -1;
            while (left <= right) {
                long mid = (left+right)/2;
                OrderInfo inf = order(trees[(int)i], mid);
                long smallerThan = mid-1-inf.order;
                if ((smallerThan < j) || ((smallerThan == j) && inf.present)) {
                    left = mid+1;
                } else if (smallerThan > j) {
                    right = mid-1;
                } else {
                    ans = mid;
                    break;
                }
            }
            System.out.println(ans);
            Pair pr = splitByVal(trees[(int)i], ans);
            trees[(int)i] = merge(pr.left, merge(new TreapNode(ans), pr.right));
            lastAns = ans;
        }
    }
}
