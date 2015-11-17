package fr.enseirb.battleship;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tools.Config;

public class Type {
	private TypeElt[] types;
	
	public Type(int nb) {
		this.types = new TypeElt[nb];
	}
	
	public Type(int nb, NodeList ships) {
		this.types = new TypeElt[nb];
		
		setTypes(ships);
	}
	
	// Return number of cases taken by ships
	public int getGridOccupation() {

		// Number of case taken by ships
		int num_cases = 0;

		for (int i = 0; i < this.types.length; i++) {
			num_cases += types[i].quantity * types[i].size;
		}
		
		return num_cases;
	}
	
	public void setTypes(NodeList ships) {
		
		int arr_pos = 0;
		
		for (int i = 0; i < ships.getLength(); i++) {

			Node ship = ships.item(i);
			String name = ship.getNodeName();
			
			// We check if we really are in a node
			if (ship.getNodeType() == Node.ELEMENT_NODE) {
				
				
				
				// TODO : Quand on enleve cette ligne, le verbose de 'name' ne s'affiche pas
				System.out.println(name + " ??");
				
				// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
				Element elt = (Element) ship;

				if (Config.VERBOSE)
					System.out.println("There is " + elt.getFirstChild().getNodeValue() + " " + 
										elt.getAttribute("size") + "-length ship of type " +
										name);
				
				System.out.println("arr_pos = "+arr_pos);
				
				TypeElt single_type = new TypeElt(name, Integer.parseInt(elt.getAttribute("size")), Integer.parseInt(elt.getTextContent()));
				
				types[arr_pos] = single_type;
				
				// Put elements in the array
				// types[arr_pos].type = name; // TODO : Java NullPointer ?!
				// types[arr_pos].size = Integer.parseInt(elt.getAttribute("size"));
				// types[arr_pos].quantity = Integer.parseInt(elt.getTextContent());		
				
				arr_pos++;
				
				
			}
		}
		
	}
	
	public int getSize(String type) {
		
		for (int i = 0; i < this.types.length; i++) {
			if (type.compareTo(this.types[i].type) == 0)
				return this.types[i].size;
		}
		
		// Error :
		return -1;
	}

	public int getQty(String type) {
		
		for (int i = 0; i < this.types.length; i++) {
			if (type.compareTo(this.types[i].type) == 0)
				return this.types[i].quantity;
		}
		
		// Error :
		return -1;
	}
}

// Declaring this class here, because it's only used in that file
class TypeElt {
	
	public String type;
	public int size;
	public int quantity;
	
	TypeElt( String type, int size, int quantity) {
		this.type = type;
		this.size = size;
		this.quantity = quantity;
	}
}