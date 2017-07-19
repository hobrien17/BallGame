package gui;

import java.io.File; 

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import objects.obstacles.Obstacle;

public class View {
	Scene scene;
	
	GridPane gridPane;
	Pane mainPane;
	EventHandler<MouseEvent> clickHandler;
	
	Label counter;
	
	ImageView[][] images;
	ImageView ball;
	ImageView arrow;
	ImageView popup;
	
	final static int WIDTH = 390;
	final static int HEIGHT = 650;
	final static int POPUPWIDTH = 200;
	final static int POPUPHEIGHT = 100;
	//6p popup border
	//futura medium italic 25/20
		
	public View() {
		mainPane = new Pane();
		gridPane = new GridPane();
		gridPane.setHgap(1);
		gridPane.setVgap(1);
		mainPane.getChildren().add(gridPane);
				
		setBG();
		
		scene = new Scene(mainPane, WIDTH, HEIGHT);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public void addObstacles(Obstacle[][] arr) {
		images = new ImageView[arr.length][arr[0].length];
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				Obstacle obs = arr[i][j];
				updateObstacle(obs, j, i);
			}
		}	
	}
	
	public void updateObstacle(Obstacle obs, int row, int col) {
		if(images[col][row] != null) {
			gridPane.getChildren().remove(images[col][row]);
		}
		ImageView im;
		if(obs != null) {
			System.out.println(obs);
			im = new ImageView(obs.getImgURL());
			if(obs.colsOccupied() > 1) {
				images[col][row] = im;
				gridPane.add(im, col, row, 2, 1);
				return;
			}
		}
		else {
			im = new ImageView("file:resources/Blank.png");
		}
		
		images[col][row] = im;
		gridPane.add(im, col, row);
	}
	
	public void addArrow(int row, int col) {
		arrow = new ImageView("file:resources/Arrow.png");
		gridPane.add(arrow, col, row);
	}
	
	public void addCounter() {
		counter = new Label();
		counter.setFont(new Font("Arial", 30));
		counter.setTextFill(Color.WHITE);
		mainPane.getChildren().add(counter);
	}
	
	public void updateCounter(int count) {
		counter.setText(String.format("%d", count));
		if(count == 0) {
			mainPane.getChildren().remove(counter);
		}
	}
	
	public void redrawBall(int x, int y) {
		if(ball == null) {
			ball = new ImageView("file:resources/Ball.png");
			mainPane.getChildren().add(ball);
		}
		ball.setX(x);
		ball.setY(y);
	}
	
	public void disp(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public void showNotification(String imgURL) {
		popup = new ImageView(imgURL);
		popup.setX(WIDTH/2 - POPUPWIDTH/2);
		popup.setY(HEIGHT/2 - POPUPHEIGHT/2);
		mainPane.getChildren().add(popup);
		popup.setOnMouseReleased(clickHandler);
	}
	
	public void hideNotification() {
		mainPane.getChildren().remove(popup);
	}
	
	public void showTutorial(String imgURL) {
		popup = new ImageView(imgURL);
		popup.setX(0);
		popup.setY(0);
		mainPane.getChildren().add(popup);
		popup.setOnMouseReleased(clickHandler);
	}
	
	private void setBG() {
		File file = new File("resources/Board.png");
		Image image = new Image(file.toURI().toString());
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, 
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		Background bg = new Background(backgroundImage);
		mainPane.backgroundProperty().set(bg);
	}
	
	public void addClickHandler(EventHandler<MouseEvent> handler) {
		clickHandler = handler;
		gridPane.setOnMouseReleased(clickHandler);
	}
	
	public void clear() {
		gridPane.getChildren().clear();
	}
	
}
