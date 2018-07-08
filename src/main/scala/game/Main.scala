package game

import game.board.Board
import game.display.OutputStreamDisplay
import game.player.{AiPlayer, InputStreamPlayer}
import game.player.PlayerId._
import game.win.{ThreeInColumnWinStrategy, ThreeInDiagonalWinStrategy, ThreeInRowWinStrategy}

object Main extends App {

  val winStrategy = {
    val threeInRow = new ThreeInRowWinStrategy()
    val twoInColumn = new ThreeInColumnWinStrategy()
    val diagonalStrategy = new ThreeInDiagonalWinStrategy()
    diagonalStrategy
  }

  val players = Seq(
    new InputStreamPlayer(1.toPlayerId, System.in),
    new InputStreamPlayer(2.toPlayerId, System.in),
    new AiPlayer(3.toPlayerId)
  )

  val board = new Board(3, 3)

  val gameDisplay = new OutputStreamDisplay(System.out, Map(
    1.toPlayerId -> 'X',
    2.toPlayerId -> 'Y',
    3.toPlayerId -> 'O'
  ))

  val game = new Game(board, players, winStrategy, gameDisplay)

  game.play()

}
