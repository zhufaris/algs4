import java.util.Arrays;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class BruteCollinearPoints {
    private final Point[] points;
    private int count;
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        this.points = Arrays.copyOf(points, points.length);
        for (int i = 0; i < points.length - 1; i++) {
            if (this.points[i] == null || this.points[i].compareTo(this.points[i+1]) == 0) throw new IllegalArgumentException();
        }
        this.count = 0;
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        LineSegment[] lls = new LineSegment[points.length];
        for (int p = 0; p < points.length - 3; p++)
        for (int q = p + 1; q < points.length - 2; q++)
        for (int r = q + 1; r < points.length - 1; r++)
        for (int s = r + 1; s < points.length; s++){
            if ((points[p].slopeTo(points[q]) == points[q].slopeTo(points[r]) 
            && points[q].slopeTo(points[r]) == points[r].slopeTo(points[s])))
            lls[count] = new LineSegment(points[p], points[s]);
            count++;
        }
        return Arrays.copyOf(lls, count);
    }
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++ ) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        for (LineSegment segment: bcp.segments()){
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}