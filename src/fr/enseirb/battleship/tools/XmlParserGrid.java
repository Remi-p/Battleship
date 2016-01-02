package fr.enseirb.battleship.tools;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.enseirb.battleship.elements.Strategy;
import fr.enseirb.battleship.elements.Type;

// We're using both xpath and nodelist for that
public class XmlParserGrid extends XmlParser {

	private final static XPath xpath = XPathFactory.newInstance().newXPath();
	private XPathExpression xpr_vert;
	private XPathExpression xpr_hor;

	public XmlParserGrid(String configs_path) {
		this(configs_path, "grid.xml");
	}
	
	public XmlParserGrid(String configs_path, String gridname) {
		super(configs_path, gridname);
		
		// Initializing XPath for dimensions
		try {
			xpr_vert = xpath.compile("//grid/dimensions/vertical");
			xpr_hor = xpath.compile("//grid/dimensions/horizontal");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getDimHorizontal() {
		
		try {
			// XPath returns an object Double, so we need to use the method intValue() for our dimensions
			return ((Double) xpr_hor.evaluate(this.document, XPathConstants.NUMBER)).intValue();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return -1;
		}
		
	}
	
	public int getDimVertical() {
		
		try {
			// XPath returns an object Double, so we need to use the method intValue() for our dimensions
			return ((Double) xpr_vert.evaluate(this.document, XPathConstants.NUMBER)).intValue();
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return -1;
		}
		
	}
	
	public int[] getDimensions() {

		int height = -1, width = -1;
		
		try {
			// XPath returns an object Double, so we need to use the method intValue() for our dimensions
			height = ((Double) xpr_vert.evaluate(this.document, XPathConstants.NUMBER)).intValue();
			width = ((Double) xpr_hor.evaluate(this.document, XPathConstants.NUMBER)).intValue();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new int[] {height, width};
	}
	
	
	public Type getShips() {

		// We get the boat nodes
		Node ships_node = this.document.getElementsByTagName("ships").item(0);
		NodeList ships = ships_node.getChildNodes();
		
		return new Type(ships);
	}
	
	public Strategy getPlacement() {

		// We get the boat nodes
		Node strategy = this.document.getElementsByTagName("strategies").item(0);
		
		// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
		Element elt = (Element) strategy;
		
		String placement = elt.getAttribute("placement");
		
		switch (placement){
			case "random":
				return Strategy.RANDOM;
			case "pack":
				return Strategy.PACK;
			case "far":
				return Strategy.FAR;
			default :
				return Strategy.RANDOM;
		}
		//String firing = elt.getAttribute("firing");
	}
	
	public Strategy getFiring() {

		// We get the boat nodes
		Node strategy = this.document.getElementsByTagName("strategies").item(0);
		
		// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
		Element elt = (Element) strategy;
		
		String placement = elt.getAttribute("firing");
		
		switch (placement){
			case "random":
				return Strategy.RANDOM;
			case "pack":
				return Strategy.PACK;
			case "far":
				return Strategy.FAR;
			case "perso":
				return Strategy.PERSO;
			default :
				return Strategy.RANDOM;
		}
	}
}
