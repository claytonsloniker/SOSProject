package com.sos.game;

import java.util.ArrayList;
import java.util.List;

public interface GameMode {
    // All possible directions to check for SOS sequences.
    // Note: first value is the row change and the second value is the column change
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    boolean checkWin(String[][] board, String currentPlayer, int row, int col, String letter); // Gets overridden

    default List<SosSequence> findSosSequences(String[][] board, int row, int col) {
        List<SosSequence> sequences = new ArrayList<>(); // Stores all the SOS sequences
        for (int[] direction : directions) {
            if (isSOS(board, row, col, direction)) {
                // If an SOS sequence is found, add it to the list of sequences
                int endRow = row + 2 * direction[0];
                int endCol = col + 2 * direction[1];
                sequences.add(new SosSequence(row, col, endRow, endCol));
            }
        }
        return sequences;
    }

    private boolean isValid(String[][] board, int row, int col) {
        // Check if the row and column are within the bounds of the board
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