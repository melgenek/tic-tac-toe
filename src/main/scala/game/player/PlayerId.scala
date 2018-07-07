package game.player

case class PlayerId(id: Int)

object PlayerId {

  implicit class IntOps(int: Int) {
    def toPlayerId: PlayerId = PlayerId(int)
  }

}
