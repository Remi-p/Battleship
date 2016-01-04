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
			case PERSO:
				this.hybrid = true;
				this.firing = Strategy.FAR;
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

				
				if(fire_coordinates.getX() >= 0 && fire_coordinates.getY() >= 0)
					hit = player.getGrid().checkHit(fire_coordinates, this.getName());
				else 
					hit = 0;
				
	        	if(hit == 0){
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
				
				
			case FAR:
				fire_coordinates = player.grid.getRandomSmartCoordinates();
				hit= player.getGrid().checkHit(fire_coordinates, this.getName());
	        	if( hit == 1) {
	            	if(this.checkWin(player.getName()))
	            		return true;
	            	if(hybrid){
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
	        	
			case RANDOM:
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
