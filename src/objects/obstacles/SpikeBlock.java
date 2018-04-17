package objects.obstacles;

import java.lang.reflect.Method;

import objects.Board;
import objects.Ball.Dir;

public class SpikeBlock extends Obstacle {
	
	public SpikeBlock(int row, int col) {
		super(row, col);
	}
	
	public int getXHit(Dir incomingDir) {
		switch(incomingDir) {
		case RIGHT:
			return col*Board.GRIDSIZE;
		case LEFT:
			return col*Board.GRIDSIZE + Board.GRIDSIZE - 1;
		default:
			return col*Board.GRIDSIZE + Board.GRIDSIZE/2;
		}
	}

	public int getYHit(Dir incomingDir) {
		switch(incomingDir) {
		case DOWN:
			return row*Board.GRIDSIZE;
		case UP:
			return row*Board.GRIDSIZE + Board.GRIDSIZE - 1;
		default:
			return row*Board.GRIDSIZE + Board.GRIDSIZE/2;
		}
	}

	@Override
	public int colsOccupied() {
		return 1;
	}
	
	public int rowsOccupied() {
		return 1;
	}

	@Override
	public void change() {}

	@Override
	public Method getDirChange(Dir incomingDir) {
		throw new IndexOutOfBoundsException();
	}

	@Override
	public String getImgURL() {
		return "file:resources/SpikeBlock.png";
	}

	@Override
	public Obstacle copy() {
		return new SpikeBlock(row, col);
	}

	@Override
	public boolean destroyAfterHit() {
		return false;
	}

	@Override
	public boolean canClick() {
		return false;
	}

}
