package game.win

import game.Player

import scala.util.control.Breaks._

abstract class AbstractLengthWinStrategy(expectedLength: Int) extends WinStrategy {

  protected def findWinnerInFlattenedBoard(cellPlayers: Seq[Option[Player]]): Option[Player] = {
    var player: Option[Player] = None
    var length = 0

    breakable {
      for (cellPlayer <- cellPlayers) {
        val (newPlayer, newLength) = checkCell(player, cellPlayer, length)
        if (newPlayer.nonEmpty && newLength == expectedLength) {
          break()
        } else {
          player = newPlayer
          length = newLength
        }
      }
    }

    player
  }

  private def checkCell(currentPlayer: Option[Player], cellPlayer: Option[Player], length: Int): (Option[Player], Int) =
    if (currentPlayer == cellPlayer) (currentPlayer, length + 1)
    else if (cellPlayer.nonEmpty) (cellPlayer, 1)
    else (Option.empty[Player], 0)


}
