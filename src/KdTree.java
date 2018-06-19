import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static class Node {
        private Point2D p;
        private Node lb;
        private Node rt;
        private RectHV r;

        public Node(Point2D p) {
            this.p = p;
        }
    }

    private Node root;
    private int size;

    public KdTree() {

    }

    public boolean isEmpty() {
        return root == null;
    }


    public int size() {
        if (isEmpty()) return 0;
        else return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (isEmpty()) {
            root = new Node(p);
            root.r = new RectHV(0, 0, 1, 1);
            size++;
        } else {
            root = put(root, p, 0);
        }
    }

    private Node put(Node node, Point2D p, int level) {
        if (node == null) {
            size++;
            return new Node(p);
        }
        if (node.p.equals(p)) {
            return node;
        }
        int compare = compare(p, node.p, level);
        if (compare < 0) {
            node.lb = put(node.lb, p, level + 1);
            if (node.lb.r == null) {
                if (level % 2 == 0) {
                    node.lb.r = new RectHV(node.r.xmin(), node.r.ymin(), node.p.x(), node.r.ymax());
                } else {
                    node.lb.r = new RectHV(node.r.xmin(), node.r.ymin(), node.r.xmax(), node.p.y());
                }
            }
        } else if (compare >= 0) {
            node.rt = put(node.rt, p, level + 1);
            if (node.rt.r == null) {
                if (level % 2 == 0) {
                    node.rt.r = new RectHV(node.p.x(), node.r.ymin(), node.r.xmax(), node.r.ymax());
                } else {
                    node.rt.r = new RectHV(node.r.xmin(), node.p.y(), node.r.xmax(), node.r.ymax());
                }
            }
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }
        if (root == null) return false;
        Point2D point = get(root, p, 0);
        return point != null;
    }

    private Point2D get(Node node, Point2D p, int level) {
        if (node == null) return null;
        int compare = compare(p, node.p, level);
        if (compare < 0) return get(node.lb, p, level + 1);
        if (compare > 0) return get(node.rt, p, level + 1);
        else return node.p;
    }

    private int compare(Point2D p1, Point2D p2, int level) {
        int compare;
        if (level % 2 == 0) {
            if (p1.x() >= p2.x()) {
                compare = 1;
            } else {
                compare = -1;
            }
        } else {
            if (p1.y() >= p2.y()) {
                compare = 1;
            } else {
                compare = -1;
            }
        }
        return compare;
    }

    public void draw() {
        draw(root, 0);
    }

    private void draw(Node node, int level) {
        if (node != null) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.p.draw();
            StdDraw.setPenRadius();

            if (level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.p.x(), node.r.ymin(), node.p.x(), node.r.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.r.xmin(), node.p.y(), node.r.xmax(), node.p.y());
            }
            if (node.lb != null) {
                draw(node.lb, level + 1);
            }

            if (node.rt != null) {
                draw(node.rt, level + 1);
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Queue<Point2D> rangePoints = new Queue<>();
        range(root, rect, rangePoints);
        return rangePoints;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> rangePoints) {

        if (!rect.intersects(node.r)) {
            return;
        }
        if (rect.contains(node.p)) {
            rangePoints.enqueue(node.p);
        }
        if (node.lb != null) {
            range(node.lb, rect, rangePoints);
        }
        if (node.rt != null) {
            range(node.rt, rect, rangePoints);
        }

    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.IllegalArgumentException();
        }

        Node nearest = nearest(root, p, root, 0);
        if (nearest == null) {
            return null;
        }
        return nearest.p;
    }

    private Node nearest(Node node, Point2D p, Node champion, int level) {
        if (node == null) {
            return champion;
        }
        double distance = node.p.distanceTo(p);

        if (node.p.distanceSquaredTo(p) < champion.p.distanceSquaredTo(p)) {
            champion = node;
        }

        if (node.r.distanceSquaredTo(p) < champion.p.distanceSquaredTo(p)) {

            int compare = compare(p, node.p, level);

            if (compare < 0) {
                champion = nearest(node.lb, p, champion, level + 1);
                champion = nearest(node.rt, p, champion, level + 1);
            } else {
                champion = nearest(node.rt, p, champion, level + 1);
                champion = nearest(node.lb, p, champion, level + 1);
            }

        }

        return champion;

    }


    public static void main(String[] args) {
    }

}