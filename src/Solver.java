import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Solver {

    private SearchNode goalNode;
    private boolean solvable;

    private class SearchNode implements Comparable<SearchNode> {

        int moves;
        Board board;
        SearchNode predecessor;

        @Override
        public int compareTo(SearchNode o) {
            int priority_1 = this.moves + this.board.manhattan();
            int priority_2 = o.moves + o.board.manhattan();
            if (priority_1 < priority_2) {
                return -1;
            }
            if (priority_1 > priority_2) {
                return 1;
            }
            if (priority_1 == priority_2) {
                if (this.board.manhattan() < o.board.manhattan()) {
                    return -1;
                } else if (this.board.manhattan() > o.board.manhattan()) {
                    return 1;
                }
            }
            return 0;
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }
        MinPQ<SearchNode> pq = new MinPQ<>();
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        SearchNode node = new SearchNode();
        node.moves = 0;
        node.predecessor = null;
        node.board = initial;

        SearchNode twinNode = new SearchNode();
        twinNode.moves = 0;
        twinNode.predecessor = null;
        twinNode.board = initial.twin();

        pq.insert(node);
        pqTwin.insert(twinNode);
        node = pq.delMin();
        twinNode = pqTwin.delMin();
        while (!node.board.isGoal()) {
            if (twinNode.board.isGoal()) {
                node = null;
                solvable = false;
                break;
            }
            Iterator<Board> neighbors = node.board.neighbors().iterator();
            Iterator<Board> twinNeighbors = twinNode.board.neighbors().iterator();
            while (neighbors.hasNext()) {
                Board neighbor = neighbors.next();
                if (node.predecessor == null || !neighbor.equals(node.predecessor.board)) {
                    SearchNode neighborNode = new SearchNode();
                    neighborNode.board = neighbor;
                    neighborNode.predecessor = node;
                    neighborNode.moves = node.moves + 1;
                    pq.insert(neighborNode);
                }
            }

            while (twinNeighbors.hasNext()) {
                Board neighbor = twinNeighbors.next();
                if (twinNode.predecessor == null || !neighbor.equals(twinNode.predecessor.board)) {
                    SearchNode neighborNode = new SearchNode();
                    neighborNode.board = neighbor;
                    neighborNode.predecessor = twinNode;
                    neighborNode.moves = node.moves + 1;
                    pqTwin.insert(neighborNode);
                }
            }

            node = pq.delMin();
            twinNode = pqTwin.delMin();
        }

        goalNode = node;
        if (goalNode != null) {
            solvable = true;
        }

    }


    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        if (solvable) {
            return goalNode.moves;
        } else {
            return -1;
        }
    }

    public Iterable<Board> solution() {
        if (solvable) {
            SearchNode node = goalNode;
            Stack<Board> solution = new Stack<>();
            solution.push(node.board);
            while (node.predecessor != null) {
                solution.push(node.predecessor.board);
                node = node.predecessor;
            }
            return solution;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}