package com.sos.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GameTest {

    @Test
    public void testBoardSize() {
        int size = 4;
        Game game = new Game(size, "simple");
        String[][] board = game.getBoard();
        assertEquals("Board should have " + size + " rows", size, board.length);
        assertEquals("Board should have " + size + " columns", size, board[0].length);
        assertTrue("Board size should be at least 3", size >= 3);
        assertTrue("Board size should be at most 8", size <= 8);
    }

    @Test
    public void gameModeChoiceSimple() {
        Game game = new Game(4, "simple");
        assertEquals("Game mode should be SimpleGameMode", SimpleGameMode.class, game.getGameMode().getClass());
    }

    @Test
    public void gameModeChoiceGeneral() {
        Game game = new Game(4, "general");
        assertEquals("Game mode should be GeneralGameMode", GeneralGameMode.class, game.getGameMode().getClass());
    }

    @Test
    public void startNewGame()  {
        Game game = new Game(4, "simple");
        String[][] board = game.getBoard();
        assertEquals("Game should be ongoing", "ONGOING", game.getGameStatus());
        assertEquals("Current player should be 'GREEN'", "GREEN", game.getCurrentPlayer());
        assertEquals("Game mode should be SimpleGameMode", SimpleGameMode.class, game.getGameMode().getClass());
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                assertEquals("All cells should be empty", null, board[i][j]);
            }
        }
    }

    @Test
    public void testSimpleMoveS() {
        Game game = new Game(3, "simple");
        game.makeMove(0, 0, "S");
        assertEquals("Letter 'S' should be placed at (0, 0)", "S", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testSimpleMoveO() {
        Game game = new Game(3, "simple");
        game.makeMove(0, 0, "O");
        assertEquals("Letter 'O' should be placed at (0, 0)", "O", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testMoveInNonEmptyCell() {
        Game game = new Game(3, "simple");
        game.makeMove(0, 0, "S");
        assertEquals("Letter 'S' should be placed at (0, 0)", "S", game.getBoard()[0][0]);

        // Attempt to place another letter in the same cell
        game.makeMove(0, 0, "O");
        assertEquals("Cell (0, 0) should still contain 'S'", "S", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should still be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testGeneralMove() {
        Game game = new Game(3, "general");
        game.makeMove(0, 0, "S");
        assertEquals("Letter 'S' should be placed at (0, 0)", "S", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testSimpleGameDraw() {
        Game game = new Game(3, "simple");
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "S");
        game.makeMove(0, 2, "S");
        game.makeMove(1, 0, "S");
        game.makeMove(1, 1, "S");
        game.makeMove(1, 2, "S");
        game.makeMove(2, 0, "S");
        game.makeMove(2, 1, "S");
        game.makeMove(2, 2, "S");
        assertEquals("Game should be a draw", "DRAW", game.getGameStatus());
    }

    @Test
    public void testSimpleGameWin() {
        Game game = new Game(3, "simple");
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        assertEquals("Game should be won by 'GREEN'", "GREEN WON", game.getGameStatus());
    }

    @Test
    public void testGeneralGameDraw() {
        Game game = new Game(3, "general");
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        game.makeMove(1, 0, "S");
        game.makeMove(1, 1, "O");
        game.makeMove(1, 2, "S");
        game.makeMove(2, 0, "O");
        game.makeMove(2, 1, "S");
        game.makeMove(2, 2, "O");
        assertEquals("Game should be a draw", "DRAW", game.getGameStatus());
    }

    @Test
    public void testGeneralGameWin() {
        Game game = new Game(3, "general");
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        game.makeMove(1, 0, "S");
        game.makeMove(1, 1, "O");
        game.makeMove(1, 2, "S");
        game.makeMove(2, 0, "S");
        game.makeMove(2, 1, "O");
        game.makeMove(2, 2, "S");
        assertEquals("Game should be won by 'GREEN'", "GREEN WON", game.getGameStatus());
    }

    @Test
    public void testScoring() {
        Game game = new Game(3, "general");
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        assertEquals("Green player should have 1 point", 1, game.getGreenPlayerScore());
    }

    @Test
    public void testBlockPlayer() {
        Game game = new Game(3, "general");
        game.makeMove(0, 0, "S");
        game.makeMove(0, 2, "S");
        game.makeMove(0, 1, "S");
        assertEquals("Red player should have 0 point", 0, game.getRedPlayerScore());
    }
}