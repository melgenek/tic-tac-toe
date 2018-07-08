package game.player

import game.board.{Board, Point}
import game.error.StepError

trait Player {

  val id: PlayerId

  def nextStep(board: Board): Either[StepError, Point]

}

