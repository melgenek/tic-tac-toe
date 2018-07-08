package game.win

import game.board.Board
import game.player.PlayerId

class ThreeInDiagonalWinStrategy extends AbstractLengthWinStrategy(3) {

  override def findWinner(board: Board): Option[PlayerId] = {
    val (width, height) = board.size

    val mainDiagonal: Seq[Option[PlayerId]] = for {
      j <- 0 until width if j < height
    } yield board.cell(j, j)

    val antiDiagonal: Seq[Option[PlayerId]] = for {
      j <- 0 until width if j < height
    } yield board.cell(width - j - 1, j)

    findWinnerInFlattenedBoard(mainDiagonal) orElse findWinnerInFlattenedBoard(antiDiagonal)
  }

}
