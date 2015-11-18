package fr.enseirb.battleship.exceptions;

public class ShipsConfigurationException extends Exception {
	public ShipsConfigurationException (){
		System.out.println("Too many ships of one type");
	}
}
