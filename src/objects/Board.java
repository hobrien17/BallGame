package objects;

import java.util.List;

import leveldata.Level;
import objects.obstacles.Obstacle;
import objects.obstacles.Target;

public class Board {
	private Obstacle[][] obstacles;
	private Obstacle[][] clickMap;
	private Level leveldata;
	
	private int targets;
	
	public final static int ROWS = 10;
	public final static int COLS = 10;
	public final static int GRIDSIZE = 65;
	
	public Board(Level leveldata) {
		this.leveldata = leveldata;
		fillBoard();
		fillObstacles();
	}
		
	public void fillBoard() {
		obstacles = new Obstacle[COLS][ROWS];
		clickMap = new Obstacle[COLS][ROWS];
		for(int i = 0; i < COLS; i++) {
			for(int j = 0; j < ROWS; j++) {
				obstacles[i][j] = null;
				clickMap[i][j] = null;
			}
		}
	}
	
	public void fillObstacles() {
		targets = 0;
		List<Obstacle> lst = leveldata.getObstacles();
		for(Obstacle o : lst) {
			o = o.copy();
			obstacles[o.getCol()][o.getRow()] = o;
			for(int i = 0; i < o.colsOccupied(); i++) {
				clickMap[o.getCol() + i][o.getRow()] = o;
			}
			
			if(o instanceof Target) {
				targets++;
			}
		}
	}
	
	public int getTotalTargets() {
		return targets;
	}
	
	public Obstacle getObstacleAt(int x, int y) {
		return clickMap[(int)x/GRIDSIZE][(int)y/GRIDSIZE];
	}
	
	public Obstacle[][] getObstacles() {
		return obstacles;
	}
	
	public Obstacle[][] getClickMap() {
		return clickMap;
	}
	
	public void removeObstacle(int row, int col) {
		Obstacle o = obstacles[col][row];
		obstacles[col][row] = null;
		for(int i = 0; i < o.colsOccupied(); i++) {
			clickMap[col + i][row] = null;
		}
	}
	
	public void replaceObstacle(Obstacle newObs, int row, int col) {
		removeObstacle(row, col);
		obstacles[col][row] = newObs;
		for(int i = 0; i < newObs.colsOccupied(); i++) {
			clickMap[col + i][row] = newObs;
		}
	}
	
	public static int getYFromRow(int row) {
		return row*GRIDSIZE + GRIDSIZE/2;
	}
}
