package fr.enseirb.battleship;


import java.io.*;

import tools.Config;
import tools.SvgWriter;

// Main classes
public class App {

	public static void main(String[] args) throws Exception {
		
		Config.setVerbose(true);
		
		if (args.length != 3)
			throw new Exception("Incorrect number of arguments.");
		
		// Creating players objects
		Player human = new Human("Anonymous", args[1], args[2]);
		Player ia = new IA();
		
		// We get the dimensions of grids
		int width = human.getGrid().getWidth();
		int height = human.getGrid().getHeight();
		
		// Object for writing svg debug file
		SvgWriter writer = new SvgWriter(width, height);

		if ("debug".compareTo(args[0]) == 0) {
			writer.debugGrids(new FileWriter("debug.svg"), human, ia);
		}
		
		
		String input;
		
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
