package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball.Dir;
import objects.obstacles.Obstacle.Type;
import objects.Board;

public class Rotator extends Obstacle {

	public enum Type {
		NW, SW, SE, NE
	}
	
	private Type init;
	private Type type;
	
	private int rightCol;
	private int lowerRow;
	
	public Rotator(int row, int col, Type type) {
		super(row, col);
		rightCol = col + 1;
		lowerRow = row + 1;
		this.init = type;
		this.type = type;
	}

	@Override
	public int getXHit(Dir incomingDir) {
		switch(type) {
		case NW:
			return col*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case NE:
			return rightCol*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case SE:
			return rightCol*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case SW:
			return col*Board.GRIDSIZE + Board.GRIDSIZE/2;
		}
		return -1; // should never get here
	}

	@Override
	public int getYHit(Dir incomingDir) {
		switch(type) {
		case NW:
			return row*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case NE:
			return row*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case SE:
			return lowerRow*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case SW:
			return lowerRow*Board.GRIDSIZE + Board.GRIDSIZE/2;
		}
		return -1;
	}

	@Override
	public int colsOccupied() {
		return 2;
	}
	
	@Override
	public int rowsOccupied() {
		return 2;
	}

	@Override
	public boolean destroyAfterHit() {
		return false;
	}

	@Override
	public String getImgURL() {
		return String.format("file:resources/Rotator%s.png", type.name());
	}

	@Override
	public Obstacle copy() {
		return new Rotator(row, col, init);
	}
	
	public boolean canClick() {
		return false;
	}
	
	public void change() {
		rotateClockwise();
	}
	
	private void rotateClockwise() {
		switch(type) {
		case NE:
			type = Type.SE;
			break;
		case NW:
			type = Type.NE;
			break;
		case SE:
			type = Type.SW;
			break;
		case SW:
			type = Type.NW;
			break;
		}
	}
	
	private void rotateAntiClockwise() {
		switch(type) {
		case NE:
			type = Type.NW;
			break;
		case NW:
			type = Type.SW;
			break;
		case SE:
			type = Type.NE;
			break;
		case SW:
			type = Type.SE;
			break;
		}
	}

	@Override
	public Method getDirChange(Dir incomingDir) {
		Method[] methods = this.getDirMethods(incomingDir);
		Method clockwise = methods[0];
		Method anticlockwise = methods[1];
		
		if((type == Type.NW && incomingDir == Dir.RIGHT) ||
				(type == Type.NE && incomingDir == Dir.DOWN) ||
				(type == Type.SE && incomingDir == Dir.LEFT) ||
				(type == Type.SW && incomingDir == Dir.UP)) {
			this.rotateClockwise();
			return clockwise;
		} else if((type == Type.NW && incomingDir == Dir.LEFT) ||
				(type == Type.NE && incomingDir == Dir.UP) ||
				(type == Type.SE && incomingDir == Dir.RIGHT) ||
				(type == Type.SW && incomingDir == Dir.DOWN)) {
			this.rotateAntiClockwise();
			return clockwise;
		} else if((type == Type.NW && incomingDir == Dir.UP) || 
				(type == Type.NE && incomingDir == Dir.RIGHT) ||
				(type == Type.SE && incomingDir == Dir.DOWN) ||
				(type == Type.SW && incomingDir == Dir.LEFT)) {
			this.rotateClockwise();
			return anticlockwise;
		} else if((type == Type.NW && incomingDir == Dir.DOWN) ||
				(type == Type.NE && incomingDir == Dir.LEFT) ||
				(type == Type.SE && incomingDir == Dir.UP) ||
				(type == Type.SW && incomingDir == Dir.RIGHT)) {
			this.rotateAntiClockwise();
			return anticlockwise;
		}
		
		return null; // should never get here
	}
	
	public String toString() {
		return String.format("ROTATOR_%s", type.name());
	}

}
