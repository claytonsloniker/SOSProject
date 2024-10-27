package com.sos.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class GameMode {
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    public abstract void updateGameStatus(Game game, int row, int col, String letter);

    public List<SosSequence> findSosSequences(String[][] board, int row, int col, String currentPlayer, Set<SosSequence> existingSequences) {
        Set<SosSequence> uniqueSequences = new HashSet<>();
        String color = currentPlayer.equals("GREEN") ? "green" : "red";
        for (int[] direction : directions) {
            if (isSOS(board, row, col, direction)) {
                int startRow, startCol, endRow, endCol;
                // Checks for case where O is in the middle of the sequence
                if ("O".equals(board[row][col])) {
                    startRow = row - direction[0];
                    startCol = col - direction[1];
                    endRow = row + direction[0];
                    endCol = col + direction[1];
                } else {
                    startRow = row;
                    startCol = col;
                    endRow = row + 2 * direction[0];
                    endCol = col + 2 * direction[1];
                }
                // Added to normalize coordinates to avoid duplicate sequences with different start and end points
                if (startRow > endRow || (startRow == endRow && startCol > endCol)) {
                    int tempRow = startRow;
                    int tempCol = startCol;
                    startRow = endRow;
                    startCol = endCol;
                    endRow = tempRow;
                    endCol = tempCol;
                }
                // Checks if the sequence is unique
                // equals and hashCode methods are overridden in SosSequence class to compare objects based on content
                SosSequence sequence = new SosSequence(startRow, startCol, endRow, endCol, color);
                if (!existingSequences.contains(sequence)) {
                    uniqueSequences.add(sequence);
                }
            }
        }
        return new ArrayList<>(uniqueSequences); // Converts set to list to remove duplicates
    }

    private boolean isValid(String[][] board, int row, int col) {
        // Checks if the coordinates are within the board
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

    private boolean isSOS(String[][] board, int row, int col, int[] direction) {
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