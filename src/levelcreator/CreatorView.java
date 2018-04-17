package levelcreator;

import java.util.Optional;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import objects.Board;
import objects.obstacles.Obstacle;

public class CreatorView {
	Scene scene;
	
	GridPane gridPane;
	Pane mainPane;
	
	ImageView curObs;
	int curObsX;
	int curObsY;
	
	EventHandler<MouseEvent> clickHandler;
	EventHandler<MouseEvent> moveHandler;
	
	final static int WIDTH = Board.COLS*Board.GRIDSIZE;
	final static int HEIGHT = Board.ROWS*Board.GRIDSIZE;
	
	public CreatorView() {
		mainPane = new Pane();
		gridPane = new GridPane();
		mainPane.getChildren().add(gridPane);
						
		scene = new Scene(mainPane, WIDTH, HEIGHT);
		
		draw();
	}
	
	public void draw() {
		for(int i = 0; i < Board.COLS; i++) {
			for(int j = 0; j < Board.ROWS; j++) {
				gridPane.add(new ImageView("file:resources/Empty.png"), i, j);
			}
		}
		curObsX = -1;
		curObsY = -1;
	}
	
	public void clearObs() {
		if(curObs != null) {
			gridPane.getChildren().remove(curObs);
		}
	}
	
	public void clearPlacedObs(int col, int row) {
		gridPane.add(new ImageView("file:resources/Empty.png"), col, row);
	}
	
	public void drawObs(Obstacle obs, int x, int y) {
		ImageView img = new ImageView(obs.getImgURL());
		img.setOpacity(0.5);
		int newX = getCell(x);
		int newY = getCell(y);
		
		if(newX != curObsX || newY != curObsY) {
			if(curObs != null) {
				gridPane.getChildren().remove(curObs);
			}
			curObs = img;
			gridPane.add(curObs, getCell(x), getCell(y), obs.colsOccupied(), obs.rowsOccupied());
		}
	}
	
	public void placeObs(Obstacle obs, int x, int y) {
		ImageView img = new ImageView(obs.getImgURL());
		gridPane.add(img, getCell(x), getCell(y), obs.colsOccupied(), obs.rowsOccupied());
	}
	
	public int getCell(int x) {
		return x/Board.GRIDSIZE;
	}
	
	public void showError(String title, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public boolean showConfirmation(String title, String message) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(title);
		alert.setContentText(message);
		
		Optional<ButtonType> result = alert.showAndWait();
		return result.get() == ButtonType.OK;
	}
	
	public String showInputBox(String preset, String title, String message) {
		TextInputDialog dialog = new TextInputDialog(preset);
		dialog.setTitle("");
		dialog.setHeaderText(title);
		dialog.setContentText(message);
		
		Optional<String> result = dialog.showAndWait();
		if(result.isPresent()) {
			return result.get();
		}
		return null;
	}
	
	public void showInfo(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Info");
		alert.setHeaderText(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void addPressHandler(EventHandler<KeyEvent> handler) {
		scene.setOnKeyPressed(handler);
	}
	
	public void addClickHandler(EventHandler<MouseEvent> handler) {
		gridPane.setOnMouseClicked(handler);
	}
	
	public void addMoveHandler(EventHandler<MouseEvent> handler) {
		gridPane.setOnMouseMoved(handler);
	}
}
