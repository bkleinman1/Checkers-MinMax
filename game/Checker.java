package game;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Checker {
	
	enum CheckerColour {
		Black,
		Red
	}
	
	public int x;
	public int y;
	ImageView img;
	CheckerColour colour;
	boolean isKing;
	
	public ArrayList<Position> possibleMoves = new ArrayList<Position>();
	public HashMap<Position, Checker> takenCheckers = new HashMap<Position, Checker>();
	
	public Checker(int x, int y, CheckerColour colour) {
		this.x = x;
		this.y = y;
		this.colour = colour;
		isKing = false;
		if(colour == CheckerColour.Black)
			this.img = new ImageView(new Image("resources/checkerblack.png"));
		else
			this.img = new ImageView(new Image("resources/checkerred.png"));
	}
	
	//gets all move for checker
	public ArrayList<Position> getPossibleMoves() {
		this.possibleMoves.clear();
		this.takenCheckers.clear();
		ArrayList<Position> moves = new ArrayList<Position>();
		ArrayList<Position> jumps = getJumps();
		if(!jumps.isEmpty()) { //first check if can jump
			moves.addAll(jumps);
		} else { // Only check for standard moves if cant take a piece
			if(colour == CheckerColour.Black) {
				moves.addAll(checkMovesUp());
				if(isKing) {
					moves.addAll(checkMovesDown());
				}
			} else {
				moves.addAll(checkMovesDown());
				if(isKing) {
					moves.addAll(checkMovesUp());
				}
			}
		}
		
		this.possibleMoves = moves;
		return moves;
	}
	
	private ArrayList<Position> checkMovesUp() {
		ArrayList<Position> moves = new ArrayList<Position>();
		if(isTileFree(x+1, y-1)) {
			moves.add(new Position(x+1, y-1)); //up right
		}
		if(isTileFree(x-1, y-1)) {
			moves.add(new Position(x-1, y-1)); //up left
		}
		return moves;
	}
	
	private ArrayList<Position> checkMovesDown() {
		ArrayList<Position> moves = new ArrayList<Position>();
		if(isTileFree(x+1, y+1)) {
			moves.add(new Position(x+1, y+1)); //down right
		}
		if(isTileFree(x-1, y+1)) {
			moves.add(new Position(x-1, y+1)); //down left
		}
		return moves;
	}
	
	public ArrayList<Position> getJumps() {
		ArrayList<Position> jumps = new ArrayList<Position>();
		if(colour == CheckerColour.Black) {
			jumps.addAll(getJumpsUp());
			if(isKing) {
				jumps.addAll(getJumpsDown());
			}
		} else {
			jumps.addAll(getJumpsDown());
			if(isKing) {
				jumps.addAll(getJumpsUp());
			}
		}
		return jumps;
	}
	
	private ArrayList<Position> getJumpsUp() {
		ArrayList<Position> jumps = new ArrayList<Position>();

		if(Gui.board.getCheckerAt(x+1, y-1) != null) { //up right
			if(Gui.board.getCheckerAt(x+1, y-1).colour != colour) {
				if(isTileFree(x+2, y-2)) {
					takenCheckers.put(new Position(x+2, y-2), Gui.board.getCheckerAt(x+1, y-1));
					jumps.add(new Position(x+2, y-2));
				}
			}
		}
		
		if(Gui.board.getCheckerAt(x-1, y-1) != null) { //up left
			if(Gui.board.getCheckerAt(x-1, y-1).colour != colour) {
				if(isTileFree(x-2, y-2)) {
					takenCheckers.put(new Position(x-2, y-2), Gui.board.getCheckerAt(x-1, y-1));
					jumps.add(new Position(x-2, y-2));
				}
			}
		}
		return jumps;
	}
	
	private ArrayList<Position> getJumpsDown() {
		ArrayList<Position> jumps = new ArrayList<Position>();

		if(Gui.board.getCheckerAt(x+1, y+1) != null) { //down right
			if(Gui.board.getCheckerAt(x+1, y+1).colour != colour) {
				if(isTileFree(x+2, y+2)) {
					takenCheckers.put(new Position(x+2, y+2), Gui.board.getCheckerAt(x+1, y+1));
					jumps.add(new Position(x+2, y+2));
				}
			}
		}
		
		if(Gui.board.getCheckerAt(x-1, y+1) != null) { //down left
			if(Gui.board.getCheckerAt(x-1, y+1).colour != colour) {
				if(isTileFree(x-2, y+2)) {
					takenCheckers.put(new Position(x-2, y+2), Gui.board.getCheckerAt(x-1, y+1));
					jumps.add(new Position(x-2, y+2));
				}
			}
		}
		return jumps;
	}
	
	private boolean isTileFree(int x, int y) {
		if(Board.isPositionOnBoard(x, y)) {
			if(Gui.board.getCheckerAt(x, y) == null) {
				return true;
			}
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void setPosition(int x, int y) {
		setPosition(new Position(x, y));
	}
	
	//sets position and checks if king
	public void setPosition(Position p) {
		this.x = p.getX();
		this.y = p.getY();
		if(colour == CheckerColour.Black && y == 0) {
			makeKing();
		}
			
		if(colour == CheckerColour.Red && y == 7) {
			makeKing();
		}
	}
	
	//updates image
	public void makeKing() {
		isKing = true;
		if(colour == CheckerColour.Red)
			img = new ImageView(new Image("resources/checkerredking.png"));
		else
			img = new ImageView(new Image("resources/checkerblackking.png"));
	}

	public ImageView getImg() {
		return img;
	}

	public void setImg(ImageView img) {
		this.img = img;
	}

	public boolean isAPossibleMove(int x, int y) {
		for (Position p : possibleMoves) {
			if (x == p.getX() && y == p.getY()) {
				return true;
			}
		}
		return false;
	}

}
