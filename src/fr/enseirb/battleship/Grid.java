package fr.enseirb.battleship;

import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;
import tools.Constant;
import tools.Coordinates;
import tools.XmlParserGrid;
import tools.XmlParserShips;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class Grid {
	private int height;
	private int width;
	private Ship[] ships;

	public Grid() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		this(Constant.CONFIGS);
	}
	
	// This constructor is mainly here for test purpose
	public Grid(int height, int width) throws InvalidGridException {
		this.setDim(height, width);
	}
	
	public Grid(String configs_path) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		this(configs_path, Constant.GRID, Constant.SHIPS);
	}

	public Grid(String configs_path, String gridfilename, String shipfilename) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		super();
		
		// --------------- grid.xml
		XmlParserGrid grid = new XmlParserGrid(configs_path, gridfilename);
		
		// Dimensions
		int height = grid.getDimHorizontal();
		int width = grid.getDimVertical();
		
		this.setDim(height, width);
		
		// Boat types
		Type ships_type = grid.getShips();

		// -------------- ships.xml
		XmlParserShips ships_xml = new XmlParserShips(configs_path, shipfilename);
		Ship[] ships = ships_xml.getShips(ships_type, height, width);

		System.out.println("Occupation : "+ships_type.getGridOccupation());
		
		double occupation = (double) ships_type.getGridOccupation() / ((double)height  *(double)width); 
		// Ships has to take less than 20% of total number of cases, else InvalidGridException
		if (occupation > 0.2) {
			throw new InvalidGridException(occupation);
		}
		else {
			if(shipGridOverLap(ships, ships_type) == false) {
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
	
	// Check if overlap ships exists
	private boolean shipGridOverLap(Ship[] ships, Type ships_type) {
		
		int x, y, size;
		int index = 0;
		// Coordinates array initializing
		Coordinates[] coordinates = new Coordinates[ships_type.getGridOccupation()]; 
		
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
		
		// Comparison between each coordinates from the array
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
