package fr.enseirb.battleship.elements;

public class TypeElt {
	
	private String type;
	private int size;
	private int quantity;
	
	public String getType() {
		return type;
	}

	public int getSize() {
		return size;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public boolean isType(String type) {
		if (type.compareTo(this.type) == 0)
			return true;
		else
			return false;
	}

	TypeElt( String type, int size, int quantity) {
		this.type = type;
		this.size = size;
		this.quantity = quantity;
	}
}
