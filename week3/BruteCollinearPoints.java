import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
<<<<<<< HEAD
    private final Point[] points;
    private int length;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
=======
    private final ArrayList<LineSegment> segmentlist = new ArrayList<>();
    private final Point[] points;

    /**
     * @param points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
>>>>>>> 0c65e3784be9e3d7806085190c196e30b395e305
        this.points = Arrays.copyOf(points, points.length);
        for (int i = 0; i < this.points.length; i++) {
            if (this.points[i] == null) throw new IllegalArgumentException();
        }
        Arrays.sort(this.points);
        for (int i = 0; i < this.points.length - 1; i++) {
            if (this.points[i].compareTo(this.points[i+1]) == 0) throw new IllegalArgumentException();
        }
<<<<<<< HEAD
        this.length = 0;
    }

    public int numberOfSegments() {
        return length;
    }

    public LineSegment[] segments() {
        ArrayList<LineSegment> segmentlist = new ArrayList<>();
        for (int p = 0; p < points.length - 3; p++)
        for (int q = p + 1; q < points.length - 2; q++)
        for (int r = q + 1; r < points.length - 1; r++)
        for (int s = r + 1; s < points.length; s++){
            if ((points[p].slopeTo(points[q]) == points[q].slopeTo(points[r]) 
            && points[q].slopeTo(points[r]) == points[r].slopeTo(points[s])))
            segmentlist.add(new LineSegment(points[p], points[s]));
        }
        length = segmentlist.size();
        LineSegment[] seg = new LineSegment[length];
=======
        findSegments();
    }

    public int numberOfSegments() {
        return segmentlist.size();
    }

    private void findSegments() {
        for (int p = 0; p < points.length - 3; p++)
        for (int q = p + 1; q < points.length - 2; q++)
        for (int r = q + 1; r < points.length - 1; r++)
        for (int s = r + 1; s < points.length; s++) {
            if ((points[p].slopeTo(points[q]) == points[q].slopeTo(points[r]) 
            && points[q].slopeTo(points[r]) == points[r].slopeTo(points[s]))) {
                segmentlist.add(new LineSegment(points[p], points[s]));
            }
        }
    }

    public LineSegment[] segments() {
        LineSegment[] seg = new LineSegment[segmentlist.size()];
>>>>>>> 0c65e3784be9e3d7806085190c196e30b395e305
        return segmentlist.toArray(seg);
    }
}