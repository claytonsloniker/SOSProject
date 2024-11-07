package com.sos.game;

public abstract class Player {
    protected String color; // "GREEN" or "RED"

    public Player(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract void makeMove(Game game);
}