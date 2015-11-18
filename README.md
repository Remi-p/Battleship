# Battleship

Object oriented programming project

Enseirb-Matmeca, 2015

*Guillaume Claudel*

*Thibaut Gourdel*

*Rémi Perrot*

T2-G1

# Step 1

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
