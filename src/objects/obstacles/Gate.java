package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball.Dir;

public class Gate {
	/*private final static int GRIDSIZE = 65;
	
	int row;
	int col;
	
	public enum Type {
		OPEN,
		CLOSED
	}
	
	Type face;
	Type init;
	
	public Gate(int row, int col, Type face) {
		this.row = row;
		this.col = col;
		this.face = face;
		this.init = face;
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
		return col * GRIDSIZE;
	}

	@Override
	public int getYPos() {
		return row * GRIDSIZE;
	}

	@Override
	public int[] getXHit(Dir incomingDir) {
		int[] arr = new int[3];
		switch(incomingDir) {
		case RIGHT:
			arr[0] = col*GRIDSIZE;
			arr[1] = -1;
			arr[2] = -1;
			break;
		case LEFT:
			arr[0] = col*GRIDSIZE + GRIDSIZE - 1;
			arr[1] = -1;
			arr[2] = -1;
			break;
		default:
			arr[0] = col*GRIDSIZE + GRIDSIZE/2;
			arr[2] = col*GRIDSIZE + 2*GRIDSIZE + GRIDSIZE/2;
			switch(face) {
			case OPEN:
				arr[1] = -1;
				break;
			case CLOSED:
				arr[1] = col*GRIDSIZE + GRIDSIZE + GRIDSIZE/2;
				System.out.println("Gate: " + arr[1]);
				break;
			}
			break;
		}
		return arr;
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
		return 3;
	}

	@Override
	public void change() {}

	@Override
	public Method getDirChange(Dir incomingDir) {
		throw new ArrayIndexOutOfBoundsException();
	}

	@Override
	public String getImgURL() {
		switch(face) {
		case OPEN:
			return "file:resources/SpikeGateOpen.png";
		case CLOSED:
			return "file:resources/SpikeGateClosed.png";
		}
		return null;
	}

	@Override
	public Obstacle copy() {
		return new Gate(row, col, init);
	}

	@Override
	public void switchChange() {
		switch(face) {
		case OPEN:
			face = Type.CLOSED;
			break;
		case CLOSED:
			face = Type.OPEN;
			break;
		}

	}*/

}
