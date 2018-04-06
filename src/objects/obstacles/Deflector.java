package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;
import objects.Board;

public class Deflector extends Obstacle {
	
	public Deflector(int row, int col, Type type) {
		super(row, col);
		this.init = type;
		this.type = type;
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
		return false;
	}
	
	public String getImgURL() {
		switch(type) {
		case RIGHT:
			return "file:resources/DeflectorR.png";
		case LEFT:
			return "file:resources/DeflectorL.png";
		default:
			return null;
		}
	}
		
	public Method getDirChange(Ball.Dir incomingDir) {		
		Method[] methods = this.getDirMethods(incomingDir);
		Method clockwise = methods[0];
		Method anticlockwise = methods[1];
		
		if(incomingDir.equals(Ball.Dir.LEFT) || incomingDir.equals(Ball.Dir.RIGHT)) {
			switch(type) {
			case RIGHT:
				return anticlockwise;
			case LEFT:
				return clockwise;
			default:
				return null;
			}
		} else{
			switch(type) {
			case RIGHT:
				return clockwise;
			case LEFT:
				return anticlockwise;
			default:
				return null;
			}
		}
	}
	
	public Obstacle copy() {
		return new Deflector(row, col, init);
	}
	
	public String toString() {
		if(type == Type.LEFT) {
			return "DEFLECTOR_L";
		} else {
			return "DEFLECTOR_R";
		}
	}
}
