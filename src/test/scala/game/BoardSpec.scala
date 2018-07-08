package game

import game.player.PlayerId
import game.player.PlayerId._
import org.scalatest.{FlatSpec, Matchers}

class BoardSpec extends FlatSpec with Matchers {

  "board" should "be created with default size" in {
    val board = new Board()
    board.size should be(3, 3)
  }

  it should "have configurable size" in {
    val board = new Board(5, 3)

    board.size should be(5, 3)
  }

  it should "not have height smaller than 3" in {
    intercept[AssertionError] {
      new Board(5, 2)
    }
  }

  it should "not have width smaller than 3" in {
    intercept[AssertionError] {
      new Board(2, 3)
    }
  }

  it should "not have height bigger than 10" in {
    intercept[AssertionError] {
      new Board(3, 11)
    }
  }

  it should "not have width bigger than 10" in {
    intercept[AssertionError] {
      new Board(11, 3)
    }
  }

  "step" should "occupy the board cell with player" in new Wiring {
    val updatedBoard: Board = board.step(player1, (0, 2)).right.get

    updatedBoard.cell(0, 2) should be(Some(player1))
  }

  it should "not modify original board" in new Wiring {
    val updatedBoard: Board = board.step(player1, (0, 2)).right.get

    updatedBoard.cell(0, 2) should be(Some(player1))
    board.cell(0, 2) should be(None)
  }

  it should "not allow to occupy the cell for the same user" in new Wiring {
    val updatedBoard: Board = board.step(player1, (0, 2)).right.get

    updatedBoard.step(player1, (0, 2)) should be(Left(AlreadyOccupiedError()))
  }

  it should "not allow to occupy the cell for another user" in new Wiring {
    val updatedBoard: Board = board.step(player1, (0, 2)).right.get

    updatedBoard.step(player2, (0, 2)) should be(Left(AlreadyOccupiedError()))
  }

  it should "not allow to occupy not existing cell" in new Wiring {
    board.step(player1, (-1, 2)) should be(Left(WrongPointError()))
    board.step(player1, (2, -1)) should be(Left(WrongPointError()))
    board.step(player1, (-1, -1)) should be(Left(WrongPointError()))

    board.step(player1, (100, 2)) should be(Left(WrongPointError()))
    board.step(player1, (2, 100)) should be(Left(WrongPointError()))
    board.step(player1, (100, 100)) should be(Left(WrongPointError()))

    board.step(player1, (100, -1)) should be(Left(WrongPointError()))
    board.step(player1, (-1, 100)) should be(Left(WrongPointError()))
  }

  "cell" should "return nothing when cell is not occupied" in new Wiring {
    board.cell(0, 2) should be(None)
  }

  "hasSpace" should "be true when there are empty cells" in new Wiring {
    board.hasSpace should be(true)
  }

  it should "be false when there are no empty cells" in new Wiring {
    val updatedBoard: Either[StepError, Board] = for {
      board <- board.step(player1, (0, 0))
      board <- board.step(player1, (1, 0))
      board <- board.step(player1, (2, 0))
      board <- board.step(player1, (0, 1))
      board <- board.step(player1, (1, 1))
      board <- board.step(player1, (2, 1))
      board <- board.step(player1, (0, 2))
      board <- board.step(player1, (1, 2))
      board <- board.step(player1, (2, 2))
    } yield board

    updatedBoard.right.get.hasSpace should be(false)
  }

  private trait Wiring {
    val board = new Board()
    val player1: PlayerId = 1.toPlayerId
    val player2: PlayerId = 2.toPlayerId
  }

}
