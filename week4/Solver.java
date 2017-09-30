import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;

public class Solver {
    private int move = 0;
    private int flag = 0;
    private BoardNode target = null;

    private class BoardNode implements Comparable<BoardNode> {
        public Board board;
        public int moves;
        public BoardNode previous;
        public int priority;

        public BoardNode(Board board, int moves, BoardNode previous) {
            this.board = board;
            this.moves = moves;
            this.priority = this.board.manhattan() + moves;
            this.previous = previous;
        }

        @Override
        public int compareTo(BoardNode o) {
            if (this.priority > o.priority) return 1;
            else if (this.priority < o.priority) return -1;
            else return 0;
        }
    }

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        Comparator<BoardNode> comparator = new Comparator<BoardNode>() {
            @Override
            public int compare(BoardNode o1, BoardNode o2) {
                return o1.compareTo(o2);
            }
        };

        MinPQ<BoardNode> pq1 = new MinPQ<>(comparator);
        MinPQ<BoardNode> pq2 = new MinPQ<>(comparator);

        pq1.insert(new BoardNode(initial, 0, null));
        pq2.insert(new BoardNode(initial.twin(), 0, null));

        BoardNode current1, current2;

        while (!pq1.isEmpty() && !pq2.isEmpty()) {
            //first child
            current1 = pq1.delMin();

            if (current1.board.isGoal()) {
                this.flag = 1;
                this.move = current1.moves;
                this.target = current1;
                break;
            }
            else {
                for (Board b : current1.board.neighbors()) {
                    if (current1.previous != null && current1.previous.board.equals(b)) continue;
                    pq1.insert(new BoardNode(b, (current1.moves + 1), current1));
                }
            }


            //twin
            current2 = pq2.delMin();

            if (current2.board.isGoal()) {
                this.flag = 2;
                this.move = current2.moves;
                this.target = current2;
                break;
            }

            else {
                for (Board b : current2.board.neighbors()) {
                    if (current2.previous != null && current2.previous.board.equals(b)) continue;
                    pq2.insert(new BoardNode(b, (current2.moves + 1), current2));
                }
            }
        }
    }

    public boolean isSolvable() {
        return this.flag == 1;
    }

    public int moves() {
        if (isSolvable()) return this.move;
        else return -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> stack = new Stack<>();
        BoardNode current = this.target;
        while (current != null) {
            stack.push(current.board);
            current = current.previous;
        }
        ArrayList<Board> solution = new ArrayList<>();
        while (!stack.isEmpty()) {
            solution.add(stack.pop());
        }
        return solution;
    }

    public static void main(String[] args) {
        In in = new In("./8puzzle/" + args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        Solver solver = new Solver(initial);

        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board: solver.solution())
                StdOut.println(board);
        }
    }
}