package game.win

import game.{Board, Player}


class ThreeInRowWinStrategy extends AbstractLengthWinStrategy(3) {

  override def findWinner(board: Board): Option[Player] = {
    val (width, height) = board.size

    val cellPlayers: Seq[Option[Player]] = for {
      i <- 0 until width
      j <- 0 until height
    } yield board.cell(i, j)

    findWinnerInFlattenedBoard(cellPlayers)
  }
}

