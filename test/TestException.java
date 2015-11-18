import org.junit.Test;

import fr.enseirb.battleship.Grid;
import fr.enseirb.battleship.elements.Ship;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.exceptions.ShipOverlapException;
import fr.enseirb.battleship.exceptions.ShipsConfigurationException;
import fr.enseirb.battleship.tools.Config;

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

	@Test(expected=InvalidGridException.class)
	public void testGridTooMuchShips() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		new Grid(Config.CONFIGS, "grid_too_much_ships.xml", Config.SHIPS);
	}

	@Test(expected=ShipOverlapException.class)
	public void testGridShipsOverlap() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		new Grid(Config.CONFIGS, Config.GRID, "ships_overlap.xml");
	}
	
	// Testing with different coordinates
	@Test(expected=ShipOverlapException.class)
	public void testGridShipsOverlapDifferents() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		new Grid(Config.CONFIGS, Config.GRID, "ships_overlap_diff.xml");
	}
	
	@Test(expected=ShipsConfigurationException.class)
	public void testGridTooManyShips() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		new Grid(Config.CONFIGS, Config.GRID, "too_many_ships.xml");
	}
	
}
