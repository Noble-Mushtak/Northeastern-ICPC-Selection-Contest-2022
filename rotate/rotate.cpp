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

int main() {
	cin.tie(0)->sync_with_stdio(0);
	cin.exceptions(cin.failbit);

    num N, K;
    cin >> N >> K;
    vector<num> arr(N);
    vector<num> sums(N, 0);
    REPI(i, N) {
        cin >> arr[i];
    }

    num ans = 0;
    REPI(i, N) {
        if (i < K) {
            REPI(j, N/K) sums[i] += arr[(i+K*j) % N];
        } else {
            sums[i] = sums[i-K]-arr[i-K]+arr[(i-K+K*(N/K)) % N];
        }
        if (i == 0) ans = sums[i];
        else ans = max(ans, sums[i]);
    }

    cout << ans << "\n";
}
