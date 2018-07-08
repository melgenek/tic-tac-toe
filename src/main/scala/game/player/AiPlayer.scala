package game.player

import game.board.{Board, Point}
import game.error.StepError

import scala.util.Random

class AiPlayer(val id: PlayerId) extends Player {

  override def nextStep(board: Board): Either[StepError, Point] = {
    val (width, height) = board.size

    Right(Random.nextInt(width), Random.nextInt(height))
  }

}
