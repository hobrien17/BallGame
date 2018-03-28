package gui;

import game.LevelRunner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import leveldata.LevelLibrary;

public class App extends Application {

	private int lno;
	private Stage stage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		lno = 1;
		LevelLibrary.loadLevels();
		this.stage = stage;

		setup();
	}

	private void setup() {
		LevelRunner level = new LevelRunner(lno, this);
		level.start();
		//new LevelListener(level).start();
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void nextLevel(LevelRunner level) {
		if(level.getGameState() == LevelRunner.State.LEVEL_COMPLETE) {
			lno++;
		}
		if (lno <= LevelLibrary.getLevelCount()) {
			setup();
		} else {
			System.out.println("No more levels!");
			Platform.exit();
		}
	}

	/*private class LevelListener extends Thread {
		LevelRunner level;

		LevelListener(LevelRunner level) {
			this.level = level;
		}

		@Override
		public void run() {
			synchronized(level.getNotifier()) {
				try {
					level.getNotifier().wait();
				} catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
			if (level.getGameState() == LevelRunner.State.LEVEL_COMPLETE) {
				lno++;
			}
			if (lno <= LevelLibrary.getLevelCount()) {
				setup();
			} else {
				System.out.println("No more levels!");
				Platform.exit();
			}
		}
	}*/
}
