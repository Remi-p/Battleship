package fr.enseirb.battleship.elements;

public enum Direction {
	NORTH, WEST, SOUTH, EAST;

	public Direction getNext() {
		return values()[(ordinal() + 1) % values().length];
	}
}
