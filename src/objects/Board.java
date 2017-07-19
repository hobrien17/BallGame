package objects;

import java.util.List;

import leveldata.Level;
import objects.obstacles.Obstacle;
import objects.obstacles.Target;

public class Board {
	private Obstacle[][] obstacles;
	private Level leveldata;
	
	private int targets;
	
	private final static int ROWS = 10;
	private final static int COLS = 6;
	private final static int GRIDSIZE = 65;
	
	public Board(Level leveldata) {
		this.leveldata = leveldata;
		fillBoard();
		fillObstacles();
	}
		
	public void fillBoard() {
		obstacles = new Obstacle[COLS][ROWS];
		for(int i = 0; i < COLS; i++) {
			for(int j = 0; j < ROWS; j++) {
				obstacles[i][j] = null;
			}
		}
	}
	
	public void fillObstacles() {
		targets = 0;
		List<Obstacle> lst = leveldata.getObstacles();
		for(Obstacle o : lst) {
			obstacles[o.getCol()][o.getRow()] = o;
			if(o instanceof Target) {
				targets++;
			}
		}
	}
	
	public int getTotalTargets() {
		return targets;
	}
	
	public Obstacle getObstacleAt(int x, int y) {
		Obstacle o;
		try{
			if((o = obstacles[(int)x/GRIDSIZE - 1][(int)y/GRIDSIZE]) != null && o.colsOccupied() == 2) {
				return o;
			}	
		}catch(ArrayIndexOutOfBoundsException ex) {}
		return obstacles[(int)x/GRIDSIZE][(int)y/GRIDSIZE];
	}
	
	public Obstacle[][] getObstacles() {
		return obstacles;
	}
	
	public void removeObstacle(int row, int col) {
		obstacles[col][row] = null;
	}
	
	public void replaceObstacle(Obstacle newObs, int row, int col) {
		obstacles[col][row] = newObs;
	}
	
	public static int getYFromRow(int row) {
		return row*GRIDSIZE + GRIDSIZE/2;
	}
}
