package u04lab.polyglot.a01b
import scala.jdk.javaapi.OptionConverters
import u04lab.polyglot.OptionToOptional
import u04lab.code.{List, Option, Stream}
import u04lab.code.Option.*
import scala.util.Random
import u04lab.code.List.*
import u04lab.code.Stream.*

/** solution and descriptions at https://bitbucket.org/mviroli/oop2019-esami/src/master/a01b/sol2/ */
class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:

  private val _r: Random = Random()
  // when constructed, fill with "mines" pairs of random ints in range(0, size - 1)
  /* private val _minesList: List[(Int, Int)] =
    Stream.toList(
      Stream.take(Stream.iterate((0, 0))(_ => (_r.nextInt(size-1), _r.nextInt(size-1)))
      )(mines)
    ) */
  private var _minesList: List[(Int, Int)] = Nil()
  private var _selected: List[(Int, Int)] = Nil()
  while(List.length(_minesList) != mines)
    _minesList = Cons((_r.nextInt(size-1), _r.nextInt(size-1)), _minesList)

  private def computeNeighbors(x: Int, y: Int): List[(Int, Int)] =
    var neighbors: List[(Int, Int)] = Nil()
    for(i <- x - 1 to x + 1)
      for(j <- y - 1 to y + 1)
          neighbors = Cons((i,j), neighbors)
    neighbors


  def hit(x: Int, y: Int): java.util.Optional[Integer] = (x, y) match
    case (_, _) if List.contains(_minesList, (x, y)) => OptionToOptional(None())
    case _ => _selected = Cons((x, y), _selected)
              val neighbors = computeNeighbors(x, y)
              val adjMines = List.length(List.filter(neighbors)(e => List.contains(_minesList, e)))
              if(adjMines == 0)
                ???
              OptionToOptional(Some(adjMines))

  def won(): Boolean = length(_minesList) + length(_selected) == size*size

