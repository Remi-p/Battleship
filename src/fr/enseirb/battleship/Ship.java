package fr.enseirb.battleship;

public class Ship {
	String name;
	Type type;
	int x;
	int y;
	Orientation orientation;
	int size;
	
	Ship(String name,Type type,int x,int y,Orientation orientation, int size){
		super();
		this.name = name;
		this.type = type;
		this.x = x;
		this.y = y;
		this.orientation = orientation;
		this.size = size;
	}
}
