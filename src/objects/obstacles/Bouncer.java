package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;

public class Bouncer implements Obstacle {
private final static int GRIDSIZE = 65;
	
	private int row;
	private int col;
	
	public enum Type {
		LR,
		UD
	}
	
	private Type face;
	private Type init;
	
	public Bouncer(int row, int col, Type face) {
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
		switch(face) {
		case LR:
			if(incomingDir.equals(Ball.Dir.RIGHT)) {
				return col*GRIDSIZE;
			}
			else if(incomingDir.equals(Ball.Dir.LEFT)) {
				return col*GRIDSIZE + GRIDSIZE - 1;
			}
			else {
				return -1;
			}
		case UD:
			return col*GRIDSIZE + GRIDSIZE/2;
		default:
			return -1;
		}
	}
	
	public int getYHit(Ball.Dir incomingDir) {
		switch(face) {
		case LR:
			return row*GRIDSIZE + GRIDSIZE/2;
		case UD:
			if(incomingDir.equals(Ball.Dir.DOWN)) {
				return row*GRIDSIZE;
			}
			else if(incomingDir.equals(Ball.Dir.UP)) {
				return row*GRIDSIZE + GRIDSIZE - 1;
			}
			else {
				return -1;
			}
		default:
			return -1;
		}
	}
	
	public int colsOccupied() {
		return 1;
	}
	
	public String getImgURL() {
		switch(face) {
		case LR:
			return "file:resources/BouncerLR.png";
		case UD:
			return "file:resources/BouncerUD.png";
		default:
			return null;
		}
	}
	
	public void change() {
		switch(face) {
		case LR:
			face = Type.UD;
			break;
		case UD:
			face = Type.LR;
			break;
		}
	}
		
	public Method getDirChange(Ball.Dir incomingDir) {
		Method rot;
		Method noRot;
		try {
			rot = Ball.class.getMethod("rotate180");
			noRot = Ball.class.getMethod("noRotate");
		} catch (NoSuchMethodException | SecurityException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
		
		if(incomingDir.equals(Ball.Dir.LEFT) || incomingDir.equals(Ball.Dir.RIGHT)) {
			switch(face) {
			case LR:
				return rot;
			case UD:
				return noRot;
			}
		} else {
			switch(face) {
			case LR:
				return noRot;
			case UD:
				return rot;
			}
		}
		
		return null;
	}
	
	public Obstacle copy() {
		return new Bouncer(row, col, init);
	}
}
