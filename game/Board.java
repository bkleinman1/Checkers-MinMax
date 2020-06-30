package game;

import java.util.ArrayList;

import game.Checker.CheckerColour;

public class Board {

	ArrayList<Checker> checkersRed = new ArrayList<Checker>();
	ArrayList<Checker> checkersBlack = new ArrayList<Checker>();
	
	public Checker activeChecker;
	
	public int turn; //0 = Player, 1 = AI
	
	Player player;
	Ai ai;

	public Board() {
		setupCheckers();
		turn = 0;
		player = new Player(Checker.CheckerColour.Black);
		ai = new Ai(Checker.CheckerColour.Red);
	}

	public void setupCheckers() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((j + i) % 2 != 0) {
					if (j < 3) { // Red Checkers
						Checker c = new Checker(i, j, CheckerColour.Red);
						checkersRed.add(c);
					} else if (j > 4) { // Black Checkers
						Checker c = new Checker(i, j, CheckerColour.Black);
						checkersBlack.add(c);
					}
				}
			}
		}
	}
	
	public void switchTurn() {
		if(turn == 0) {
			turn = 1;
			ai.makeMove();
		} else {
			turn = 0;
			player.checkIfHasToTake();
		}
	}

	// returns null if no checker at position
	public Checker getCheckerAt(int x, int y) {
		ArrayList<Checker> temp = new ArrayList<Checker>(checkersBlack);
		temp.addAll(checkersRed);
		for (Checker c : temp) {
			if (c.getX() == x && c.getY() == y) {
				return c;
			}
		}
		return null;
	}
	
	//Bounds check
	public static boolean isPositionOnBoard(int x, int y) {
		if(x >= 0 && x <= 7)
			if(y >= 0 && y <= 7)
				return true;
		return false;
	}

	public void killChecker(Checker checker) {
		checkersRed.remove(checker);
		checkersBlack.remove(checker);
	}
	
	public void unKillChecker(Checker checker) {
		if(checker.colour == CheckerColour.Black)
			checkersBlack.add(checker);
		if(checker.colour == CheckerColour.Red)
			checkersRed.add(checker);
	}
	
	public boolean redHasWon() {
		if(this.checkersBlack.isEmpty())
			return true;
		return false;
	}
	
	public boolean blackHasWon() {
		if(this.checkersRed.isEmpty())
			return true;
		return false;
	}

}
