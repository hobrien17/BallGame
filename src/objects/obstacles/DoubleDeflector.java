package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;
import objects.Board;

public abstract class DoubleDeflector extends Obstacle {
	
	protected int rightCol;

	public DoubleDeflector(int row, int leftCol, Type type) {
		super(row, leftCol);
		this.rightCol = leftCol + 1;
		this.init = type;
		this.type = type;
	}

	@Override
	public int getXHit(Ball.Dir incomingDir) {
		switch(type) {
		case LEFT:
			return col*Board.GRIDSIZE + Board.GRIDSIZE/2;
		case RIGHT:
			return rightCol*Board.GRIDSIZE + Board.GRIDSIZE/2;
		}
		return -1;
	}

	@Override
	public int getYHit(Ball.Dir incomingDir) {
		return row*Board.GRIDSIZE + Board.GRIDSIZE/2;
	}

	@Override
	public int colsOccupied() {
		return 2;
	}
	
	public int rowsOccupied() {
		return 1;
	}

	@Override
	public boolean destroyAfterHit() {
		return false;
	}
	
	public boolean canClick() {
		return true;
	}
}
