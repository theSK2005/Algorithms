class ParReduceThread extends Thread {
    public int[] A;
    private int i;
    private int h;
    public ParReduceThread (int[] Arr) {
        int[] A = new int [Arr.length];
        for (int x = 0; x < Arr.length; x++) {
            A[x] = Arr[x];
        }
    }

    @Override
    public void run () {
        int n = A.length - 1;
        if (i <= n / Math.pow(2, h)) {
            A[i] = A[2 * i - 1] + A[2 * i];
        }
    }

    public void setH (int h) {
        this.h = h;
    }

    public void setI (int i) {
        this.i = i;
    }
}