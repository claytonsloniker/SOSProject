package com.sos.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class GameTest {

    @Test
    public void testBoardSize() {
        int size = 4;
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(size, "simple", greenPlayer, redPlayer);
        String[][] board = game.getBoard();
        assertEquals("Board should have " + size + " rows", size, board.length);
        assertEquals("Board should have " + size + " columns", size, board[0].length);
        assertTrue("Board size should be at least 3", size >= 3);
        assertTrue("Board size should be at most 8", size <= 8);
    }

    @Test
    public void gameModeChoiceSimple() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(4, "simple", greenPlayer, redPlayer);
        assertEquals("Game mode should be SimpleGameMode", SimpleGameMode.class, game.getGameMode().getClass());
    }

    @Test
    public void gameModeChoiceGeneral() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(4, "general", greenPlayer, redPlayer);
        assertEquals("Game mode should be GeneralGameMode", GeneralGameMode.class, game.getGameMode().getClass());
    }

    @Test
    public void startNewGame() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(4, "simple", greenPlayer, redPlayer);
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
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "simple", greenPlayer, redPlayer);
        game.makeMove(0, 0, "S");
        assertEquals("Letter 'S' should be placed at (0, 0)", "S", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testSimpleMoveO() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "simple", greenPlayer, redPlayer);
        game.makeMove(0, 0, "O");
        assertEquals("Letter 'O' should be placed at (0, 0)", "O", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testMoveInNonEmptyCell() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "simple", greenPlayer, redPlayer);
        game.makeMove(0, 0, "S");
        assertEquals("Letter 'S' should be placed at (0, 0)", "S", game.getBoard()[0][0]);

        // Attempt to place another letter in the same cell
        game.makeMove(0, 0, "O");
        assertEquals("Cell (0, 0) should still contain 'S'", "S", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should still be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testGeneralMove() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "general", greenPlayer, redPlayer);
        game.makeMove(0, 0, "S");
        assertEquals("Letter 'S' should be placed at (0, 0)", "S", game.getBoard()[0][0]);
        assertEquals("Player 'RED' should be the current player", "RED", game.getCurrentPlayer());
    }

    @Test
    public void testSimpleGameDraw() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "simple", greenPlayer, redPlayer);
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
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "simple", greenPlayer, redPlayer);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        assertEquals("Game should be won by 'GREEN'", "GREEN WON", game.getGameStatus());
    }

    @Test
    public void testGeneralGameDraw() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "general", greenPlayer, redPlayer);
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
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "general", greenPlayer, redPlayer);
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
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "general", greenPlayer, redPlayer);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 1, "O");
        game.makeMove(0, 2, "S");
        assertEquals("Green player should have 1 point", 1, game.getGreenPlayerScore());
    }

    @Test
    public void testBlockPlayer() {
        Player greenPlayer = new HumanPlayer("GREEN");
        Player redPlayer = new HumanPlayer("RED");
        Game game = new Game(3, "general", greenPlayer, redPlayer);
        game.makeMove(0, 0, "S");
        game.makeMove(0, 2, "S");
        game.makeMove(0, 1, "S");
        assertEquals("Red player should have 0 point", 0, game.getRedPlayerScore());
    }

    @Test
    public void testHumanPlayer() {
        GameService gameService = new GameService();
        gameService.createGame(3, "simple", "human", "computer");
        assertEquals("Current player should be 'GREEN'", "GREEN", gameService.getCurrentPlayer());
        assertEquals("Current player type should be 'human'", "human", gameService.getCurrentPlayerType());
    }

    @Test
    public void testComputerPlayer() {
        GameService gameService = new GameService();
        gameService.createGame(3, "simple", "computer", "human");
        assertEquals("Current player should be 'GREEN'", "GREEN", gameService.getCurrentPlayer());
        assertEquals("Current player type should be 'computer'", "computer", gameService.getCurrentPlayerType());
    }

    @Test
    public void testRandomComputerMove() {
        GameService gameService = new GameService();
        gameService.createGame(3, "simple", "computer", "human");
        gameService.makeMoveComputer();
        String[][] board = gameService.getBoard();
        boolean moveMade = false;
        for (String[] row : board) {
            for (String column : row) {
                if (column != null) {
                    moveMade = true;
                    break;
                }
            }
        }
        assertTrue("Computer should make a random move", moveMade);
    }

    @Test
    public void testCompleteSOSMove() {
        GameService gameService = new GameService();
        gameService.createGame(3, "simple", "computer", "human");
        gameService.makeMove(0, 0, "S");
        gameService.makeMove(0, 1, "O");
        gameService.makeMoveComputer();

        assertEquals("Game should be won by 'GREEN'", "GREEN WON", gameService.getGameStatus());
    }

    @Test
    public void testComputerDelay() {
        GameService gameService = new GameService();
        gameService.createGame(3, "simple", "computer", "human");
        long startTime = System.currentTimeMillis();
        gameService.makeMoveComputer();
        long endTime = System.currentTimeMillis();
        assertTrue("Computer should wait at least 1 second before making a move", endTime - startTime >= 1000);
    }
}