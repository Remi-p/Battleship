package fr.enseirb.battleship;

import fr.enseirb.battleship.tools.Config;

// Main class
public class App {
	
	public static void main(String[] args) throws Exception {
		
		Config.setVerbose(false);
		
		Game game = new Game(args);
		
		// In case of debug, we just display grid and leave
		if (game.isDebug())
			game.debugAndExit();
		
		// When it's a client, the server will play first
		if (game.isClient()) {
			System.out.println("Opponent will play first.");
			game.turnOfPlayer(false);
		}
		else
			game.turnOfPlayer(true);
		
		game.end();
	}
}
