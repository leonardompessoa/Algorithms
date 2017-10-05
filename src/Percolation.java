import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final Site[][] matrix;
    private final int n;
    private int openSites;

    private class Site {

        private final int name;
        private String status;

        public Site(int name) {
            this.name = name;
            this.status = "blocked";
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getName() {
            return name;
        }
    }

    public Percolation(int n) {
        this.n = n;

        if (n <= 0) {
            throw new IllegalArgumentException("Invalid n parameter");
        }

        matrix = new Site[n][n];

        weightedQuickUnionUF = new WeightedQuickUnionUF((n * n) + 2);
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = new Site(index);
                index++;
            }
        }

        for (int i = 0; i < n; i++) {
            weightedQuickUnionUF.union((n * n), matrix[0][i].getName());
        }

        for (int i = 0; i < n; i++) {
            weightedQuickUnionUF.union((n * n) + 1, matrix[n - 1][i].getName());
        }

    }

    public void open(int row, int col) {
        row--;
        col--;
        Site site = matrix[row][col];
        if (!site.getStatus().equals("open")) {
            site.setStatus("open");
            openSites++;
            if (row > 1) {
                Site top = matrix[row - 1][col];
                if (top.getStatus().equals("open")) {
                    weightedQuickUnionUF.union(site.getName(), top.getName());
                }
            }

            if (row < this.n - 1) {
                Site bottom = matrix[row + 1][col];
                if (bottom.getStatus().equals("open")) {
                    weightedQuickUnionUF.union(site.getName(), bottom.getName());
                }
            }

            if (col > 1) {
                Site left = matrix[row][col - 1];
                if (left.getStatus().equals("open")) {
                    weightedQuickUnionUF.union(site.getName(), left.getName());
                }
            }

            if (col < this.n - 1) {
                Site right = matrix[row][col + 1];
                if (right.getStatus().equals("open")) {
                    weightedQuickUnionUF.union(site.getName(), right.getName());
                }
            }
        }


    }

    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0 || row > n || col > n) {
            throw new IllegalArgumentException("Invalid parameters");
        }
        Site site = matrix[row - 1][col - 1];
        return site.getStatus().equals("open");
    }

    public boolean isFull(int row, int col) {
//        if(row <= 0 || col <= 0 || row > n || col > n) {
//            throw new IllegalArgumentException("Invalid parameters");
//        }

        Site site = matrix[row - 1][col - 1];
        boolean isFull = false;
        if (site.getStatus().equals("open")) {
            isFull = weightedQuickUnionUF.connected(site.getName(), (n * n));
        }
        return isFull;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected((n * n), (n * n) + 1);
    }
}

