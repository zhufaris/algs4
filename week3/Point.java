import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
<<<<<<< HEAD
=======
import edu.princeton.cs.algs4.StdOut;
>>>>>>> 0c65e3784be9e3d7806085190c196e30b395e305

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
<<<<<<< HEAD
    public void draw(){
=======
    public void draw() {
>>>>>>> 0c65e3784be9e3d7806085190c196e30b395e305
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
<<<<<<< HEAD
        return 1.0 *(that.y - this.y)/(that.x -this.x);
=======
        return absolute(1.0 * (that.y - this.y)/(that.x -this.x));
    }

    private double absolute(double x) {
        if ( 1/ x > 0) return x;
        else return -1 * x;
>>>>>>> 0c65e3784be9e3d7806085190c196e30b395e305
    }

    @Override
    public int compareTo(Point that) {
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
<<<<<<< HEAD
                else if(slopeTo(a) == slopeTo(b)) return 0;
=======
                else if (slopeTo(a) == slopeTo(b)) return 0;
>>>>>>> 0c65e3784be9e3d7806085190c196e30b395e305
                else return 1; 
            }
        };
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
<<<<<<< HEAD
=======

    public static void main(String[] args) {
        Point a = new Point(391, 263);
        Point b = new Point(130, 263);
        StdOut.println(b.slopeTo(a));
    }
>>>>>>> 0c65e3784be9e3d7806085190c196e30b395e305
}