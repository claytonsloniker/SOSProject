package com.sos.game;

import java.util.Objects;

public class SosSequence {
    private final int startRow;
    private final int startCol;
    private final int endRow;
    private final int endCol;
    private String color;

    public SosSequence(int startRow, int startCol, int endRow, int endCol, String color) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        // Compares objects based on content rather than memory address
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SosSequence that = (SosSequence) o;
        return startRow == that.startRow && startCol == that.startCol &&
                endRow == that.endRow && endCol == that.endCol && color.equals(that.color);
    }

    @Override
    public int hashCode() {
        // Generates a hash code based on the content of the object
        return Objects.hash(startRow, startCol, endRow, endCol, color);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }
}