package objects.obstacles;

import java.lang.reflect.Method;
import objects.Ball;
import objects.Board;

public abstract class Obstacle {
	protected int row;
	protected int col;
	
	public enum Type {
		LEFT,
		RIGHT
	}
	
	protected Type type;
	protected Type init;
	
	public Obstacle(int row, int col) {
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
		return col*Board.GRIDSIZE;
	}
	
	public int getYPos() {
		return row*Board.GRIDSIZE;
	}
	
	public void change() {
		switch(type) {
		case LEFT:
			type = Type.RIGHT;
			return;
		default:
			type = Type.LEFT;
			return;
		}
	}
	
	public abstract int getXHit(Ball.Dir incomingDir);
	public abstract int getYHit(Ball.Dir incomingDir);
	public abstract int colsOccupied();
	public abstract boolean destroyAfterHit();
	public abstract String getImgURL();
	public abstract Obstacle copy();
	
	public abstract Method getDirChange(Ball.Dir incomingDir);
	
	protected Method[] getDirMethods(Ball.Dir incomingDir) {
		Method clockwise;
		Method anticlockwise;
		try {
			clockwise = Ball.class.getMethod("rotateClockwise");
			anticlockwise = Ball.class.getMethod("rotateCounterclockwise");
		} catch (NoSuchMethodException | SecurityException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
		Method[] result = {clockwise, anticlockwise};
		return result;
	}
}
