package fr.enseirb.battleship;

import java.util.List;

import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;
import tools.Config;

public class IA extends Player{
	
	public IA() throws InvalidGridException {
		this("Jarvis");
	}
	
	public IA(String name) throws InvalidGridException {
		super(name);
		this.grid = InitialisationGrid();
	}
	
	private Grid InitialisationGrid() throws InvalidGridException {
		
		this.grid = new Grid(Config.CONFIGS, Config.GRID);
		
		return grid;
	}
	
}
