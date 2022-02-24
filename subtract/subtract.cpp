//replace Ofast with O3 for floating-point accuracy
#pragma GCC optimize("Ofast,unroll-loops")
#pragma GCC target("avx2,popcnt,lzcnt,abm,bmi,bmi2,fma")
#include <bits/stdc++.h>

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

bool isPerfectSquare(num x) {
    num sr = sqrt(x);
    return (sr*sr == x);
}

int main() {
	cin.tie(0)->sync_with_stdio(0);
	cin.exceptions(cin.failbit);

    const num MAXN = 3e5+10;
    vector<bool> isPrime(MAXN, true);
    isPrime[0] = isPrime[1] = false;
    vector<num> goodPrimes;
    for (num p = 2; p < MAXN; ++p) {
        if (isPrime[p]) {
            if (isPerfectSquare(p-1)) goodPrimes.push_back(p);
            for (num j = 2*p; j < MAXN; j += p) isPrime[j] = false;
        }
    }

    vector<bool> dp(MAXN, false);
    REPI(g, MAXN) {
        for (auto p : goodPrimes) {
            if (p > g) break;
            if (!dp[g-p]) {
                dp[g] = true;
                break;
            }
        }
    }

    num T;
    cin >> T;
    while (T--) {
        num N;
        cin >> N;
        cout << (2-dp[N]) << "\n";
    }
}
