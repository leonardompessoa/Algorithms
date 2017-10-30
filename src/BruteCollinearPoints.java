import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    private final LineSegment[] segments;
    private int count;


    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        LineSegment[] auxSegments = new LineSegment[points.length];
        Point[] maxs = new Point[points.length];
        Point[] mins = new Point[points.length];
        int size = 0;
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            Point p = points[i];
            double[] slopes = new double[points.length];
            for (int j = 0; j < points.length; j++) {
                if (j != i) {
                    if (points[j] == null || points[j].compareTo(p) == 0) {
                        throw new IllegalArgumentException();
                    }
                    Point q = points[j];
                    slopes[j] = p.slopeTo(q);
                }
            }

            for (int k = 0; k < slopes.length; k++) {
                int y = 0;
                Point max = p;
                Point min = p;
                if (k != i) {
                    for (int x = 0; x < slopes.length; x++) {
                        if (slopes[k] == slopes[x]) {
                            y++;
                            if (max.compareTo(points[x]) < 0) {
                                max = points[x];
                            }
                            if (min.compareTo(points[x]) > 0) {
                                min = points[x];
                            }
                        }
                    }
                    if (y > 2) {
                        if (size == 0) {
                            mins[size] = min;
                            maxs[size] = max;
                            size++;
                            auxSegments[count++] = new LineSegment(min, max);
                        } else {
                            boolean exists = false;
                            for (int z = 0; z < size; z++) {
                                if (min.compareTo(mins[z]) == 0
                                        && max.compareTo(maxs[z]) == 0) {
                                    exists = true;
                                    break;
                                } else if (min.compareTo(maxs[z]) == 0
                                        && max.compareTo(mins[z]) == 0) {
                                    exists = true;
                                    break;
                                }
                            }
                            if (!exists) {
                                auxSegments[count++] = new LineSegment(min, max);
                                mins[size] = min;
                                maxs[size] = max;
                                size++;

                            }
                        }


                    }
                }
            }
        }

        segments = new LineSegment[count];
        for (int m = 0; m < count; m++) {
            segments[m] = auxSegments[m];
        }
    }

    public int numberOfSegments() {
        return count;
    }

    public LineSegment[] segments() {
        LineSegment[] copyArray = new LineSegment[segments.length];
        for (int i = 0; i < segments.length; i++) {
            copyArray[i] = segments[i];
        }
        return copyArray;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
