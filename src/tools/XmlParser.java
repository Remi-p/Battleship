package tools;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class XmlParser {

	// http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
	// http://stackoverflow.com/questions/12891992/get-node-by-name-from-nodelist
	// https://openclassrooms.com/courses/structurez-vos-donnees-avec-xml/dom-exemple-d-utilisation-en-java
	
	private static final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	protected Document document;
	
	public XmlParser(String configs_path, String filename) {

		File file = new File(configs_path + filename);
		
		try {
			// Creating the Document object from the XML file
			document = factory.newDocumentBuilder().parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Recommended
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		document.getDocumentElement().normalize();
		
	}
	
}
