package game

import java.io.ByteArrayOutputStream

import game.display.OutputStreamDisplay
import game.player.PlayerId
import game.player.PlayerId._
import org.scalatest.{FlatSpec, Matchers}

class OutputStreamDisplaySpec extends FlatSpec with Matchers {

  "displayError" should "write error message" in new Wiring {
    display.displayError(AlreadyOccupiedError())

    out.toString should not be empty
  }

  "displayWinner" should "write winner message" in new Wiring {
    display.displayWinner(1.toPlayerId)

    out.toString should be("Player #1 is the winner!\n")
  }

  "displayNoWinner" should "write winner message" in new Wiring {
    display.displayNoWinner()

    out.toString should not be empty
  }

  "displayBoard" should "write position of occupied cells" in new Wiring {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board().step(player1, (0, 0))
      board <- board.step(player1, (0, 1))
      board <- board.step(player2, (1, 1))
    } yield board

    display.displayBoard(updatedBoard.right.get)

    out.toString should be(
      """|_____________
         || X | X |   |
         |_____________
         ||   | Y |   |
         |_____________
         ||   |   |   |
         |_____________
         |""".stripMargin)
  }

  private trait Wiring {
    val player1: PlayerId = 1.toPlayerId
    val player2: PlayerId = 2.toPlayerId
    val playerChars = Map(player1 -> 'X', player2 -> 'Y')

    val out = new ByteArrayOutputStream()

    val display = new OutputStreamDisplay(out, playerChars)
  }

}
