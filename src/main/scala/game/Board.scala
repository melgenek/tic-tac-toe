package game

import game.Board.StepError
import game.Board.StepError.{AlreadyOccupiedError, WrongPointError}

case class Board private(width: Int, height: Int, board: Vector[Vector[Option[Player]]]) {

  assert(height >= 3 && height <= 10)
  assert(width >= 3 && width <= 10)

  def this(width: Int, height: Int) {
    this(width, height, Vector.fill(width, height)(Option.empty[Player]))
  }

  def this() {
    this(3, 3)
  }

  def size: (Int, Int) = (width, height)

  def cell(point: Point): Option[Player] = board(point.x)(point.y)

  def step(player: Player, point: Point): Either[StepError, Board] =
    if (notValid(point)) Left(WrongPointError())
    else if (isOccupied(point)) Left(AlreadyOccupiedError())
    else {
      val updatedRow: Vector[Option[Player]] = board(point.x).updated(point.y, Some(player))
      val updatedBoard: Vector[Vector[Option[Player]]] = board.updated(point.x, updatedRow)
      Right(copy(board = updatedBoard))
    }

  private def notValid(point: Point): Boolean =
    point.x < 0 || point.x >= width || point.y < 0 || point.y >= height

  private def isOccupied(point: Point): Boolean =
    board(point.x)(point.y).nonEmpty

}

object Board {

  trait StepError

  object StepError {

    case class AlreadyOccupiedError() extends StepError

    case class WrongPointError() extends StepError

  }

}


