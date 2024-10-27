package com.sos.game;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 3;
    private String[][] board;
    private String currentPlayer; // "GREEN" or "RED"
    private String gameStatus; // "ONGOING", "WON", "DRAW"
    private GameMode gameMode;
    private final List<SosSequence> sosSequences;
    private int greenPlayerScore;
    private int redPlayerScore;

    public Game(int size, String gameMode) {
        this.board = new String[size][size]; // Initialize a size x size board
        this.currentPlayer = "GREEN"; // Green player starts the game
        this.gameStatus = "ONGOING";
        this.sosSequences = new ArrayList<>();
        this.greenPlayerScore = 0;
        this.redPlayerScore = 0;
        setGameMode(gameMode);
    }

    private void setGameMode(String gameMode) {
        if ("simple".equalsIgnoreCase(gameMode)) {
            this.gameMode = new SimpleGameMode();
        } else if ("general".equalsIgnoreCase(gameMode)) {
            this.gameMode = new GeneralGameMode();
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getGreenPlayerScore() {
        return greenPlayerScore;
    }

    public void incrementGreenPlayerScore(int score) {
        greenPlayerScore += score;
    }

    public void incrementRedPlayerScore(int score) {
        redPlayerScore += score;
    }

    public int getRedPlayerScore() {
        return redPlayerScore;
    }

    public List<SosSequence> getSosSequences() {
        return sosSequences;
    }

    public static int getMaxSize() {
        return MAX_SIZE;
    }

    public static int getMinSize() {
        return MIN_SIZE;
    }

    public boolean checkIfBoardIsFull() {
        return isBoardFull();
    }

    public synchronized void makeMove(int row, int col, String letter) {
        if (board[row][col] == null && gameStatus.equals("ONGOING")) {
            board[row][col] = letter; // Set the chosen letter
            gameMode.updateGameStatus(this, row, col, letter);
            if (gameStatus.equals("ONGOING")) {
                currentPlayer = currentPlayer.equals("GREEN") ? "RED" : "GREEN"; // Switch player
            }
        }
    }

    private boolean isBoardFull() {
        for (String[] row : board) {
            for (String cell : row) {
                if (cell == null) {
                    return false;
                }
            }
        }
        return true;
    }

    public void resetGame(int size) {
        this.board = new String[size][size];
        this.currentPlayer = "GREEN";
        this.gameStatus = "ONGOING";
        this.sosSequences.clear();
    }
}