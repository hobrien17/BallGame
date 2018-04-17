package objects.obstacles;

import java.lang.reflect.Method;

import objects.Ball;
import objects.Ball.Dir;

public class RightVDeflector extends VDeflector {

	public RightVDeflector(int row, int col, Type type) {
		super(row, col, type);
	}

	@Override
	public String getImgURL() {
		switch(type) {
		case UP:
			return "file:resources/DeflectorRU.png";
		case DOWN:
			return "file:resources/DeflectorRD.png";
		}
		return null;
	}

	@Override
	public Obstacle copy() {
		return new RightVDeflector(row, col, init);
	}

	@Override
	public Method getDirChange(Dir incomingDir) {
		Method[] methods = this.getDirMethods(incomingDir);
		Method clockwise = methods[0];
		Method anticlockwise = methods[1];
		
		if(incomingDir.equals(Ball.Dir.LEFT) || incomingDir.equals(Ball.Dir.RIGHT)) {
			return anticlockwise;
		} else{
			return clockwise;
		}
	}
	
	public String toString() {
		switch(type) {
		case UP:
			return "DEFLECTOR_RU";
		case DOWN:
			return "DEFLECTOR_RD";
		}
		return null;
	}

}
