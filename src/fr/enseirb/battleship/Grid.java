package fr.enseirb.battleship;

public class Grid {
	int height;
	int width;
	Ship[] ship;
	Fire[] fire;
	
	Grid(){
		super ();
		//extraction XML
		
		this.height = height;
		this.width = width;
		this.ship = build_ship();
	}
}
