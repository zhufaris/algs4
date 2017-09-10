import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private final int size;
    private final int trials;
    private final double[] threadRatio;
    
    public PercolationStats (int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        this.size = n;
        this.trials = trials;
        this.threadRatio = new double[trials];
        for (int i = 0; i < trials; i++ ) {
            this.threadRatio[i] = percolateThreadRatio();
        }
    }

    private int getRandomIndex() {
        return StdRandom.uniform(1, size + 1);
    }

    private double percolateThreadRatio() {
        Percolation p = new Percolation(size);
        int i,j;
        while (!p.percolates()) {
            i = getRandomIndex();
            j = getRandomIndex();
            if (p.isOpen(i,j)) continue;
            p.open(i,j);
        }
        return 1.0*(p.numberOfOpenSites())/(size * size);
    }

    public double mean() {
        return StdStats.mean(threadRatio);
    }

    public double stddev() {
        return StdStats.stddev(threadRatio);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials); 
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(size, trials);
        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}