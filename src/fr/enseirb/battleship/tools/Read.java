package fr.enseirb.battleship.tools;

import java.io.IOException;

import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.exceptions.CommandException;


public class Read {
	
	public String getInput() {
		
		String text = "";
		char C='\0';
		
		try {
			// Get input
			while ((C=(char) System.in.read()) !='\n')
	        {
				if (C != '\r')  text = text+C;
	        }
		}		
		catch (IOException e)
        {
			System.out.println("Erreur de frappe");
			System.exit(0);
        }
		return text;
	}
	
	
	public Command getCommand(String input) throws CommandException { // Read a command
		
			String command = "";
			
			// Extract first word
		    if (input.indexOf(' ') > -1) { // Check if there is more than one word.
		    	command = input.substring(0, input.indexOf(' ')); // Extract first word.
		    } 
		    else {
		    	command = input; // Text is the first word itself.
		    }
		    
			if ("view".compareTo(command) == 0) {
				return Command.VIEW;
			}
			else if("debug".compareTo(command) == 0) {
				return Command.DEBUG;
			}
			else if("fire".compareTo(command) == 0) {
				return Command.FIRE;
			}
			else {
				throw new CommandException();
			}
			
	} 
	
	public Coordinates getCoordinates(String input, int height, int width) throws CommandException {
		
		int x, y;
		try{
			String coord[] = input.split(" ", 3);
			
			x = Integer.parseInt(coord[1]);
			y = Integer.parseInt(coord[2]);
			
			if( (x < 0 || x > width) || (y < 0 || y > height) ) {
				throw new CommandException(height, width);
			}
			
			Coordinates coordinates = new Coordinates(x,y);
			return coordinates;
		}
		catch (NumberFormatException e) {
			// Exception if x and y are not numbers.
		}
		return null;
	}


	
}         
