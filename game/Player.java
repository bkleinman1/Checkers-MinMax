package game;

import java.util.ArrayList;

import game.Checker.CheckerColour;

public class Player {

	public ArrayList<Checker> movableCheckers = new ArrayList<Checker>();

	public Checker.CheckerColour colour;

	public Player(Checker.CheckerColour colour) {
		this.colour = colour;
	}

	public void checkIfHasToTake() {
		movableCheckers.clear();
		ArrayList<Checker> itterator;
		if(colour == CheckerColour.Black)
			itterator = Gui.board.checkersBlack;
		else
			itterator = Gui.board.checkersRed;
		
		boolean hasToTake = false;
		for (Checker c : itterator) {
			c.getPossibleMoves();
			if (!c.takenCheckers.isEmpty()) {
				movableCheckers.add(c);
				hasToTake = true;
			}
		}
		if (hasToTake) {
			for (Checker c : itterator) {
				if (!movableCheckers.contains(c)) {
					c.possibleMoves.clear();
					c.takenCheckers.clear();
				}
			}
		}
	}
}
