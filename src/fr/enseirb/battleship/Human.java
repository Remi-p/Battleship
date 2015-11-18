package fr.enseirb.battleship;

import tools.Config;
import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;
import exceptions.ShipsConfigurationException;

public class Human extends Player {
	
	public Human() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		this("Anonymous");
	}

	public Human(String name, String grid_filename, String ships_filename ) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		super(name);
		this.grid = new Grid(Config.CONFIGS, grid_filename, ships_filename);
	}
	
	public Human(String name) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		super(name);
		this.grid = new Grid();
	}
}
