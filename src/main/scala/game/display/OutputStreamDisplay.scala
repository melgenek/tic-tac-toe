package game.display

import java.io.{OutputStream, PrintWriter}

import game.board.Board
import game.error.{AlreadyOccupiedError, StepError, WrongPointError, WrongPointFormatError}
import game.player.PlayerId

class OutputStreamDisplay(out: OutputStream, playerChars: Map[PlayerId, Char]) extends GameDisplay {

  private val writer = new PrintWriter(out)

  override def displayBoard(board: Board): Unit = {
    val (width, height) = board.size

    writeLine("____" * width + "_")
    for (i <- 0 until width) {
      writer.write("|")
      for (j <- 0 until height) {
        val ch: Char = board.cell(i, j).map(playerChars).getOrElse(' ')
        writer.write(s" $ch |")
      }
      writer.println()
      writeLine("____" * width + "_")
    }
  }

  override def displayError(stepError: StepError): Unit =
    stepError match {
      case _: AlreadyOccupiedError => writeLine("The cell has been already occupied")
      case _: WrongPointError => writeLine("The coordinates are out of field")
      case _: WrongPointFormatError => writeLine("The format of coordinates should be 'x,y'")
    }

  override def displayWinner(id: PlayerId): Unit =
    writeLine(s"Player #$id('${playerChars(id)}') is the winner!")

  override def displayNoWinner(): Unit =
    writeLine("No winner :(")

  private def writeLine(str: String): Unit = {
    writer.write(str)
    writer.println()
    writer.flush()
  }

}
