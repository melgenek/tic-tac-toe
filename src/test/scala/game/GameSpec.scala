package game

import game.Game.CircularCounter
import game.display.GameDisplay
import game.player.PlayerId._
import game.player.{Player, PlayerId}
import game.win.WinStrategy
import org.mockito.InOrder
import org.mockito.Matchers.any
import org.mockito.Mockito.{verify, when, inOrder => ordered}
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{FlatSpec, Matchers}

class GameSpec extends FlatSpec with Matchers with MockitoSugar {

  "CircularCounter.inc" should "increment counter" in {
    CircularCounter(0, 5).inc should be(CircularCounter(1, 5))
  }

  it should "start from the beginning when last value" in {
    CircularCounter(4, 5).inc should be(CircularCounter(0, 5))
  }

  "play" should "ask next player for a step before finish" in new Wiring {
    game.play()

    val order: InOrder = ordered(gameDisplay, player)
    order.verify(gameDisplay).displayBoard(any())
    order.verify(player).nextStep(any())
  }

  it should "show result when the game is finished" in new Wiring {
    game.play()

    verify(gameDisplay).displayWinner(playerId)
  }

  it should "show error when the step is wrong" in new Wiring {
    val error = WrongPointFormatError()
    when(player.nextStep(board)).thenReturn(Left(error), Right(point))

    game.play()

    verify(gameDisplay).displayError(error)
  }

  it should "show error when the board step result is wrong" in new Wiring {
    val error = WrongPointError()
    when(board.step(any(), any())).thenReturn(Left(error), Right(board))

    game.play()

    verify(gameDisplay).displayError(error)
  }

  it should "finish game when no space left on the board" in new Wiring {
    when(winStrategy.findWinner(any())).thenReturn(None)
    when(board.hasSpace).thenReturn(false)

    game.play()

    verify(gameDisplay).displayNoWinner()
  }

  private trait Wiring {
    val point = Point(1, 1)
    val playerId: PlayerId = 1.toPlayerId
    val board: Board = mock[Board]
    when(board.step(any(), any())).thenReturn(Right(board))
    when(board.hasSpace).thenReturn(false)

    val player: Player = mock[Player]
    when(player.nextStep(board)).thenReturn(Right(point))
    val players = Seq(player)

    val winStrategy: WinStrategy = mock[WinStrategy]
    when(winStrategy.findWinner(any())).thenReturn(Some(playerId))

    val gameDisplay: GameDisplay = mock[GameDisplay]

    val game = new Game(board, players, winStrategy, gameDisplay)
  }

}
