package game.win

import game.{Board, Player}

import scala.collection.immutable
import scala.util.control.Breaks._


class TwoInColumnWinStrategy extends AbstractLengthWinStrategy(2) {

  override def findWinner(board: Board): Option[Player] = {
    val (width, height) = board.size

    val cellPlayers: Seq[Option[Player]] = for {
      j <- 0 until height
      i <- 0 until width
    } yield board.cell(i, j)

    findWinnerInFlattenedBoard(cellPlayers)
  }

}

