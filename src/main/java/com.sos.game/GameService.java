package com.sos.game;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    private Game game;

    public void createGame(int size, String gameMode, String greenPlayerType, String redPlayerType) {
        if (size < Game.getMinSize() || size > Game.getMaxSize()) {
            throw new IllegalArgumentException("Board size must be between " + Game.getMinSize() + " and " + Game.getMaxSize());
        }

        Player greenPlayer = createPlayer(greenPlayerType, "GREEN");
        Player redPlayer = createPlayer(redPlayerType, "RED");

        game = new Game(size, gameMode, greenPlayer, redPlayer);
    }

    private Player createPlayer(String playerType, String color) {
        if ("human".equalsIgnoreCase(playerType)) {
            return new HumanPlayer(color);
        } else if ("computer".equalsIgnoreCase(playerType)) {
            return new ComputerPlayer(color);
        } else {
            throw new IllegalArgumentException("Invalid player type: " + playerType);
        }
    }

    public String[][] getBoard() {
        return game.getBoard();
    }

    public String getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public void makeMove(int row, int col, String letter) {
        game.makeMove(row, col, letter);
    }

    public String getGameStatus() {
        return game.getGameStatus();
    }

    public List<SosSequence> getSosSequences() {
        return game.getSosSequences();
    }
}