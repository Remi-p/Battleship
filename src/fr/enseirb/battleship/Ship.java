package fr.enseirb.battleship;

public class Ship {
	private String name;
	private Type type;
	private int x;
	private int y;
	private Orientation orientation;
	private int size;
	
	public Ship(String name, String type, int x, int y, String orientation, int size) {
		super();
		
		this.name = name;
		setType(type);
		this.x = x;
		this.y = y;
		setOrientation(orientation);
		this.size = size;
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
