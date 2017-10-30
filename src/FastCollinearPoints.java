import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] segments;
    private int count;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        LineSegment[] tempSegments = new LineSegment[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            Point p = points[i];
            Point[] auxArray = new Point[points.length - 1];
            int x = 0;
            for (int j = 0; j < points.length; j++) {
                if (i != j) {
                    if (points[j] == null || points[j].compareTo(p) == 0) {
                        throw new IllegalArgumentException();
                    }
                    auxArray[x++] = points[j];
                }
            }
            Arrays.sort(auxArray, p.slopeOrder());
            double lastSlope = p.slopeTo(auxArray[0]);
            int y = 0;
            Point[] tempArray = new Point[points.length];
            tempArray[0] = p;
            for (int k = 0; k < auxArray.length; k++) {
                Point q = auxArray[k];
                double slope = p.slopeTo(q);
                if (lastSlope == slope) {
                    if (q.compareTo(tempArray[0]) > 0) {
                        Point aux = tempArray[0];
                        tempArray[0] = q;
                        tempArray[y + 1] = aux;
                    }
                    if (q.compareTo(tempArray[y]) > 0) {
                        Point aux = tempArray[y];
                        tempArray[y] = q;
                        tempArray[y + 1] = aux;
                    }
                    if (q.compareTo(tempArray[y]) < 0 && q.compareTo(tempArray[0]) < 0) {
                        tempArray[y + 1] = q;
                    }
                    y++;
                    if (k == auxArray.length - 1 && y > 2) {
                        if (tempArray[0].equals(points[i])) {
                            if (count + 1 == tempSegments.length) {
                                LineSegment[] aux = new LineSegment[tempSegments.length * 2];
                                for (int o = 0; o < tempSegments.length; o++) {
                                    aux[o] = tempSegments[o];
                                }
                                tempSegments = aux;
                            }
                            tempSegments[count++] = new LineSegment(tempArray[0], tempArray[y]);
                        }
                    }
                } else {
                    if (y > 2) {
                        if (tempArray[0].equals(points[i])) {
                            if (count + 1 == tempSegments.length) {
                                LineSegment[] aux = new LineSegment[tempSegments.length * 2];
                                for (int o = 0; o < tempSegments.length; o++) {
                                    aux[o] = tempSegments[o];
                                }
                                tempSegments = aux;
                            }
                            tempSegments[count++] = new LineSegment(tempArray[0], tempArray[y]);
                        }
                    }
                    y = 0;
                    tempArray = new Point[points.length];
                    if (p.compareTo(q) > 0) {
                        tempArray[y] = p;
                        tempArray[++y] = q;
                    } else {
                        tempArray[y] = q;
                        tempArray[++y] = p;
                    }
                }
                lastSlope = slope;
            }
        }

        segments = new LineSegment[count];
        for (int i = 0; i < count; i++) {
            segments[i] = tempSegments[i];
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
