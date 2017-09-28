import edu.princeton.cs.algs4.StdOut;

public class Board {
    private int[][] board;
    private final int dim;

    public Board(int[][] blocks) {
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < block.length; j++)
                board[i][j] = blocks[i][j];
        dim = blocks.length;
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        int hd = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (board[i][j] ! = (dim * i + j)) hd++;
        return hd;
    }

    private int abs(int x) {
        return x >= 0 ? x: (-1 * x);
    }

    private int row(int index) {
        return index / dim;
    }

    private int col(int index) {
        return index % dim;
    }

    public int manhattan() {
        int md = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                md += abs(i - row(board[i][j]));
                md += abs(j - col(board[i][j]));
            }
        }
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {

    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        return
    }

    private boolean equalElements(Board that) {
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (board[i][j] != that[i][j]) return false;
        return true;
    }

    public Iterable<Board> neighbors() {
        
    }

    public String toString() {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                StdOut.print(board[i][j]);
                StdOut.print(" ");
            }
            StdOut.println();
        }
    }


}