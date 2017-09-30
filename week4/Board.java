import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class Board {
    private final int[][] board;
    private final int dim;
    private final int zeroi;
    private final int zeroj;

    public Board(int[][] blocks) {
        int zeroi = 0;
        int zeroj = 0;
        board = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks.length; j++) {
                board[i][j] = blocks[i][j];
                if (blocks[i][j] == 0) {
                    zeroi = i;
                    zeroj = j;
                }
            }
        }
        this.zeroi = zeroi;
        this.zeroj = zeroj;
        dim = blocks.length;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> children = new ArrayList<>();
        if (zeroi != 0) children.add(moveup());
        if (zeroj != 0) children.add(moveleft());
        if (zeroi + 1 != dim) children.add(movedown());
        if (zeroj + 1 != dim) children.add(moveright());
        return children;
    }

    private int[][] clone(int[][] blocks) {
        int[][] copy = new int[blocks.length][blocks.length];
        for (int i = 0; i < blocks.length; i++)
            for (int j = 0; j < blocks.length; j++)
                copy[i][j] = blocks[i][j];
        return copy;
    }

    private Board moveup() {
        assert (zeroi != 0);
        int[][] copy = clone(board);
        swap(copy, zeroi, zeroj, zeroi - 1, zeroj);
        return new Board(copy);
    }

    private Board movedown() {
        assert (zeroi + 1 != dim);
        int[][] copy = clone(board);
        swap(copy, zeroi, zeroj, zeroi + 1, zeroj);
        return new Board(copy);
    }

    private Board moveleft() {
        assert (zeroj != 0);
        int[][] copy = clone(board);
        swap(copy, zeroi, zeroj, zeroi, zeroj - 1);
        return new Board(copy);
    }

    private Board moveright() {
        assert (zeroj + 1 != dim);
        int[][] copy = clone(board);
        swap(copy, zeroi, zeroj, zeroi, zeroj + 1);
        return new Board(copy);
    }

    public int dimension() {
        return board.length;
    }

    public int hamming() {
        int hd = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (board[i][j] != 0 && board[i][j] != (i * dim + j + 1)) hd++;
        return hd;
    }

    private int abs(int x) {
        return x >= 0 ? x: (-1 * x);
    }

    private int row(int index) {
        return ((index + dim * dim - 1 ) % (dim * dim)) / dim;
    }

    private int col(int index) {
        return ((index +  dim * dim - 1) % (dim * dim)) % dim;
    }

    public int manhattan() {
        int md = 0;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == 0) continue;
                md += abs(i - row(board[i][j]));
                md += abs(j - col(board[i][j]));
            }
        }
        return md;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        int[][] copy = clone(board);
        assert dim >= 2;
        int i = 0;
        int j = 0;
        if (copy[i][j] != 0) {
            if (copy[i][j + 1] != 0) swap(copy, i, j, i, j + 1);
            else swap(copy, i, j, i + 1, j); 
        }
        else swap(copy, i, j + 1, i + 1, j);
        return new Board(copy);
    }

    private void swap(int[][] matrix, int row1, int col1, int row2, int col2) {
        int temp = matrix[row1][col1];
        matrix[row1][col1] = matrix[row2][col2];
        matrix[row2][col2] = temp;
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension()) return false;
        return equalElements(that);
    }

    private boolean equalElements(Board that) {
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (board[i][j] != that.board[i][j]) return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dim);
        s.append("\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                s.append(" ");
                s.append(board[i][j]);
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In("./8puzzle/" + args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i= 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board b = new Board(blocks);
        StdOut.println(b.hamming());
        StdOut.println(b.manhattan());
        StdOut.println(b.dimension());
        StdOut.println(b.toString());
        StdOut.println(b.isGoal());
        StdOut.println(b.twin().toString());
        for (Board board: b.neighbors()) {
            StdOut.println(board.toString());
        }
    }
}