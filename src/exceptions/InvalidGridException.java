package exceptions;

public class InvalidGridException extends Exception {

	public InvalidGridException(double occupation) {
		System.out.println("Ships has to take less than 20% of total number of cases.");
		System.out.println("\tCurrently : " + occupation + "%");
	}

	public InvalidGridException(int height, int width) {
		
		System.out.println("The minimum size of the grid is 10x10.");
		System.out.println("\th = " + height + "\n\tw = " + width);
	}
	
}
