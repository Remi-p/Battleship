package tools;

import java.util.HashMap;
import java.util.List;
import java.util.Arrays;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		
		// Initialising XPath for dimensions
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

	public HashMap<String, List<Integer> > getShips() {
		
		Node ships_node = this.document.getElementsByTagName("ships").item(0);
		NodeList ships = ships_node.getChildNodes();
		
		// set of an hashMap to associate a name of ship to a size
		// hashMap for temporarily store size of boats
		// a simple hashMap declaration with default size and load factor
		HashMap<String, List<Integer> > ships_size = new HashMap<String, List<Integer> >();
 
		for (int i = 0; i < ships.getLength(); i++) {

			Node ship = ships.item(i);
			
			// We check if we really are in a node
			if (ship.getNodeType() == Node.ELEMENT_NODE) {

				// We use this conversion, this way, we can use getAttribute() and getElementsByTagName()
				Element elt = (Element) ship;
				
				// Put elements to the ships_size hashmap
				ships_size.put(elt.getNodeName(), Arrays.asList( new Integer(Integer.parseInt(elt.getAttribute("size"))), 
																 new Integer(Integer.parseInt(elt.getTextContent() ))));		
				
				// TODO : Save the number of time we can use one ship
				
				/* System.out.println("There is " + elt.getFirstChild().getNodeValue() + " " + 
									elt.getAttribute("size") + "-length ship of type " +
									elt.getNodeName());
				*/
				
			}
		}
		
		return ships_size;
	}

}
