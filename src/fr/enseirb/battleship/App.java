package fr.enseirb.battleship;

import java.io.FileWriter;

import tools.Config;
import tools.SvgWriter;

// Main classes
public class App {

	public static void main(String[] args) throws Exception {
		if (args.length != 3)
			throw new Exception("Nombre d'arguments invalides");
		
		//Player human = new Human();
		//Player ia = new IA();
		
		Config.setVerbose(true);
		
		Grid grid = new Grid(Config.CONFIGS, args[1], args[2]);

		SvgWriter writer = new SvgWriter(grid.getWidth(), grid.getHeight());
		
		if ("debug".compareTo(args[0]) == 0) {
			writer.debugGrid(new FileWriter("debug.svg"), grid);
		}
	}

}
