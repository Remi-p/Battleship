package fr.enseirb.battleship.exceptions;

@SuppressWarnings("serial")
public class ShipOverlapException extends Exception {

	public ShipOverlapException() {
		
		System.out.println("Ships overlap !");
	}
	
}
