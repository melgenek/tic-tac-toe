package game.win

import game.Board
import game.player.PlayerId


trait WinStrategy {

  def findWinner(board: Board): Option[PlayerId]

  def or(anotherStrategy: WinStrategy): WinStrategy = (board: Board) => {
    val thisResult: Option[PlayerId] = WinStrategy.this.findWinner(board)
    if (thisResult.nonEmpty) thisResult
    else anotherStrategy.findWinner(board)
  }

}
