package game.win

import game.player.PlayerId

import scala.util.control.Breaks._

abstract class AbstractLengthWinStrategy(expectedLength: Int) extends WinStrategy {

  protected def findWinnerInFlattenedBoard(cellPlayers: Seq[Option[PlayerId]]): Option[PlayerId] = {
    var player: Option[PlayerId] = None
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

  private def checkCell(currentPlayer: Option[PlayerId], cellPlayer: Option[PlayerId], length: Int): (Option[PlayerId], Int) =
    if (currentPlayer == cellPlayer) (currentPlayer, length + 1)
    else if (cellPlayer.nonEmpty) (cellPlayer, 1)
    else (Option.empty[PlayerId], 0)


}
