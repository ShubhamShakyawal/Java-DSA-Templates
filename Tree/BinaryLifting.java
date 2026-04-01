public class BinaryLifting {
    static int n;
    static ArrayList<ArrayList<Integer>> adj ;
    static int[][] par, agg;
    static int[] depth, val;

    static void dfs(int node,int prev) {
	    par[node][0] = prev;
	    agg[node][0] = gcd(val[node],val[prev]);
	    depth[node] = depth[prev] + 1;

	    for(int i=1;i<=19;i++) {
	        par[node][i] = par[ par[node][i-1] ][i-1];
            agg[node][i] = gcd(agg[node][i-1], agg[par[node][i-1]][i-1]);
	    }
	    
	    for(int to : adj.get(node)) {
	        if(to == prev) continue;
	        dfs(to,node);
	    }
	}
	
	static int lca(int u, int v) {
        int gc = 0;

        if (depth[u] < depth[v]) {
            int t = u; u = v; v = t;
        }

        // Lift u up to same depth
        int k = depth[u] - depth[v];
        for (int i = 0; i <= 19; i++) {
            if ((k & (1 << i)) != 0) {
                gc = gcd(gc, agg[u][i]);
                u = par[u][i];
            }
        }

        if (u == v) {
            return gcd(gc, val[u]);
        }

        int gcu = 0, gcv = 0;

        for (int i = 19; i >= 0; i--) {
            if (par[u][i] != par[v][i]) {
                gcu = gcd(gcu, agg[u][i]);
                gcv = gcd(gcv, agg[v][i]);
                u = par[u][i];
                v = par[v][i];
            }
        }

        // u and v are children of LCA
        gcu = gcd(gcu, agg[u][0]);
        gcv = gcd(gcv, agg[v][0]);

        int lca = par[u][0];

        return gcd(gc, gcd(gcu, gcd(gcv, val[lca])));
    }
	
    public static void main(String[] args) {
		// gcd of path values between two nodes
        n = sc.nextInt();
        adj = new ArrayList<>();
        par = new int[n + 1][20];
        agg = new int[n + 1][20];
		depth = new int[n + 1];
		val = new int[n + 1];
		
		for(int i=1;i<=n;i++)
            val[i] = sc.nextInt();
		
        for(int i=0;i<=n;i++)
		    adj.add(new ArrayList<>());
		for(int i=0;i<n-1;i++) {
		    int u = sc.nextInt(), v = sc.nextInt();
		    adj.get(u).add(v);
		    adj.get(v).add(u);
		}

		
        dfs(1,0);
    }
}