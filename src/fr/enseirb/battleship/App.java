package fr.enseirb.battleship;

import fr.enseirb.battleship.exceptions.CommandException;
import java.io.*;
import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.tools.Config;
import fr.enseirb.battleship.tools.Command;
import fr.enseirb.battleship.tools.Read;
import fr.enseirb.battleship.tools.SvgWriter;

// Main classes
public class App {

	public static void main(String[] args) throws Exception {
		
		Config.setVerbose(true);
		
		if (args.length != 3)
			throw new Exception("Incorrect number of arguments.");
		
		// Creating players objects
		Human human = new Human("Anonymous", args[1], args[2]);
		IA ia = new IA();
		
		// We get the dimensions of grids
		int width = human.getGrid().getWidth();
		int height = human.getGrid().getHeight();
		
		// Object for writing svg debug file
		SvgWriter writer = new SvgWriter(width, height);

		if ("debug".compareTo(args[0]) == 0) {
			writer.debugGrids(new FileWriter("debug.svg"), human, ia);
		}
		
		// Human player
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
			            	System.out.println("View ");
			            break;
			            
			            case DEBUG:
			            	writer.debugGrids(new FileWriter("debug.svg"), human, ia);
			            break;
			            
			            case FIRE:
			            	Coordinates fire_coordinates = read.getCoordinates(input, height, width);
			            	ia.getGrid().checkHit(fire_coordinates);
			            	human.checkWin();

			            break;
			            
			            default: 
			            break;
			            
			        }
			}
			catch(CommandException e) {
						
			}
			
		} while(true);
	}
	
}
