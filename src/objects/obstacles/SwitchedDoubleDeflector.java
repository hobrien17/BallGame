package objects.obstacles;

public class SwitchedDoubleDeflector extends DoubleDeflector implements SwitchedObstacle {
	
	public SwitchedDoubleDeflector(int row, int col, Type face) {
		super(row, col, face);
	}
	
	public void change() {}
	
	public void switchChange() {
		switch(face) {
		case RIGHT_RIGHT:
			face = Type.RIGHT_LEFT;
			break;
		case RIGHT_LEFT:
			face = Type.RIGHT_RIGHT;
			break;
		case LEFT_LEFT:
			face = Type.LEFT_RIGHT;
			break;
		case LEFT_RIGHT:
			face = Type.LEFT_LEFT;
			break;
		}
	}
	
	public String getImgURL() {
		switch(face) {
		case RIGHT_RIGHT:
			return "file:resources/RemoteDeflectorRR.png";
		case RIGHT_LEFT:
			return "file:resources/RemoteDeflectorRL.png";
		case LEFT_LEFT:
			return "file:resources/RemoteDeflectorLL.png";
		case LEFT_RIGHT:
			return "file:resources/RemoteDeflectorLR.png";
		default:
			return null;
		}
	}
	
	public Obstacle copy() {
		return new SwitchedDoubleDeflector(row, leftCol, init);
	}
}
