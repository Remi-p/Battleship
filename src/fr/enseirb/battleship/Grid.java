package fr.enseirb.battleship;

import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;

import tools.Coordinates;
import tools.XmlParserGrid;
import tools.XmlParserShips;

import java.util.HashMap;
import java.util.List;


public class Grid {
	private int height;
	private int width;
	private Ship[] ships;

	public Grid() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		this("configs/");
	}
	
	// This constructor is mainly here for test purpose
	public Grid(int height, int width) throws InvalidGridException {
		this.setDim(height, width);
	}

	public Grid(String configs_path) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		super();
		
		// --------------- grid.xml
		XmlParserGrid grid = new XmlParserGrid(configs_path);
		
		// Dimensions
		int height = grid.getDimHorizontal();
		int width = grid.getDimVertical();
		
		this.setDim(height, width);
		
		// Boat types
		HashMap<String, List<Integer> > ships_size = grid.getShips();

		// -------------- ships.xml
		XmlParserShips ships_xml = new XmlParserShips(configs_path);
		Ship[] ships = ships_xml.getShips(ships_size, height, width);

		double occupation = (double)shipGridTaking(ships) / ((double)height  *(double)width); 
		// Ships has to take less than 20% of total number of cases, else InvalidGridException
		if (occupation > 0.2) {
			throw new InvalidGridException(occupation);
		}
		else {
			if(shipGridOverLap(ships) == false) {
				throw new ShipOverlapException();
			}
			else {
				this.ships = ships;
			}
		}
	}
	
	private void setDim(int height, int width) throws InvalidGridException {

		// Minimum of 10*10 grid, else InvalidGridException
		if (height < 10 || width < 10)
			throw new InvalidGridException(height, width);
		else {
			this.height = height;
			this.width = width;
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
	
	// Check if overlap ships exists
	private boolean shipGridOverLap(Ship[] ships) {
		
		int x, y, size;
		int index = 0;
		// Coordinates array initializing
		Coordinates[] coordinates = new Coordinates[shipGridTaking(ships)]; 
		
		// Store all coordinates in the Coordinates[]
		for (int i = 0; i < ships.length; i++) {
			
			size = ships[i].getSize();
			Orientation orientation = ships[i].getOrientation();
			x = ships[i].getX();
			y = ships[i].getY();
			
			for(int j = 0; j < size; j++) {

				switch(orientation) {
		            case HORIZONTAL: 
		            	coordinates[index] = new Coordinates(x + j,y);
		                break;
	
		            case VERTICAL: 
		            	coordinates[index] = new Coordinates(x,y + j);
		                break;
				}
				index++;
			}
		}
		
		// Comparation between each coordinates from the array
		for (int k = 0; k < coordinates.length; k++) {
			
			for(int l = k + 1; l < coordinates.length ; l++ ) {
					
				if(coordinates[k].getX() == coordinates[l].getX() 
				   && coordinates[k].getY() == coordinates[l].getY() ) {
					return false; // Overlapping
				}
			}	
		}
		return true; // No overlapping
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
