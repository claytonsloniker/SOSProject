package com.sos.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GeneralGameMode extends GameMode {
    private static final Logger logger = LoggerFactory.getLogger(GeneralGameMode.class);

    @Override
    public void updateGameStatus(Game game, int row, int col, String letter) {
        Set<SosSequence> existingSequences = new HashSet<>(game.getSosSequences());
        List<SosSequence> newSequences = findSosSequences(game.getBoard(), row, col, game.getCurrentPlayer(), existingSequences);

        if (!newSequences.isEmpty()) {
            game.getSosSequences().addAll(newSequences);
            if (game.getCurrentPlayer().equals("GREEN")) {
                game.incrementGreenPlayerScore(newSequences.size());
            } else {
                game.incrementRedPlayerScore(newSequences.size());
            }
        }

        logger.debug("Green Player Score: {}", game.getGreenPlayerScore());
        logger.debug("Red Player Score: {}", game.getRedPlayerScore());
        logger.debug("Game Status: {}", game.getGameStatus());

        if (game.checkIfBoardIsFull()) {
            if (game.getGreenPlayerScore() > game.getRedPlayerScore()) {
                game.setGameStatus("GREEN WON");
            } else if (game.getGreenPlayerScore() < game.getRedPlayerScore()) {
                game.setGameStatus("RED WON");
            } else {
                game.setGameStatus("DRAW");
            }
        }
    }
}