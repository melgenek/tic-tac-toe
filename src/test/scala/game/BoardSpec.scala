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
    val updatedBoard: Board = board.step(firstPlayer, (0, 2)).right.get

    updatedBoard.cell(0, 2) should be(Some(firstPlayer))
  }

  it should "not modify original board" in new Wiring {
    val updatedBoard: Board = board.step(firstPlayer, (0, 2)).right.get

    updatedBoard.cell(0, 2) should be(Some(firstPlayer))
    board.cell(0, 2) should be(None)
  }

  it should "not allow to occupy the cell for the same user" in new Wiring {
    val updatedBoard: Board = board.step(firstPlayer, (0, 2)).right.get

    updatedBoard.step(firstPlayer, (0, 2)) should be(Left(AlreadyOccupiedError()))
  }

  it should "not allow to occupy the cell for another user" in new Wiring {
    val updatedBoard: Board = board.step(firstPlayer, (0, 2)).right.get

    updatedBoard.step(secondPlayer, (0, 2)) should be(Left(AlreadyOccupiedError()))
  }

  it should "not allow to occupy not existing cell" in new Wiring {
    board.step(firstPlayer, (-1, 2)) should be(Left(WrongPointError()))
    board.step(firstPlayer, (2, -1)) should be(Left(WrongPointError()))
    board.step(firstPlayer, (-1, -1)) should be(Left(WrongPointError()))

    board.step(firstPlayer, (100, 2)) should be(Left(WrongPointError()))
    board.step(firstPlayer, (2, 100)) should be(Left(WrongPointError()))
    board.step(firstPlayer, (100, 100)) should be(Left(WrongPointError()))

    board.step(firstPlayer, (100, -1)) should be(Left(WrongPointError()))
    board.step(firstPlayer, (-1, 100)) should be(Left(WrongPointError()))
  }

  "cell" should "return nothing when cell is not occupied" in new Wiring {
    board.cell(0, 2) should be(None)
  }

  private trait Wiring {
    val board = new Board()
    val firstPlayer: PlayerId = 1.toPlayerId
    val secondPlayer: PlayerId = 2.toPlayerId
  }

}
