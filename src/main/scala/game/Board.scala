package game

import game.player.PlayerId

case class Board private(width: Int, height: Int, board: Vector[Vector[Option[PlayerId]]]) {

  assert(height >= 3 && height <= 10)
  assert(width >= 3 && width <= 10)

  def this(width: Int, height: Int) {
    this(width, height, Vector.fill(width, height)(Option.empty[PlayerId]))
  }

  def this() {
    this(3, 3)
  }

  def size: (Int, Int) = (width, height)

  def cell(point: Point): Option[PlayerId] = board(point.x)(point.y)

  def step(player: PlayerId, point: Point): Either[StepError, Board] =
    if (notValid(point)) Left(WrongPointError())
    else if (isOccupied(point)) Left(AlreadyOccupiedError())
    else {
      val updatedRow: Vector[Option[PlayerId]] = board(point.x).updated(point.y, Some(player))
      val updatedBoard: Vector[Vector[Option[PlayerId]]] = board.updated(point.x, updatedRow)
      Right(copy(board = updatedBoard))
    }

  private def notValid(point: Point): Boolean =
    point.x < 0 || point.x >= width || point.y < 0 || point.y >= height

  private def isOccupied(point: Point): Boolean =
    board(point.x)(point.y).nonEmpty

}
