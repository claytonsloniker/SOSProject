package com.sos.game;

public interface GameMode {
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    boolean checkWin(String[][] board, String currentPlayer, int row, int col, String letter);

    default boolean checkAdjacent(String[][] board, String currentPlayer, int row, int col, String letter) {
        for (int[] direction : directions) {
            if (isSOS(board, row, col, direction)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(String[][] board, int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    private boolean isSOS(String[][] board, int row, int col, int[] direction) {
        int newRow1 = row + direction[0];
        int newCol1 = col + direction[1];
        int newRow2 = row + 2 * direction[0];
        int newCol2 = col + 2 * direction[1];
        return isValid(board, newRow1, newCol1) && isValid(board, newRow2, newCol2) &&
                "S".equals(board[row][col]) && "O".equals(board[newRow1][newCol1]) && "S".equals(board[newRow2][newCol2]);
    }
}