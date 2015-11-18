package fr.enseirb.battleship.elements;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.enseirb.battleship.tools.Config;

public class Type {
	private List<TypeElt> types;
	
	public Type() {
		this.types = new ArrayList<TypeElt>();
	}
	
	public Type(NodeList ships) {
		this.types = new ArrayList<TypeElt>();
		
		setTypes(ships);
	}
	
	// Return number of cases taken by ships
	public int getGridOccupation() {

		// Number of case taken by ships
		int num_cases = 0;

		for (TypeElt e : types) {
			num_cases += e.getQuantity() * e.getSize();
		}
		
		return num_cases;
	}
	
	public void setTypes(NodeList ships) {
		
		for (int i = 0; i < ships.getLength(); i++) {

			Node ship = ships.item(i);
			String name = ship.getNodeName();
			
			// We check if we really are in a node
			if (ship.getNodeType() == Node.ELEMENT_NODE) {
				
				// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
				Element elt = (Element) ship;

				if (Config.VERBOSE)
					System.out.println("There is " + elt.getFirstChild().getNodeValue() + " " + 
										elt.getAttribute("size") + "-length availabe ship of type " +
										name);
				
				TypeElt single_type = new TypeElt(name, Integer.parseInt(elt.getAttribute("size")), Integer.parseInt(elt.getTextContent()));
				
				types.add(single_type);
				
			}
		}
		
	}
	
	public int getSize(String type) {
		for	(TypeElt elt : this.types)
			if (elt.isType(type))
				return elt.getSize();
		
		// Error
		return -1;
	}

	public int getQty(String type) {
		
		for (TypeElt e : types) {
			if (type.compareTo(e.getType()) == 0)
				return e.getQuantity();
		}
		
		// Error :
		return -1;
	}
	
	//GETTER
	public List<TypeElt> getListType() {
		return this.types;
	}
}