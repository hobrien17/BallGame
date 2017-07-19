package game;

import java.lang.reflect.InvocationTargetException;

import gui.View;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
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

public class Game extends Thread {
	private Board board;
	private Ball ball;

	private View view;

	private int timer;
	private boolean active;
	private boolean pre;
	private boolean post;

	private int level;

	private String state = "ONGOING";

	public Game(View view, int level) {
		this.view = view;
		this.level = level;

		newGame();
	}

	private void newGame() {
		timer = LevelLibrary.getLevel(level).getTime();
		board = new Board(LevelLibrary.getLevel(level));
		setupView();
	}

	private void setupView() {
		view.clear();
		Obstacle[][] arr = board.getObstacles();
		view.addObstacles(arr);
		view.addCounter();
		view.addArrow(LevelLibrary.getLevel(level).getBallStart(), 0);

		view.addClickHandler(new ClickActionHandler());
	}

	public Board getBoard() {
		return board;
	}

	public Ball getBall() {
		return ball;
	}

	public String getGameState() {
		return state;
	}

	private void checkCollision() {
		Obstacle obs = board.getObstacleAt(ball.getCentreX(), ball.getCentreY());
		if (obs == null) {
			return;
		}
		if (ball.getCentreX() == obs.getXHit(ball.getDir()) && ball.getCentreY() == obs.getYHit(ball.getDir())) {
			try {
				obs.getDirChange(ball.getDir()).invoke(ball);
				if (obs instanceof DoubleTarget) {
					DoubleTarget dt = (DoubleTarget) obs;
					Obstacle t = dt.toTarget();
					board.replaceObstacle(t, obs.getRow(), obs.getCol());
					updateObstacle(t, obs.getRow(), obs.getCol());
				} else if (obs instanceof Target) {
					board.removeObstacle(obs.getRow(), obs.getCol());
					removeObstacle(obs.getRow(), obs.getCol());
				} else if (obs instanceof Switch) {
					broadcastSwitchChange();
					updateObstacle(obs, obs.getRow(), obs.getCol());
					if (obs instanceof RedSwitch) {
						board.removeObstacle(obs.getRow(), obs.getCol());
						removeObstacle(obs.getRow(), obs.getCol());
					}
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
				ex.printStackTrace();
			}
		}
		if (ball.getTargetsHit() == board.getTotalTargets()) {
			active = false;
			post = true;
			levelComplete();
			System.out.println("All targets hit!");
		}
	}

	private void removeObstacle(int row, int col) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				view.updateObstacle(null, row, col);
			}
		});
	}

	private void updateObstacle(Obstacle obs, int row, int col) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				view.updateObstacle(obs, row, col);
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
				state = "NEXTLEVEL";
				view.showNotification("file:resources/LevelComplete.png");
			}
		});
	}

	private void gameOver() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				state = "GAMEOVER";
				view.showNotification("file:resources/gameOver.png");
			}
		});
	}

	private void showTutorial(Tutorial tutorial) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				view.showTutorial(tutorial.getImage());
			}
		});
	}

	private void broadcastSwitchChange() {
		Obstacle[][] lst = board.getObstacles();
		for (Obstacle[] sub : lst) {
			for (Obstacle obs : sub) {
				if (obs != null) {
					if (obs instanceof SwitchedObstacle) {
						SwitchedObstacle so = (SwitchedObstacle) obs;
						so.switchChange();
						updateObstacle(so, so.getRow(), so.getCol());
					}
				}
			}
		}
	}

	@Override
	public void run() {
		synchronized (this) {
			pre = true;
			post = false;
			active = false;

			if (LevelLibrary.getLevel(level).hasTutorial()) {
				showTutorial(LevelLibrary.getLevel(level).getTutorial());
				try {
					this.wait();
				} catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
			pre = false;
			active = true;
			updateViewCounter();
			while (timer > 0) {
				try {
					Thread.sleep(1000);
					timer--;
					updateViewCounter();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
			ball = new Ball(0, Board.getYFromRow(LevelLibrary.getLevel(level).getBallStart()) - Ball.getHalfSize());
			while (active) {
				try {
					Thread.sleep(2);
					ball.move();
					updateBallPos();
					checkCollision();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				} catch (ArrayIndexOutOfBoundsException ex) {
					active = false;
					post = true;
					gameOver();
				}
			}
			try {
				this.wait();
			} catch (InterruptedException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			post = false;
		}
	}

	private class ClickActionHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if (!post && !pre) {
				int mouseX = (int) event.getX();
				int mouseY = (int) event.getY();
				Obstacle obs = board.getObstacleAt(mouseX, mouseY);
				if (obs != null) {
					obs.change();
					view.updateObstacle(obs, obs.getRow(), obs.getCol());
				}
			} else {
				synchronized (Game.this) {
					view.hideNotification();
					Game.this.notifyAll();
				}
			}
		}
	}

}
