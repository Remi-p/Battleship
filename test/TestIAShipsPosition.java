import java.io.FileWriter;
import java.io.IOException;

import fr.enseirb.battleship.Grid;
import fr.enseirb.battleship.IA;
import fr.enseirb.battleship.Player;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.tools.Config;
import fr.enseirb.battleship.tools.SvgWriter;


public class TestIAShipsPosition {

	public static void main(String[] args) throws IOException, InvalidGridException {
		
		Config.setVerbose(true);
		
		Player ia = new IA();
		
		Grid grid = ia.getGrid();
		
		SvgWriter writer = new SvgWriter(grid.getWidth(), grid.getHeight());

		writer.debugGrid(new FileWriter("debug.svg"), grid);
	}

}
