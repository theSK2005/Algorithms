import java.util.ArrayList;
import java.util.Arrays;
class StableMarriageProblem {
    public int[] GS (int[][] mpref, int[][] rank, int n) {
        ArrayList<Integer> mList = new ArrayList<Integer>();
        for (int i = 1; i <= n; i++) {
            mList.add(i);
        }
        int[] partner = new int[n + 1];
        int[] G = new int[n + 1];
//        for (int x = 0; x < n; x++) {
//            G[x] = -1;
//            partner[x] = -1;
//        }
        while (!mList.isEmpty()) {
            System.out.println(Arrays.toString(partner));
            int i = mList.remove(0);
            G[i] += 1;
            int z = mpref[i][G[i]];
            if (partner[z] == 0) {
                partner[z] = i;
            }
            else if (rank[z][i] < rank[z][partner[z]]) {
                mList.add(partner[z]);
                partner[z] = i;
            }
            else {
                mList.add(i);
            }
        }
        return partner;
    }

    public int[] algAlpha (int[][]mpref, int[][]rank, int[] I, int n, int m) {
        int[] G = I;
        ArrayList<Integer> mList = new ArrayList<Integer>();
        int[] partner = new int [n + 1];
        int[] mostPref = new int [n + 1];
        int numw = 0;
        for (int i = 1; i <= m; i++) {
            for (int k = 1; k <= G[i]; k++) {
                int q = mpref[i][k];
                if (mostPref[q] == 0) {
                    mostPref[q] = i;
                    numw += 1;
                }
                else if (rank[q][i] < rank[q][mostPref[q]]) {
                    mostPref[q] = i;
                }
                if (numw > m) {
                    return null;
                }
            }
        }
        for (int i = 1; i <= m; i++) {
            int q = mpref[i][G[i]];
            if (mostPref[q] != i) {
                mList.add(i);
            }
            else {
                partner[q] = i;
            }
        }
        while (!mList.isEmpty()) {
            int i = mList.remove(0);
            boolean found = false;
            while (!found && (G[i] < m)) {
                G[i] += 1;
                int q = mpref[i][G[i]];
                if (mostPref[q] == 0) {
                    numw += 1;
                    if (numw > m) {
                        return null;
                    }
                    found = true;
                    mostPref[q] = i;
                }
                else if (rank[q][i] < rank[q][mostPref[q]]) {
                    found = true;
                    mostPref[q] = i;
                    if (partner[q] != 0) {
                        mList.add(partner[q]);
                        partner[q] = i;
                    }
                }
            }
            if (!found) {
                return null;
            }
        }
        return G;
    }

    public boolean forbidden (int[] G, int[][] mpref, int[][] rank, int j) {
        int z = mpref[j][G[j]];
        for (int i = 0; i <= mpref.length; i++) {
            for (int k = 0; k <= rank.length; k++) {
                if (k <= G[i]) {
                    if ((z == mpref[i][k]) && (rank[z][i] < rank[z][j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void LLPManOptimal (int[] G, int[][] mpref, int[][]rank, int j) {
        
    }
}