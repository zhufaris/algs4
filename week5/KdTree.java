import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

enum Direction {
    X, Y
}

public class KdTree{
    private KdNode root;
    private int count;
    private double min;

    private class KdNode implements Comparable<KdNode>{
        Point2D point;
        KdNode left;
        KdNode right;
        KdNode parent;
        Direction direction;

        public KdNode(Point2D point, Direction direction) {
            this.point = point;
            this.direction = direction;
            this.left = null;
            this.right = null;
            this.parent = null;
        }

        @Override
        public int compareTo(KdNode that) {
            if (this.direction == Direction.X) {
                return Double.compare(this.point.x(), that.point.x());
            }
            else {
                return Double.compare(this.point.y(), that.point.y());
            }
        }
    }

    public KdTree() {
        root = null;
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    private Direction opposite(Direction p) {
        if (p == Direction.X) return Direction.Y;
        else return Direction.X;
    }

    public void insert(Point2D p) {
        root = insert(root, p, Direction.X);
    }

    private KdNode insert(KdNode kd, Point2D p, Direction d) {
        if (p == null) throw new IllegalArgumentException();
        if (kd == null) {
            count++;
            return new KdNode(p, d);
        }
        if (kd.point.compareTo(p) == 0) return kd;
        KdNode current = kd;
        KdNode newnode = new KdNode(p, d);
        if (current.compareTo(newnode) > 0) {
            KdNode k = insert(current.left, p, opposite(d));
            current.left = k;
            k.parent = current;

        }
        else {
            KdNode k = insert(current.right, p, opposite(d));
            current.right = k;
            k.parent = current;
        }
        return current;
    }

    public boolean contains(Point2D p) {
        return contains(root, p);
    }

    private boolean contains(KdNode kd, Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (kd == null) return false;
        KdNode current = kd;
        Direction currentd = kd.direction;
        if (current.point.equals(p)) {
            return true;
        }
        KdNode newnode = new KdNode(p, opposite(currentd));
        if (current.compareTo(newnode) > 0) {
            return contains(current.left, p);
        }
        else {
            return contains(current.right, p);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> ranges = new ArrayList<>();
        if (root != null) {
            findrange(rect, root, ranges);
        }
        return ranges;
    }

    private void findrange(RectHV rect, KdNode kd, ArrayList<Point2D> ranges) {
        if (rect.contains(kd.point)) {
            ranges.add(kd.point);
        }
        if (kd.direction == Direction.X) {
            double x = kd.point.x();
            if (rect.xmin() <= x && kd.left != null) findrange(rect, kd.left, ranges);
            if (rect.xmax() >= x && kd.right != null) findrange(rect, kd.right, ranges);
        }
        if (kd.direction == Direction.Y) {
            double y = kd.point.y();
            if (rect.ymin() <= y && kd.left != null) findrange(rect, kd.left, ranges);
            if (rect.ymax() >= y && kd.right != null) findrange(rect, kd.right, ranges);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        Point2D minp = root.point;
        min = p.distanceSquaredTo(minp);
        return nearest(p, root, minp);
    }

    private Point2D nearest(Point2D p, KdNode kd, Point2D minp) {
        if (kd.direction == Direction.X) {
            double x = kd.point.x();
            if (p.x() < x) {
                if (kd.left != null) {
                    if (min > p.distanceSquaredTo(kd.left.point)) {
                        min = p.distanceSquaredTo(kd.left.point);
                        minp = kd.left.point;
                    }
                    minp = nearest(p, kd.left, minp);
                    min = p.distanceSquaredTo(minp);
                }
                if (kd.right !=null && min > (x - p.x())) {
                    if (min > p.distanceSquaredTo(kd.right.point)) {
                        min = p.distanceSquaredTo(kd.right.point);
                        minp = kd.right.point;
                    }
                    minp = nearest(p, kd.right, minp);
                    min = p.distanceSquaredTo(minp);
                }
            }
            else {
                if (kd.right != null) {
                    if (min > p.distanceSquaredTo(kd.right.point)) {
                        min = p.distanceSquaredTo(kd.right.point);
                        minp = kd.right.point;
                    }
                    minp = nearest(p, kd.right, minp);
                    min = p.distanceSquaredTo(minp);
                }
                if (kd.left != null && min > (p.x() - x)) {
                    if (min > p.distanceSquaredTo(kd.left.point)) {
                        min = p.distanceSquaredTo(kd.left.point);
                        minp = kd.left.point;
                    }
                    minp = nearest(p, kd.left, minp);
                    min = p.distanceSquaredTo(minp);
                }
            }
        }

        else {
            double y = kd.point.y();
            if (p.y() < y) {
                if (kd.left != null) {
                    if (min > p.distanceSquaredTo(kd.left.point)) {
                        min = p.distanceSquaredTo(kd.left.point);
                        minp = kd.left.point;
                    }
                    minp = nearest(p, kd.left, minp);
                    min = p.distanceSquaredTo(minp);
                }
                if (kd.right != null && min > (y - p.y())) {
                    if (min > p.distanceSquaredTo(kd.right.point)) {
                        min = p.distanceSquaredTo(kd.right.point);
                        minp = kd.right.point;
                    }
                    minp = nearest(p, kd.right, minp);
                    min = p.distanceSquaredTo(minp);
                }
            }
            else {
                if (kd.right != null) {
                    if (min > p.distanceSquaredTo(kd.right.point)) {
                        min = p.distanceSquaredTo(kd.right.point);
                        minp = kd.right.point;
                    }
                    minp = nearest(p, kd.right, minp);
                    min = p.distanceSquaredTo(minp);
                }
                if (kd.left !=null && min > (p.y() - y)) {
                    if (min > p.distanceSquaredTo(kd.left.point)) {
                        min = p.distanceSquaredTo(kd.left.point);
                        minp = kd.left.point;
                    }
                    minp = nearest(p, kd.left, minp);
                    min = p.distanceSquaredTo(minp);
                }
            }
        }
        return minp;
    }

    public void draw() {
        if (root != null) {
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.RED);
            double x= root.point.x();
            StdDraw.line(x, 0, x, 1);

            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(root.point.x(), root.point.y());

            draw(root);
        }
    }

    private void draw(KdNode kd) {
        if (kd.left != null) {

            if (kd.direction == Direction.X) {
                double y = kd.left.point.y();
                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(0, y, kd.point.x(), y);
            }
            else {
                double x = kd.left.point.x();
                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x, 0, x, kd.point.y());
            }
            draw(kd.left);

            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(kd.left.point.x(), kd.left.point.y());
        }

        if (kd.right != null) {

            if (kd.direction == Direction.X) {
                double y = kd.right.point.y();
                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(1, y, kd.point.x(), y);
            }
            else {
                double x = kd.right.point.x();
                StdDraw.setPenRadius(0.01);
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x, 1, x, kd.point.y());
            }

            draw(kd.right);

            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(kd.right.point.x(), kd.right.point.y());

        }
    }

    public static void main(String[] args) {
        KdTree kd = new KdTree();
        kd.insert(new Point2D(0.5, 0.2));
        kd.insert(new Point2D(0.5, 0.4));
        kd.insert(new Point2D(0.5, 0.3));
        kd.insert(new Point2D(0.5, 0.7));
        kd.insert(new Point2D(0.5, 0.6));
        kd.insert(new Point2D(0.5, 0.2));
        StdOut.println(kd.contains(new Point2D(0.5, 0.4)));
        StdOut.println(kd.contains(new Point2D(0.2, 0.4)));
        StdOut.println(kd.contains(new Point2D(0.8,0.8)));
        StdOut.println(kd.size());
        kd.draw();
        Iterable<Point2D> ranges = kd.range(new RectHV(0.2,0.3,0.7,0.7));
        for (Point2D p : ranges) {
            StdOut.println(p);
        }
        StdOut.print("nearest point is ---: ");
        StdOut.println(kd.nearest(new Point2D(0.5, 0.8)));
    }
}
