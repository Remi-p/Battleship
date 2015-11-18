package fr.enseirb.battleship;

import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.exceptions.ShipOverlapException;
import fr.enseirb.battleship.exceptions.ShipsConfigurationException;
import fr.enseirb.battleship.tools.Config;

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
