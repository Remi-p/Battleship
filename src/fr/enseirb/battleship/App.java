package fr.enseirb.battleship;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import fr.enseirb.battleship.exceptions.CommandException;
import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.tools.Config;
import fr.enseirb.battleship.tools.Command;
import fr.enseirb.battleship.tools.Read;
import fr.enseirb.battleship.tools.SvgWriter;

// Main classes
public class App {

	private static void gameLoop(SvgWriter writer, int height, int width, Human human, Player player2, boolean jump) {
		
		// Human player
		gameloop:
		do{

			if (!jump) {
				if (human.play(player2, writer, height, width))
					break gameloop;
			}
			else {
				System.out.println("The opponent will play first.");
				jump = false;
			}
				

			if (player2.play(human))
				break gameloop;

		} while(true);
		// End game loop
	}
	
	public static void main(String[] args) throws Exception {
		
		Config.setVerbose(false);
		
		if (args.length < 3 || args.length > 5)
			throw new Exception("Incorrect number of arguments.");
		
		// Creating human object
		Human human = new Human("Anonymous", args[1], args[2]);
		
		// We get the dimensions of grids
		int width = human.getGrid().getWidth();
		int height = human.getGrid().getHeight();

		// Creating IA object / opponent
		Player player2;
		
		// http://www.jmdoudoux.fr/java/dej/chap-net.htm
		if ("server".compareTo(args[0]) == 0) {
			
			// Port is passed in args[3]
			ServerSocket socketServer = new ServerSocket(Integer.parseInt(args[3]));
			
			Socket socketClient = socketServer.accept();
			
			player2 = new RemoteHuman(socketClient);
			human.setOpponent(socketClient);
		}
		else if ("client".compareTo(args[0]) == 0) {
			
			// Addr is in args[3], port in args[4]
			InetAddress serveur = InetAddress.getByName(args[3]);
			Socket socketServeur = new Socket(serveur, Integer.parseInt(args[4]));
			
			player2 = new RemoteHuman(socketServeur);
			human.setOpponent(socketServeur);
			
		}
		else // Against IA
			player2 = new IA();
		
		// Object for writing svg debug file
		SvgWriter writer = new SvgWriter(width, height, human, player2);

		// In case of debug, we just display grid and leave
		if ("debug".compareTo(args[0]) == 0) {
			writer.debugGrids();
	    	System.exit(0); // End of game
		}
		
		// When it's a client, the server will play first
		if ("client".compareTo(args[0]) == 0)
			gameLoop(writer, height, width, human, player2, true);
		else
			gameLoop(writer, height, width, human, player2, false);
		
	}
}
