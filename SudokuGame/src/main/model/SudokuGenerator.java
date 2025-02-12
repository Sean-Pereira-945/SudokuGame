package main.model;

import java.util.Random;

public class SudokuGenerator {
    private static final int SIZE = 9;

    public static int[][] generatePuzzle(int difficulty) {
        int[][] board = new int[SIZE][SIZE];
        solve(board); // Fill the board with a valid solution
        removeNumbers(board, difficulty); // Remove numbers based on difficulty
        return board;
    }

    private static boolean solve(int[][] board) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) {
                                return true;
                            }
                            board[row][col] = 0; // Backtrack
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static void removeNumbers(int[][] board, int difficulty) {
        int cellsToRemove = difficulty == 1 ? 30 : difficulty == 2 ? 40 : 50;
        Random random = new Random();
        while (cellsToRemove > 0) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                cellsToRemove--;
            }
        }
    }

    private static boolean isValid(int[][] board, int row, int col, int num) {
        return !usedInRow(board, row, num) &&
               !usedInColumn(board, col, num) &&
               !usedInBox(board, row - row % 3, col - col % 3, num);
    }

    private static boolean usedInRow(int[][] board, int row, int num) {
        for (int col = 0; col < SIZE; col++) {
            if (board[row][col] == num) return true;
        }
        return false;
    }

    private static boolean usedInColumn(int[][] board, int col, int num) {
        for (int row = 0; row < SIZE; row++) {
            if (board[row][col] == num) return true;
        }
        return false;
    }

    private static boolean usedInBox(int[][] board, int boxStartRow, int boxStartCol, int num) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row + boxStartRow][col + boxStartCol] == num) return true;
            }
        }
        return false;
    }
}