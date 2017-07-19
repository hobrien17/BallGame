package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;

public interface Obstacle {
	public int getRow();
	public int getCol();
	
	public int getXPos();
	public int getYPos();
	public int getXHit(Ball.Dir incomingDir);
	public int getYHit(Ball.Dir incomingDir);
	
	public int colsOccupied();
	
	public void change();
	
	public Method getDirChange(Ball.Dir incomingDir);
	
	public String getImgURL();
	
	public Obstacle copy();
}
