import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    private void validate(Point that) {
        if (that == null) throw new IllegalArgumentException();
    }

    public double slopeTo(Point that) {
        validate(that);
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
    }
        validate(that);
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else {
            if (this.x < that.x) return -1;
            if (this.x == that.x) return 0;
        }
        return 1;
    }

    public Comparator<Point> slopeOrder() {
        return new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                if (slopeTo(a) < slopeTo(b)) return -1;
                else if (slopeTo(a) == slopeTo(b)) return 0;
                else return 1; 
            }
        };
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        Point a = new Point(391, 263);
        Point b = new Point(130, 263);
        StdOut.println(b.slopeTo(a));
    }
}