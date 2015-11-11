package fr.enseirb.battleship;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;


public class Grid {
	private int height;
	private int width;
	private Ship[] ship;

	public Grid() {
		this("configs/");
	}

	public Grid(String configs_path){
		super();

		//extraction XML
		getXml(configs_path);

		//this.height = height;
		//this.width = width;
		//this.ship = build_ship();
	}

	private void getXml(String configs_path) {

		// http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
		// http://stackoverflow.com/questions/12891992/get-node-by-name-from-nodelist

		// We're using both xpath and nodelist for that
		try {
			// ----------------- GRID XML -------------------
			// Creating the Document object from the XML file
			File fXmlFile_grid = new File(configs_path + "grid.xml");
			DocumentBuilderFactory dbFactory_grid = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder_grid = dbFactory_grid.newDocumentBuilder();
			Document doc_grid = dBuilder_grid.parse(fXmlFile_grid);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc_grid.getDocumentElement().normalize();

			// Dimensions
			XPath xpath = XPathFactory.newInstance().newXPath();
			// XPath returns an object Double, so we need to use the method intValue for our dimensions

			XPathExpression xpr = xpath.compile("//grid/dimensions/vertical");
			this.height = ((Double) xpr.evaluate(doc_grid, XPathConstants.NUMBER)).intValue();

			xpr = xpath.compile("//grid/dimensions/horizontal");
			this.width = ((Double) xpr.evaluate(doc_grid, XPathConstants.NUMBER)).intValue();

			System.out.println("Dimensions : " + this.height + "x" + this.width);
			

			// Ships

			Node ships_node_grid = doc_grid.getElementsByTagName("ships").item(0);
			NodeList ships_grid = ships_node_grid.getChildNodes();

			System.out.println("----------------------------");
			
			// set of an hashMap to associate a name of ship to a size
			// hashMap for temporarily store size of boats
			// a simple hashMap declaration with default size and load factor
			HashMap<String, Integer> ships_size = new HashMap<String, Integer>();
	 
			for (int i = 0; i < ships_grid.getLength(); i++) {

				Node ship = ships_grid.item(i);
				
				// We check if we really are in a node
				if (ship.getNodeType() == Node.ELEMENT_NODE) {

					// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
					Element elt = (Element) ship;
					
					// Put elements to the ships_size
					ships_size.put(elt.getNodeName(), new Integer(Integer.parseInt(elt.getAttribute("size"))));
					
					System.out.println("There is " + elt.getFirstChild().getNodeValue() + " " + 
										elt.getAttribute("size") + "-length ship of type " +
										elt.getNodeName());
					
				}
			}
			
			// ----------------- SHIPS XML -------------------
			// Creating the Document object from the XML file
			File fXmlFile_ships = new File(configs_path + "ships.xml");
			DocumentBuilderFactory dbFactory_ships = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder_ships = dbFactory_ships.newDocumentBuilder();
			Document doc_ships = dBuilder_ships.parse(fXmlFile_ships);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc_ships.getDocumentElement().normalize();
			
			// Ships
			
			Node ships_node = doc_ships.getElementsByTagName("ships").item(0);
			NodeList ships = ships_node.getChildNodes();
			
			// Initialization of ship array
			this.ship = new Ship[ships.getLength()];
			
			System.out.println("----------------------------");	
			
			for (int i = 0; i < ships.getLength(); i++) {
	
				Node ship = ships.item(i);
				
				// We check if we really are in a node
				if (ship.getNodeType() == Node.ELEMENT_NODE) {
	
					// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
					Element elt = (Element) ship;
					Element position = (Element) elt.getElementsByTagName("position").item(0);
				
					// Add new Ship object
					this.ship[i] = new Ship(elt.getAttribute("id"),
										elt.getAttribute("type"),
										Integer.parseInt(position.getAttribute("x")),
										Integer.parseInt(position.getAttribute("y")),
										position.getAttribute("orientation"),
										ships_size.get(elt.getAttribute("type")));
									
					System.out.println("Player has one " + 
										elt.getAttribute("type") +
										" named " +
										elt.getAttribute("id") +
										"at position (" +
										position.getAttribute("x") + "," 
										+ position.getAttribute("y") + ") " +
										position.getAttribute("orientation") + " oriented and " +
										ships_size.get(elt.getAttribute("type")) + "-length");
										
					
				}
			}				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// GETTERS
	// ATTENTION : methodes public a changer en fonction après
	
	public int getHeight(int height) {
		return this.height;
	}
	
	public int getWidth(int width) {
		return this.width;
	}
	
}
