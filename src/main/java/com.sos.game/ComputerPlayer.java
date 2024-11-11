package com.sos.game;

import java.util.Random;

public class ComputerPlayer extends Player {
    private Random random = new Random();
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    public ComputerPlayer(String color) {
        super(color);
    }

    @Override
    public void makeMove(Game game) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        String[][] board = game.getBoard();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == null) {
                    if (createSOS(board, row, col, "S")) {
                        game.makeMove(row, col, "S");
                        return;
                    } else if (createSOS(board, row, col, "O")) {
                        game.makeMove(row, col, "O");
                        return;
                    }
                }
            }
        }

        // Make random move if no SOS possible
        int row = random.nextInt(board.length);
        int col = random.nextInt(board[0].length);
        String letter = random.nextBoolean() ? "S" : "O";
        while (board[row][col] != null) {
            row = random.nextInt(board.length);
            col = random.nextInt(board[0].length);
        }
        game.makeMove(row, col, letter);
    }

    private boolean createSOS(String[][] board, int row, int col, String letter) {
        board[row][col] = letter;
        boolean isSOSCreated = false;
        for (int[] direction : directions) {
            if (ValidateSOS.isSOS(board, row, col, direction)) {
                isSOSCreated = true;
                break;
            }
        }
        board[row][col] = null;
        return isSOSCreated;
    }
}