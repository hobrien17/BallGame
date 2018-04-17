package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;

public class DoubleTarget extends Target {
	
	public DoubleTarget(int row, int col) {
		super(row, col);
	}
	
	@Override
	public boolean destroyAfterHit() {
		return false;
	}
	
	@Override
	public String getImgURL() {
		return "file:resources/DoubleTarget.png";
	}
	
	@Override
	public Method getDirChange(Ball.Dir incomingDir) {
		try {
			return Ball.class.getMethod("noRotate");
		} catch (NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Obstacle copy() {
		return new DoubleTarget(row, col);
	}
	
	public Target toTarget() {
		return new Target(row, col);
	}
	
	public String toString() {
		return "DOUBLETARGET";
	}
}
