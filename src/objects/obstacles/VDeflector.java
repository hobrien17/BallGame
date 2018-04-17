package objects.obstacles;

import objects.Board;
import objects.Ball.Dir;

public abstract class VDeflector extends Obstacle {
	
	public enum Type {
		UP, DOWN
	}
	
	protected Type type;
	protected Type init;
	
	protected int lowerRow;
	
	public VDeflector(int row, int col, Type type) {
		super(row, col);
		this.lowerRow = row + 1;
		this.type = type;
		this.init = type;
	}

	@Override
	public int getXHit(Dir incomingDir) {
		return col*Board.GRIDSIZE + Board.GRIDSIZE/2;
	}

	@Override
	public int getYHit(Dir incomingDir) {
		switch(type) {
		case UP:
			return row*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case DOWN:
			return lowerRow*Board.GRIDSIZE + Board.GRIDSIZE/2;
		}
		return -1;
	}

	@Override
	public int colsOccupied() {
		return 1;
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
	public boolean canClick() {
		return true;
	}
	
	public void change() {
		switch(type) {
		case UP:
			type = Type.DOWN;
			break;
		case DOWN:
			type = Type.UP;
			break;
		}
	}

}
