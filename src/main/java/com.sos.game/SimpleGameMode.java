package com.sos.game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SimpleGameMode extends GameMode {
    @Override
    public void updateGameStatus(Game game, int row, int col, String letter) {
        Set<SosSequence> existingSequences = new HashSet<>(game.getSosSequences());
        List<SosSequence> newSequences = findSosSequences(game.getBoard(), row, col, game.getCurrentPlayer(), existingSequences);

        if (!newSequences.isEmpty()) {
            game.getSosSequences().addAll(newSequences);
            if (game.getCurrentPlayer().equals("GREEN")) {
                game.setGameStatus("GREEN WON");
            } else {
                game.setGameStatus("RED WON");
            }
        } else if (game.checkIfBoardIsFull()) {
            game.setGameStatus("DRAW");
        }
    }
}