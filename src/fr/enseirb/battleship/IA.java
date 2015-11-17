package fr.enseirb.battleship;

import java.util.List;

import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;
import tools.Config;

public class IA extends Player{
	
	public IA() {
		this("Jarvis");
	}
	
	public IA(String name) {
		super(name);
		this.grid = InitialisationGrid();
	}
	
	private Grid InitialisationGrid() {
		
		Grid grid = null;
		List<Ship> ships = null;
		
		try {

			grid = new Grid(Config.CONFIGS, Config.GRID);
			

		} catch (InvalidGridException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShipOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ShipOverlapException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return grid;
	}
	
}
