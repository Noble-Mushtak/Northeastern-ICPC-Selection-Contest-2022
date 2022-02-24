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

num calcDist(pair<num, num> p1, pair<num, num> p2) {
    return (p1.first-p2.first)*(p1.first-p2.first)+(p1.second-p2.second)*(p1.second-p2.second);
}

//From https://github.com/kth-competitive-programming/kactl/blob/main/content/graph/SCC.h
vi val, comp, z, cont;
int Time, ncomps;
template<class G, class F> int dfs(int j, G& g, F& f) {
	int low = val[j] = ++Time, x; z.push_back(j);
	for (auto e : g[j]) if (comp[e] < 0)
		low = min(low, val[e] ?: dfs(e,g,f));

	if (low == val[j]) {
		do {
			x = z.back(); z.pop_back();
			comp[x] = ncomps;
			cont.push_back(x);
		} while (x != j);
		f(cont); cont.clear();
		ncomps++;
	}
	return val[j] = low;
}
template<class G, class F> void scc(G& g, F f) {
	int n = sz(g);
	val.assign(n, 0); comp.assign(n, -1);
	Time = ncomps = 0;
	rep(i,0,n) if (comp[i] < 0) dfs(i, g, f);
}

int main() {
	cin.tie(0)->sync_with_stdio(0);
	cin.exceptions(cin.failbit);

    num N, K;
    cin >> N >> K;
    vector<pair<num, num>> points(N);
    REPI(i, N) cin >> points[i].first >> points[i].second;

    vector<vector<num>> adj(N);
    REPI(i, N) {
        vector<pair<num, num>> dsts(N);
        REPI(j, N) {
            dsts[j] = {-calcDist(points[i], points[j]), j};
        }
        priority_queue<pair<num, num>> dstsQ(dsts.begin(), dsts.end());
        vector<pair<num, num>> kBest;
        kBest.reserve(K);
        REPI(j, K) {
            kBest.push_back(dstsQ.top());
            dstsQ.pop();
        }
        num mi = -dstsQ.top().first;
        for (auto pr : kBest) {
            if (-pr.first < mi) {
                adj[i].push_back(pr.second);
            }
        }
    }

    scc(adj, [&](vector<int> &scc) {});
    if (ncomps == 1) {
        cout << "0\n";
        exit(0);
    }
    
    vector<bool> inDegZero(ncomps, true);
    vector<bool> outDegZero(ncomps, true);
    REPI(i, N) {
        for (num j : adj[i]) {
            if (comp[i] != comp[j]) {
                outDegZero[comp[i]] = false;
                inDegZero[comp[j]] = false;
            }
        }
    }
    num inDegsZero = 0;
    REPI(i, ncomps) inDegsZero += inDegZero[i];
    num outDegsZero = 0;
    REPI(i, ncomps) outDegsZero += outDegZero[i];
    assert(inDegsZero > 0);
    assert(outDegsZero > 0);
    cout << max(inDegsZero, outDegsZero) << "\n";
}
