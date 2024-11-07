package com.sos.game;

import java.util.Random;

public class ComputerPlayer extends Player {
    private Random random = new Random();

    public ComputerPlayer(String color) {
        super(color);
    }

    @Override
    public void makeMove(Game game) {
        String[][] board = game.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null) {
                    String letter = random.nextBoolean() ? "S" : "O";
                    game.makeMove(row, col, letter);
                    return;
                }
            }
        }
    }
}