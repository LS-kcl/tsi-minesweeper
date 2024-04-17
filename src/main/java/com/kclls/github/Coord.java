package com.kclls.github;

public class Coord {
    // Accessed publicly, but immutable
    public final int row;
    public final int col;

    public Coord(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public String getStrVersion() {
        return "(" + row + ", " + col + ")";
    }
}
