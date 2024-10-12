package com.sos.game;

public class SimpleGameMode implements GameMode {
    @Override
    public boolean checkWin(String[][] board, String currentPlayer) {

        // Note: Ignore this code for now, will update in next sprint

        int size = board.length;
        boolean win;

        // Check rows
        for (int i = 0; i < size; i++) {
            win = true;
            for (int j = 0; j < size; j++) {
                if (!currentPlayer.equals(board[i][j])) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check columns
        for (int j = 0; j < size; j++) {
            win = true;
            for (int i = 0; i < size; i++) {
                if (!currentPlayer.equals(board[i][j])) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }

        // Check main diagonal
        win = true;
        for (int i = 0; i < size; i++) {
            if (!currentPlayer.equals(board[i][i])) {
                win = false;
                break;
            }
        }
        if (win) return true;

        // Check anti-diagonal
        win = true;
        for (int i = 0; i < size; i++) {
            if (!currentPlayer.equals(board[i][size - 1 - i])) {
                win = false;
                break;
            }
        }
        return win;
    }
}