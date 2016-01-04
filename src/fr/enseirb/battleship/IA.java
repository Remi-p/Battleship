package fr.enseirb.battleship;

import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.elements.Strategy;
import fr.enseirb.battleship.elements.Direction;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.tools.Config;
import fr.enseirb.battleship.tools.XmlParserGrid;

public class IA extends Player{
	private Strategy placement;
	private Strategy firing;
	private Coordinates cell_locked;
	private Direction direction_locked;
	private int depth;
	private boolean hybrid;
	
	public IA() throws InvalidGridException {	
		this("Jarvis");
	}
	
	public IA(String name) throws InvalidGridException {
		super(name);
		getPlacement();
		getFiring();
		this.grid = initialisationGrid();
		this.cell_locked = this.grid.getRandomCoordinates();
		this.direction_locked = Direction.NORTH;
		this.depth = 0;
		this.hybrid = false;
	}
	
	private Grid initialisationGrid() throws InvalidGridException {
		
		this.grid = new Grid(Config.CONFIGS, Config.GRID, this.placement, this.firing);
		
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
			switch (this.firing){
			
			case PERSO:						// Enable the hybrid mode , start in strategy far
				this.hybrid = true;
				this.firing = Strategy.FAR;
			case PACK:								// Fire from a locked cell in all 4 directions , continue in the direction while it hits something
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

				
				if(fire_coordinates.getX() >= 0 && fire_coordinates.getY() >= 0)			// If the fire is in the grid , check if it hit
					hit = player.getGrid().checkHit(fire_coordinates, this.getName());
				else 
					hit = 0;
				
	        	if(hit == 0){											// Fire isn't in the grid
	        		if(this.direction_locked == Direction.EAST)			// Reach the last direction
	        			if(hybrid)										// If hybrid mode , change strategy
	        				this.firing = Strategy.FAR;
	        			else{											// Else choose an another locked cell
	        				this.cell_locked = player.grid.getRandomCoordinates();
	        				this.depth = 1;
	        				this.direction_locked = Direction.NORTH;
	        			}
	        		else{												// Change direction
	        			this.depth = 1;
	        			this.direction_locked =  this.direction_locked.getNext();
	        		}
	        	}
        		else if(hit ==1){							// If a ship is touched check if the IA win and continue firing in the same direction
	            	if(this.checkWin(player.getName()))
	            		return true;
	            	else{
	            		this.depth += 1;
	            		break opponentloop;
	            	}
        		}
        		else{										// Fire missed
        			if(this.direction_locked == Direction.EAST)
	        			if(hybrid)
	        				this.firing = Strategy.FAR;
	        			else{
	        				this.cell_locked = player.grid.getRandomCoordinates();
	        				this.depth = 1;
	        				this.direction_locked = Direction.NORTH;
	        			}
	        		else{
	        			this.depth = 1;
	        			this.direction_locked =  this.direction_locked.getNext();
	        		}
        			break opponentloop;
        		}
				
				
			case FAR:				// Fire at separate coordinates
				fire_coordinates = player.grid.getRandomSmartCoordinates();
				hit= player.getGrid().checkHit(fire_coordinates, this.getName());
	        	if( hit == 1) {
	            	if(this.checkWin(player.getName()))
	            		return true;
	            	if(hybrid){		// if it hit and we are in hybrid mode , change strategy
	            		this.cell_locked = fire_coordinates;
	            		this.firing = Strategy.PACK;
	            		this.depth = 1;
	            		this.direction_locked = Direction.NORTH;
	            		break opponentloop;
	            	}
	            	else
	            		break opponentloop;
	        	}
	        	else if(hit ==2)
	        		break opponentloop;
	        	
			case RANDOM:		// Fire at random coordinates
				fire_coordinates = player.grid.getRandomCoordinates();
				hit= player.getGrid().checkHit(fire_coordinates, this.getName());
	        	if( hit == 1) {
	            	if(this.checkWin(player.getName()))
	            		return true;
	            	else
	            		break opponentloop;
	        	}
	        	else if(hit ==2)
	        		break opponentloop;
			}	
		} while(true);
	
		return false;
	}
	
	private void getPlacement() {
		XmlParserGrid grid = new XmlParserGrid(Config.CONFIGS, Config.GRID);
		this.placement = grid.getPlacement();
		
		if (Config.VERBOSE)
			System.out.println("Placement : " + this.placement);
		
	}
	
	private void getFiring() {
		XmlParserGrid grid = new XmlParserGrid(Config.CONFIGS, Config.GRID);
		this.firing = grid.getFiring();
		
		if (Config.VERBOSE)
			System.out.println("Firing : " + this.firing);
	}
	
	
}
