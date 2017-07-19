package gui;

import game.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import leveldata.LevelLibrary;

public class App extends Application {

	private int level;
	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		level = 1;
		this.stage = stage;

		setup();
	}

	private void setup() {
		View view = new View();
		Game game = new Game(view, level);
		game.start();

		stage.setScene(view.getScene());
		stage.setTitle("Level " + level);
		stage.setResizable(false);
		stage.show();

		new GameListener(game).start();
	}

	private class GameListener extends Thread {
		Game game;

		GameListener(Game game) {
			this.game = game;
		}

		@Override
		public void run() {
			synchronized (game) {
				while (game.getGameState().equals("ONGOING")) {
					try {
						game.wait();
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
				}
				if (game.getGameState().equals("NEXTLEVEL")) {
					level++;
				}
				if (level <= LevelLibrary.getLevelNum()) {
					System.out.println("end");
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							stage.hide();
							setup();
						}
					});
				} else {
					System.out.println("No more levels!");
				}
			}
		}
	}
}
