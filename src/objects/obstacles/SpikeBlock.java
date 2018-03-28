package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball.Dir;

public class SpikeBlock {
	/*private final static int GRIDSIZE = 65;
	
	private int row;
	private int col;
	
	public SpikeBlock(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCol() {
		return col;
	}

	@Override
	public int getXPos() {
		return col*GRIDSIZE;
	}

	@Override
	public int getYPos() {
		return row*GRIDSIZE;
	}

	@Override
	public int[] getXHit(Dir incomingDir) {
		switch(incomingDir) {
		case RIGHT:
			int[] arr1 = {col*GRIDSIZE};
			return arr1;
		case LEFT:
			int[] arr2 = {col*GRIDSIZE + GRIDSIZE - 1};
			return arr2;
		default:
			int[] arr3 = {col*GRIDSIZE + GRIDSIZE/2};
			return arr3;
		}
	}

	@Override
	public int getYHit(Dir incomingDir) {
		switch(incomingDir) {
		case DOWN:
			return row*GRIDSIZE;
		case UP:
			return row*GRIDSIZE + GRIDSIZE - 1;
		default:
			return row*GRIDSIZE + GRIDSIZE/2;
		}
	}

	@Override
	public int colsOccupied() {
		return 1;
	}

	@Override
	public void change() {}

	@Override
	public Method getDirChange(Dir incomingDir) {
		throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public String getImgURL() {
		return "file:resources/SpikeBlock.png";
	}

	@Override
	public Obstacle copy() {
		return new SpikeBlock(row, col);
	}*/

}
