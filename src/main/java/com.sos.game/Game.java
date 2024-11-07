package com.sos.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final Logger logger = LoggerFactory.getLogger(Game.class);
    private static final int MAX_SIZE = 10;
    private static final int MIN_SIZE = 3;
    private String[][] board;
    private Player greenPlayer;
    private Player redPlayer;
    private Player currentPlayer;
    private String gameStatus; // "ONGOING", "WON", "DRAW"
    private GameMode gameMode;
    private final List<SosSequence> sosSequences;
    private int greenPlayerScore;
    private int redPlayerScore;

    public Game(int size, String gameMode, Player greenPlayer, Player redPlayer) {
        this.board = new String[size][size]; // Initialize a size x size board
        this.greenPlayer = greenPlayer;
        this.redPlayer = redPlayer;
        this.currentPlayer = greenPlayer; // Green player starts the game
        this.gameStatus = "ONGOING";
        this.sosSequences = new ArrayList<>();
        this.greenPlayerScore = 0;
        this.redPlayerScore = 0;
        setGameMode(gameMode);
        logGameStartInfo();
    }

    private void setGameMode(String gameMode) {
        if ("simple".equalsIgnoreCase(gameMode)) {
            this.gameMode = new SimpleGameMode();
        } else if ("general".equalsIgnoreCase(gameMode)) {
            this.gameMode = new GeneralGameMode();
        }
    }

    private void logGameStartInfo() {
        logger.info("Game started with size: {}", board.length);
        logger.info("Game mode: {}", gameMode.getClass().getSimpleName());
        logger.info("Green Player: {}", greenPlayer instanceof HumanPlayer ? "HumanPlayer" : "ComputerPlayer");
        logger.info("Red Player: {}", redPlayer instanceof HumanPlayer ? "HumanPlayer" : "ComputerPlayer");
        logger.info("Initial game status: {}", gameStatus);
    }

    public String[][] getBoard() {
        return board;
    }

    public String getCurrentPlayer() {
        return currentPlayer.getColor();
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

    public int getRedPlayerScore() {
        return redPlayerScore;
    }

    public void incrementRedPlayerScore(int score) {
        redPlayerScore += score;
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
                currentPlayer = currentPlayer.equals(greenPlayer) ? redPlayer : greenPlayer; // Switch player
                if (currentPlayer instanceof ComputerPlayer) {
                    currentPlayer.makeMove(this);
                }
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
        this.currentPlayer = greenPlayer;
        this.gameStatus = "ONGOING";
        this.sosSequences.clear();
    }
}