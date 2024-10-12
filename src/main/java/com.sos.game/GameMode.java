package com.sos.game;

public interface GameMode {
    boolean checkWin(String[][] board, String currentPlayer);
    // Update next sprint
}