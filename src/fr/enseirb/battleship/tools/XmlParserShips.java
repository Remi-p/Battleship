package fr.enseirb.battleship.tools;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.enseirb.battleship.elements.Ship;
import fr.enseirb.battleship.elements.Type;
import fr.enseirb.battleship.exceptions.*;

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
	
	public List<Ship> getShips(Type ships_type, int height, int width) throws ShipOutOfBoundsException,ShipsConfigurationException{
		
		// Boat array initializing
		List<Ship> ships_array = new ArrayList();
		
		// Index of ships_array
		int j = 0;
		
		int number = 0;
		
		for (int i = 0; i < ships.getLength(); i++) {

			Node ship = ships.item(i);
			number = 0;
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
				
				for(Ship s : ships_array){
					if(s.getType().equals(type)){
						number ++;
					}
				}
				if(number == max_ships){
					throw new ShipsConfigurationException();
				}
				
				// Adding new Ship object in the array
				ships_array.add(new Ship(name, type, x, y, orientation, size, height, width));
				j++ ; // incrementing ships_array index	
				
				if (Config.VERBOSE)
					System.out.println("Player has one " + size + "-length " + type +
									   " named " + name + " at position (" + x + "," + y + "), " +
									   orientation + "-oriented, over " + max_ships + " maximum.");
				
			}
		}
		
		return ships_array;
	}
}
