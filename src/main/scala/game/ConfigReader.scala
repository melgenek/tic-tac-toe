package game

import com.typesafe.config.ConfigFactory
import game.player.PlayerId._
import game.player.{AiPlayer, InputStreamPlayer, Player, PlayerId}

import scala.collection.JavaConverters._

object ConfigReader {

  private val config = ConfigFactory.load()

  val width: Int = config.getInt("game.size.width")

  val height: Int = config.getInt("game.size.height")

  val players: Seq[Player] = config.getConfigList("game.players").asScala.map { playerConfig =>
    val id: Int = playerConfig.getInt("id")
    playerConfig.getString("kind") match {
      case "human" => new InputStreamPlayer(id.toPlayerId, System.in)
      case "ai" => new AiPlayer(id.toPlayerId)
    }
  }

  val playerChars: Map[PlayerId, Char] = config.getConfigList("game.players").asScala.map { playerConfig =>
    val id: Int = playerConfig.getInt("id")
    val char: Char = playerConfig.getString("char").head
    id.toPlayerId -> char
  }.toMap


}
