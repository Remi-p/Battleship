package fr.enseirb.battleship.elements;

public class BoatCase extends Coordinates {
	
	boolean touched;
	
	public BoatCase(int x, int y) {
		super(x,y);
		this.touched = false;
	}

	public boolean getTouched() {
		return this.touched;
	}

	public void setTouched() {
		this.touched = true;
	}
	
}
