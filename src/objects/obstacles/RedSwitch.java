package objects.obstacles;

public class RedSwitch extends Switch {
	public RedSwitch(int row, int col) {
		super(row, col);
	}
	
	public String getImgURL() {
		switch(face) {
		case GREEN:
			return "file:resources/RedSwitchGreen.png";
		case BLUE:
			return "file:resources/RedSwitchBlue.png";
		default:
			return null;
		}
	}
	
	public Obstacle copy() {
		return new RedSwitch(row, col);
	}
}
