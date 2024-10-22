package u04lab.polyglot.minesweeper.logic;

import e2.structures.Pair;

import java.util.List;
import java.util.Set;

public interface Logics {

    List<Pair<Integer, Integer>> getMines();
    Set<Pair<Integer, Integer>> getCounters();
    List<Pair<Integer, Integer>> getFlags();
    boolean mineHit(Pair<Integer, Integer> position);
    boolean isGameWon();
    List<Pair<Integer, Integer>> computeAdjacentMines(Pair<Integer, Integer> position);
    List<Pair<Integer, Integer>> computeAdjacentTiles(Pair<Integer, Integer> position);
    void addCounters(Pair<Integer, Integer> position);
    void addFlag(Pair<Integer, Integer> position);

}
