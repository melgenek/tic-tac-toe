package game.player

case class PlayerId(id: Int){
  override def toString: String = id.toString
}

object PlayerId {

  implicit class IntOps(int: Int) {
    def toPlayerId: PlayerId = PlayerId(int)
  }

}
