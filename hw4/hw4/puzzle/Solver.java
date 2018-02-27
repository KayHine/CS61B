package hw4.puzzle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public final class Solver {

    private final PriorityQueue<searchNode> queue;
    private LinkedList<Board> solution;
    private int moves;

    // Constructor which solves the puzzle, computing everything necessary
    // for moves() and solution() to not have to solve the problem again.
    // Solves the puzzle using the A* algorithm. Assumes a solution exists.
    public Solver(Board initial) {
        queue = new PriorityQueue<searchNode>(1, new searchNodeComparator());
        solution = new LinkedList<Board>();
        // initialize searchNode
        // push onto PQ
        // while PQ is not empty
        //      pop lowest priority searchNode
        //      if not solved puzzle
        //          generate children
        //          push onto queue
        searchNode init = new searchNode(initial, 0, null);
        queue.add(init);

        while (!queue.isEmpty()) {
            searchNode current = queue.poll();
            Board currBoard = current.board;

            if (currBoard.isGoal()) {
                solution.add(currBoard);
                moves = current.moves;
                return;
            }

            Queue<Board> children = (Queue) BoardUtils.neighbors(current.board);
            for (Board b : children) {
                if (!b.equals(currBoard)) {
                    searchNode child = new searchNode(b, current.moves + 1, current);
                    queue.add(child);
                }
            }

            solution.add(currBoard);
        }

    }

    // Returns the minimum number of moves to solve the initial board
    public int moves() {
        return moves;
    }

    // Returns the sequence of Boards from the initial board to the solution
    public Iterable<Board> solution() {
        return solution;
    }

    private class searchNode{
        private Board board;
        private int priority;
        private int moves;
        private searchNode prev;

        searchNode(Board b, int m, searchNode prevNode) {
            moves = m;
            board = b;
            priority = board.hamming() + moves;
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
