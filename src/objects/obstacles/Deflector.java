package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;

public class Deflector implements Obstacle {
	private final static int GRIDSIZE = 65;
	
	private int row;
	private int col;
	
	public enum Type {
		RIGHT,
		LEFT
	}
	
	private Type face;
	private Type init;
	
	public Deflector(int row, int col, Type face) {
		this.row = row;
		this.col = col;
		this.init = face;
		this.face = face;
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
	
	public String getImgURL() {
		switch(face) {
		case RIGHT:
			return "file:resources/DeflectorR.png";
		case LEFT:
			return "file:resources/DeflectorL.png";
		default:
			return null;
		}
	}
	
	public void change() {
		switch(face) {
		case RIGHT:
			face = Type.LEFT;
			break;
		case LEFT:
			face = Type.RIGHT;
			break;
		}
	}
		
	public Method getDirChange(Ball.Dir incomingDir) {		
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
		
		if(incomingDir.equals(Ball.Dir.LEFT) || incomingDir.equals(Ball.Dir.RIGHT)) {
			switch(face) {
			case RIGHT:
				System.out.println("anti");
				return anticlockwise;
			case LEFT:
				System.out.println("clock");
				return clockwise;
			default:
				return null;
			}
		} else{
			switch(face) {
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
}
