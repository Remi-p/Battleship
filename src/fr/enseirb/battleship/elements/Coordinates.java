package fr.enseirb.battleship.elements;

public class Coordinates implements java.io.Serializable {
	int x;
	int y;
	
	public Coordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	static public Coordinates strToCoord(String txt) {
		 String[] coord_txt = txt.split(":");
		 
		 return new Coordinates(Integer.parseInt(coord_txt[0]),
				 				Integer.parseInt(coord_txt[1]));
	}

	public String toString() {
		return this.getX() + ":" + this.getY();
	}
	
	// GETTERS
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	// Equals method is usefull for checking overlapping
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinates other = (Coordinates) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
