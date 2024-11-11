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
            if (ValidateSOS.isSOS(board, row, col, direction)) {
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
}