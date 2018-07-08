package game.display

import game.player.PlayerId
import game.{Board, StepError}

trait GameDisplay {

  def displayBoard(board: Board): Unit

  def displayError(stepError: StepError): Unit

  def displayWinner(id: PlayerId): Unit

  def displayNoWinner(): Unit

}
