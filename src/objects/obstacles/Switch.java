package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;
import objects.Ball.Dir;

public class Switch implements SwitchedObstacle {
	private final static int GRIDSIZE = 65;
	
	int row;
	int col;
	
	enum Type {
		GREEN,
		BLUE
	}
	
	Type face;
	
	public Switch(int row, int col) {
		this.row = row;
		this.col = col;
		face = Type.GREEN;
	}
	
	@Override
	public int getRow() {
		return row;
	}

	@Override
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

	@Override
	public int colsOccupied() {
		return 1;
	}

	@Override
	public void change() {}
	
	@Override
	public void switchChange() {
		switch(face) {
		case GREEN:
			face = Type.BLUE;
			break;
		case BLUE:
			face = Type.GREEN;
			break;
		}
	}

	@Override
	public Method getDirChange(Dir incomingDir) {
		try {
			return Ball.class.getMethod("noRotate");
		} catch (NoSuchMethodException | SecurityException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public String getImgURL() {
		switch (face) {
		case GREEN:
			return "file:resources/SwitchGreen.png";
		case BLUE:
			System.out.println("yes, blue");
			return "file:resources/SwitchBlue.png";
		default:
			return null;
		}
	}

	@Override
	public Obstacle copy() {
		return new Switch(row, col);
	}

}
