import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private boolean[][] grid;
    private final int size;
    private int noOfOpenSites;
    private final WeightedQuickUnionUF qu;
    private final int start;
    private final int end;

    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException();
        size = n;
        grid = new boolean[size+1][size+1];
        noOfOpenSites = 0;
        start = 0;
        end = size * size + 1;
        int i, j;
        qu = new WeightedQuickUnionUF(size*size + 2);
    }
    private int getIndex(int row, int col) {
        return row * (size - 1) + col;
    }
    
    public void open(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) throw new IllegalArgumentException();
        if (grid[row][col]) return;
        else {
            grid[row][col] = true;
            noOfOpenSites++;
        }
        if (row >1 && isOpen(row - 1, col)) {
            qu.union(getIndex(row, col), getIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            qu.union(getIndex(row, col), getIndex(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            qu.union(getIndex(row, col), getIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            qu.union(getIndex(row, col), getIndex(row, col + 1));
        }
        if (row == 1) {
            qu.union(getIndex(row, col), start);
        }
        if (row == size) {
            qu.union(getIndex(row, col), end);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) throw new IllegalArgumentException();
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) throw new IllegalArgumentException();
        if (!isOpen(row, col)) return false;
        if (row == 1) return true;
        return qu.connected(getIndex(row, col), start);
    }

    public int numberOfOpenSites() {
        return noOfOpenSites;
    }

    public boolean percolates() {
        return qu.connected(start, end);
    }

    public static void main (String[] args) {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        p.open(2, 2);
        p.open(3, 1);
        StdOut.println(p.percolates());
        StdOut.println(p.numberOfOpenSites());
        StdOut.println(p.isFull(2, 2));
    }
}