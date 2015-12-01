package fr.enseirb.battleship;

import java.io.IOException;
import java.net.Socket;

import fr.enseirb.battleship.exceptions.InvalidGridException;
import fr.enseirb.battleship.exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.exceptions.ShipOverlapException;
import fr.enseirb.battleship.exceptions.ShipsConfigurationException;

public class RemoteHuman extends Human {

	private Socket socket;
	
	public RemoteHuman(Socket socket) throws InvalidGridException, ShipOutOfBoundsException,
			ShipOverlapException, ShipsConfigurationException {
		super();
		this.socket = socket;
	}
	
	public void closeSocket() {
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
