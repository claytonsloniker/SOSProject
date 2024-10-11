package com.sos.game;

public class Game {
    private String[] board;
    private String currentPlayer;
    private String gameStatus; // "ONGOING", "WON", "DRAW"
    private GameMode gameMode;

    public Game(int size, String gameMode) {
        this.board = new String[size * size]; // Initialize a size x size board
        this.currentPlayer = "S"; // Player 'S' starts the game
        this.gameStatus = "ONGOING";
        setGameMode(gameMode);
    }

    private void setGameMode(String gameMode) {
        if ("simple".equalsIgnoreCase(gameMode)) {
            this.gameMode = new SimpleGameMode();
        } else if ("general".equalsIgnoreCase(gameMode)) {
            this.gameMode = new GeneralGameMode();
        }
    }

    public String[] getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void makeMove(int index, String gameMode) {
        if (board[index] == null && gameStatus.equals("ONGOING")) {
            board[index] = currentPlayer; // Set the current player's move
            if (this.gameMode.checkWin(board, currentPlayer)) {
                gameStatus = "WON";
            } else if (isBoardFull()) {
                gameStatus = "DRAW";
            } else {
                currentPlayer = currentPlayer.equals("S") ? "O" : "S"; // Switch player
            }
        }
    }

    private boolean isBoardFull() {
        for (String cell : board) {
            if (cell == null) {
                return false;
            }
        }
        return true;
    }

    public void resetGame(int size) {
        this.board = new String[size * size];
        this.currentPlayer = "S";
        this.gameStatus = "ONGOING";
    }
}