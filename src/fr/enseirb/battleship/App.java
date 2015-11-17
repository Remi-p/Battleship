package fr.enseirb.battleship;


import java.io.*;
import tools.Config;
import tools.SvgWriter;

// Main classes
public class App {

	public static void main(String[] args) throws Exception {
		if (args.length != 3)
			throw new Exception("Nombre d'arguments invalides");
		
		//Player human = new Human();
		//Player ia = new IA();
		String input;
		
		Config.setVerbose(true);
		
		Grid grid = new Grid(Config.CONFIGS, args[1], args[2]);

		SvgWriter writer = new SvgWriter(grid.getWidth(), grid.getHeight());
		
		if ("debug".compareTo(args[0]) == 0) {
			writer.debugGrid(new FileWriter("debug.svg"), grid);
		}
		
		do{
			input = "";
			char C='\0'; 
			try {
				while ((C=(char) System.in.read()) !='\n'){
					if (C != '\r')  input = input+C;
				}
			}     
			catch (IOException e){
			          System.out.println("Error inputing command");
	        } 
			
			
			
			
		}while(true);
	}
	

}
