package game;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import game.Checker.CheckerColour;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Gui extends Application {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 640;

	GridPane grid;

	public static Board board;

	Pane startButton;
	Pane settingsButton;
	Pane helpButton;
	Button backButton = new Button("Back");
	ComboBox<String> difficultyDropdown = new ComboBox<String>();
	ComboBox<String> colourDropdown = new ComboBox<String>();
	CheckBox checkbox = new CheckBox();

	public static int difficulty = 2;
	public static boolean showPossibleMoves = true;

	public void start(Stage stage) {
		board = new Board();

		Scene startScene = setupStartScreen();
		stage.setScene(startScene);

		// EVENT HANDLERS FOR BUTTONS ON MENU

		startButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			Scene gameScene = setupGame();
			stage.setScene(gameScene);
		});

		settingsButton.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
			Scene settingsScene = setupSettings();
			stage.setScene(settingsScene);
		});

		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (difficultyDropdown.getValue() == "Easy")
				difficulty = 2;
			if (difficultyDropdown.getValue() == "Medium")
				difficulty = 5;
			if (difficultyDropdown.getValue() == "Hard")
				difficulty = 7;
			showPossibleMoves = checkbox.isSelected();

			if (colourDropdown.getValue() == "Red") {
				board.player.colour = CheckerColour.Red;
				board.ai.colour = CheckerColour.Black;
			} else {
				board.player.colour = CheckerColour.Black;
				board.ai.colour = CheckerColour.Red;
			}

			stage.setScene(startScene);
		});

		helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				try {
					Desktop.getDesktop().browse(new URI("http://www.indepthinfo.com/checkers/play.shtml"));
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});

		stage.setResizable(false);
		stage.show();
	}

	// Menu screen
	public Scene setupStartScreen() {
		Pane background = new Pane();
		background.getChildren().add(new ImageView(new Image("resources/blurredbackground.png")));
		Pane inner = new Pane();
		inner.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
		inner.setMinHeight(480);
		inner.setMinWidth(400);
		inner.setLayoutX(120);
		inner.setLayoutY(80);

		VBox vbox = new VBox();

		Pane p = new Pane();
		ImageView titleImg = new ImageView(new Image("resources/title.png"));
		titleImg.setTranslateX(70);
		p.getChildren().add(titleImg);
		vbox.getChildren().add(titleImg);

		startButton = new Pane();
		startButton.setMaxWidth(300);
		startButton.setMinHeight(100);
		startButton.setTranslateX(75);
		startButton.setTranslateY(50);
		startButton.setStyle("-fx-background-color: rgba(3,223,252, 0.7);");
		Text startText = new Text("Start");
		startText.setTranslateY(52);
		startText.setTranslateX(110);
		startText.setScaleX(5);
		startText.setScaleY(5);
		startButton.getChildren().add(startText);
		vbox.getChildren().add(startButton);

		settingsButton = new Pane();
		settingsButton.setMaxWidth(300);
		settingsButton.setMinHeight(100);
		settingsButton.setTranslateX(75);
		settingsButton.setTranslateY(75);
		settingsButton.setStyle("-fx-background-color: rgba(3,223,252, 0.7);");
		Text settingsText = new Text("Settings");
		settingsText.setTranslateY(52);
		settingsText.setTranslateX(105);
		settingsText.setScaleX(5);
		settingsText.setScaleY(5);
		settingsButton.getChildren().add(settingsText);

		vbox.getChildren().add(settingsButton);

		helpButton = new Pane();
		helpButton.setMaxWidth(300);
		helpButton.setMinHeight(100);
		helpButton.setTranslateX(75);
		helpButton.setTranslateY(100);
		helpButton.setStyle("-fx-background-color: rgba(3,223,252, 0.7);");
		Text helpText = new Text("Help");
		helpText.setTranslateY(52);
		helpText.setTranslateX(110);
		helpText.setScaleX(5);
		helpText.setScaleY(5);
		helpButton.getChildren().add(helpText);
		vbox.getChildren().add(helpButton);

		inner.getChildren().add(vbox);
		background.getChildren().add(inner);

		Scene scene = new Scene(background, WIDTH, HEIGHT);
		return scene;
	}

	// Settings screen
	public Scene setupSettings() {
		Pane background = new Pane();
		background.getChildren().add(new ImageView(new Image("resources/blurredbackground.png")));
		Pane inner = new Pane();
		inner.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");
		inner.setMinHeight(480);
		inner.setMinWidth(400);
		inner.setLayoutX(120);
		inner.setLayoutY(80);

		VBox vbox = new VBox();

		Text title = new Text("Settings");
		title.setScaleX(5);
		title.setScaleY(5);
		title.setTranslateX(110);
		title.setTranslateY(30);
		vbox.getChildren().add(title);

		HBox difficultyLine = new HBox();
		Text difficultyText = new Text("Difficulty: ");
		difficultyText.setScaleX(2);
		difficultyText.setScaleY(2);
		difficultyText.setTranslateX(80);
		difficultyText.setTranslateY(100);
		difficultyDropdown.getItems().add("Easy");
		difficultyDropdown.getItems().add("Medium");
		difficultyDropdown.getItems().add("Hard");
		difficultyDropdown.getSelectionModel().selectFirst();
		difficultyDropdown.setTranslateX(110);
		difficultyDropdown.setTranslateY(95);
		difficultyLine.getChildren().addAll(difficultyText, difficultyDropdown);
		vbox.getChildren().add(difficultyLine);

		HBox colourLine = new HBox();
		Text colourText = new Text("Colour: ");
		colourText.setScaleX(2);
		colourText.setScaleY(2);
		colourText.setTranslateX(72);
		colourText.setTranslateY(140);
		colourDropdown.getItems().add("Black");
		colourDropdown.getItems().add("Red");
		colourDropdown.getSelectionModel().selectFirst();
		colourDropdown.setTranslateX(110);
		colourDropdown.setTranslateY(138);
		colourLine.getChildren().addAll(colourText, colourDropdown);
		vbox.getChildren().add(colourLine);

		HBox possMovesLine = new HBox();
		Text possMovesText = new Text("Show Possible Moves: ");
		checkbox.setTranslateX(70);
		checkbox.setSelected(true);
		possMovesLine.getChildren().add(possMovesText);
		possMovesLine.getChildren().add(checkbox);
		possMovesText.setScaleX(2);
		possMovesText.setScaleY(2);
		possMovesLine.setTranslateX(118);
		possMovesLine.setTranslateY(190);
		vbox.getChildren().add(possMovesLine);

		backButton.setTranslateX(170);
		backButton.setTranslateY(250);
		vbox.getChildren().add(backButton);

		inner.getChildren().add(vbox);
		background.getChildren().add(inner);
		return new Scene(background, WIDTH, HEIGHT);
	}

	// Game setup screen
	public Scene setupGame() {
		board.player.checkIfHasToTake();
		Pane root = new Pane();
		root.getChildren().add(new ImageView(new Image("resources/background.png")));
		grid = new GridPane();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Pane tile = new Pane();
				tile.setMinHeight(80);
				tile.setMinWidth(80);

				// On click of tile
				tile.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
					int x = GridPane.getColumnIndex(tile);
					int y = GridPane.getRowIndex(tile);
					onClickOfTile(x, y);
				});

				grid.add(tile, i, j);
			}
		}

		root.getChildren().add(grid);
		displayCheckers();

		return new Scene(root, WIDTH, HEIGHT);
	}

	public void onClickOfTile(int x, int y) {
		if (board.turn == 0) {
			Checker c = board.getCheckerAt(x, y);
			if (c != null) { // If there is a checker at (x, y)
				if (c.colour == board.player.colour) { // If checker is the
														// players checker
					updateActiveCheckerImage(false);
					board.activeChecker = c;// Set as active checker

					updateActiveCheckerImage(true);

					if (showPossibleMoves)
						displayPossibleMoves(c.possibleMoves);
				}
			} else if (board.activeChecker != null) { // If there is no active
														// checker
				if (board.activeChecker.isAPossibleMove(x, y)) {
					updateActiveCheckerImage(false);
					board.activeChecker.setPosition(x, y);
					board.killChecker(board.activeChecker.takenCheckers.get(new Position(x, y)));
					if (board.activeChecker.takenCheckers.get(new Position(x, y)) != null) {
						if (board.activeChecker.takenCheckers.get(new Position(x, y)).isKing) { // Regicide
							board.activeChecker.makeKing();
						}
						board.activeChecker.getPossibleMoves();
						if (!board.activeChecker.takenCheckers.isEmpty()) { // Double
																			// jumps
							if (showPossibleMoves)
								displayPossibleMoves(board.activeChecker.possibleMoves);
						} else {
							board.activeChecker = null;
							displayCheckers();
							board.switchTurn();
							displayWinAlert();
							displayCheckers();
						}
					} else {
						board.activeChecker = null;
						displayCheckers();
						board.switchTurn();
						displayWinAlert();
						displayCheckers();
					}
				} else { // Alert if wrong move was made
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Move");
					alert.setHeaderText("This move is not valid");
					alert.setContentText(
							"Please try again. Use the 'show possible moves' setting for guidance on where to move.");
					alert.showAndWait();
				}
			}
		}
	}

	public void displayCheckers() {
		clearDisplay();
		ArrayList<Checker> temp = new ArrayList<Checker>(board.checkersBlack);
		temp.addAll(board.checkersRed);
		for (Checker c : temp) {
			Pane p = getTileAt(c.getX(), c.getY());
			p.getChildren().add(c.getImg());

			// EVENT HANDLERS FOR DRAGGING CHECKERS
			c.getImg().setOnDragDetected(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (board.turn == 0) {
						Dragboard db = c.getImg().startDragAndDrop(TransferMode.ANY);
						ClipboardContent content = new ClipboardContent();
						content.putImage(c.getImg().getImage());

						board.activeChecker = c;
						if (showPossibleMoves)
							displayPossibleMoves(board.activeChecker.possibleMoves);

						db.setContent(content);
						event.consume();
					}
				}
			});

			grid.setOnDragOver(event -> {
				if (event.getDragboard().hasImage()) {
					event.acceptTransferModes(TransferMode.MOVE);
					event.consume();
				}
			});

			grid.setOnDragDropped(event -> {
				int gridX = (int) event.getSceneX() / 80;
				int gridY = (int) event.getSceneY() / 80;

				if (board.activeChecker.isAPossibleMove(gridX, gridY)) {
					board.activeChecker.setPosition(gridX, gridY);
					board.killChecker(board.activeChecker.takenCheckers.get(new Position(gridX, gridY)));
					if (board.activeChecker.takenCheckers.get(new Position(gridX, gridY)) != null) {
						if (board.activeChecker.takenCheckers.get(new Position(gridX, gridY)).isKing) {
							board.activeChecker.isKing = true;
						}
						board.activeChecker.getPossibleMoves();
						if (!board.activeChecker.takenCheckers.isEmpty()) {
							if (showPossibleMoves)
								displayPossibleMoves(board.activeChecker.possibleMoves);
						} else {
							board.activeChecker = null;
							displayCheckers();
							board.switchTurn();
							displayWinAlert();
							displayCheckers();
						}
					} else {

						updateActiveCheckerImage(false);

						displayCheckers();
						board.switchTurn();
						displayWinAlert();
					}
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Invalid Move");
					alert.setHeaderText("This move is not valid");
					alert.setContentText(
							"Please try again. Use the 'show possible moves' setting for guidance on where to move.");
					alert.showAndWait();
				}

				clearDisplay();
				displayCheckers();

				event.setDropCompleted(true);
				event.consume();
			});

		}
	}

	// if makeActive = true, it will make the active checker have a green
	// surround to show its active
	// otherwise it will remove the green surround to deactive the checker
	public void updateActiveCheckerImage(boolean makeActive) {
		if (board.activeChecker != null) {
			if (makeActive) {
				if (board.activeChecker.colour == CheckerColour.Red) {
					if (board.activeChecker.isKing)
						board.activeChecker.setImg(new ImageView(new Image("resources/checkerredkingactive.png")));
					else
						board.activeChecker.setImg(new ImageView(new Image("resources/checkerredactive.png")));
				}
				if (board.activeChecker.colour == CheckerColour.Black) {
					if (board.activeChecker.isKing)
						board.activeChecker.setImg(new ImageView(new Image("resources/checkerblackkingactive.png")));
					else
						board.activeChecker.setImg(new ImageView(new Image("resources/checkerblackactive.png")));
				}
			} else {
				if (board.activeChecker.colour == CheckerColour.Red) {
					if (board.activeChecker.isKing)
						board.activeChecker.img = new ImageView(new Image("resources/checkerredking.png"));
					else
						board.activeChecker.img = new ImageView(new Image("resources/checkerred.png"));
				} else {
					if (board.activeChecker.isKing)
						board.activeChecker.img = new ImageView(new Image("resources/checkerblackking.png"));
					else
						board.activeChecker.img = new ImageView(new Image("resources/checkerblack.png"));
				}
			}
		}
	}
	
	//returns true if game over
	public static boolean displayWinAlert() {
		String txt = "";
		if (board.blackHasWon()) {
			if (board.player.colour == CheckerColour.Black)
				txt = "Congratulations! You have won!";
			else
				txt = "Sorry! You have lost!";
		}
		if (board.redHasWon()) {
			if (board.player.colour == CheckerColour.Black)
				txt = "Sorry! You have lost!";
			else
				txt = "Congratulations! You have won!";
		}
		if (board.redHasWon() || board.blackHasWon()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText("Game Over!");
			alert.setContentText(txt);

			alert.showAndWait();
			alert.setOnCloseRequest(e -> {
				System.exit(0);
			});
			return true;
		} else {
			return false;
		}
	}

	public void displayPossibleMoves(ArrayList<Position> possibleMoves) {
		clearDisplay();
		displayCheckers();
		for (Position p : possibleMoves) {
			ImageView possMoveToken = new ImageView(new Image("resources/possiblemove.png"));
			getTileAt(p.getX(), p.getY()).getChildren().add(possMoveToken);
		}
	}

	public void clearDisplay() {
		for (Node node : grid.getChildren()) {
			Pane tile = (Pane) node;
			tile.getChildren().clear();
		}
	}

	public Pane getTileAt(int x, int y) {
		for (Node node : grid.getChildren()) {
			Pane tile = (Pane) node;
			if (GridPane.getColumnIndex(tile) == x && GridPane.getRowIndex(tile) == y) {
				return tile;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
