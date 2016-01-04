package fr.enseirb.battleship.tools;

// 'singleton'
// http://blog.paumard.org/2011/04/22/bilan-sur-le-pattern-singleton/
public class Config {
	
	public final static String GRID = "grid.xml";
	public final static String SHIPS = "ships.xml";
	public final static String CONFIGS = "configs/";
	
	public final static String FIRE_SVG = "configs/fire.svg"; 
	public final static String MISSED_SVG = "configs/missed.svg"; 
	
	public final static String DEBUG_SVG = "debug.svg";
	public final static String PLAY_SVG = "game.svg";
	
	public static boolean VERBOSE = false;
	
	// Private constructor : disallow creating instance
	private Config() {}
	
	public static void setVerbose(boolean verb) {
		Config.VERBOSE = verb;
	}
	
}
