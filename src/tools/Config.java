package tools;

// 'singleton'
// http://blog.paumard.org/2011/04/22/bilan-sur-le-pattern-singleton/
public class Config {
	
	//private static Config instance = new Config() ;
	
	public final static String GRID = "grid.xml";
	public final static String SHIPS = "ships.xml";
	public final static String CONFIGS = "configs/";
	
	public final static int CELL_DIM = 40;
	public final static int STROKE_WIDTH = 2;
	
	public static boolean VERBOSE = false;
	
	// Private constructor : disallow creating instance
	private Config() {}
	
	public static void setVerbose(boolean verb) {
		Config.VERBOSE = verb;
	}
	
}
