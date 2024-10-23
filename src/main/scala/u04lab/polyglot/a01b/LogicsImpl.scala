package u04lab.polyglot.a01b

import u04lab.polyglot.{OptionToOptional, Pair}
import u04lab.code.{List, Option}
import u04lab.code.Option.*
import scala.util.Random
import u04lab.code.List.*
import scala.annotation.tailrec

case class Position(x: Int, y: Int)
case class Cell(pos: Position, content: Int)

class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:

  private val MINE: Int = -1
  private val UNDISCOVERED: Int = -2
  private val _r: Random = Random()
  private val _cells: List[Cell] = generateCells(size)
  private var _discoveredCells: List[Position] = Nil()

  @tailrec
  private def generateMinePositions(minesLeft: Int, acc: List[Position]): List[Position] =
    if minesLeft == 0 then acc
    else
      val newMine = Position(_r.nextInt(size), _r.nextInt(size))
      if !contains(acc, newMine) then
        generateMinePositions(minesLeft - 1, Cons(newMine, acc))
      else
        generateMinePositions(minesLeft, acc)

  private def generateCells(size: Int): List[Cell] =
    val minePositions: List[Position] = generateMinePositions(mines, Nil())
    val cells = for
      x <- 0 until size
      y <- 0 until size
    yield Cell(Position(x, y), if contains(minePositions, Position(x, y)) then MINE else computeAdjacentMines(x, y, minePositions))
    List(cells: _*)

  private def computeNeighbors(x: Int, y: Int): List[(Int, Int)] =
    val neighbors = for {
      i <- (x - 1) to (x + 1)
      j <- (y - 1) to (y + 1)
      if isBound(i) && isBound(j) && (i != x || j != y)
    } yield (i, j)
    List(neighbors: _*)

  private def isBound(n: Int): Boolean = n >= 0 && n < size

  private def computeAdjacentMines(x: Int, y: Int, minePositions: List[Position]): Int =
    length(List.filter(computeNeighbors(x, y))(e => contains(minePositions, Position(e._1, e._2))))

  def discover(x: Int, y: Int): java.util.Optional[Integer] =
    val position = Position(x, y)
    val cell = orElse(find(_cells)(c => c.pos == position), Cell(position, 0))
    if cell.content == MINE then java.util.Optional.empty()
    else
      _discoveredCells = Cons(cell.pos, _discoveredCells)
      if cell.content == 0 then
        foreach(computeNeighbors(x, y)){
          n => if !contains(_discoveredCells, Position(n._1, n._2)) then discover(n._1, n._2)
        }
      OptionToOptional(Some(cell.content))

  override def getCellState(x: Int, y: Int): Integer =
    val position = Position(x, y)
    val discovered = filter(_cells)(c => contains(_discoveredCells, c.pos))
    val cell = orElse(find(discovered)(c => c.pos == position), Cell(position, UNDISCOVERED))
    cell.content

  def won(): Boolean = length(_discoveredCells) + mines == size * size