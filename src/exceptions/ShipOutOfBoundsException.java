package exceptions;

public class ShipOutOfBoundsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShipOutOfBoundsException(int x, int y, String name) {
		
		System.out.println("One or both of " + name + " coordinates are out of bounds");
		System.out.println("\tx => " + x + "\n\ty => " + y);
	}

	public ShipOutOfBoundsException(String name) {
		
		System.out.println("The boat " + name + " exceeds the grid");
	}

}
