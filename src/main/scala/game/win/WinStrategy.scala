package game.win

import game.{Board, Player}


trait WinStrategy {

  def findWinner(board: Board): Option[Player]

  def or(anotherStrategy: WinStrategy): WinStrategy = (board: Board) => {
    val thisResult: Option[Player] = WinStrategy.this.findWinner(board)
    if (thisResult.nonEmpty) thisResult
    else anotherStrategy.findWinner(board)
  }

}
