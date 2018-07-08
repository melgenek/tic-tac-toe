package game.win

import game.board.Board
import game.player.PlayerId


class ThreeInRowWinStrategy extends AbstractLengthWinStrategy(3) {

  override def findWinner(board: Board): Option[PlayerId] = {
    val (width, height) = board.size

    val cellPlayers: Seq[Option[PlayerId]] = for {
      i <- 0 until width
      j <- 0 until height
    } yield board.cell(i, j)

    findWinnerInFlattenedBoard(cellPlayers)
  }
}

