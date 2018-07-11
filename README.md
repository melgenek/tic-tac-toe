### Running the game
Run game using jar:
1) run tests and build fat jar:

```
./xsbt.sh assembly
```

2) run the game via jar:
```
java -jar target/scala-2.12/tic-tac-toe-assembly-0.1.jar
```

Optionally you can use docker:
1) build docker image
```
./xsbt.sh docker
```

2) run docker image

```
docker run --rm -it game/tic-tac-toe:latest
```

Both jar or docker can be distributed to deploy the system.
Docker is preferable as it has needed dependencies installed.


### Assumptions

1) The game objects are mutable. 
For example, Board.step return a bord with updated position or error.
Internal state of previous board is left unchanged. 
This gives opportunity to safely run game multiple times and care only about interface ignoring internal state.

2) Win strategy is build to be composable. Each rule is as small as possible.
The composite winner search strategy returns either nothing or result of the first strategy that founds winner.

3) Players and game display does not require System.in/out to be present and can have possible implementations 
based on other ui techniques.

4) The code does not require config to be run. Config is only applied in case it is needed, 
in our case - the program itself. Tests does not require configs.
Amount of users is configurable and can be changed via config.

  


