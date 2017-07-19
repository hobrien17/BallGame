package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;

public class DoubleDeflector implements Obstacle {
	private final static int GRIDSIZE = 65;
	
	int row;
	int leftCol;
	int rightCol;
	
	public enum Type {
		RIGHT_RIGHT,
		RIGHT_LEFT,
		LEFT_LEFT,
		LEFT_RIGHT
	}
	
	Type face;
	Type init;
	
	public DoubleDeflector(int row, int col, Type face) {
		this.row = row;
		this.leftCol = col;
		this.rightCol = leftCol + 1;
		this.init = face;
		this.face = face;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return leftCol;
	}
	
	public int getXPos() {
		return leftCol*GRIDSIZE;
	}
	
	public int getYPos() {
		return row*GRIDSIZE;
	}
	
	public int getXHit(Ball.Dir incomingDir) {
		if(face.equals(Type.RIGHT_RIGHT) || face.equals(Type.LEFT_RIGHT)) {
			return rightCol*GRIDSIZE + GRIDSIZE/2;
		}else{
			return leftCol*GRIDSIZE + GRIDSIZE/2;
		}
	}
	
	public int getYHit(Ball.Dir incomingDir) {
		return row*GRIDSIZE + GRIDSIZE/2;
	}
	
	public int colsOccupied() {
		return 2;
	}
	
	public boolean destroyAfterHit() {
		return false;
	}
	
	public void change() {
		switch(face) {
		case RIGHT_RIGHT:
			face = Type.RIGHT_LEFT;
			break;
		case RIGHT_LEFT:
			face = Type.RIGHT_RIGHT;
			break;
		case LEFT_LEFT:
			face = Type.LEFT_RIGHT;
			break;
		case LEFT_RIGHT:
			face = Type.LEFT_LEFT;
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
			if(face.equals(Type.LEFT_LEFT) || face.equals(Type.LEFT_RIGHT)) {
				return clockwise;
			}else{
				return anticlockwise;
			}
		} else{
			if(face.equals(Type.LEFT_LEFT) || face.equals(Type.LEFT_RIGHT)) {
				return anticlockwise;
			}else{
				return clockwise;
			}
		}
	}
	
	public String getImgURL() {
		switch(face) {
		case RIGHT_RIGHT:
			return "file:resources/DeflectorRR.png";
		case RIGHT_LEFT:
			return "file:resources/DeflectorRL.png";
		case LEFT_LEFT:
			return "file:resources/DeflectorLL.png";
		case LEFT_RIGHT:
			return "file:resources/DeflectorLR.png";
		default:
			return null;
		}
	}
	
	public Obstacle copy() {
		return new DoubleDeflector(row, leftCol, init);
	}
}
