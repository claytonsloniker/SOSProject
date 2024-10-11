package com.sos.game;

public interface GameMode {
    boolean checkWin(String[] board, String currentPlayer);
    // Add other game mode-specific methods if needed
}