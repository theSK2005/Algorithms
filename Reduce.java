class Reduce {
    public int ParReduce (int[] A) {
        int n = A.length - 1;
        ParReduceThread[] myThreads = new ParReduceThread[n + 1];
        for (int x = 1; x <= n; x++) {
            myThreads[x] = new ParReduceThread(A);
        }
        for (int h = 1; h < (Math.log(n) / Math.log(2)); h++) {
            for (int i = 1; i <= n; i++) {
                myThreads[i].setH(h);
                myThreads[i].setI(i);
                myThreads[i].start();
            }
        }
        for (int x = 1; x <= n; x++) {
            try {
                myThreads[x].join();
            }
            catch (Exception e) {
                
            }
        }
        return A[1];
    }

    public int AlgLLPReduce (int[] A, int j) {
        int n = A.length - 1;
        int[] G = new int[n - 1];
        G[j] = Integer.MIN_VALUE;
        if (j >= 1 && j <= n / 2) {
            if (G[j] < G[2j] + G[2j + 1]) {
                G[j] = G[2j] + G[2j + 1];
            }
        }
        if (j >= n / 2 && j > n) {
            if (G[j] < A[2j - n + 1] + A[2j - n + 2]) {
                G[j] = A[2j - n + 1] + A[2j - n + 2]
            }
        }
    }
}