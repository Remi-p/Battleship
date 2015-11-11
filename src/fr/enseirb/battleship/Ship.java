package fr.enseirb.battleship;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Ship {
	private String name;
	private Type type;
	private int x;
	private int y;
	private Orientation orientation;
	private int size;
	
	public Ship() {
		this("configs/");
	}
	
	public Ship(String configs_path){
		super();

		//extraction XML
		getXml(configs_path);
	}
	
	private void getXml(String configs_path) {
	
		// http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
		// http://stackoverflow.com/questions/12891992/get-node-by-name-from-nodelist
	
		// We're using both xpath and nodelist for that
		try {
	
			// Creating the Document object from the XML file
			File fXmlFile = new File(configs_path + "ships.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
	
			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();
	
			// Ships
	
			Node ships_node = doc.getElementsByTagName("ships").item(0);
			NodeList ships = ships_node.getChildNodes();
	
			for (int i = 0; i < ships.getLength(); i++) {
	
				Node ship = ships.item(i);
				
				// We check if we really are in a node
				if (ship.getNodeType() == Node.ELEMENT_NODE) {
	
					// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
					Element elt = (Element) ship;
					Element position = (Element) elt.getElementsByTagName("position").item(0);
					
					this.name = elt.getAttribute("id");
					setType(elt.getAttribute("type"));
					this.x = Integer.parseInt(position.getAttribute("x"));
					this.y = Integer.parseInt(position.getAttribute("y"));
					setOrientation(position.getAttribute("orientation"));
										
					System.out.println("Player has one " + 
										elt.getAttribute("type") +
										" named " +
										elt.getAttribute("id") +
										"at position (" +
										position.getAttribute("x") + "," 
										+ position.getAttribute("y") + ") " +
										position.getAttribute("orientation") + " oriented");
										
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// SETTERS
	public void setType(String type) {
		
		if("battleship".compareTo(type) == 0) {
			this.type = Type.BATTLESHIP;
		}
		else if("submarine".compareTo(type) == 0) {
			this.type = Type.SUBMARINE;
		}
		else if("destroyer".compareTo(type) == 0) {
			this.type = Type.DESTROYER;
		}
		else if("patrol-boat".compareTo(type) == 0) {
			this.type = Type.PATROL_BOARD;
		}
		else if("aircraft-carrier".compareTo(type) == 0) {
			this.type = Type.AIRCRAFT_CARRIER;
		}
	}
	
	public void setOrientation(String orientation) {
		
		if("horizontal".compareTo(orientation) == 0) {
			this.orientation = Orientation.HORIZONTAL;
		}
		else {
			this.orientation = Orientation.VERTICAL;
		}
	}
	
	
	// GETTERS
	// ATTENTION : methodes public a changer en fonction après
	
	public String getName(String name) {
		return this.name;
	}
	
	public Type getType(Type type) {
		return this.type;
	}
	
	public int getX(int x) {
		return this.x;
	}
	
	public int getY(int y) {
		return this.y;
	}
	
	public Orientation getOrientation(Orientation orientation) {
		return this.orientation;
	}
	
	public int getSize(int size) {
		return this.size;
	}
}
