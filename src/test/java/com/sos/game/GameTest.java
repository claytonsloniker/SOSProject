package com.sos.game;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class GameTest {

    @Test
    public void testBoardSize() {
        int size = 4;
        Game game = new Game(size, "simple");
        String[][] board = game.getBoard();
        assertEquals("Board should have " + size + " rows", size, board.length);
        assertEquals("Board should have " + size + " columns", size, board[0].length);
    }

    @Test
    public void gameModeChoice() {
        Game game = new Game(4, "simple");
        assertEquals("Game mode should be SimpleGameMode", SimpleGameMode.class, game.getGameMode().getClass());
    }

    @Test
    public void testSimpleMove() {
        Game game = new Game(3, "simple");
        game.makeMove(0, 0, "S");
        assertEquals("Letter 'S' should be placed at (0, 0)", "S", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testGeneralMove() {
        Game game = new Game(3, "general");
        game.makeMove(0, 0, "S");
        assertEquals("Letter 'S' should be placed at (0, 0)", "S", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should be the current player", "RED", game.getCurrentPlayer());
    }
}