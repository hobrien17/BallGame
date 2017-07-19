package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;

public class Target implements Obstacle {
	private final static int GRIDSIZE = 65;
	
	int row;
	int col;
	
	public Target(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public int getXPos() {
		return col*GRIDSIZE;
	}
	
	public int getYPos() {
		return row*GRIDSIZE;
	}
	
	public int getXHit(Ball.Dir incomingDir) {
		return col*GRIDSIZE + GRIDSIZE/2;
	}
	
	public int getYHit(Ball.Dir incomingDir) {
		return row*GRIDSIZE + GRIDSIZE/2;
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
}
