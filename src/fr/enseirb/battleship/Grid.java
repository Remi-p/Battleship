package fr.enseirb.battleship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import tools.Config;
import tools.Coordinates;
import tools.XmlParserGrid;
import tools.XmlParserShips;
import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;
import exceptions.ShipsConfigurationException;



public class Grid {
	private int height;
	private int width;
	private List<Ship> ships;

	public Grid() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		this(Config.CONFIGS);
	}
	
	// This constructor is mainly here for test purpose
	public Grid(int height, int width) throws InvalidGridException {
		this.setDim(height, width);
	}
	
	public Grid(String configs_path) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		this(configs_path, Config.GRID, Config.SHIPS);
	}

	public Grid(String configs_path, String gridfilename, String shipfilename) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException , ShipsConfigurationException {
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
		List<Ship> ships = ships_xml.getShips(ships_type, height, width);

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
			else{
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
	private boolean shipGridOverLap(Collection<Ship> ships, Type ships_type) {
		
		int x, y, size;
		
		// We will store all the ships coordinates
		List<Coordinates> coordinates = new ArrayList<Coordinates>();
		
		// Store all coordinates in the Coordinates[]
		for (Ship boat : ships) {
			
			size = boat.getSize();
			Orientation orientation = boat.getOrientation();
			x = boat.getX();
			y = boat.getY();
			
			for(int j = 0; j < size; j++) {

				switch(orientation) {
		            case HORIZONTAL: 
		            	coordinates.add(new Coordinates(x + j, y));
		                break;
	
		            case VERTICAL: 
		            	coordinates.add(new Coordinates(x, y + j));
		                break;
				}
			}
		}
		
		// Comparison between each coordinates from the array
		for (Coordinates c : coordinates) {
			if (Collections.frequency(coordinates, c) > 1)
				// Overlapping
				return false;
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
