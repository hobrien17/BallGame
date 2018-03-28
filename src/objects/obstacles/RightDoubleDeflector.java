package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;

public class RightDoubleDeflector extends DoubleDeflector {
	
	public RightDoubleDeflector(int row, int col, Type type) {
		super(row, col, type);
	}
	
	public Method getDirChange(Ball.Dir incomingDir) {
		Method[] methods = this.getDirMethods(incomingDir);
		Method clockwise = methods[0];
		Method anticlockwise = methods[1];
		
		if(incomingDir.equals(Ball.Dir.LEFT) || incomingDir.equals(Ball.Dir.RIGHT)) {
			return anticlockwise;
		} else{
			return clockwise;
		}
	}
	
	public String getImgURL() {
		switch(type) {
		case LEFT:
			return "file:resources/DeflectorLR.png";
		case RIGHT:
			return "file:resources/DeflectorRR.png";
		default:
			return null;
		}
	}
	
	public Obstacle copy() {
		return new RightDoubleDeflector(row, col, init);
	}
}
