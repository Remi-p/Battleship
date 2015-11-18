import fr.enseirb.battleship.Grid;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.exceptions.ShipOverlapException;
import fr.enseirb.battleship.exceptions.ShipsConfigurationException;
import fr.enseirb.battleship.tools.Config;

public class TestXML {

	public static void main(String[] args) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException, ShipsConfigurationException {
		
		//Grid grid = new Grid(Constant.CONFIGS, "grid_too_much_ships.xml", Constant.SHIPS);
		
		new Grid(Config.CONFIGS, Config.GRID, Config.SHIPS);
		
	}

}
