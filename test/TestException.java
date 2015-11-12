import org.junit.Test;

import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.Grid;
import fr.enseirb.battleship.Ship;

public class TestException {
	
	@Test(expected=ShipOutOfBoundsException.class)
	public void testShipOutLowerThanZero() throws ShipOutOfBoundsException {
		new Ship("Boat1", "", 1, -1, "", 1, 10, 10);
	}

	@Test(expected=ShipOutOfBoundsException.class)
	public void testShipOutGreaterThanGrid() throws ShipOutOfBoundsException {
		new Ship("Boat2", "", 13, 7, "", 1, 10, 10);
	}

	@Test(expected=ShipOutOfBoundsException.class)
	public void testShipExceedsGrid() throws ShipOutOfBoundsException {
		new Ship("Boat3", "", 7, 7, "horizontal", 4, 10, 10);
	}

	@Test(expected=InvalidGridException.class)
	public void testGridMin() throws InvalidGridException {
		new Grid(8, 11);
	}

//  Comment faire quand cela dépend des informations de ships.xml ?
//	@Test(expected=ShipOverlapException.class)
//	public void testGridMin() throws InvalidGridException {
//		new Grid(8, 11);
//	}
	
}
