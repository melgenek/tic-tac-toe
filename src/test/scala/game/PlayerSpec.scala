package game

import java.io.ByteArrayInputStream

import game.player.{AiPlayer, InputStreamPlayer, PlayerId}
import org.scalatest.{FlatSpec, Matchers}
import game.player.PlayerId._

class PlayerSpec extends FlatSpec with Matchers {

  "ai player" should "return random point" in {
    val player = new AiPlayer(playerId)

    for (_ <- 0 to 100) {
      val point: Point = player.nextStep(board).right.get
      board.step(playerId, point) shouldBe a[Right[_, _]]
    }
  }

  "human player" should "parse input value" in {
    val input = new ByteArrayInputStream("   3   ,   2 ".getBytes)

    val player = new InputStreamPlayer(playerId, input)

    player.nextStep(board) should be(Right(Point(3, 2)))
  }

  it should "not parse invalid input value" in {
    val input = new ByteArrayInputStream("invalid input".getBytes)

    val player = new InputStreamPlayer(playerId, input)

    player.nextStep(board) should be(Left(WrongPointFormatError()))
  }


  it should "not parse more than 2 coordinates" in {
    val input = new ByteArrayInputStream("2,3,4".getBytes)

    val player = new InputStreamPlayer(playerId, input)

    player.nextStep(board) should be(Left(WrongPointFormatError()))
  }

  val board = new Board()
  val playerId: PlayerId = 1.toPlayerId

}
