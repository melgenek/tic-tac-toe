package game.error

sealed trait StepError

case class AlreadyOccupiedError() extends StepError

case class WrongPointError() extends StepError

case class WrongPointFormatError() extends StepError
