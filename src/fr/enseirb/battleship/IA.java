package fr.enseirb.battleship;

import fr.enseirb.battleship.elements.Coordinates;
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
	
	public void checkWin(){
		super.checkWin("You loose");
	}

	// Return true when the gameloop needs to be broken
	@Override
	public boolean play(Player player) {

		opponentloop:
		do {
			
			// IA turn
			Coordinates random_coordinates = player.grid.getRandomCoordinates();
        	if(player.getGrid().checkHit(random_coordinates, this.getName())) {
            	if(this.checkWin(player.getName()))
            		return true;
            	else
            		break opponentloop;
        	}
		} while(true);
	
		return false;
	}
}
