package exceptions;

public class ShipOverlapException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ShipOverlapException() {
		
		System.out.println("Ships overlap !");
	}
	
}
