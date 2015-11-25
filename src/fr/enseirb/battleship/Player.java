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
	
	public void checkWin(String message){
		if(this.getGrid().getSinkedShip() == this.getGrid().getShips().size()){
			System.out.println(message);
		}
	}
}
