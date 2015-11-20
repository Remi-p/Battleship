package fr.enseirb.battleship;

import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.tools.Config;

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