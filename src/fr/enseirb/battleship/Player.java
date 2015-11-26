package fr.enseirb.battleship;

public abstract class Player {
	protected String name;

	protected int score;
	Grid grid;
	
	public Player(String name) {
		this.name = name;
		this.score = 0;
	}

	public Grid getGrid() {
		return grid;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean checkWin(String name){
		if(this.getGrid().checkAllShipSunk()){
			if(name.compareTo("human") == 0)
				System.out.println("You win !");
			else {
				System.out.println(name + " win !");
				System.out.println("You loose !");
			}
			return true;
		}
		return false;
	}
}
