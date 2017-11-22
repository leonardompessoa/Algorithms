import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {

    private int hamming = 0;
    private int manhattan = 0;
    private int dimension = 0;
    private int blankRow;
    private int blankColumn;
    private int[][] tiles;

    public Board(int[][] blocks) {

        if (blocks == null) {
            throw new java.lang.IllegalArgumentException();
        }

        int x = 0;
        this.dimension = blocks.length;
        this.tiles = new int[dimension][dimension];

        for (int i = 0; i < blocks.length; i++) {
            for (int j = 0; j < blocks[i].length; j++) {
                this.tiles[i][j] = blocks[i][j];
                if (this.tiles[i][j] != 0 && this.tiles[i][j] - 1 != x) {
                    hamming++;
                    int row = (this.tiles[i][j] - 1) / dimension;
                    int column = (this.tiles[i][j] - 1) % dimension;
                    if (row > i) {
                        manhattan += (row - i);
                    } else if (row < i) {
                        manhattan += (i - row);
                    }
                    if (column > j) {
                        manhattan += (column - j);
                    } else if (column < j) {
                        manhattan += (j - column);
                    }
                } else if (this.tiles[i][j] == 0) {
                    blankRow = i;
                    blankColumn = j;
                }
                x++;
            }
        }
    }

    public int dimension() {
        return this.dimension;
    }

    public int hamming() {
        return this.hamming;
    }

    public int manhattan() {
        return this.manhattan;
    }

    public boolean isGoal() {
        return hamming == 0 && manhattan == 0;
    }

    public Board twin() {
        int row = dimension - 1;
        int col = dimension - 1;
        if (tiles[row][col] == 0) {
            row--;
        }

        if (tiles[row][col - 1] == 0) {
            col--;
        }

        int aux = tiles[row][col];
        tiles[row][col] = tiles[row][col - 1];
        tiles[row][col - 1] = aux;

        Board twin = new Board(tiles);

        tiles[row][col - 1] = tiles[row][col];
        tiles[row][col] = aux;

        return twin;
    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (!(y instanceof Board)) return false;
        if (y == this) return true;

        Board other = (Board) y;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (this.tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();

        int row = blankRow;
        int column = blankColumn;

        int left = column - 1;
        int right = column + 1;
        int up = row - 1;
        int down = row + 1;

        int blank = tiles[row][column];
        if (left >= 0) {
            tiles[row][column] = tiles[row][left];
            tiles[row][left] = blank;
            neighbors.push(new Board(tiles));
            tiles[row][left] = tiles[row][column];
            tiles[row][column] = blank;
        }

        if (right < dimension) {
            tiles[row][column] = tiles[row][right];
            tiles[row][right] = blank;
            neighbors.push(new Board(tiles));
            tiles[row][right] = tiles[row][column];
            tiles[row][column] = blank;
        }

        if (up >= 0) {
            tiles[row][column] = tiles[up][column];
            tiles[up][column] = blank;
            neighbors.push(new Board(tiles));
            tiles[up][column] = tiles[row][column];
            tiles[row][column] = blank;
        }

        if (down < dimension) {
            tiles[row][column] = tiles[down][column];
            tiles[down][column] = blank;
            neighbors.push(new Board(tiles));
            tiles[down][column] = tiles[row][column];
            tiles[row][column] = blank;
        }

        return neighbors;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension + "\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {

        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        System.out.println("Twin");
        System.out.println(initial.twin());
        System.out.println("Initial");
        System.out.println(initial);
        System.out.println(initial.manhattan());
        System.out.println(initial.hamming());
    }

}