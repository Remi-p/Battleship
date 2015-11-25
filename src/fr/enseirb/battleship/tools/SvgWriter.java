package fr.enseirb.battleship.tools;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import fr.enseirb.battleship.Grid;
import fr.enseirb.battleship.Player;
import fr.enseirb.battleship.elements.Coordinates;
import fr.enseirb.battleship.elements.Ship;

// http://www.labri.fr/perso/falleri/dist/ens/pg220/tps/tp2/tp2_src.zip

public class SvgWriter {

	private int width;
	private int height;

	private int cell;
	private int stroke;
	private int font_size;
	
	private int img_height;
	private int img_width;
	
	private int space;
	private int header;
	
	private final static int CELL_DIM = 40;
	private final static int STROKE_WIDTH = 2;
	private final static int SPACE = 20;
	private final static int HEADER = 50;
	
	private static final String line = "<line x1='%d' y1='%d' x2='%d' y2='%d' style='stroke:rgb(0,0,0);stroke-width:%d' />\n";
	private static final String text = "<text x='%d' y='%d' transform='translate(0, %d) %s' alignment-baseline='central' fill='%s' text-anchor='middle' font-size='%d'>%s</text>\n";
	private static final String boat = "<rect x='%d' y='%d' width='%d' height='%d' style='fill:rgb(0,0,0);fill-opacity:0.5;' />\n";

	private static final String fire = "<symbol id='fire'><g transform='scale(%d, %d)'>" + readFile(Config.FIRE_SVG) + "</g></symbol>";
	
	private String writer_debug = Config.DEBUG_SVG;
	private String writer_play = Config.PLAY_SVG;
	
	// Player1 is the main
	private Player player1;
	private Player player2;
	
	// http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
	private static String readFile(String path) {
		byte[] encoded;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public SvgWriter(int width, int height, Player player1, Player player2) {
		
		// Dimensions
		this.width = width;
		this.height = height;
		this.cell = SvgWriter.CELL_DIM;
		this.stroke = SvgWriter.STROKE_WIDTH;
		this.font_size = this.cell / 2;
		
		this.space = SvgWriter.SPACE;
		this.header = SvgWriter.HEADER;
		
		this.img_height = (this.width+1) * cell;
		this.img_width = (this.height+1) * cell;
		
		// Players
		this.player1 = player1;
		this.player2 = player2;
	};
	
	private void text(Writer w, int x, int y, int font_size, String text) throws IOException {
		this.text(w, x, y, font_size, text, "", "");
	}
	
	private void text(Writer w, int x, int y, int font_size, String text, int rotation) throws IOException {
		// Rotation is made based on the origin. We need to changed pivot
		this.text(w, x, y, font_size, text, "", "rotate(" + rotation + ", " + x + ", " + y + ")");
	}
	
	private void text(Writer w, int x, int y, int font_size, String text, String color, String transform) throws IOException {
		w.append(String.format(SvgWriter.text, x, y, font_size/2, transform, color, font_size, text));
	}

	private void header(Writer w) throws IOException {
		
		w.append("<?xml version='1.0' encoding='utf-8'?>\n");
		
		w.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d'>\n",
				img_width * 2 + space + 100, img_height + header + 100));
		// Empirical offset for letting boat's name outside the grids

		// Eventually, styling for mouse effects
		//w.append("<style>\nrect:hover\n{\nopacity: 0.5;\n}\n</style>");
		
		// Definitions
		w.append("<defs>");
		w.append(String.format(fire, cell, cell));
		w.append("</defs>");
		
		// First player, first grid
		this.text( w, img_width/2, header/2, header/2, player1.getName());
		this.writeOneGrid(w, player1.getGrid(), 0, header);

		// Second player, second grid
		this.text( w, img_width/2 + img_width + space, header/2, header/2, player2.getName());
		this.writeOneGrid(w, player2.getGrid(), img_width + space, header);
	}
	
	private void createGrid(Writer w) throws IOException {

		// Creating a group for the background
		w.append("<g>\n");
		
		// Borders
		w.append(String.format("<rect x='%d' y='%d' width='%d' height='%d' style='fill:rgb(255,255,255);stroke-width:%d;stroke:rgb(0,0,0)' />\n", 0, 0, img_width, img_height, stroke));
		
		// Separating index 
		w.append(String.format(SvgWriter.line, cell, 0, cell, img_height, stroke*2));
		w.append(String.format(SvgWriter.line, 0, cell, img_width, cell, stroke*2));
		
		for (int i = 2; i <= width; i++) {
			// Vertical
			w.append(String.format(SvgWriter.line, i*cell, 0, i*cell, img_height, stroke));
			// Horizontal
			w.append(String.format(SvgWriter.line, 0, i*cell, img_width, i*cell, stroke));
		}
		
		// Numbering
		for (int i = 0; i < width; i++) {
			// Vertical
			this.text( w, (i+1)*cell + cell/2, cell/2, font_size, Integer.toString(i));
			
			// Horizontal
			this.text( w, cell/2, (i+2)*cell - cell/2, font_size, Integer.toString(i));
		}
		
		w.append("</g>\n");
	}

	public void debugGrids() {

		try {
			FileWriter w = new FileWriter(writer_debug);
		
			header(w);
			
			w.append("</svg>");
			w.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void writeOneGrid(Writer w, Grid grid, int x, int y) throws IOException {

		// Creating a group for the grid
		w.append(String.format("<g transform='translate(%d, %d)'>\n", x, y));
		
		this.createGrid(w);
		
		// Boats
		for (Ship ship : grid.getShips()) {
			
			if (ship.isHorizontal()) {
				w.append(String.format(SvgWriter.boat,
									(ship.getX()+1)*cell, // Adding one, as there is numbering
									(ship.getY()+1)*cell,
									ship.getSize()*cell,
									cell));
				
				this.text(w, (ship.getX()+1+ship.getSize()/2)*cell, 
						(ship.getY()+1)*cell + cell/2, 
						font_size, ship.getName());
			}
			else {
				w.append(String.format(SvgWriter.boat,
						(ship.getX()+1)*cell,
						(ship.getY()+1)*cell,
						cell,
						ship.getSize()*cell));
			
				this.text(w, (ship.getX()+1)*cell + cell/2,
						(ship.getY()+1+ship.getSize()/2)*cell, 
						font_size, ship.getName(), -90);
			}
		}
		
		// Missed
		for (Coordinates coord : grid.getFires()) {
			// TODO : Erreur ici sur un dépassement ....
			//w.append("<use xlink:href='#fire' x='%d' y='%d'/>", coord.getX(), coord.getY());			
		}
		
		// Closing group
		w.append("</g>");
		
	}

	public void debugGrid(Writer w, Grid grid) {
		
		try {
			w.append("<?xml version='1.0' encoding='utf-8'?>\n");
			
			w.append(String.format("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' width='%d' height='%d'>\n",
					img_width, img_height));
			
			this.writeOneGrid(w, grid, 0, 0);
			
			w.append("</svg>");
			w.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
