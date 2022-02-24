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

struct edge {
    num start, end, val;
};

//From https://github.com/kth-competitive-programming/kactl/blob/main/content/graph/TopoSort.h
vi topoSort(const vector<vi>& gr) {
	vi indeg(sz(gr)), ret;
	for (auto& li : gr) for (int x : li) indeg[x]++;
	queue<int> q; // use priority_queue for lexic. largest ans.
	rep(i,0,sz(gr)) if (indeg[i] == 0) q.push(i);
	while (!q.empty()) {
		int i = q.front(); // top() for priority queue
		ret.push_back(i);
		q.pop();
		for (int x : gr[i])
			if (--indeg[x] == 0) q.push(x);
	}
	return ret;
}

int main() {
	cin.tie(0)->sync_with_stdio(0);
	cin.exceptions(cin.failbit);

    num N;
    cin >> N;
    vector<edge> edges;
    map<string, num> stringToNum;
    auto addString = [&](string s) {
                         auto it = stringToNum.find(s);
                         if (it != stringToNum.end()) return it->second;
                         else {
                             num cursz = sz(stringToNum);
                             stringToNum.insert({s,cursz});
                             return cursz;
                         }
                     };
    REPI(i, N) {
        num K;
        string w;
        cin >> K >> w;
        string suff;
        if (sz(w) > 10) {
            suff = w.substr(sz(w)-10, 10);
        } else {
            suff = w;
        }
        REPI(j, K) {
            string beg;
            cin >> beg;
            num begIdx = addString(beg);
            string end;
            if (sz(suff) < 10) {
                num gap = 10-sz(suff);
                end = beg.substr(sz(beg)-gap, gap)+suff;
            } else {
                end = suff;
            }
            num endIdx = addString(end);
            edges.push_back({begIdx, endIdx, sz(w)});
        }
    }

    vector<vi> adj(sz(stringToNum));
    map<pair<num, num>, num> edgeWeights;
    for (auto curedge : edges) {
        auto it = edgeWeights.find({curedge.start, curedge.end});
        if (it != edgeWeights.end()) {
            it->second = max(it->second, curedge.val);
        } else {
            adj[curedge.start].push_back(curedge.end);
            edgeWeights.insert({{curedge.start, curedge.end}, curedge.val});
        }
    }

    vector<vector<pair<num,num>>> revAdj(sz(adj));
    for (auto pr : edgeWeights) {
        revAdj[pr.first.second].push_back({pr.first.first, pr.second});
    }
    
    vi ordering = topoSort(adj);
    if (sz(ordering) < sz(adj)) {
        cout << "-1\n";
        exit(0);
    }
    vector<num> dp(sz(adj));
    num ans = 0;
    for (auto v : ordering) {
        for (auto pr : revAdj[v]) {
            dp[v] = max(dp[v], dp[pr.first]+pr.second);
        }
        ans = max(ans, dp[v]);
    }
    cout << (10+ans) << "\n";
}
