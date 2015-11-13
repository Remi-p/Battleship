import org.junit.Test;

import tools.Constant;
import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;
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

	@Test(expected=InvalidGridException.class)
	public void testGridTooMuchShips() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		new Grid(Constant.CONFIGS, "grid_too_much_ships.xml", Constant.SHIPS);
	}

	// TODO : Vérifier le bon fonctionnement. Here it seems like it works only when first coordinates are equals
	@Test(expected=ShipOverlapException.class)
	public void testGridShipsOverlap() throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		new Grid(Constant.CONFIGS, Constant.GRID, "ships_overlap.xml");
	}
	
}
