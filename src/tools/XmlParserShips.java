package tools;

import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.enseirb.battleship.Ship;

public class XmlParserShips extends XmlParser {

	private NodeList ships;
	
	public XmlParserShips(String configs_path) {
		super(configs_path, "ships.xml");
		
		// NodeList initializing
		Node ships_node = this.document.getElementsByTagName("ships").item(0);
		this.ships = ships_node.getChildNodes();
	}
	
	public Ship[] getShips(HashMap<String, List<Integer>> ships_size) {
		
		// Boat array initializing
		Ship[] ships_array = new Ship[this.ships.getLength()];	
		
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
				int size = ships_size.get(elt.getAttribute("type")).get(0);
				int max_ships = ships_size.get(elt.getAttribute("type")).get(1);
				
				// Adding new Ship object in the array
				ships_array[i] = new Ship(name, type, x, y, orientation, size);
								
				System.out.println("Player has one " + size + "-length " + type +
								   " named " + name + " at position (" + x + "," + y + "), " +
								   orientation + "-oriented, over " + max_ships + " maximum.");
				
			}
		}
		
		return ships_array;
	}
}
