package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;
import java.util.PriorityQueue;

public final class Solver {

    private final PriorityQueue<searchNode> queue;
    private int moves;

    // Constructor which solves the puzzle, computing everything necessary
    // for moves() and solution() to not have to solve the problem again.
    // Solves the puzzle using the A* algorithm. Assumes a solution exists.
    public Solver(Board initial) {
        queue = new PriorityQueue<searchNode>(1, new searchNodeComparator());
        // initialize searchNode
        // push onto PQ
        // create children searchNodes
        // push onto PQ
    }

    // Returns the minimum number of moves to solve the initial board
    public int moves() {
        return moves;
    }

    // Returns the sequence of Boards from the initial board to the solution
    public Iterable<Board> solution() {
        return null;
    }

    private class searchNode{
        private Board board;
        private int priority;
        private searchNode prev;

        searchNode(Board b, searchNode prevNode) {
            board = b;
            priority = board.hamming();
            prev = prevNode;
        }
    }

    public class searchNodeComparator implements Comparator<searchNode> {

        @Override
        public int compare(searchNode a, searchNode b) {
            if (a.priority < b.priority) {
                return -1;
            }
            if (a.priority > b.priority) {
                return 1;
            }
            return 0;
        }
    }

    // DO NOT MODIFY MAIN METHOD
    // Uncomment this method once your Solver and Board classes are ready.
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution()) {
            StdOut.println(board);
       }
    }

}
