import java.util.ArrayList;
class AlgLLP {
    public int[] getLeastFeasible (int[] T, boolean B, int n) {
        int[] G = new int[n];
        ArrayList<Integer> jList = new ArrayList<Integer>();
        for (int j = 0; j <= T.length; j++) {
            if (prefixSumForbidden(G, j, B)) {
                jList.add(j);
            }
        }
        for (int j : jList) {
            if (prefixSumAlpha(G, j, B) > T[j]) {
                return null;
            }
            else {
                G[j] = prefixSumAlpha(G, j, B);
            }
        }
        return G;
    }
    
    public boolean prefixSumForbidden (int[] G, int j, boolean B) {
        return G[j] < G[j - 1] + A[j - 1];
    }
    public int prefixSumAlpha (int[] G, int j, boolean B) {
        return G[j - 1] + A[j - 1];
    }
}