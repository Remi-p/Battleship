package fr.enseirb.battleship;

import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.elements.Strategy;
import fr.enseirb.battleship.elements.Direction;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.tools.Config;

public class IA extends Player{
	private Strategy strategy;
	private Coordinates cell_locked;
	private Direction direction_locked;
	private int depth;
	
	public IA() throws InvalidGridException {
		this("Jarvis");
	}
	
	public IA(String name) throws InvalidGridException {
		super(name);
		this.grid = InitialisationGrid();
		this.strategy = Strategy.FAR;
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
		int hit;
		opponentloop:
		do {
			Coordinates fire_coordinates;
			// IA turn
			switch (this.strategy){
			case PACK:
				switch (this.direction_locked){
				case NORTH:
					fire_coordinates = new Coordinates(this.cell_locked.getX(),this.cell_locked.getY()-this.depth);
					break;
				case WEST:
					fire_coordinates = new Coordinates(this.cell_locked.getX()-this.depth,this.cell_locked.getY());
					break;
				case SOUTH:
					fire_coordinates = new Coordinates(this.cell_locked.getX(),this.cell_locked.getY()+this.depth);
					break;
				default:
					fire_coordinates = new Coordinates(this.cell_locked.getX()+this.depth,this.cell_locked.getY());
					break;
				}
				
				
				hit = player.getGrid().checkHit(fire_coordinates, this.getName());
	        	if(hit == 0){
	        		if(this.direction_locked == Direction.EAST)
	        			this.strategy = Strategy.FAR;   
	        		else{
	        			this.depth = 1;
	        			this.direction_locked =  this.direction_locked.getNext();
	        		}
	        	}
        		else if(hit ==1){
	            	if(this.checkWin(player.getName()))
	            		return true;
	            	else{
	            		this.depth += 1;
	            		break opponentloop;
	            	}
        		}
        		else{
        			if(this.direction_locked == Direction.EAST)
	        			this.strategy = Strategy.FAR;   
	        		else{
	        			this.depth = 1;
	        			this.direction_locked =  this.direction_locked.getNext();
	        		}
        			break opponentloop;
        		}
				
			case FAR:
				fire_coordinates = player.grid.getRandomSmartCoordinates();
				hit= player.getGrid().checkHit(fire_coordinates, this.getName());
	        	if( hit == 1) {
	            	if(this.checkWin(player.getName()))
	            		return true;
	            	else
	            		this.cell_locked = fire_coordinates;
	            		this.strategy = Strategy.PACK;
	            		this.depth = 1;
	            		this.direction_locked = Direction.NORTH;
	            		break opponentloop;
	        	}
	        	else if(hit ==2)
	        		break opponentloop;
			case RANDOM:
				break;
			default:
				break;
			}	
		} while(true);
	
		return false;
	}
}
