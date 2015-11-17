import java.io.FileWriter;
import java.io.IOException;

import tools.Config;
import tools.SvgWriter;
import exceptions.InvalidGridException;
import fr.enseirb.battleship.Grid;
import fr.enseirb.battleship.IA;
import fr.enseirb.battleship.Player;


public class TestIAShipsPosition {

	public static void main(String[] args) throws IOException, InvalidGridException {
		
		Config.setVerbose(true);
		
		Player ia = new IA();
		
		Grid grid = ia.getGrid();
		
		SvgWriter writer = new SvgWriter(grid.getWidth(), grid.getHeight());

		writer.debugGrid(new FileWriter("debug.svg"), grid);
	}

}
