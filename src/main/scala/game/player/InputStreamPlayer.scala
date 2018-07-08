package game.player

import java.io.InputStream

import game.board.{Board, Point}
import game.error.{StepError, WrongPointFormatError}

import scala.io.Source
import scala.util.Try


class InputStreamPlayer(val id: PlayerId, in: InputStream) extends Player {

  private val reader = Source.fromInputStream(in).bufferedReader()

  override def nextStep(board: Board): Either[StepError, Point] =
    Try {
      val Array(x, y) = reader.readLine().split(",")
      Point(x.trim.toInt, y.trim.toInt)
    }.toEither.left.map(_ => WrongPointFormatError())

}
