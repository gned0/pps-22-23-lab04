package u04lab.polyglot.a01b;
import u04lab.polyglot.Pair;

import java.util.List;
import java.util.Optional;

/** solution and descriptions at https://bitbucket.org/mviroli/oop2019-esami/src/master/a01b/sol2/ */
public interface Logics {
    Optional<Integer> discover(int x, int y);

    boolean won();
    
    List<Pair<Integer, Integer>> getState();
}
