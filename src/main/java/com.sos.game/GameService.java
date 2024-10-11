package com.sos.game;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Game game;

    public void createGame(int size, String gameMode) {
        game = new Game(size, gameMode);
    }

    public String[] getBoard() {
        return game.getBoard();
    }

    public String getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public void makeMove(int index, String gameMode) {
        game.makeMove(index, gameMode);
    }
}