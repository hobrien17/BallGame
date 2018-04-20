package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;
import objects.Board;

public class Target extends Obstacle {
	
	private int initHits;
	private int hits;
	
	public Target(int row, int col, int hits) {
		super(row, col);
		this.hits = hits;
		this.initHits = hits;
	}
	
	public Target(int row, int col) {
		this(row, col, 1);
	}
	
	public int getXHit(Ball.Dir incomingDir) {
		return col*Board.GRIDSIZE + Board.GRIDSIZE/2;
	}
	
	public int getYHit(Ball.Dir incomingDir) {
		return row*Board.GRIDSIZE + Board.GRIDSIZE/2;
	}
	
	public int colsOccupied() {
		return 1;
	}
	
	public int rowsOccupied() {
		return 1;
	}
	
	public boolean destroyAfterHit() {
		if(hits == 0) {
			return true;
		}
		return false;
	}
	
	public boolean canClick() {
		return false;
	}
	
	public int getHits() {
		return hits;
	}
	
	public void change() {}
	
	public Method getDirChange(Ball.Dir incomingDir) {
		hits--;
		try {
			if(hits == 0) {
				return Ball.class.getMethod("incTargetsHit");
			} else {
				return Ball.class.getMethod("noRotate");
			}
		} catch (NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public String getImgURL() {
		switch(hits) {
		case 1:
			return "file:resources/Target.png";
		case 2:
			return "file:resources/DoubleTarget.png";
		default:
			return "file:resources/TripleTarget.png";
		}
	}
	
	public Obstacle copy() {
		return new Target(row, col, initHits);
	}
	
	public String toString() {
		if(hits == 1) {
			return "TARGET";
		} else {
			return "TARGET:" + hits;
		}
	}
}
