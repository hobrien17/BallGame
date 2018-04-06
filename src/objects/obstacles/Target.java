package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;
import objects.Board;

public class Target extends Obstacle {
	
	public Target(int row, int col) {
		super(row, col);
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
	
	public boolean destroyAfterHit() {
		return true;
	}
	
	public void change() {}
	
	public Method getDirChange(Ball.Dir incomingDir) {
		try {
			return Ball.class.getMethod("incTargetsHit");
		} catch (NoSuchMethodException | SecurityException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
	}
	
	public String getImgURL() {
		return "file:resources/Target.png";
	}
	
	public Obstacle copy() {
		return new Target(row, col);
	}
	
	public String toString() {
		return "TARGET";
	}
}
