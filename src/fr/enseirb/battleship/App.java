package fr.enseirb.battleship;

import fr.enseirb.battleship.exceptions.CommandException;
import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.tools.Config;
import fr.enseirb.battleship.tools.Command;
import fr.enseirb.battleship.tools.Read;
import fr.enseirb.battleship.tools.SvgWriter;

// Main classes
public class App {

	public static void main(String[] args) throws Exception {
		
		Config.setVerbose(false);
		
		if (args.length != 3)
			throw new Exception("Incorrect number of arguments.");
		
		// Creating players objects
		Human human = new Human("Anonymous", args[1], args[2]);
		IA ia = new IA();
		
		// We get the dimensions of grids
		int width = human.getGrid().getWidth();
		int height = human.getGrid().getHeight();
		
		// Object for writing svg debug file
		SvgWriter writer = new SvgWriter(width, height, human, ia);

		if ("debug".compareTo(args[0]) == 0) {
			writer.debugGrids();
		}
		else {
		// Human player
			gameloop:
			do{
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
				            	if(ia.getGrid().checkHit(fire_coordinates, "human")) {
					            	if(ia.checkWin("human")) {
					            		break gameloop;
					            	}
					            	else
					            		break humanloop;
				            	}
				            break;
				            
				            case QUIT:
				            	System.out.println("End of the game. Bye !");
				            	break gameloop;

				            
				            default: 
				            break;
				            
				        }
					}
					catch(CommandException e) {
								
					}
					
				} while(true);
				// End human loop
				
			ialoop:
				do {
					// IA turn
					Coordinates random_coordinates = human.grid.getRandomCoordinates();
	            	if(human.getGrid().checkHit(random_coordinates, ia.getName())) {
		            	if(ia.checkWin(human.getName()))
		            		break gameloop;
		            	else
		            		break ialoop;
	            	}
				} while(true);

			} while(true);
			// End game loop
    	System.exit(0); // End of game
	}
}}
