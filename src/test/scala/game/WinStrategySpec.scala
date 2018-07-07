package game

import game.Player._
import game.win.{ThreeInRowWinStrategy, TwoInColumnWinStrategy}
import org.scalatest.{FlatSpec, Matchers}

class WinStrategySpec extends FlatSpec with Matchers {

  "three in a row" should "find a winner if there are 3 cells in a row for one player" in {
    val board = new Board()
    board.step(player1, (0, 0))
    board.step(player1, (0, 1))
    board.step(player1, (0, 2))

    threeInRow.findWinner(board) should be(Some(player1))
  }

  it should "not find a winner if there are no 3 cells in a row for one player" in {
    val board = new Board(4, 4)
    board.step(player1, (0, 0))
    board.step(player1, (0, 1))
    board.step(player2, (0, 2))
    board.step(player1, (0, 3))

    threeInRow.findWinner(board) should be(None)
  }

  "two in a column" should "find a winner if there are 2 cells in a column for one player" in {
    val board = new Board()
    board.step(player1, (1, 0))
    board.step(player1, (2, 0))

    twoInColumn.findWinner(board) should be(Some(player1))
  }

  it should "not find a winner if there are no 2 cells in a column for one player" in {
    val board = new Board(4, 4)
    board.step(player1, (0, 0))
    board.step(player2, (2, 0))
    board.step(player1, (3, 0))

    threeInRow.findWinner(board) should be(None)
  }

  "composed strategy" should "not find a winner if there are no 3 cells in a row for one player" in {
    val board = new Board()
    board.step(player1, (0, 0))
    board.step(player1, (0, 1))
    board.step(player1, (0, 2))

    composed.findWinner(board) should be(Some(player1))
  }

  it should "find a winner if there are 2 cells in a column for one player" in {
    val board = new Board()
    board.step(player1, (1, 0))
    board.step(player1, (2, 0))

    composed.findWinner(board) should be(Some(player1))
  }

  it should "not find a winner if there are no preconditions" in {
    val board = new Board()

    composed.findWinner(board) should be(None)
  }

  private val threeInRow = new ThreeInRowWinStrategy()
  private val twoInColumn = new TwoInColumnWinStrategy()
  private val composed = threeInRow or twoInColumn
  private val player1 = 1.toPlayer
  private val player2 = 2.toPlayer

}
