# Battleship

Object oriented programming project

Enseirb-Matmeca, 2015

*Guillaume Claudel*

*Thibaut Gourdel*

*Rémi Perrot*

T2-G1

# Step 1 : Grid

## Importing Grid & ships

* We use the *Javax XML Parsers* library for importing `grid.xml`, in the package **tools**
* The *XmlParser-* classes are called in files `Grid.java`, for parsing both general grid informations and ships positions

## Exceptions / placing AI boats

* Exceptions are dealt with in the **exceptions** package
* Most of the time, they are not catch, but directly sent to the main method
* However, they are a few exceptions which are catched
	* `ShipOutOfBoundsException` is catched to determined if the randomly created boat (for the AI) is inside the grid
	* `ShipOverlapException` is replaced, for random generation, by a simple boolean-return function for testing overlapping between AI boats

## Drawing

* When entering the following command, a svg-debug file is created :

```
$ java fr.enseirb.battleship.App debug grid.xml ships.xml
```

* The drawing writing process is managed in the *SvgWriter* class

# Step 2 : Game

## Turn by turn

* Function `turnOfPlayer(i)` processes the turn of IA or Human player
	* It calls the abstract method `play` of player and his children
* Keyboards inputs are handled in the `play` method of *Human* class, extending *Player*
* Random fires of the IA are managed in the `play` method of *IA*, extending *Player*
	* Random coordinates are generated from `getRandomCoordinates` of *Grid*
	* They keep being generated while none have been encounter satisfying every conditions of `checkHit` (*Grid*)

# Step 3 : Advanced IA

## Strategies

* Boats placement and attack strategy are indicated in **grid.xml** by the user.
	* These informations change the way the function `.......` behaves

# Bonus : Network

## Multiplayer

* *RemoteHuman*, extending *Player*, is seamlessly changing the way *IA* behaved
	* It uses the same function, `play`, but the program waits for external data
* An optional **opponent** socket is added in *Human*
	* When defined, the class sends through the socket information of human moves
* At initialization, grids are exchanged between players
	* The use of *Java.io.Serializable* allows us to convert a *Grid* to a String
	* *Human* and *RemoteHuman* implements respectively `sendGridToOpponent` and `recvGrid` for exchanging grids
