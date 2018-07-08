package game

import game.board.Board
import game.error.StepError
import game.player.PlayerId._
import game.win.{ThreeInColumnWinStrategy, ThreeInDiagonalWinStrategy, ThreeInRowWinStrategy}
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

  "two in a column" should "find a winner if there are 3 cells in a column for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board().step(player1, (0, 0))
      board <- board.step(player1, (1, 0))
      board <- board.step(player1, (2, 0))
    } yield board

    threeInColumn.findWinner(updatedBoard.right.get) should be(Some(player1))
  }

  it should "not find a winner if there are no 3 cells in a column for one player" in {
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

  it should "find a winner if there are 3 cells in a column for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board().step(player1, (0, 0))
      board <- board.step(player1, (1, 0))
      board <- board.step(player1, (2, 0))
    } yield board

    composed.findWinner(updatedBoard.right.get) should be(Some(player1))
  }

  it should "not find a winner if there are no preconditions" in {
    val board = new Board()

    composed.findWinner(board) should be(None)
  }

  "diagonal strategy" should "find a winner if there are 3 cells in the main diagonal for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board(3, 4).step(player1, (0, 0))
      board <- board.step(player1, (1, 1))
      board <- board.step(player1, (2, 2))
    } yield board

    diagonalStrategy.findWinner(updatedBoard.right.get) should be(Some(player1))
  }

  it should "find a winner if there are 3 cells in the anti diagonal for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board(3, 4).step(player1, (2, 0))
      board <- board.step(player1, (1, 1))
      board <- board.step(player1, (0, 2))
    } yield board

    diagonalStrategy.findWinner(updatedBoard.right.get) should be(Some(player1))
  }

  it should "find a winner if there are no 3 cells in a diagonal for one player" in {
    val updatedBoard: Either[StepError, Board] = for {
      board <- new Board().step(player1, (0, 2))
    } yield board

    diagonalStrategy.findWinner(updatedBoard.right.get) should be(None)
  }

  private val threeInRow = new ThreeInRowWinStrategy()
  private val threeInColumn = new ThreeInColumnWinStrategy()
  private val composed = threeInRow or threeInColumn
  private val diagonalStrategy = new ThreeInDiagonalWinStrategy()
  private val player1 = 1.toPlayerId
  private val player2 = 2.toPlayerId

}
