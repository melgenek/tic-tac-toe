package game

import game.ConfigReader.{height, playerChars, players, width}
import game.board.Board
import game.display.OutputStreamDisplay
import game.win.{ThreeInColumnWinStrategy, ThreeInDiagonalWinStrategy, ThreeInRowWinStrategy}

object Main extends App {

  val winStrategy = {
    val threeInRow = new ThreeInRowWinStrategy()
    val threeInColumn = new ThreeInColumnWinStrategy()
    val diagonalStrategy = new ThreeInDiagonalWinStrategy()
    threeInRow or threeInColumn or diagonalStrategy
  }

  val board = new Board(width, height)

  val gameDisplay = new OutputStreamDisplay(System.out, playerChars)

  val game = new Game(board, players, winStrategy, gameDisplay)

  game.play()

}
