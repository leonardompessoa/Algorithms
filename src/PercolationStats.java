
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double [] trialsThreshold;
    private int trials;
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
        double deviation = 0.0;
        for (int i = 0; i < trialsThreshold.length; i++) {
            deviation+= Math.pow((trialsThreshold[i] - mean()),2);
        }
        if(trials > 1) {
            return Math.sqrt(deviation /(trials - 1));
        } else {
            return 0;
        }
    }

    public double confidenceLo() {
        return  mean() - ((1.96 *stddev())/Math.sqrt(trials));
    }

    public double confidenceHi() {
        return  mean() + ((1.96 *stddev())/Math.sqrt(trials));
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.valueOf(args[0]), Integer.valueOf(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
//        mean                    = 0.5929934999999997
//        stddev                  = 0.00876990421552567
//        95% confidence interval = [0.5912745987737567, 0.5947124012262428]

    }        // test client (described below)
}