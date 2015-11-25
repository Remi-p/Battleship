package fr.enseirb.battleship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.elements.Orientation;
import fr.enseirb.battleship.elements.Ship;
import fr.enseirb.battleship.elements.Type;
import fr.enseirb.battleship.elements.TypeElt;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.exceptions.ShipOverlapException;
import fr.enseirb.battleship.exceptions.ShipsConfigurationException;
import fr.enseirb.battleship.tools.Config;
import fr.enseirb.battleship.tools.XmlParserGrid;
import fr.enseirb.battleship.tools.XmlParserShips;

public class Grid {
	private int height;
	private int width;
	private List<Ship> ships;
	private List<Coordinates> fires;
	private List<String> shipnames;
	private int sinkedShip;
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
		Type ships_type = configs_extract(configs_path, gridfilename);
		ships_extract(configs_path, shipfilename, ships_type);
		init_fires();
		sinkedShip = 0;
	}
	
	// Constructor for IA
	public Grid(String configs_path, String gridfilename) throws InvalidGridException {
		
		// We do not throw errors about boats here, but we check them
		
		Type ships_type;
		ships_type = configs_extract(configs_path, gridfilename);
			
		this.shipnames = ShipsNameInitialisation();
		
		this.ships = random_ships(this.height, this.width, ships_type);
		
		init_fires();
	}
	
	// Configs extraction from grid.xml
	private Type configs_extract(String configs_path, String gridfilename) throws InvalidGridException {
		// --------------- grid.xml
		XmlParserGrid grid = new XmlParserGrid(configs_path, gridfilename);
		
		// Dimensions
		int height = grid.getDimHorizontal();
		int width = grid.getDimVertical();
		
		this.setDim(height, width);
				
		// Boat types
		Type ships_type = grid.getShips();
		
		return ships_type;
	}
	
	// Ships features extraction from ships.xml
	private void ships_extract(String configs_path, String shipfilename, Type ships_type) throws InvalidGridException, ShipOverlapException, ShipOutOfBoundsException, ShipsConfigurationException{
		// -------------- ships.xml
		XmlParserShips ships_xml = new XmlParserShips(configs_path, shipfilename);
		List<Ship> ships = ships_xml.getShips(ships_type, height, width);

		if (Config.VERBOSE)
			System.out.println("Occupation : "+ships_type.getGridOccupation());
		
		// TODO : A déplacer
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
	}List<Coordinates> coordinates = new ArrayList<Coordinates>();
	
	private void init_fires() {
		List<Coordinates> fires = new ArrayList<Coordinates>();
		this.fires = fires;
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
	
	private List<Ship> random_ships(int height, int width, Type ships_type) {
		
		List<Ship> ships = new ArrayList<Ship>();
		
		for (TypeElt e : ships_type.getListType()) {
			
			for( int i = 0; i < e.getQuantity(); i++) {
				
				int x = (int)(Math.random() * (width-0)) + 0;
				int y = (int)(Math.random() * (height-0)) + 0;
				double o = Math.random();
				String orientation;
				String random_shipname;
				
				if (o >= 0.5)
					orientation = "horizontal";
				else
					orientation = "vertical";
				
				try {
					random_shipname = random_shipname(this.shipnames);
					Ship ship = new Ship(random_shipname, e.getType(), x, y, orientation, e.getSize(), height, width );
					
					// There is an overlapping problem
					if (Grid.shipOverlapShips(ships, ships_type, ship)) {
						i--;
						this.shipnames.add(random_shipname);
					}
					else
						ships.add(ship);
					
				} catch (ShipOutOfBoundsException e1) {
					i--;
				}
				
				
			}
		}
		return ships;
		
	}
	
	private List<String> ShipsNameInitialisation() {
		List<String> shipNames = new ArrayList<String>();
		
		shipNames.add("USS Enterprise");
		shipNames.add("Destiny");
		shipNames.add("O'Neill");
		shipNames.add("Millennium Falcon");
		shipNames.add("Galactica Battlestar");
		shipNames.add("Tardis");
		shipNames.add("Planet Express");
		shipNames.add("Serenity");
		shipNames.add("Voyager 1");
		shipNames.add("Star Destroyer");
		shipNames.add("Prometheus");
		
		return shipNames;
	}
	
	public String random_shipname(List<String> names) {
		String name = new String();
		int index = (int)(Math.random() * (names.size()-1) ) + 0;
		name = names.get(index);
		ShipsNamesDelete(names, index);
		return name;
	}
	
	public void ShipsNamesDelete(List<String> shipnames, int index) {
		shipnames.remove(index);
	}
	
	public static List<Coordinates> getShipsCoordinates(Collection<Ship> ships) {
		
		// We will store all the ships coordinates
		List<Coordinates> coordinates = new ArrayList<Coordinates>();
		
		// Store all coordinates in the Coordinates[]
		for (Ship boat : ships) {
			coordinates.addAll(boat.getBoatCoordinates());
		}
		
		return coordinates;
	}
	
	// Check if a particular ship overlaps the others
	private static boolean shipOverlapShips(Collection<Ship> ships, Type ships_type, Ship ship) {
		
		List<Coordinates> coordinates = getShipsCoordinates(ships);
		
		// Comparison between each coordinates from the ship
		for(int j = 0; j < ship.getSize(); j++) {

			switch(ship.getOrientation()) {
	            case HORIZONTAL:
	            	if (Collections.frequency(coordinates, new Coordinates(ship.getX() + j, ship.getY())) > 0)
	            		return true;
	                break;

	            case VERTICAL: 
	            	if (Collections.frequency(coordinates, new Coordinates(ship.getX(), ship.getY() + j)) > 0)
	            		return true;
	                break;
			}
		}
		
		// TODO Change output to true, sémantique
		return false; // No overlapping
	}
	
	// Check if overlap ships exists
	private boolean shipGridOverLap(Collection<Ship> ships, Type ships_type) {
		
		List<Coordinates> coordinates = getShipsCoordinates(ships);
		
		// Comparison between each coordinates from the array
		for (Coordinates c : coordinates) {
			if (Collections.frequency(coordinates, c) > 1)
				// Overlapping
				return false;
		}
		
		return true; // No overlapping
	}
	
	public void checkSunk(){
		int sunk = 0;
		int hit;
		List<Coordinates> coordinates = new ArrayList<Coordinates>();
		for (Ship boat : this.ships){
			hit = 0;
			coordinates = boat.getBoatCoordinates();
			for(Coordinates test : this.fires){
				if(Collections.frequency(coordinates,test) > 1){
					hit++;
				}
			}
			if(hit == boat.getSize()){
				sunk++;
			}
		}
		if(sunk>this.sinkedShip){
			this.sinkedShip = sunk;
			System.out.println("Sunk boat"); //TODO Search a way to get the sinked boat name
		}
	}
	
	
	public void addFire(Coordinates coordinates) {
    	System.out.println("\ngr x : "+coordinates.getX());
    	System.out.println("\ngr y : "+coordinates.getY());
		this.fires.add(coordinates);
	}
	
	
	private boolean alreadyFired(Coordinates coordinates){
		if (Collections.frequency(this.fires,coordinates)>0){
	    	System.out.println("\ngr x : "+coordinates.getX());
	    	System.out.println("\ngr y : "+coordinates.getY());
	    	System.out.println("Missed");
			return true;
		}
		return false;
	}
	
	public void checkHit(Coordinates coordinates){
		List<Coordinates> shipInGrid = getShipsCoordinates(this.ships);
		if (!this.alreadyFired(coordinates)){
			if (Collections.frequency(shipInGrid,coordinates)>0){
				// Successful fire
				this.addFire(coordinates);
				this.checkSunk();
			}
			
			else{
				// Missed fire
				this.addFire(coordinates);
	    		System.out.println("Missed");
			}
		}		
	}
	
	// GETTERS
	// ATTENTION : methodes public a changer en fonction après
	
	public int getHeight() {
		return this.height;
	}
	
	public int getWidth() {
		return this.width;
	}

	public List<Ship> getShips() {
		return ships;
	}
	
	public List<Coordinates> getFires() {
		return this.fires;
	}
	
	public int getSinkedShip(){
		return this.sinkedShip;
	}
	
}
