
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double [] trialsThreshold;
    private final int trials;
    private static final double CONFIDENCE_95 = 1.96;

    public PercolationStats(int n, int trials) {
        this.trials = trials;
        trialsThreshold = new double[trials];
        for(int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            int row = StdRandom.uniform(n)+1;
            int col = StdRandom.uniform(n)+1;

            while(!percolation.percolates()){
                percolation.open(row,col);
                row = StdRandom.uniform(n)+1;
                col = StdRandom.uniform(n)+1;
            }
            trialsThreshold[i] = percolation.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    public double mean() {
        return StdStats.mean(trialsThreshold);
    }

    public double stddev() {
       return StdStats.stddev(trialsThreshold);
    }

    public double confidenceLo() {
        return  mean() - ((CONFIDENCE_95 *stddev())/Math.sqrt(trials));
    }

    public double confidenceHi() {
        return  mean() + ((CONFIDENCE_95 *stddev())/Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}