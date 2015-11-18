package fr.enseirb.battleship.exceptions;

@SuppressWarnings("serial")
public class CommandException extends RuntimeException{
	public CommandException(){
		System.out.println("Not a valid command");
	}
}
