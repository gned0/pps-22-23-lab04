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
  private val _minesList: List[(Int, Int)] =
    Stream.toList(
      Stream.take(Stream.iterate((0, 0))(_ => (_r.nextInt(size-1), _r.nextInt(size-1)))
      )(mines)
    )
  private var _selected: List[(Int, Int)] = Nil()

  def neighbors(x: Int, y: Int): Int =
    var count = 0
    for(i <- x - 1 to x + 1)
      for(j <- y - 1 to y + 1)
        if List.contains(_minesList, (i, j)) then count = count + 1
    count

  def hit(x: Int, y: Int): java.util.Optional[Integer] = (x, y) match
    case (_, _) if List.contains(_minesList, (x, y)) => OptionToOptional(None())
    case _ => _selected = Cons((x, y), _selected)
              OptionToOptional(Some(neighbors(x, y)))

  def won(): Boolean = length(_minesList) + length(_selected) == size*size

