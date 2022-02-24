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

//From https://github.com/kth-competitive-programming/kactl/blob/main/content/number-theory/euclid.h
ll euclid(ll a, ll b, ll &x, ll &y) {
	if (!b) return x = 1, y = 0, a;
	ll d = euclid(b, a % b, y, x);
	return y -= a/b * x, d;
}
const ll MOD = 1e9+7;
//Based on https://github.com/kth-competitive-programming/kactl/blob/main/content/number-theory/ModularArithmetic.h
ll invert(ll z) {
    ll x, y, g = euclid(z, MOD, x, y);
	assert(g == 1);
    return (x + MOD) % MOD;
}

int main() {
	cin.tie(0)->sync_with_stdio(0);
	cin.exceptions(cin.failbit);

    num N, Q;
    cin >> N >> Q;
    vector<num> arr(N);
    vector<num> prod(N+1, 1);
    vector<num> numZeroes(N+1);
    REPI(i, N) {
        cin >> arr[i];
        if (arr[i] == 0) {
            prod[i+1] = 1;
            numZeroes[i+1] = numZeroes[i]+1;
        } else {
            prod[i+1] = (prod[i]*arr[i]) % MOD;
            numZeroes[i+1] = numZeroes[i];
        }
    }
    REPI(i, Q) {
        num l, r;
        cin >> l >> r;
        if (numZeroes[l-1] == numZeroes[r]) {
            cout << ((prod[r]*invert(prod[l-1])) % MOD) << "\n";
        } else {
            cout << "0\n";
        }
    }
}
