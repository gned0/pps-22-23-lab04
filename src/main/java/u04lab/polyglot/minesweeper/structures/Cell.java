package u04lab.polyglot.minesweeper.structures;

enum CellType{
    MINE,
    COUNTER,
    FLAG;
}

public interface Cell {

    public Pair<Integer, Integer> getPosition();
    public CellType getCellType();

}
