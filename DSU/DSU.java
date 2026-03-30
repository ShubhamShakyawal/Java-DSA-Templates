class DSU {
    int[] parent;
    int[] size;

    public int find(int x ) {
        if(parent[x] == x) return x;
		// path compression
        return parent[x] = find(parent[x]);
    }

    public void union(int i,int j) {
        int rootI = find(i);
        int rootJ = find(j);

		// compare only if roots are different
        if(rootI != rootJ) {
            if(size[rootI] >= size[rootJ]) {
                parent[rootJ] = parent[rootI];
                size[rootI] += size[rootJ];
            } else {
                parent[rootI] = rootJ;
                size[rootJ] += size[rootI];
            }
        }
    }
}
