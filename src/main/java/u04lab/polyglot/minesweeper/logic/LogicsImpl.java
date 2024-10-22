package u04lab.polyglot.minesweeper.logic;

import e2.structures.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class LogicsImpl implements Logics {

    private final int size;
    private List<Pair<Integer, Integer>> mines;
    private Set<Pair<Integer, Integer>> counters = new HashSet<>();
    private List<Pair<Integer, Integer>> flags = new ArrayList<>();
    private final Random random = new Random();
    public LogicsImpl(int size, int numberOfMines) {
        this.size = size;
        this.mines = new ArrayList<>();
        for(int i=0; i<numberOfMines; i++) {
            this.mines.add(this.randomEmptyPosition());
        }
    }

    public LogicsImpl(int size, List<Pair<Integer, Integer>> mines) {
        this.size = size;
        this.mines = mines;
    }

    private Pair<Integer,Integer> randomEmptyPosition(){
        Pair<Integer,Integer> pos = new Pair<>(this.random.nextInt(size),this.random.nextInt(size));
        // the recursive call below prevents clash with an existing pawn
        return this.mines!=null && this.mines.equals(pos) ? randomEmptyPosition() : pos;
    }

    @Override
    public List<Pair<Integer, Integer>> getMines() {
        return mines;
    }

    public Set<Pair<Integer, Integer>> getCounters() {
        return counters;
    }

    public List<Pair<Integer, Integer>> getFlags() {
        return flags;
    }

    @Override
    public boolean mineHit(Pair<Integer, Integer> position) {
        if (!this.isInBoundaries(position)) {
            throw new IndexOutOfBoundsException();
        }
        return mines.contains(position);
    }

    @Override
    public boolean isGameWon() {
        var totalTiles = size*size;
        System.out.println(totalTiles - counters.size());
        return (totalTiles - counters.size()) == mines.size();
    }


    @Override
    public List<Pair<Integer, Integer>> computeAdjacentMines(Pair<Integer, Integer> position) {
        List<Pair<Integer, Integer>> adjacentTiles = this.computeAdjacentTiles(position);
        return mines.stream().filter(adjacentTiles::contains).collect(Collectors.toList());
    }

    @Override
    public List<Pair<Integer, Integer>> computeAdjacentTiles(Pair<Integer, Integer> position) {
        List<Pair<Integer, Integer>> adjacentTiles = new ArrayList<>();
        for(int i = position.getX() - 1; i <= position.getX() + 1; i++) {
            for(int j = position.getY() - 1; j <= position.getY() + 1; j++) {
                var tile = new Pair<>(i, j);
                if(this.isInBoundaries(tile)) {
                    adjacentTiles.add(tile);
                    adjacentTiles.remove(position);
                }
            }
        }
        return adjacentTiles;
    }
    @Override
    public void addCounters(Pair<Integer, Integer> position) {
        this.counters.add(position);
        var adjacentTiles = this.computeAdjacentTiles(position);
        if(this.computeAdjacentMines(position).size() == 0) {
            for(var elem : adjacentTiles) {
                if(!this.counters.contains(elem)){
                    this.counters.add(elem);
                    this.addCounters(elem);
                }
            }
        }
    }

    @Override
    public void addFlag(Pair<Integer, Integer> position) {
        this.flags.add(position);
    }

    private boolean isInBoundaries(Pair<Integer, Integer> position) {
        return !(position.getX()<0 || position.getY()<0 ||
                position.getX() >= this.size || position.getY() >= this.size);
    }
}
