import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    private Picture picture;
    private int width;
    private int height;
    private double[][] energy;
    private double[][] minPath;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        width = picture.width();
        height = picture.height();
        energy = new double[width][height];
        minPath = new double[width][height];
    }

    // Current picture
    public Picture picture() {
        return picture;
    }

    // Width of current picture
    public int width() {
        return width;
    }

    // Height of current picture
    public int height() {
        return height;
    }

    // Energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > picture.width() - 1 || y < 0 || y > picture.height() - 1) {
            throw new IndexOutOfBoundsException();
        }

        if (energy[x][y] != 0) {
            return energy[x][y];
        } else {
            Color leftPixel;
            Color rightPixel;
            Color topPixel;
            Color botPixel;
            if (x == 0) {
                leftPixel = picture.get(picture.width() - 1, y);
                rightPixel = picture.get(x + 1, y);
            } else if (x == picture.width() - 1) {
                leftPixel = picture.get(x - 1, y);
                rightPixel = picture.get(0, y);
            } else {
                leftPixel = picture.get(x - 1, y);
                rightPixel = picture.get(x + 1, y);
            }

            if (y == 0) {
                topPixel = picture.get(x, picture.height() - 1);
                botPixel = picture.get(x, y + 1);
            } else if (y == picture.height() - 1) {
                topPixel = picture.get(x, y - 1);
                botPixel = picture.get(x, 0);
            } else {
                topPixel = picture.get(x, y - 1);
                botPixel = picture.get(x, y + 1);
            }

            int Rx = Math.abs(leftPixel.getRed() - rightPixel.getRed()) ;
            int Gx = Math.abs(leftPixel.getGreen() - rightPixel.getGreen());
            int Bx = Math.abs(leftPixel.getBlue() - rightPixel.getBlue());

            int Ry = Math.abs(topPixel.getRed() - botPixel.getRed());
            int Gy = Math.abs(topPixel.getGreen() - botPixel.getGreen());
            int By = Math.abs(topPixel.getBlue() - botPixel.getBlue());

            double pixelEnergy = Math.pow(Rx, 2) + Math.pow(Gx, 2) + Math.pow(Bx, 2) +
                    Math.pow(Ry, 2) + Math.pow(Gy, 2) + Math.pow(By, 2);

            energy[x][y] = pixelEnergy;

            return pixelEnergy;
        }
    }

    // Sequence of indicies for horizontal seam
    public int[] findHorizontalSeam() {
        // Transpose the image
        for (int i = 0; i < picture.height(); i++) {
            for (int j = 0; j < picture.width(); j++) {

            }
        }
        return null;
    }

    // Sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        /*  backtrack:
        *   0 -> col - 1
        *   1 -> col
        *   2 -> col + 1
        * */
        int[][] backtrack = new int[picture.width()][picture.height()];
        int[] path = new int[picture.height()];
        double minimum;
        for (int row = 0; row < picture.height(); row++) {
            for (int col = 0; col < picture.width(); col++) {
                // Calculate trivial case with the top row
                if (row == 0) {
                    minPath[col][row] = energy[col][row];
                    backtrack[col][row] = -1;
                } else {
                    // find the minimum value from row above and track the position in backtrack
                    if (col == 0) {
                        minimum = Math.min(minPath[col][row - 1], minPath[col + 1][row - 1]);
                        if (minimum == minPath[col][row - 1]) {
                            backtrack[col][row] = 1;
                        } else {
                            backtrack[col][row] = 2;
                        }
                    } else if (col == picture.width() - 1) {
                        minimum = Math.min(minPath[col - 1][row - 1], minPath[col][row - 1]);
                        if (minimum == minPath[col - 1][row - 1]) {
                            backtrack[col][row] = 0;
                        } else {
                            backtrack[col][row] = 1;
                        }
                    } else {
                        minimum = Math.min(minPath[col - 1][row - 1],
                                    Math.min(minPath[col][row - 1], minPath[col + 1][row - 1]));
                        if (minimum == minPath[col - 1][row - 1]) {
                            backtrack[col][row] = 0;
                        } else if (minimum == minPath[col][row - 1]) {
                            backtrack[col][row] = 1;
                        } else {
                            backtrack[col][row] = 2;
                        }
                    }
                    minPath[col][row] = energy[col][row] + minimum;
                }
            }
        }

        // Find the minimum value on the bottom row
        double lowest = minPath[0][picture.height() - 1];
        int minIndex = 0;
        for (int col = 0; col < picture.width(); col++) {
            if (minPath[col][picture.height() - 1] < lowest) {
                lowest = minPath[col][picture.height() - 1];
                minIndex = col;
            }
        }

        // Now we create the backtrack array
        int row = picture.height() - 1;
        path[0] = minIndex;
        int path_index = 1;
        while (row > 0) {
            if (backtrack[minIndex][row] == 0) {
                minIndex = minIndex - 1;
                path[path_index] = minIndex;
                path_index++;
            } else if (backtrack[minIndex][row] == 1) {
                path[path_index] = minIndex;
                path_index++;
            } else {
                minIndex = minIndex + 1;
                path[path_index] = minIndex;
                path_index++;
            }
            row--;
        }

        return path;
    }

    // Remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // Remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {

    }
}
