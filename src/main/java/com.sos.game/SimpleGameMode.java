package com.sos.game;

public class SimpleGameMode implements GameMode {
    @Override
    public boolean checkWin(String[][] board, String currentPlayer, int row, int col, String letter) {
        return checkAdjacent(board, currentPlayer, row, col, letter);
    }
}