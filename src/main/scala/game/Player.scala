package game

case class Player(id: Int)

object Player {

  implicit class IntOps(int: Int) {
    def toPlayer: Player = Player(int)
  }

}
