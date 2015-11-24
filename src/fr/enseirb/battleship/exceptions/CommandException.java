package fr.enseirb.battleship.exceptions;

@SuppressWarnings("serial")
public class CommandException extends RuntimeException {
	
	public CommandException(){
		System.out.println("Not a valid command.");
		System.out.println("You can only use : view, debug and fire x y.");
	}

	public CommandException(int height, int width) {
		System.out.println("Not a valid command.");
		System.out.println("The size of the grid is 10x10.");
	}
	
}
