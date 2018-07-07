package game

import game.player.PlayerId._
import game.win.{ThreeInRowWinStrategy, TwoInColumnWinStrategy}
import org.scalatest.{FlatSpec, Matchers}

class WinStrategySpec extends FlatSpec with Matchers {

  "three in a row" should "find a winner if there are 3 cells in a row for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board().step(player1, (0, 0))
      board <- board.step(player1, (0, 1))
      board <- board.step(player1, (0, 2))
    } yield board

    threeInRow.findWinner(updatedBoard.right.get) should be(Some(player1))
  }

  it should "not find a winner if there are no 3 cells in a row for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board(4, 4).step(player1, (0, 0))
      board <- board.step(player1, (0, 1))
      board <- board.step(player2, (0, 2))
      board <- board.step(player1, (0, 3))
    } yield board

    threeInRow.findWinner(updatedBoard.right.get) should be(None)
  }

  "two in a column" should "find a winner if there are 2 cells in a column for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board().step(player1, (1, 0))
      board <- board.step(player1, (2, 0))
    } yield board

    twoInColumn.findWinner(updatedBoard.right.get) should be(Some(player1))
  }

  it should "not find a winner if there are no 2 cells in a column for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board(4, 4).step(player1, (1, 0))
      board <- board.step(player2, (2, 0))
      board <- board.step(player1, (3, 0))
    } yield board

    threeInRow.findWinner(updatedBoard.right.get) should be(None)
  }

  "composed strategy" should "not find a winner if there are no 3 cells in a row for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board().step(player1, (0, 0))
      board <- board.step(player1, (0, 1))
      board <- board.step(player1, (0, 2))
    } yield board

    composed.findWinner(updatedBoard.right.get) should be(Some(player1))
  }

  it should "find a winner if there are 2 cells in a column for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board().step(player1, (1, 0))
      board <- board.step(player1, (2, 0))
    } yield board

    composed.findWinner(updatedBoard.right.get) should be(Some(player1))
  }

  it should "not find a winner if there are no preconditions" in {
    val board = new Board()

    composed.findWinner(board) should be(None)
  }

  private val threeInRow = new ThreeInRowWinStrategy()
  private val twoInColumn = new TwoInColumnWinStrategy()
  private val composed = threeInRow or twoInColumn
  private val player1 = 1.toPlayerId
  private val player2 = 2.toPlayerId

}
