package u04lab.polyglot.a01b
import scala.jdk.javaapi.OptionConverters
import u04lab.polyglot.OptionToOptional
import u04lab.code.{List, Option, Stream}
import u04lab.code.Option.*

import scala.util.Random
import u04lab.code.List.*

import scala.annotation.tailrec

case class Position(x: Int, y: Int)
case class Cell(pos: Position, content: Int)
/** solution and descriptions at https://bitbucket.org/mviroli/oop2019-esami/src/master/a01b/sol2/ */
class LogicsImpl(private val size: Int, private val mines: Int) extends Logics:

  private val _r: Random = Random()
  val _cells: List[Cell] = generateCells(size)
  var _discoveredCells: List[Cell] = Nil()
  @tailrec
  private def generateMinesPositions(minesLeft: Int, acc: List[Position]): List[Position] =
    if minesLeft == 0 then acc
    else
      val newMine = Position(_r.nextInt(size), _r.nextInt(size))
      if !contains(acc, newMine) then // Ensure no duplicate mine positions
        generateMinesPositions(minesLeft - 1, Cons(newMine, acc))
      else
        generateMinesPositions(minesLeft, acc)

  private def generateCells(size: Int): List[Cell] =
    val minesPositions: List[Position] = generateMinesPositions(mines, Nil())
    val cells = for
      x <- 0 until size
      y <- 0 until size
    yield Cell(Position(x, y), if contains(minesPositions, Position(x, y)) then -1 else computeAdjacentMines(x, y, minesPositions))
    List(cells: _*)

  private def computeNeighbors(x: Int, y: Int): List[(Int, Int)] =
    var neighbors: List[(Int, Int)] = Nil()
    for(i <- x - 1 to x + 1)
      for(j <- y - 1 to y + 1)
        if(isBound(i) && isBound(j))
          neighbors = Cons((i,j), neighbors)
    List.filter(neighbors)(_ != (x, y))

  private def isBound(n: Int): Boolean = n >= 0 && n < size

  private def computeAdjacentMines(i: Int, i1: Int, value: List[Position]): Int =
    val neighbors = computeNeighbors(i, i1)
    length(List.filter(neighbors)(e => contains(value, Position(e._1, e._2))))
  
  def discover(x: Int, y: Int): java.util.Optional[Integer] =
    val cell = orElse(find(_cells)(c => c.pos.x == x && c.pos.y == y), Cell(Position(x, y), 0))
    if cell.content == -1 then java.util.Optional.empty()
    else
      _discoveredCells = Cons(cell, _discoveredCells)
      if cell.content == 0 then
        foreach(computeNeighbors(x, y))(e => discover(e._1, e._2))
      OptionToOptional(Some(cell.content))
  
  def won(): Boolean = length(_discoveredCells) + mines == size * size 
