package fr.enseirb.battleship;

import tools.XmlParserGrid;
import tools.XmlParserShips;

import java.util.HashMap;


public class Grid {
	private int height;
	private int width;
	private Ship[] ships;

	public Grid() {
		this("configs/");
	}

	public Grid(String configs_path){
		super();
		
		// --------------- grid.xml
		XmlParserGrid grid = new XmlParserGrid(configs_path);
		
		// Dimensions
		this.height = grid.getDimHorizontal();
		this.width = grid.getDimVertical();
		
		// Boat types
		HashMap<String, Integer> ships_size = grid.getShips();

		// -------------- ships.xml
		XmlParserShips ships_xml = new XmlParserShips(configs_path);
		
		this.ships = ships_xml.getShips(ships_size);
		
	}
	
	// GETTERS
	// ATTENTION : methodes public a changer en fonction après
	
	public int getHeight(int height) {
		return this.height;
	}
	
	public int getWidth(int width) {
		return this.width;
	}
	
}
