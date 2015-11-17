package tools;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exceptions.ShipOutOfBoundsException;
import fr.enseirb.battleship.Ship;
import fr.enseirb.battleship.Type;

public class XmlParserShips extends XmlParser {

	private NodeList ships;
	
	public XmlParserShips(String configs_path) {
		this(configs_path, "ships.xml");
	}
	
	public XmlParserShips(String configs_path, String xmlfilename) {
		super(configs_path, xmlfilename);
		
		// NodeList initializing
		Node ships_node = this.document.getElementsByTagName("ships").item(0);
		this.ships = ships_node.getChildNodes();
	}
	
	public Ship[] getShips(Type ships_type, int height, int width) throws ShipOutOfBoundsException{
		
		// Boat array initializing
		Ship[] ships_array = new Ship[(this.ships.getLength()-1)/2];	
		// Index of ships_array
		int j = 0;
		
		for (int i = 0; i < ships.getLength(); i++) {

			Node ship = ships.item(i);
			
			// We check if we really are in a node
			if (ship.getNodeType() == Node.ELEMENT_NODE) {
				
				// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
				Element elt = (Element) ship;
				Element position = (Element) elt.getElementsByTagName("position").item(0);
			
				// Parsing
				String name = elt.getAttribute("id");
				String type = elt.getAttribute("type");
				int x = Integer.parseInt(position.getAttribute("x"));
				int y = Integer.parseInt(position.getAttribute("y"));
				String orientation = position.getAttribute("orientation");
				int size = ships_type.getSize(elt.getAttribute("type"));
				int max_ships = ships_type.getQty(elt.getAttribute("type"));
				
				// Adding new Ship object in the array
				 
				ships_array[j] = new Ship(name, type, x, y, orientation, size, height, width);
				j++ ; // incr of ships_array index	
				
				if (Config.VERBOSE)
					System.out.println("Player has one " + size + "-length " + type +
									   " named " + name + " at position (" + x + "," + y + "), " +
									   orientation + "-oriented, over " + max_ships + " maximum.");
				
			}
		}
		
		return ships_array;
	}
}
