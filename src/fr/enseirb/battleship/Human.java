package fr.enseirb.battleship;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.exceptions.CommandException;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.exceptions.ShipOverlapException;
import fr.enseirb.battleship.exceptions.ShipsConfigurationException;
import fr.enseirb.battleship.tools.Command;
import fr.enseirb.battleship.tools.Config;
import fr.enseirb.battleship.tools.Read;
import fr.enseirb.battleship.tools.SvgWriter;

public class Human extends Player {
	
	private Socket opponent;
	private PrintStream out;
	
	public Human() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		this("Anonymous");
	}

	public Human(String name, String grid_filename, String ships_filename ) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		super(name);
		this.grid = new Grid(Config.CONFIGS, grid_filename, ships_filename);
		this.opponent = null;
	}
	
	public Human(String name) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		super(name);
		this.grid = new Grid();
		this.opponent = null;
	}
	
	public void checkWin(){
		super.checkWin("You win");
	}

	public void setOpponent(Socket opp) {
		this.opponent = opp;
		
		try {
			this.out = new PrintStream(opp.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeScktOpponent() throws IOException {
		this.opponent.close();
	}
	
	@Override
	public boolean play(Player player) {return false;}
	
	// Return true when the gameloop needs to be broken
	public boolean play(Player player, SvgWriter writer, int height, int width) {

		humanloop:
		do{
			
			try {
				
				String input;
				Command command;
				
				Read read = new Read();
				input = read.getInput();
				command = read.getCommand(input);
				
				switch(command)
		        {
		            case VIEW:
		            	writer.view(1);
		            	System.out.println("View written in " + writer.getWriter_play());
		            break;
		            
		            case DEBUG:
		            	writer.debugGrids();
		            	System.out.println("Debug written in " + writer.getWriter_debug());
		            break;
		            
		            case FIRE:
		            	Coordinates fire_coordinates = read.getCoordinates(input, height, width);
		            	if(player.getGrid().checkHit(fire_coordinates, "human")) {
		            		
		            		// If socket is set, we send informations
		            		if (this.opponent != null)
		            			this.tellOpponent(fire_coordinates);
		            		
			            	if(player.checkWin("human")) {
			            		return true;
			            	}
			            	else
			            		break humanloop;
		            	}
		            break;
		            
		            case QUIT:
		            	System.out.println("End of the game. Bye !");
		            	return true;

		            
		            default: 
		            break;
		            
		        }
			}
			catch(CommandException e) {
			}
			
		} while(true);
		// End human loop
		
		return false;
	}

	private void tellOpponent(Coordinates fire_coordinates) {
		this.out.println(fire_coordinates.toString());
	}
}
