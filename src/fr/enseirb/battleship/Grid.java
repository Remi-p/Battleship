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

public class Grid {
	private int height;
	private int width;
	private Ship[] ship;
	private Fire[] fire;

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

			// Creating the Document object from the XML file
			File fXmlFile = new File(configs_path + "grid.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			//optional, but recommended
			//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			// Dimensions
			XPath xpath = XPathFactory.newInstance().newXPath();
			// XPath returns an object Double, so we need to use the method intValue for our dimensions

			XPathExpression xpr = xpath.compile("//grid/dimensions/vertical");
			this.height = ((Double) xpr.evaluate(doc, XPathConstants.NUMBER)).intValue();

			xpr = xpath.compile("//grid/dimensions/horizontal");
			this.width = ((Double) xpr.evaluate(doc, XPathConstants.NUMBER)).intValue();

			System.out.println("Dimensions : " + this.height + "x" + this.width);

			// Ships

			Node ships_node = doc.getElementsByTagName("ships").item(0);
			NodeList ships = ships_node.getChildNodes();

			System.out.println("----------------------------");

			for (int i = 0; i < ships.getLength(); i++) {

				Node ship = ships.item(i);
				
				// We check if we really are in a node
				if (ship.getNodeType() == Node.ELEMENT_NODE) {

					// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
					Element elt = (Element) ship;
					
					System.out.println("There is " + elt.getFirstChild().getNodeValue() + " " + 
										elt.getAttribute("size") + "-length ship of type " +
										elt.getNodeName());
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// GETTERS
	// ATTENTION : methodes public a changer en fonction après
	
	public int getHeight(int height) {
		return height;
	}
	
	public int getWidth(int width) {
		return width;
	}
	
}
