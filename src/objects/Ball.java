package objects;

public class Ball {
	private int x;
	private int y;
	private Dir direction;
	
	private int targetsHit;
	
	private static final int SIZE = 15;
	
	public enum Dir {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	
	public Ball(int startX, int startY, Dir startDir) {
		x = startX;
		y = startY;
		direction = startDir;
		
		targetsHit = 0;
	}
	
	public Ball(int startX, int startY) {
		x = startX;
		y = startY;
		direction = Dir.RIGHT;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getCentreX() {
		return x + SIZE/2;
	}
	
	public int getCentreY() {
		return y + SIZE/2;
	}
	
	public Dir getDir() {
		return direction;
	}
	
	public void setDir(Dir dir) {
		direction = dir;
	}
	
	public void rotateClockwise() {
		switch(direction) {
		case UP:
			setDir(Dir.RIGHT);
			break;
		case DOWN:
			setDir(Dir.LEFT);
			break;
		case LEFT:
			setDir(Dir.UP);
			break;
		case RIGHT:
			setDir(Dir.DOWN);
			break;
		}
	}
	
	public void rotateCounterclockwise() {
		switch(direction) {
		case UP:
			setDir(Dir.LEFT);
			break;
		case DOWN:
			setDir(Dir.RIGHT);
			break;
		case LEFT:
			setDir(Dir.DOWN);
			break;
		case RIGHT:
			setDir(Dir.UP);
			break;
		}
	}
	
	public void rotate180() {
		switch(direction) {
		case UP:
			setDir(Dir.DOWN);
			break;
		case DOWN:
			setDir(Dir.UP);
			break;
		case LEFT:
			setDir(Dir.RIGHT);
			break;
		case RIGHT:
			setDir(Dir.LEFT);
			break;
		}
	}
	
	public void noRotate() {}
		
	public void move() {
		switch(direction) {
		case UP:
			y--;
			break;
		case DOWN:
			y++;
			break;
		case LEFT:
			x--;
			break;
		case RIGHT:
			x++;
			break;
		}
	}
	
	public static int getHalfSize() {
		return SIZE/2;
	}
	
	public int getTargetsHit() {
		return targetsHit;
	}
	
	public void incTargetsHit() {
		targetsHit++;
	}
}
