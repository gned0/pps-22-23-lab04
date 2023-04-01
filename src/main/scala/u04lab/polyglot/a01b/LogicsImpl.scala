package u04lab.polyglot.a01b
import scala.jdk.javaapi.OptionConverters
import u04lab.polyglot.OptionToOptional
import u04lab.code.Option
import u04lab.code.Option.*
import scala.util.Random
import u04lab.code.List
import u04lab.code.List.*
import u04lab.code.Stream

/** solution and descriptions at https://bitbucket.org/mviroli/oop2019-esami/src/master/a01b/sol2/ */
class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:

  private val _r: Random = Random()
  // when constructed fill with "mines" pairs of random ints in range(0, size - 1)
  private val _minesSet: List[(Int, Int)] = ???
  private val _selected: List[(Int, Int)] = Nil()

  def hit(x: Int, y: Int): java.util.Optional[Integer] =
    OptionToOptional(None()) // Option => Optional converter

  def won(): Boolean = length(_minesSet) + length(_selected) == size*size

