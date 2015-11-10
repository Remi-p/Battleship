package fr.enseirb.battleship;

public class Ship {
	private String name;
	private Type type;
	private int x;
	private int y;
	private Orientation orientation;
	private int size;
	
	Ship(String name,Type type,int x,int y,Orientation orientation, int size){
		super();
		this.name = name;
		this.type = type;
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.size = size;
	}
	  
	// GETTERS
	// ATTENTION : methodes public a changer en fonction après
	
	public String getName(String name) {
		return name;
	}
	
	public Type getType(Type type) {
		return type;
	}
	
	public int getX(int x) {
		return x;
	}
	
	public int getY(int y) {
		return y;
	}
	
	public Orientation getOrientation(Orientation orientation) {
		return orientation;
	}
	
	public int getSize(int size) {
		return size;
	}
}
