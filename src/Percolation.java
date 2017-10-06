import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int[][] matrix;
    private final String[] sites;
    private final int n;
    private int openSites;

    public Percolation(int n) {
        this.n = n;

        if (n <= 0) {
            throw new IllegalArgumentException("Invalid n parameter");
        }

        matrix = new int[n][n];
        sites = new String[n * n];
        for (int i = 0; i < n * n; i++) {
            sites[i] = "blocked";
        }

        weightedQuickUnionUF = new WeightedQuickUnionUF((n * n) + 2);
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = index;
                index++;
            }
        }

        for (int i = 0; i < n; i++) {
            weightedQuickUnionUF.union((n * n), matrix[0][i]);
        }

        for (int i = 0; i < n; i++) {
            weightedQuickUnionUF.union((n * n) + 1, matrix[n - 1][i]);
        }

    }

    public void open(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException("row index i out of bounds");

        int center = matrix[--row][--col];

        if (sites[center].equals("blocked")) {
//            if (row == 0) {
//                sites[center] = "full";
//            } else {
                sites[center] = "open";
//            }
            openSites++;

            if (row >= 1) {
                int top = matrix[row - 1][col];
                if (sites[top].equals("open")) {
                    weightedQuickUnionUF.union(center, top);
                }
            }

            if (row < this.n - 1) {
                int bottom = matrix[row + 1][col];
                if (sites[bottom].equals("open")) {
                    weightedQuickUnionUF.union(center, bottom);
                }
            }

            if (col >= 1) {
                int left = matrix[row][col - 1];
                if (sites[left].equals("open")) {
                    weightedQuickUnionUF.union(center, left);
                }
            }

            if (col < this.n - 1) {
                int right = matrix[row][col + 1];
                if (sites[right].equals("open")) {
                    weightedQuickUnionUF.union(center, right);
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException("row index i out of bounds");

        int site = matrix[--row][--col];
        return sites[site].equals("open") || sites[site].equals("full");
    }

    //    public boolean isFull(int row, int col) {
//        if (row <= 0 || row > n || col <= 0 || col > n)
//            throw new IllegalArgumentException("row index i out of bounds");
//        int site = matrix[--row][--col];
//        return sites[site].equals("full");
//    }
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n)
            throw new IllegalArgumentException("row index i out of bounds");
        int site = matrix[--row][--col];
        boolean isFull = false;
        if (sites[site].equals("open")) {
            isFull = weightedQuickUnionUF.connected(site, (n * n));
        }
        return isFull;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return openSites > 0 && weightedQuickUnionUF.connected((n * n), (n * n) + 1);
    }
}
