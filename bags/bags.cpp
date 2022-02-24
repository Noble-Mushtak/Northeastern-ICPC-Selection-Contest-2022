//replace Ofast with O3 for floating-point accuracy
#pragma GCC optimize("Ofast,unroll-loops")
#pragma GCC target("avx2,popcnt,lzcnt,abm,bmi,bmi2,fma")
#include <bits/stdc++.h>
#include <bits/extc++.h>

using num = int64_t;
using namespace std;
#define rep(i, a, b) for(int i = a; i < (b); ++i)
#define REPI(t, n) for (num t = 0; t < n; ++t)
#define all(x) begin(x), end(x)
#define sz(x) (int)(x).size()
using ll = long long;
using pii = pair<int, int>;
using vi = vector<int>;
#ifdef TESTING
#define DEBUG(...) __VA_ARGS__
#else
#define DEBUG(...)
#endif

//From https://github.com/kth-competitive-programming/kactl/blob/main/content/data-structures/OrderStatisticTree.h
using namespace __gnu_pbds;
template<class T> using Tree = tree<T, null_type, less<T>, rb_tree_tag, tree_order_statistics_node_update>;

int main() {
	cin.tie(0)->sync_with_stdio(0);
	cin.exceptions(cin.failbit);

    const num HUNDTHOU = 1e5;
    const num MOD = 1e10+33;
    vector<num> modPows(HUNDTHOU+100);
    REPI(i, sz(modPows)) {
        if (i == 0) modPows[i] = 1;
        else modPows[i] = (5*modPows[i-1]) % MOD;
    }

    num N, C;
    cin >> N >> C;
    vector<num> Ls(N);
    REPI(i, N) cin >> Ls[i];
    
    vector<Tree<num>> trees(N);

    num lastAns = -1;
    REPI(l, C) {
        num i, j;
        if (l == 0) {
            cin >> i >> j;
        } else {
            num curH;
            cin >> curH;
            curH = (curH + MOD-modPows[lastAns+15]) % MOD;
            i = curH/HUNDTHOU;
            j = curH % HUNDTHOU;
        }
        --i, --j;

        num left = 1, right = Ls[i], ans = -1;
        while (left <= right) {
            num mid = (left+right) >> 1;
            num smallerThan = mid-1-trees[i].order_of_key(mid);
            if ((smallerThan < j) || ((smallerThan == j) && (trees[i].find(mid) != trees[i].end()))) {
                left = mid+1;
            } else if (smallerThan > j) {
                right = mid-1;
            } else {
                ans = mid;
                break;
            }
        }
        assert(ans >= 1);
        cout << ans << "\n";
        trees[i].insert(ans);
        lastAns = ans;
    }
}
