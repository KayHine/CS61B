package hw4.puzzle;

public final class Board {
    private final int size;
    private final int[][] board;

    // constructs a board from an N-by-N array of tiles where
    // titles[i][j] = tile at row i, column j
    public Board(int[][] tiles) {
        size = tiles.length;
        board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    // returns value of tile at row i, column j (or 0 if blank)
    public int tileAt(int i, int j) {
        if (i < 0 || i > size - 1 || j < 0 || j > size -1) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return board[i][j];
    }

    // returns the board size N
    public int size() {
        return size;
    }

    public int properPlace(int i, int j) {
        return size * i + j + 1;
    }

    // hamming priority function - # of tiles in wrong position
    public int hamming() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) != properPlace(i, j) && properPlace(i, j) < 9) {
                    if (tileAt(i, j) != 0) {
                        sum = sum + 1;
                    }
                }
            }
        }
        return sum;
    }

    // manhattan priority function - sum of vertical and horizontal distance
    // each tile is from its goal position
    public int manhattan() {
        return 0;
    }

    // returns true if is this board the goal board
    public boolean isGoal() {
        return hamming() == 0;
    }

    // returns true if this board's title values are the same
    // position as y's
    public boolean equals(Object y) {
        if (y.getClass() != this.getClass()) {
            return false;
        }

        return false;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
