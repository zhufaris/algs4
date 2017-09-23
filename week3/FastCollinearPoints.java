import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class FastCollinearPoints {
    private Point[] points;
    private final ArrayList<LineSegment> segments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        checkNull(points);
        checkRepeating(points);

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
 
    private void getSegment(Point p, int index) {
        Point[] array = Arrays.copyOfRange(points, index + 1, points.length);
        Arrays.sort(array, p.slopeOrder());
        double slope;
        int i = 0;
        while (i < array.length) {
            slope = p.slopeTo(array[i]);
            int j = i + 1;  
            while ( j < array.length && slope == p.slopeTo(array[j])) j++;
            if (j - i >= 3) {
                segments.add(new LineSegment(p, array[j - 1]));
            }
            i = j;
        }
    }

    private void getAllSegment() {
        for (int i = 0; i < points.length - 3; i++) {
            getSegment(points[i], i);
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