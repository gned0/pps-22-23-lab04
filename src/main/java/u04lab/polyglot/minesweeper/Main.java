package u04lab.polyglot.minesweeper;

import e2.presentation.GUI;

public class Main {

    private final static int SIZE = 7;
    private final static int NUMBER_OF_MINES = 3;
    public static void main(String[] args) {
        new GUI(SIZE, NUMBER_OF_MINES);
    }
}
