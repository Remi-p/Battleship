package fr.enseirb.battleship.exceptions;

@SuppressWarnings("serial")
public class ShipOutOfBoundsException extends Exception {

	public ShipOutOfBoundsException(int x, int y, String name) {
		
		System.out.println("One or both of " + name + " coordinates are out of bounds");
		System.out.println("\tx => " + x + "\n\ty => " + y);
	}

	public ShipOutOfBoundsException(String name) {
		
		// TODO : find a way that this warning does not come in commandline when initializing IA boats
		//System.out.println("The boat " + name + " exceeds the grid");
	}

}
