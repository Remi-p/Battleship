package fr.enseirb.battleship;

import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;

import tools.XmlParserGrid;
import tools.XmlParserShips;

import java.util.HashMap;
import java.util.List;


public class Grid {
	private int height;
	private int width;
	private Ship[] ships;

	public Grid() throws InvalidGridException, ShipOutOfBoundsException {
		this("configs/");
	}

	public Grid(String configs_path) throws InvalidGridException, ShipOutOfBoundsException {
		super();
		
		// --------------- grid.xml
		XmlParserGrid grid = new XmlParserGrid(configs_path);
		
		// Dimensions
		int height = grid.getDimHorizontal();
		int width = grid.getDimVertical();
		
		// Minimum of 10*10 grid, else InvalidGridException
		if (height < 10 || width < 10)
			throw new InvalidGridException();
		else {
			this.height = height;
			this.width = width;
		}
		
		// Boat types
		HashMap<String, List<Integer> > ships_size = grid.getShips();

		// -------------- ships.xml
		XmlParserShips ships_xml = new XmlParserShips(configs_path);
		Ship[] ships = ships_xml.getShips(ships_size, height, width);

		// Ships has to take less than 20% of total number of cases, else InvalidGridException
		if (((double)shipGridTaking(ships) / ((double)height  *(double)width)) > 0.2) {
			throw new InvalidGridException();
		}
		else {
			this.ships = ships;
		}
	}
	
	// Return number of cases taken by ships
	private int shipGridTaking(Ship[] ships) {
		
		// Number of case taken by ships
		int num_cases = 0;
		
		for (int i = 0; i < ships.length; i++) {
			int size = ships[i].getSize();
			num_cases += size;
		}
		return num_cases;
	}
	
	// GETTERS
	// ATTENTION : methodes public a changer en fonction après
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}
	
}
