package game.player

import game.{Board, Point, StepError}

import scala.util.Random

class AiPlayer(val id: PlayerId) extends Player {

  override def nextStep(board: Board): Either[StepError, Point] = {
    val (width, height) = board.size

    Right(Random.nextInt(width), Random.nextInt(height))
  }

}
