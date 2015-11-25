package fr.enseirb.battleship.elements;

public class BoatCase extends Coordinates {
	
	boolean touched;
	
	public BoatCase(int x, int y) {
		super(x,y);
		this.touched = false;
	}
	
}
