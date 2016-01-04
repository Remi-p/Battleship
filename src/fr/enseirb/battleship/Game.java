package fr.enseirb.battleship;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import fr.enseirb.battleship.tools.SvgWriter;

public class Game {

	private Human player1;
	// Seamless for player2's subclass (IA, RemoteHuman)
	private Player player2;
	// debug, play, etc.
	private String type;
	
	private SvgWriter writer;

	// Saved only in order to close the socket
	private ServerSocket socketServer;
	
	public Game(String[] args) throws Exception {

		if (args.length < 3 || args.length > 5)
			throw new Exception("Incorrect number of arguments.");
		
		// Creating human object
		this.player1 = new Human("Anonymous", args[1], args[2]);
		
		// Type of game (debug, play, multiplayer ...)
		this.type = args[0];
		
		// http://www.jmdoudoux.fr/java/dej/chap-net.htm
		// Behavior is not the same if we are a server or a client
		if (this.isServer()) {
			
			// Port is passed in args[3]
			this.socketServer = new ServerSocket(Integer.parseInt(args[3]));
			
			Socket socketClient = this.socketServer.accept();
			
			player2 = new RemoteHuman(socketClient);
			player1.setOpponent(socketClient);
		}
		else if (this.isClient()) {

			// Addr is in args[3], port in args[4]
			InetAddress serveur = InetAddress.getByName(args[3]);
			Socket socketServeur = new Socket(serveur, Integer.parseInt(args[4]));
			
			player2 = new RemoteHuman(socketServeur);
			player1.setOpponent(socketServeur);
		}
		else // Against IA
			player2 = new IA();
		
		// When we are on multiplayer, we exchange grids
		if (this.isServer() || this.isClient()) {
			player1.sendGridToOpponent();
			((RemoteHuman) player2).recvGrid();
		}
		
		// Object for writing svg debug file
		this.writer = new SvgWriter(getWidth(), getHeight(), player1, player2);
	}
	
	// This function call itself when finished, to take charge of the next turn
	public void turnOfPlayer(boolean i) {
		
		if (i) { // Turn of player #1
			if (player1.play(player2, writer, getHeight(), getWidth()))
				return;
			else
				turnOfPlayer(!i);
		}
		else { // Turn of player #2
			if (player2.play(player1))
				return;
			else
				turnOfPlayer(!i);
		}
		
	}
	
	public boolean isServer() {
		return ("server".compareTo(this.type) == 0);
	}
	
	public boolean isClient() {
		return ("client".compareTo(this.type) == 0);
	}
	
	public boolean isDebug() {
		return ("debug".compareTo(this.type) == 0);
	}
	
	public void debugAndExit() {
		this.writer.debugGrids();
		System.out.println("End of debug.");
    	System.exit(0); // End of game
	}
	
	public int getWidth() {
		return player1.getGrid().getWidth();
	}
	
	public int getHeight() {
		return player1.getGrid().getHeight();
	}
	
	// End the game, eventually close connections
	public void end() throws IOException {
		if (isServer() || isClient()) {
			if (isServer())
				socketServer.close();
			
			player1.closeScktOpponent();
			
			// If this is multiplayer, player2 is of type RemoteHuman
			((RemoteHuman)player2).closeSocket();
		}
	}
	
}
