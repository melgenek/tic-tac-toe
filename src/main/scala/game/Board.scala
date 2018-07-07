package game

import game.Board.StepError
import game.Board.StepError.{AlreadyOccupiedError, WrongPointError}

class Board(private val width: Int, private val height: Int) {

  assert(height >= 3 && height <= 10)
  assert(width >= 3 && width <= 10)

  private val board: Array[Array[Player]] = Array.ofDim[Player](width, height)

  def this() {
    this(3, 3)
  }

  def size: (Int, Int) = (width, height)

  def cell(point: Point): Option[Player] =
    Option(board(point.x)(point.y))

  def step(player: Player, point: Point): Either[StepError, Unit] =
    if (notValid(point)) Left(WrongPointError())
    else if (isOccupied(point)) Left(AlreadyOccupiedError())
    else {
      board(point.x)(point.y) = player
      Right()
    }

  private def notValid(point: Point): Boolean =
    point.x < 0 || point.x >= width || point.y < 0 || point.y >= height

  private def isOccupied(point: Point): Boolean =
    board(point.x)(point.y) != null

}

object Board {

  trait StepError

  object StepError {

    case class AlreadyOccupiedError() extends StepError

    case class WrongPointError() extends StepError

  }

}


