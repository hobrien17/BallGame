package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;
import objects.Board;

public class Bouncer extends Obstacle {
	
	public enum Type {
		LR,
		UD
	}
	
	private Type type;
	private Type init;
	
	public Bouncer(int row, int col, Type type) {
		super(row, col);
		this.init = type;
		this.type = type;
	}
	
	public int getXHit(Ball.Dir incomingDir) {
		switch(type) {
		case LR:
			if(incomingDir.equals(Ball.Dir.RIGHT)) {
				return col*Board.GRIDSIZE;
			} else if(incomingDir.equals(Ball.Dir.LEFT)) {
				return col*Board.GRIDSIZE + Board.GRIDSIZE - 1;
			}
		case UD:
			return col*Board.GRIDSIZE + Board.GRIDSIZE/2;
		}
		return -1;
	}
	
	public int getYHit(Ball.Dir incomingDir) {
		switch(type) {
		case LR:
			return row*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case UD:
			if(incomingDir.equals(Ball.Dir.DOWN)) {
				return row*Board.GRIDSIZE;
			} else if(incomingDir.equals(Ball.Dir.UP)) {
				return row*Board.GRIDSIZE + Board.GRIDSIZE - 1;
			}
		}
		return -1;
	}
	
	public int colsOccupied() {
		return 1;
	}
	
	public int rowsOccupied() {
		return 1;
	}
	
	public String getImgURL() {
		switch(type) {
		case LR:
			return "file:resources/BouncerLR.png";
		case UD:
			return "file:resources/BouncerUD.png";
		default:
			return null;
		}
	}
	
	public void change() {
		switch(type) {
		case LR:
			type = Type.UD;
			break;
		case UD:
			type = Type.LR;
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
			switch(type) {
			case LR:
				return rot;
			case UD:
				return noRot;
			}
		} else {
			switch(type) {
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

	@Override
	public boolean destroyAfterHit() {
		return false;
	}
	
	public boolean canClick() {
		return true;
	}
	
	@Override
	public String toString() {
		if(type == Type.UD) {
			return "BOUNCER_UD";
		} else {
			return "BOUNCER_LR";
		}
	}
}
