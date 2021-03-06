package fr.enseirb.battleship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.elements.BoatCase;
import fr.enseirb.battleship.elements.Ship;
import fr.enseirb.battleship.elements.Strategy;
import fr.enseirb.battleship.elements.Type;
import fr.enseirb.battleship.elements.TypeElt;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.exceptions.ShipOverlapException;
import fr.enseirb.battleship.exceptions.ShipsConfigurationException;
import fr.enseirb.battleship.tools.Config;
import fr.enseirb.battleship.tools.XmlParserGrid;
import fr.enseirb.battleship.tools.XmlParserShips;

public class Grid implements java.io.Serializable {
	
	/**
	 * Serial allows to block the system if he receives a serialized version of
	 * an ancient object version (when exchanging grids)
	 */
	private static final long serialVersionUID = 1L;
	
	private int height;
	private int width;
	private List<Ship> ships;
	private List<Coordinates> missedfires;
	private List<String> shipnames;
	
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
		Type ships_type = configsExtract(configs_path, gridfilename);
		shipsExtract(configs_path, shipfilename, ships_type);
		initFires();
	}
	
	// Constructor for IA
	public Grid(String configs_path, String gridfilename, Strategy placement, Strategy firing) throws InvalidGridException {

		// We do not throw errors about boats here, but we check them
		Type ships_type;
		ships_type = configsExtract(configs_path, gridfilename);
		this.shipnames = shipsNameInitialisation();
		this.ships = randomShips(this.height, this.width, ships_type, placement);
		initFires();
	}
	
	// Config extraction from grid.xml
	private Type configsExtract(String configs_path, String gridfilename) throws InvalidGridException {
		// --------------- grid.xml
		XmlParserGrid grid = new XmlParserGrid(configs_path, gridfilename);
		
		// Dimensions
		int width = grid.getDimHorizontal();
		int height = grid.getDimVertical();
		
		this.setDim(height, width);
				
		// Boat types
		Type ships_type = grid.getShips();
		
		return ships_type;
	}
	
	// Ships features extraction from ships.xml
	private void shipsExtract(String configs_path, String shipfilename, Type ships_type) throws InvalidGridException, ShipOverlapException, ShipOutOfBoundsException, ShipsConfigurationException{
		// -------------- ships.xml
		XmlParserShips ships_xml = new XmlParserShips(configs_path, shipfilename);
		List<Ship> ships = ships_xml.getShips(ships_type, height, width);

		if (Config.VERBOSE)
			System.out.println("Occupation : "+ships_type.getGridOccupation());
		
		double occupation = (double) ships_type.getGridOccupation() / ((double)height  *(double)width); 
		// Ships has to take less than 20% of total number of cases, else InvalidGridException
		if (occupation > 0.2) {
			throw new InvalidGridException(occupation);
		}
		else {
			if (shipGridOverLap(ships, ships_type) == true)
				throw new ShipOverlapException();
			else
				this.ships = ships;
		}
	}
	
	private void initFires() {
		List<Coordinates> fires = new ArrayList<Coordinates>();
		this.missedfires = fires;
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
		
	private List<Ship> randomShips(int height, int width, Type ships_type,Strategy strategy) {
		
		List<Ship> ships = new ArrayList<Ship>();
		List<Integer>  indexs = new ArrayList<Integer>();
		String orientation;
		String random_shipname;
		double o = Math.random();
		int x = 0;
		int y = 0;
		double number_cell = (height*width)/ships_type.getTotalQty();		//Ratio available cells over ships cells 
		int box_index = 1;													//Index of the box where ships are put
		int width_box = (int)Math.floor(width*number_cell/(height*width)+1);	// Dimensions of the boxes
		int height_box = (int)Math.floor(height*number_cell/(height*width)+1);
		int number_box =(int) Math.floor(height/height_box*width/width_box );	// Total number of boxes
		int dec_x = 0;
		int dec_y = 0;
		int boat_in_box = 0;
		int max_nb = 0;
		boolean double_pack = false;											// If true , during PACK strategy the ships will be dispatch in different boxes
		
		int pack_box_index = (int)(Math.random()*number_box);					// Choose a box

		if (Config.VERBOSE) System.out.println("Placing Boats ...");
		
		for (TypeElt e : ships_type.getListType()) {
			
			for( int i = 0; i < e.getQuantity(); i++) {
				max_nb++;
				o = Math.random();
				
				// After 100 tries we change the section
				if(max_nb == 100){
					if(double_pack == false) {		//Try to get into adjacent boxes 
						if (pack_box_index%Math.floor(height/height_box) == 0){
							pack_box_index -= 1;
						}
						else{
							pack_box_index += 1;
						}
						if (Config.VERBOSE) System.out.println("Changing section ...");
						max_nb = 0;
						double_pack = true;			
						}
					else {
						if (Config.VERBOSE) System.out.println("Changing section ...");
						pack_box_index = (int)(Math.random()*number_box+1);
						max_nb = 0;
					}
				}
				
				switch(strategy){
				
					case RANDOM:										//Ships locations are random all over the grid
						 x = (int)(Math.random() * (width-0)) + 0;
						 y = (int)(Math.random() * (height-0)) + 0;						
						break;
						
					case FAR :										// One ship is placed in one box chosen randomly 
						do{
							box_index = (int)(Math.random()*number_box+1);
						}while(Collections.frequency(indexs,box_index) >0);
					
						indexs.add(box_index);					
						dec_x = (int)((box_index-1)%Math.floor(width/width_box));
						dec_y = (int)(Math.floor((box_index-1)/Math.floor(width/width_box)));
						x = (int)((width_box+1)*dec_x);
						y = (int)((height_box)*dec_y +dec_x);
						break;
						
					case PACK :																// Put all the ships in one box , if it fails we take one more box on the side. 

						dec_x = (int)((pack_box_index-1)%Math.floor(width/width_box));
						dec_y = (int)(Math.floor((pack_box_index-1)/Math.floor(width/width_box)));
						x = (int)(Math.random()*width_box + dec_x*width_box);
						y = (int)(Math.random()*height_box + dec_y*height_box);
						
						break;
						
					case PERSO : 											// Put 3 ships in boxes chosen randomly
						if(boat_in_box ==0){
							do{
								box_index = (int)(Math.random()*number_box+1);
							}while(Collections.frequency(indexs,box_index) >0);
							indexs.add(box_index);	
						}				
						dec_x = (int)((box_index-1)%Math.floor(width/width_box));
						dec_y = (int)(Math.floor((box_index-1)/Math.floor(width/width_box)));
						x = (int)(Math.random()*width_box + dec_x*width_box);
						y = (int)(Math.random()*height_box + dec_y*height_box);
						boat_in_box ++;
						boat_in_box = boat_in_box%3; 
						break;
				}
				//Determine a random orientation
				if (o >= 0.5)
					orientation = "horizontal";
				else
					orientation = "vertical";

				try {
					random_shipname = randomShipname(this.shipnames);	
					Ship ship = new Ship(random_shipname, e.getType(), x, y, orientation, e.getSize(), height, width );
				
					// There is an overlapping problem
					if (Grid.shipOverlapShips(ships, ships_type, ship)) {
						i--;
					}
					// Successful addition of a boat
					else {
						ships.add(ship);
						if(this.shipnames.size() != 0) {
							shipsNamesDelete(this.shipnames, random_shipname);
						}
					}
				
				} catch (ShipOutOfBoundsException e1) {
					i--;
				}

			}
		}
		if (Config.VERBOSE) System.out.println("Placing Boats succeed");

		return ships;
	}

	// Initialize the list of names which are provided to IA
	private List<String> shipsNameInitialisation() {
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
	
	public String randomShipname(List<String> names) {
		String name = new String();
		
		if(names.size() == 0)
			name = "Unknown";
		else {
			int index = (int)(Math.random() * (names.size()-1) ) + 0;
			name = names.get(index);
		}
		
		return name;
	}
	
	private void shipsNamesDelete(List<String> shipnames, String random_name) {
		int ind = 0;

		for(String name:shipnames) {
			if(name.compareTo(random_name) == 0)
				break;
			
			ind++;
		}
		shipnames.remove(ind);
	}
	
	public static List<Coordinates> getShipsCoordinates(Collection<Ship> ships, String state) {
		
		// We will store all the ships coordinates
		List<Coordinates> coordinates = new ArrayList<Coordinates>();
		
		// Store all coordinates in the Coordinates[]
		for (Ship boat : ships) {
			
			if ("untouched".compareTo(state) == 0) {
				for(BoatCase boatcase : boat.getBoatCases())
					if(!boatcase.touched())
						coordinates.add(new Coordinates(boatcase.getX(), boatcase.getY()));
			}
			else if("touched".compareTo(state) == 0) {
				for(BoatCase boatcase : boat.getBoatCases())
					if(boatcase.touched())
						coordinates.add(new Coordinates(boatcase.getX(), boatcase.getY()));
			}
			else
				coordinates.addAll(boat.getBoatCoordinates());
		}
		
		return coordinates;
	}
	
	// Check if a PARTICULAR ship overlaps the others
	private static boolean shipOverlapShips(Collection<Ship> ships, Type ships_type, Ship ship) {
		
		List<Coordinates> coordinates = getShipsCoordinates(ships, "all");
		
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
		
		return false; // No overlapping
	}
	
	// Check if at least one of the ships overlaps the others
	private boolean shipGridOverLap(Collection<Ship> ships, Type ships_type) {
		
		List<Coordinates> coordinates = getShipsCoordinates(ships, "all");
		
		// Comparison between each coordinates from the array
		for (Coordinates c : coordinates) {
			if (Collections.frequency(coordinates, c) > 1)
				// Overlapping
				return true;
		}
		
		return false; // No overlapping
	}
	
	// Add missed fire
	public void addFire(Coordinates coordinates) {
		this.missedfires.add(coordinates);
	}
	
	private boolean alreadyFired(Coordinates coordinates, List<Coordinates> ships_coordinates){
		if (Collections.frequency(this.missedfires,coordinates)>0)
			return true;
		
		else if(Collections.frequency(ships_coordinates,coordinates)>0)
			return true;
		
		else
			return false;
	}
	
	public int checkHit(Coordinates coordinates, String name) {
		
		int status = 0;
		
		List<Coordinates> ships_untouched_coordinates = getShipsCoordinates(this.ships, "untouched");
		List<Coordinates> ships_touched_coordinates = getShipsCoordinates(this.ships, "touched"); 
		
		// If player hasn't already fired at same coordinates
		if (!this.alreadyFired(coordinates, ships_touched_coordinates)){
			
			if (Collections.frequency(ships_untouched_coordinates,coordinates)>0){
				// Success fire
				for (Ship boat : this.ships) {
					for(BoatCase boatcase : boat.getBoatCases()) {
						if(boatcase.getX() == coordinates.getX() && boatcase.getY() == coordinates.getY()) {
							boatcase.setTouched(); // Boat case touched
							// If boat is sunk
							
							status = 1;
							
							if(boat.checkBoatSunk()) {
								if(name.compareTo("human") == 0)
									System.out.println("Sunk boat " + boat.getName() + " at " + coordinates.getX() + " " + coordinates.getY() );
								else
									System.out.println(name + " sunk boat " + boat.getName() + " at " + coordinates.getX() + " " + coordinates.getY() );

							}
							else {
								if(name.compareTo("human") == 0)
									System.out.println("Touched boat " + boat.getName() + " at " + coordinates.getX() + " " + coordinates.getY() );
								else
									System.out.println(name + " touched boat " + boat.getName() + " at " + coordinates.getX() + " " + coordinates.getY() );
							}
						}
					}

				}
			}
			else {
				// Missed fire
				this.addFire(coordinates);
				if(name.compareTo("human") == 0)
					System.out.println("Missed at " + coordinates.getX() + " " + coordinates.getY());
				else
					System.out.println(name + " missed at " + coordinates.getX() + " " + coordinates.getY());
				status = 2;
			}
		}	
		else {
			if(name.compareTo("human") == 0)
				System.out.println("Already fired at " + coordinates.getX() + " " + coordinates.getY());
				
			status = 0;
		}
		return status;
	}
	
	public Coordinates getRandomCoordinates(){
		int x = (int)(Math.random() * (this.width-0)) + 0 ; 
		int y = (int)(Math.random() * (this.height-0)) + 0 ;
		Coordinates coordinates = new Coordinates(x,y);
		return coordinates;
	}
	
	public Coordinates getRandomSmartCoordinates(){
		int x = (int)Math.floor((Math.random()*(this.width/2))) * 2;
		int y = (int)Math.floor((Math.random()*(this.height/2))) * 2;
		Coordinates coordinates = new Coordinates(x,y);
		return coordinates;
	}
	
	// GETTERS	
	
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
		return this.missedfires;
	}
	
	public boolean checkAllShipSunk(){
		for(Ship boat : this.ships) {
			if(!boat.isSunk()) 
				return false;
		}
		return true;
	}
	
}
