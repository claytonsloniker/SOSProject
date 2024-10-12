package com.sos.game;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Game game;

    public void createGame(int size, String gameMode) {
        if (size < Game.getMinSize() || size > Game.getMaxSize()) {
            throw new IllegalArgumentException("Board size must be between " + Game.getMinSize() + " and " + Game.getMaxSize());
        }
        game = new Game(size, gameMode);
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
}