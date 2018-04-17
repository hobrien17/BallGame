package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;
import objects.Ball.Dir;

public class LeftVDeflector extends VDeflector {

	public LeftVDeflector(int row, int col, Type type) {
		super(row, col, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getImgURL() {
		switch(type) {
		case UP:
			return "file:resources/DeflectorLU.png";
		case DOWN:
			return "file:resources/DeflectorLD.png";
		}
		return null;
	}

	@Override
	public Obstacle copy() {
		return new LeftVDeflector(row, col, init);
	}

	@Override
	public Method getDirChange(Dir incomingDir) {
		Method[] methods = this.getDirMethods(incomingDir);
		Method clockwise = methods[0];
		Method anticlockwise = methods[1];
		
		if(incomingDir.equals(Ball.Dir.LEFT) || incomingDir.equals(Ball.Dir.RIGHT)) {
			return clockwise;
		} else{
			return anticlockwise;
		}
	}
	
	public String toString() {
		switch(type) {
		case UP:
			return "DEFLECTOR_LU";
		case DOWN:
			return "DEFLECTOR_LD";
		}
		return null;
	}

}
