class Prefix {
    public int[] AlgSeqPrefix (int[] A) {
        int n = A.length - 1;
        int[] C = new int[n];
        for (int i = 0; i < n; i++) {
            C[i] = A[i];
        }
        for (int i = 1; i < n; i++) {
            C[i] = C[i - 1] + A[i];
        }
    }
    public int[] AlgParPrefix (int[] A) {
        int n = A.length - 1;
        int[] C = new int[n];
        for (int i = 0; i < n; i++) {
            C[i] = A[i];
        }
        for (int d = 1; d < n; d *= 2) {
            for (int i = 1; i < n - 1; i++) {
                if (i - d >= 0) {
                    C[i] = C[i] + C[i - d];
                }
            }
        }
    }

    public int[] AlgParPrefixGPU (int[] A, int i) {
         int n = A.length - 1;
        int val = 0;
        int[] C = new int[n];
        for (int d = 1; d < n; d *= 2) {
            if (i >= d) {
                val = C[i - d];
            }
            //barrier
            if (i >= d) {
                C[i] = C[i] + val;
            }
            //barrier
        }
        return C;
    }

    public int[] AlgRecurParPrefix (int[] A) {
        int n = A.length - 1;
        int[] C = new int[n];
        int[] B = new int[n / 2];
        int[] D = new int[n / 2];
        if (n == 1) {
            C[1] = A[1];
        }
        else {
            for (int i = 0; i < n / 2; i++) {
                B[i] = A[2 * i - 1] + A[2 * i];
            }
            int[] D = new int [n / 2];
            D = AlgRecurParPrefix(B);
            for (int i = 0; i < n; i++) {
                if (i == 1) {
                    C[1] = A[1];
                }
                else if (i % 2 == 0) {
                    C[i] = D[i / 2];
                }
                else if (i % 2 == 1) {
                    C[i] = D[i / 2] + A[i];
                }
            }
        }
    }

    public int[] AlgLLPScan (int[] A, j) {
        int n = A.length - 1;
        int[] G = new int[2 * n];
        int[] S = new int[n];
        G[j] = Integer.MIN_VALUE;
        if (j == 1 && G[j] < 0) {
            G[j] = 0;
        }
        if (j % 2 == 0 && G[j] < G[j / 2]) {
            G[j] = G[j / 2];
        }
        if (j % 2 == 1) {
            if (G[j] < S[j - 1] + G[j / 2] && j < n) {
                G[j] = S[j - 1] + G[j / 2];
            }
            if (G[j] < A[j - n] + G[j / 2] && j > n) {
                G[j] = A[j - n] + G[j / 2];
            }
        }
    }
}