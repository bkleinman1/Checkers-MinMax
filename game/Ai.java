package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import game.Checker.CheckerColour;

public class Ai extends Player {

	ArrayList<Move> successorEvaluations;

	public Ai(CheckerColour colour) {
		super(colour);
	}

	public void makeMove() {
		if (!Gui.displayWinAlert()) {
			System.out.println("Thinking...");
			startEvaluation();
			Move move = getBestMove();
			makeMove(move);
			Gui.board.switchTurn();
		}
	}

	private Move getBestMove() {
		int max = -1000;
		int best = -1;
		for (int i = 0; i < successorEvaluations.size(); ++i) {
			if (max < successorEvaluations.get(i).score) {
				max = successorEvaluations.get(i).score;
				best = i;
			}

		}
		return successorEvaluations.get(best);
	}

	public void startEvaluation() {
		successorEvaluations = new ArrayList<Move>();
		minmax(0, 1, -1000, 1000);
	}

	public int minmax(int depth, int player, int alpha, int beta) {
		int bestScore = 0;
		if (player == 0)
			bestScore = -400;
		else if (player == 1)
			bestScore = 400;

		CheckerColour col = player == 0 ? Gui.board.player.colour : Gui.board.ai.colour;
		ArrayList<Move> positions = getMoves(col);

		if (Gui.board.redHasWon())
			return 400;
		if (Gui.board.blackHasWon())
			return -400;
		if (positions.isEmpty())
			return 0;

		if (depth == Gui.difficulty) {
			int blackScore = 0;
			int redScore = 0;
			for (Checker c : Gui.board.checkersBlack) {
				if (c.isKing)
					blackScore += 30;
				blackScore += 10;
			}
			for (Checker c : Gui.board.checkersRed) {
				if (c.isKing)
					redScore += 30;
				redScore += 10;
			}

			return (blackScore - redScore);
		}

		for (Move m : positions) {
			if (player == 1) {
				makeMove(m);
				int currentScore = minmax(depth + 1, 0, alpha, beta);
				if (currentScore > alpha)
					alpha = currentScore;
				if (currentScore > bestScore)
					bestScore = currentScore;
				if (depth == 0) {
					m.score = currentScore;
					successorEvaluations.add(m);
				}
			} else if (player == 0) {
				makeMove(m);
				int currentScore = minmax(depth + 1, 1, alpha, beta);
				if (currentScore < bestScore)
					bestScore = currentScore;
				if (currentScore < beta)
					beta = currentScore;
			}
			Checker c = Gui.board.getCheckerAt(m.newPosition.getX(), m.newPosition.getY());
			c.setPosition(m.originalPosition);
			if (m.takenChecker != null)
				Gui.board.unKillChecker(m.takenChecker);
			c.isKing = m.wasKing;
			c.img = m.img;

			if (alpha >= beta) {
				break;
			}

		}
		return bestScore;
	}

	public void makeMove(Move m) {
		Checker c = Gui.board.getCheckerAt(m.originalPosition.getX(), m.originalPosition.getY());
		c.setPosition(m.newPosition);
		if (m.takenChecker != null) {
			Gui.board.killChecker(m.takenChecker);
			if (m.takenChecker.isKing) {
				c.makeKing();
			}
		}
	}

	public ArrayList<Move> getMoves(CheckerColour colour) {
		ArrayList<Move> moves = new ArrayList<Move>();
		Player pl;
		if (colour == this.colour) {
			pl = this;
		} else {
			pl = Gui.board.player;
		}
		pl.checkIfHasToTake();
		if (!pl.movableCheckers.isEmpty()) {
			for (Checker c : pl.movableCheckers) {
				for (HashMap.Entry<Position, Checker> entry : c.takenCheckers.entrySet()) {
					Move m = new Move();
					m.checker = c;
					m.originalPosition = new Position(c.getX(), c.getY());
					m.newPosition = entry.getKey();
					m.takenChecker = entry.getValue();
					m.wasKing = c.isKing;
					m.img = c.img;
					moves.add(m);
				}
			}
		} else {
			ArrayList<Checker> itterator;
			if (colour == CheckerColour.Black)
				itterator = Gui.board.checkersBlack;
			else
				itterator = Gui.board.checkersRed;
			for (Checker c : itterator) {
				for (Position p : c.possibleMoves) {
					Move m = new Move();
					m.checker = c;
					m.originalPosition = new Position(c.getX(), c.getY());
					m.newPosition = p;
					m.wasKing = c.isKing;
					m.img = c.img;
					moves.add(m);
				}
			}
		}
		return moves;
	}

}
