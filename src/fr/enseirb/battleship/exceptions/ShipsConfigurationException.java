package fr.enseirb.battleship.exceptions;

@SuppressWarnings("serial")
public class ShipsConfigurationException extends Exception {
	public ShipsConfigurationException (){
		System.out.println("Too many ships of one type");
	}
}
