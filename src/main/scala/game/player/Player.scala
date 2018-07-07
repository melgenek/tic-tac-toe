package game.player

import game.{Board, Point, StepError}

trait Player {

  val id: PlayerId

  def nextStep(board: Board): Either[StepError, Point]

}

