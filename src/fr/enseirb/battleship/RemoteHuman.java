package fr.enseirb.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.exceptions.ShipOverlapException;
import fr.enseirb.battleship.exceptions.ShipsConfigurationException;

public class RemoteHuman extends Human {

	private Socket socket;
	private BufferedReader in;
	private InputStream inputStream;
	
	public RemoteHuman(Socket socket) throws InvalidGridException, ShipOutOfBoundsException,
			ShipOverlapException, ShipsConfigurationException {
		super("Remote Human");
		this.socket = socket;
		
		try {
			this.inputStream = socket.getInputStream();
			this.in = new BufferedReader(new InputStreamReader(this.inputStream));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeSocket() {
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean play(Player player) {
		String recv = null;
		
		try {
			recv = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Socket has been closed
		if (recv == null) {
			System.out.println("Disconnected.");
			return true;
		}
		
		Coordinates recv_coord = Coordinates.strToCoord(recv);
		
    	if(player.getGrid().checkHit(recv_coord, this.getName())) {
        	if(this.checkWin(player.getName()))
        		return true;
    	}
    	
    	return false;

	}

	public void recvGrid() throws Exception {
		ObjectInputStream ois = null;
		
		ois = new ObjectInputStream(this.inputStream);
		
	    this.grid = (Grid) ois.readObject();
	      
	    //ois.close();
		      
	}
	
}
