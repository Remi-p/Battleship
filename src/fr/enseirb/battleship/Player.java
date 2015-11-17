package fr.enseirb.battleship;

public abstract class Player {
	protected String name;
	protected int score;
	Grid grid;
	
	public Player(String name) {
		this.name = name;
		this.score = 0;
	}
	
}
