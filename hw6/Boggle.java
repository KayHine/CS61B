import java.util.Scanner;

public class Boggle {

    private int width;
    private int height;
    private int[][] board;
    private Trie dictionary = new Trie();

    public Boggle(int[][] board, int width, int height) {
        this.width = width;
        this.height = height;
        this.board = board;

    }

}