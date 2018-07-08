package game

import scala.language.implicitConversions

case class Point(x: Int, y: Int)

object Point {

  implicit def tupleToPoint(tuple: (Int, Int)): Point = Point(tuple._1, tuple._2)

}
