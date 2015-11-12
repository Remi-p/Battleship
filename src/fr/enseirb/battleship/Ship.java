package fr.enseirb.battleship;

import exceptions.ShipOutOfBoundsException;

public class Ship {
	private String name;
	private Type type;
	private int x;
	private int y;
	private Orientation orientation;
	private int size;
	
	public Ship(String name, String type,
			    int x, int y, String orientation,
			    int size, int height_grid, int width_grid) throws ShipOutOfBoundsException {
		
		super();
		
		this.name = name;
		this.size = size;
		setType(type);
		setOrientation(orientation);
		setType(type);
		
		if( x < 0 || y < 0 || x > width_grid || y > height_grid ) {
			throw new ShipOutOfBoundsException(x, y, name);
		}
		else {
			
			if(this.orientation == Orientation.HORIZONTAL && x+size > height_grid ) {
				throw new ShipOutOfBoundsException(name);
			}
			else if(this.orientation == Orientation.VERTICAL && y+size > width_grid ) {
				throw new ShipOutOfBoundsException(name);
			}
			else {
				this.x = x;
				this.y = y;
			}
		}
	}


	// SETTERS
	public void setType(String type) {
		
		if("battleship".compareTo(type) == 0) {
			this.type = Type.BATTLESHIP;
		}
		else if("submarine".compareTo(type) == 0) {
			this.type = Type.SUBMARINE;
		}
		else if("destroyer".compareTo(type) == 0) {
			this.type = Type.DESTROYER;
		}
		else if("patrol-boat".compareTo(type) == 0) {
			this.type = Type.PATROL_BOARD;
		}
		else if("aircraft-carrier".compareTo(type) == 0) {
			this.type = Type.AIRCRAFT_CARRIER;
		}
	}
	
	public void setOrientation(String orientation) {
		
		if("horizontal".compareTo(orientation) == 0) {
			this.orientation = Orientation.HORIZONTAL;
		}
		else {
			this.orientation = Orientation.VERTICAL;
		}
	}
	
	
	// GETTERS
	// ATTENTION : methodes public a changer en fonction après
	
	public String getName() {
		return this.name;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Orientation getOrientation() {
		return this.orientation;
	}
	
	public int getSize() {
		return this.size;
	}
}
