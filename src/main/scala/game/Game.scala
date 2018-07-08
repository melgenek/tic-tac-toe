package game

import game.Game.CircularCounter
import game.display.GameDisplay
import game.player.{Player, PlayerId}
import game.win.WinStrategy

import scala.util.Random

class Game(board: Board,
           players: Seq[Player],
           winStrategy: WinStrategy,
           gameDisplay: GameDisplay) {

  def play(): Unit = {
    val gamePlayers: Seq[Player] = Random.shuffle(players)
    val playersCount: Int = gamePlayers.size

    var gameBoard: Board = board
    var counter = CircularCounter(0, playersCount)
    var winner: Option[PlayerId] = None

    do {
      val nextPlayer = gamePlayers(counter.value)

      gameDisplay.displayBoard(gameBoard)
      val newBoardE: Either[StepError, Board] = for {
        nextStep <- nextPlayer.nextStep(gameBoard)
        newBoard <- gameBoard.step(nextPlayer.id, nextStep)
      } yield newBoard

      newBoardE match {
        case Right(newBoard) =>
          winner = winStrategy.findWinner(newBoard)
          gameBoard = newBoard
          counter = counter.inc
        case Left(error) => gameDisplay.displayError(error)
      }
    } while (winner.isEmpty && board.hasSpace)

    winner match {
      case Some(winnerId) => gameDisplay.displayWinner(winnerId)
      case None => gameDisplay.displayNoWinner()
    }
  }

}

object Game {

  case class CircularCounter(value: Int, total: Int) {
    def inc = CircularCounter(
      if (value + 1 == total) 0 else value + 1,
      total
    )
  }

}
