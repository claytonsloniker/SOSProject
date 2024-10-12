package com.sos.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/create")
    public void createGame(@RequestParam int size, @RequestParam String gameMode) {
        gameService.createGame(size, gameMode);
    }

    @GetMapping("/board")
    public String[][] getBoard() {
        return gameService.getBoard();
    }

    @GetMapping("/player")
    public String getCurrentPlayer() {
        return gameService.getCurrentPlayer();
    }

    @PostMapping("/move")
    public void makeMove(@RequestParam int row, @RequestParam int col, @RequestParam String letter) {
        gameService.makeMove(row, col, letter);
    }
}