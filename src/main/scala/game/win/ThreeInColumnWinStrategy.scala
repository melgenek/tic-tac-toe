package game.win

import game.board.Board
import game.player.PlayerId

import scala.collection.immutable
import scala.util.control.Breaks._


class ThreeInColumnWinStrategy extends AbstractLengthWinStrategy(3) {

  override def findWinner(board: Board): Option[PlayerId] = {
    val (width, height) = board.size

    val cellPlayers: Seq[Option[PlayerId]] = for {
      j <- 0 until height
      i <- 0 until width
    } yield board.cell(i, j)

    findWinnerInFlattenedBoard(cellPlayers)
  }

}

