package fr.enseirb.battleship.elements;

import java.util.ArrayList;
import java.util.List;

import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;

public class Ship implements java.io.Serializable {
	private String name;
	private String type;
	private int x;
	private int y;
	private Orientation orientation;
	private int size;
	private List<BoatCase> boatcases;
	private boolean sunk;
	
	public Ship(String name, String type,
			    int x, int y, String orientation,
			    int size, int height_grid, int width_grid) throws ShipOutOfBoundsException {
		
		super();
		
		this.name = name;
		this.size = size;
		this.type = type;
		setOrientation(orientation);
		
		if( x < 0 || y < 0 || x > width_grid || y > height_grid ) {
			throw new ShipOutOfBoundsException(x, y, name);
		}
		else {
			
			if(this.orientation == Orientation.HORIZONTAL && x+size > width_grid ) {
				throw new ShipOutOfBoundsException(name);
			}
			else if(this.orientation == Orientation.VERTICAL && y+size > height_grid ) {
				throw new ShipOutOfBoundsException(name);
			}
			else {
				this.x = x;
				this.y = y;
				this.setBoatCases();
				this.sunk = false;
			}
		}
	}

	// SETTERS
	public void setOrientation(String orientation) {
		
		if("horizontal".compareTo(orientation) == 0) {
			this.orientation = Orientation.HORIZONTAL;
		}
		else {
			this.orientation = Orientation.VERTICAL;
		}
	}
	
	
	// GETTERS
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Orientation getOrientation() {
		return this.orientation;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public boolean isSunk() {
		return this.sunk;
	}

	public List<BoatCase> getBoatCases() {
		return this.boatcases;
	}
	
	public boolean isHorizontal() {
		if(this.orientation == Orientation.HORIZONTAL)
			return true;
		else
			return false;
	}

	// Not really useful (!isHorizontal)
	public boolean isVertical() {
		if(this.orientation == Orientation.VERTICAL)
			return true;
		else
			return false;
	}
	
	public List<Coordinates> getBoatCoordinates(){
		List<Coordinates> coordinates = new ArrayList<Coordinates>();
		
		for (BoatCase boatcases : this.boatcases)
			coordinates.add(new Coordinates(boatcases.getX(), boatcases.getY()));
		
		return coordinates;
	}
	
	// Return the list of the boat coordinates in function of
	// his origin and his orientation
	public void setBoatCases() {
		List<BoatCase> coordinates = new ArrayList<BoatCase>();
		for(int j = 0; j < this.size; j++) {
	
			switch(this.orientation) {
	            case HORIZONTAL: 
	            	coordinates.add(new BoatCase(this.x + j, this.y));
	                break;
	
	            case VERTICAL: 
	            	coordinates.add(new BoatCase(this.x, this.y + j));
	                break;
			}
		}
		this.boatcases = coordinates;
	}
	
	// Check if boat is sunk
	public boolean checkBoatSunk() {
		
		int hit = 0;
			for(BoatCase boatcase : this.boatcases){
				if(boatcase.touched()){
					hit++;
				}
			}
		
		if(hit == this.size) {
			this.sunk = true;
			return true;
		}
		else
			return false;
				
	}
	
}

	
	
