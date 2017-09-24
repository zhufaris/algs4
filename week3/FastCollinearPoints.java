import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private Point[] points;
    private Point[] copy;
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        checkNull(points);
        checkRepeating(points);
        this.copy = Arrays.copyOf(this.points, this.points.length);
        getAllSegment();
    }

    private void checkNull(Point[] points) {
        for (Point p: points) {
            if (p == null) throw new IllegalArgumentException();
        }
    }

    private void checkRepeating(Point[] points) {
        this.points = Arrays.copyOf(points, points.length);
        Arrays.sort(this.points);
        for (int i = 0; i < this.points.length - 1; i++) {
            if (this.points[i].compareTo(this.points[i + 1]) == 0) throw new IllegalArgumentException(); 
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        LineSegment[] s = new LineSegment[segments.size()];
        return segments.toArray(s);
    }
 
    private void getSegment(Point p) {
        copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy, p.slopeOrder());
        double slope;
        int i = 1;
        while (i < copy.length) {
            slope = p.slopeTo(copy[i]);
            int j = i + 1;  
            while (j < copy.length && slope == p.slopeTo(copy[j])) j++;
            if (j - i >= 3 && p.compareTo(copy[i]) < 0) {
                segments.add(new LineSegment(p, copy[j - 1]));
            }
            i = j;
        }
    }


    private void getAllSegment() {
        for (Point p: points) {
            getSegment(p);
        }
    }

    public static void main(String[] args) {
        In in = new In("collinear\\input6.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment: collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}