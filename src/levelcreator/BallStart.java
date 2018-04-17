package levelcreator;

import java.lang.reflect.Method;

import objects.Ball.Dir;
import objects.obstacles.Obstacle;

public class BallStart extends Obstacle {

	public BallStart(int row, int col) {
		super(row, col);
	}

	@Override
	public int getXHit(Dir incomingDir) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int getYHit(Dir incomingDir) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public int colsOccupied() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	@Override
	public int rowsOccupied() {
		return 1;
	}

	@Override
	public boolean destroyAfterHit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean canClick() {
		return false;
	}

	@Override
	public String getImgURL() {
		// TODO Auto-generated method stub
		return "file:resources/Arrow.png";
	}

	@Override
	public Obstacle copy() {
		// TODO Auto-generated method stub
		return new BallStart(row, col);
	}

	@Override
	public Method getDirChange(Dir incomingDir) {
		// TODO Auto-generated method stub
		return null;
	}

}
