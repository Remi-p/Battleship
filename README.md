# Battleship

Object oriented programming project

*Guillaume Claudel*, *Thibaut Gourdel*, *Rémi Perrot*

**ENSEIRB-MATMECA**, 2015

T2-G1

# Step 1 : Grid

## Importing grid & ships

* We use the *Javax XML Parsers* library for importing `grid.xml`, in the package **tools**
* The *XmlParser-* classes are called in files `Grid.java`, for parsing both general grid informations and ships positions (`ships.xml`)

## Exceptions / placing AI boats

* Exceptions are dealt with in the **exceptions** package
* Most of the time, they are not catch, but directly sent to the main method :
* However, they are a few exceptions which are catched
	* `ShipOutOfBoundsException` is catched to determined if the randomly created boat (for the AI) is inside the grid
	* `ShipOverlapException` is replaced, for random generation, by a simple boolean-return function for testing overlapping between AI boats

### Tests

* Tests for verifying if some of the exceptions are well managed are made in `TestException.java`, in the **test** folder, with *JUnit 4*

## Drawing

* When entering the following command, a svg-debug file is created :

```
$ java fr.enseirb.battleship.App debug grid.xml ships.xml
```

**Remark** : The game has been built in order to be launched on eclipse. If you want to try it on a terminal, please place yourself in the `/bin` directory, and make a symbolic link of `../configs`, before launching.

* The drawing writing process is managed in the *SvgWriter* class

# Step 2 : Game

The game can be launched with the following command :

```
$ java fr.enseirb.battleship.App play grid.xml ships.xml
```

## Turn by turn

* Function `turnOfPlayer(i)` processes the turn of IA or Human player
	* It calls the abstract method `play` of player and his children
* Keyboards inputs are handled in the `play` method of *Human* class, extending *Player*
* Random fires of the IA are managed in the `play` method of *IA*, extending *Player*
	* Random coordinates are generated from `getRandomCoordinates` of *Grid*
	* They keep being generated while none have been encounter satisfying every conditions of `checkHit` (*Grid*)

# Step 3 : Advanced IA

## Strategies

* Boats placement and firing strategies are indicated in **grid.xml** by the user.
	* These informations change the way the functions `play` and `randomShips` behave.
* For both placement and firing, the **RANDOM** strategy is the simplest way the IA plays. Boats and firing coordinates are random.

* Placement strategies
	* **PACK** : The pack strategy consists in divinding the grid by rectangular sections. The algorithm choose one section randomly and place boats in it.
	* **FAR** : As above, the grid is shared in rectangular sections. However, each section includes only and maximally one boat.
	* **PERSO** : This strategy is a mix of the pack and far strategies. Boats are packed in groups of three and seperated in differents sections

* Firing strategies
	* **PACK** : The IA fires very close coordinates.
	* **FAR** : Contrary to pack strategy, the IA fires far coordinates.
	* **PERSO** : This is a hybrid strategy. The IA starts by applying the far strategy and once it touched a boat it turns to pack strategy in order to sunk the boat.

# Bonus : Network

## Multiplayer

One user has to launch the game as a server :
```
$ java fr.enseirb.battleship.App server grid.xml ships.xml [port]
```
And the other will play as client :
```
$ java fr.enseirb.battleship.App client grid.xml ships.xml [server IP] [port]
```

* *RemoteHuman*, extending *Player*, is seamlessly changing the way *IA* behaved
	* It uses the same function, `play`, but the program waits for external data
* An optional **opponent** socket is added in *Human*
	* When defined, the class sends through the socket information of human moves
* At initialization, grids are exchanged between players
	* The use of *Java.io.Serializable* allows us to convert a *Grid* to a String
	* *Human* and *RemoteHuman* implements respectively `sendGridToOpponent` and `recvGrid` for exchanging grids
