package hw2;                       

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF network;
    private int openSites;
    private HashSet<Integer> openedTops;
    private HashSet<Integer> openedBottoms;

    // Create N-by-N grid, with all sites initally blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        grid = new boolean[N][N];
        network = new WeightedQuickUnionUF(N * N);
        openedTops = new HashSet<>();
        openedBottoms = new HashSet<>();
        openSites = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 0 || row > grid.length - 1 || col < 0 || col > grid.length - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        grid[row][col] = true;
        openSites++;
        // connect current cell to any other adjacent open cells
        connectAdjacent(row, col);

        if (row == 0) openedTops.add(to1D(row, col));
        if (row == grid.length - 1) openedBottoms.add(to1D(row, col));
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row > grid.length - 1 || col < 0 || col > grid.length - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 0 || row > grid.length - 1 || col < 0 || col > grid.length - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        // check if current cell is connected to one of top cells
        int current = to1D(row, col);
        Iterator it = openedTops.iterator();
        while (it.hasNext()) {
            if (network.connected((int) it.next(), current)) {
                return true;
            }
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (!openedBottoms.isEmpty() && !openedTops.isEmpty()) {
            for (int i : openedTops) {
                for (int j : openedBottoms) {
                    if (network.connected(i, j)) return true;
                }
            }
        }
        return false;
    }

    public int to1D(int row, int col) {
        return grid.length * row + col;
    }

    // check adjacent cells and add to disjointed set if they're opened
    public void connectAdjacent(int row, int col) {
        int current = to1D(row, col);
        // check top
        if (grid[Math.max(row - 1, 0)][col] == true) {
            int top = to1D(Math.max(row - 1, 0), col);
            if (!network.connected(current, top)) network.union(current, top);
        }

        // check right
        if (grid[row][Math.min(col + 1, grid.length - 1)] == true) {
            int right = to1D(row, Math.min(col + 1, grid.length - 1));
            if (!network.connected(current, right)) network.union(current, right);
        }

        // check bottom
        if (grid[Math.min(row + 1, grid.length - 1)][col] == true) {
            int bottom = to1D(Math.min(row + 1, grid.length - 1), col);
            if (!network.connected(current, bottom)) network.union(current, bottom);
        }

        // check left
        if (grid[row][Math.max(col - 1, 0)] == true) {
            int left = to1D(row, Math.max(col - 1, 0));
            if (!network.connected(current, left)) network.union(current, left);
        }
    }
}