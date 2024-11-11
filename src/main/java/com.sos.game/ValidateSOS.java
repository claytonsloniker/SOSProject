package com.sos.game;

public class ValidateSOS {
    public static boolean isValid(String[][] board, int row, int col) {
        // Checks if the coordinates are within the board
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    public static boolean isSOS(String[][] board, int row, int col, int[] direction) {
        // Checks if the sequence S-O-S is present in the given direction
        int newRow1 = row + direction[0];
        int newCol1 = col + direction[1];
        int newRow2 = row + 2 * direction[0];
        int newCol2 = col + 2 * direction[1];
        int newRow3 = row - direction[0];
        int newCol3 = col - direction[1];
        return (isValid(board, newRow1, newCol1) && isValid(board, newRow2, newCol2) &&
                "S".equals(board[row][col]) && "O".equals(board[newRow1][newCol1]) && "S".equals(board[newRow2][newCol2])) ||
                (isValid(board, newRow1, newCol1) && isValid(board, newRow3, newCol3) &&
                        "O".equals(board[row][col]) && "S".equals(board[newRow1][newCol1]) && "S".equals(board[newRow3][newCol3]));
    }
}
