package game.win

import game.player.PlayerId

import scala.util.control.Breaks._

abstract class AbstractLengthWinStrategy(expectedLength: Int) extends WinStrategy {

  protected def findWinnerInFlattenedBoard(cellPlayers: Seq[Option[PlayerId]]): Option[PlayerId] = {
    val (player, length) = cellPlayers.foldLeft((Option.empty[PlayerId], 0)) { case ((currentPlayer, currentLength), cellPlayer) =>
      if (currentLength >= expectedLength) (currentPlayer, currentLength)
      else checkCell(currentPlayer, cellPlayer, currentLength)
    }

    if (length >= expectedLength) player
    else None
  }

  private def checkCell(currentPlayer: Option[PlayerId], cellPlayer: Option[PlayerId], length: Int): (Option[PlayerId], Int) =
    if (currentPlayer == cellPlayer) (currentPlayer, length + 1)
    else if (cellPlayer.nonEmpty) (cellPlayer, 1)
    else (Option.empty[PlayerId], 0)


}
