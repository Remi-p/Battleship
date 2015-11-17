import tools.Config;
import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;
import fr.enseirb.battleship.Grid;

public class TestXML {

	public static void main(String[] args) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		
		//Grid grid = new Grid(Constant.CONFIGS, "grid_too_much_ships.xml", Constant.SHIPS);
		
		new Grid(Config.CONFIGS, Config.GRID, Config.SHIPS);
		
	}

}
