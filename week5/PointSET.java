import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;

public class PointSET {

    private final SET<Point2D> points;

    public PointSET() {
        points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> inside = new ArrayList<>();
        for (Point2D p : points) {
            if (rect.contains(p)) inside.add(p);
        }
        return inside;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        Point2D nearest= null;
        double min = Double.MAX_VALUE;
        for (Point2D pi : points) {
            if (pi.distanceTo(p) < min) {
                nearest = pi;
                min = pi.distanceTo(p);
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        System.out.print("test");
    }
}
