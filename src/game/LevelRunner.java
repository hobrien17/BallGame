package game;

import java.lang.reflect.InvocationTargetException;

import gui.App;
import gui.View;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import leveldata.Level;
import leveldata.LevelLibrary;
import leveldata.Tutorial;
import objects.Ball;
import objects.Board;
import objects.obstacles.DoubleTarget;
import objects.obstacles.Target;
import objects.obstacles.Obstacle;
import objects.obstacles.RedSwitch;
import objects.obstacles.Switch;
import objects.obstacles.SwitchedObstacle;

public class LevelRunner extends Thread {
	private Board board;
	private Ball ball;
	private Level level;

	private App app;
	private View view;
	private Stage stage;

	private int timer;
	
	private final Object lock = new Object();
	private final Object notifier = new Object();

	public enum State {
		TUTORIAL, COUNTDOWN, ONGOING, LEVEL_COMPLETE, GAME_OVER
	}

	private State state;

	public LevelRunner(int lno, App app) {
		this.app = app;
		stage = app.getStage();
		view = new View();
		level = LevelLibrary.getLevel(lno);

		board = new Board(level);

		setupView();
	}

	private void setupView() {
		view.clear();
		view.drawBoard(board);
		view.addCounter();
		view.addArrow(level.getBallStart(), 0);

		view.addClickHandler(new ClickActionHandler());

		stage.setScene(view.getScene());
		stage.setTitle("Level " + level.getNum());
		stage.setResizable(false);
		stage.show();
	}

	public Object getNotifier() {
		return notifier;
	}

	/*
	 * public Board getBoard() { return board; }
	 * 
	 * public Ball getBall() { return ball; }
	 * 
	 * public String getGameState() { return state; }
	 */

	public State getGameState() {
		return state;
	}

	private void checkCollision() {
		Obstacle obs = board.getObstacleAt(ball.getCentreX(), ball.getCentreY());
		if (obs == null) {
			return;
		}
		int xHit = obs.getXHit(ball.getDir());
		int yHit = obs.getYHit(ball.getDir());
		if (ball.getCentreX() == xHit && ball.getCentreY() == yHit) {
			try {
				obs.getDirChange(ball.getDir()).invoke(ball);
				if (obs instanceof DoubleTarget) {
					DoubleTarget dt = (DoubleTarget) obs;
					Obstacle t = dt.toTarget();
					board.replaceObstacle(t, obs.getRow(), obs.getCol());
				} else if (obs.destroyAfterHit()) {
					board.removeObstacle(obs.getRow(), obs.getCol());
					/*
					 * } else if (obs instanceof Switch) {
					 * broadcastSwitchChange(); updateObstacle(obs,
					 * obs.getRow(), obs.getCol()); if (obs instanceof
					 * RedSwitch) { board.removeObstacle(obs.getRow(),
					 * obs.getCol()); removeObstacle(obs.getRow(),
					 * obs.getCol()); } }
					 */
				}
				updateObstacle(obs.getRow(), obs.getCol());
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		if (ball.getTargetsHit() == board.getTotalTargets()) {
			state = State.LEVEL_COMPLETE;
			levelComplete();
			System.out.println("All targets hit!");
		}
	}

	private void updateObstacle(int row, int col) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				view.updateCell(board, row, col);
			}
		});
	}

	private void updateViewCounter() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				view.updateCounter(timer);
			}
		});
	}

	private void updateBallPos() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				view.redrawBall(ball.getX(), ball.getY());
			}
		});
	}

	private void levelComplete() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				state = State.LEVEL_COMPLETE;
				view.showNotification("file:resources/LevelComplete.png");
			}
		});
	}

	private void gameOver() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				state = State.GAME_OVER;
				view.showNotification("file:resources/gameOver.png");
			}
		});
	}

	/*
	 * private void showTutorial(Tutorial tutorial) { Platform.runLater(new
	 * Runnable() {
	 * 
	 * @Override public void run() { view.showTutorial(tutorial.getImage()); }
	 * }); }
	 */

	/*
	 * private void broadcastSwitchChange() { Obstacle[][] lst =
	 * board.getObstacles(); for (Obstacle[] sub : lst) { for (Obstacle obs :
	 * sub) { if (obs != null) { if (obs instanceof SwitchedObstacle) {
	 * SwitchedObstacle so = (SwitchedObstacle) obs; so.switchChange();
	 * updateObstacle(so, so.getRow(), so.getCol()); } } } } }
	 */

	@Override
	public void run() {
		state = State.COUNTDOWN;
		timer = level.getTime();
		updateViewCounter();
		while (timer > 0) {
			try {
				Thread.sleep(1000);

				timer--;
				updateViewCounter();
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}

		state = State.ONGOING;
		ball = new Ball(0, Board.getYFromRow(level.getBallStart()) - Ball.getHalfSize());
		while (state == State.ONGOING) {
			try {
				Thread.sleep(2);

				ball.move();
				updateBallPos();
				checkCollision();
			} catch (IndexOutOfBoundsException ex) {
				state = State.GAME_OVER;
				gameOver();
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				stage.hide();
				app.nextLevel(LevelRunner.this);
			}
		});
	}

	private class ClickActionHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (state == State.COUNTDOWN || state == State.ONGOING) {
				int mouseX = (int) event.getX();
				int mouseY = (int) event.getY();
				Obstacle obs = board.getObstacleAt(mouseX, mouseY);
				if (obs != null && obs.canClick()) {
					obs.change();
					view.updateCell(board, obs.getRow(), obs.getCol());
				}
			} else {
				synchronized (lock) {
					view.hideNotification();
					lock.notify();
				}
			}
		}
	}

}
