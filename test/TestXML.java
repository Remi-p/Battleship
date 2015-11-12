import exceptions.InvalidGridException;
import exceptions.ShipOutOfBoundsException;
import exceptions.ShipOverlapException;
import fr.enseirb.battleship.Grid;
import fr.enseirb.battleship.Ship;

public class TestXML {

	public static void main(String[] args) throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException {
		// TODO Auto-generated method stub
		
		Grid grid = new Grid();
		/*
		try {
		Grid grid = new Grid();
		}
		catch(InvalidGridException e) {
			System.out.println("InvalidGridException : il y a deux causes à cette exception :\n " +
								"- votre grille de jeu n'a pas une taille minimale de 10 x 10. \n" +
								" - le nombre total des cases occupées doit être inférieur ou égal à 20% du nombre total de cases.");
		}
		catch(ShipOutOfBoundsException e) {
			System.out.println("ShipOutOfBoundsException : il ne faut pas qu'un bateau ne dépasse de la grille.\n ");
		}
		catch(ShipOverlapException e) {
			System.out.println("ShipOverlapException : des bateaux se chevauchent.\n ");
		}
		*/
		// throws InvalidGridException, ShipOutOfBoundsException, ShipOverlapException
	}

}
