package leveldata;

import java.util.ArrayList;
import java.util.List;

import objects.obstacles.Bouncer;
import objects.obstacles.Deflector;
import objects.obstacles.DoubleDeflector;
import objects.obstacles.DoubleTarget;
import objects.obstacles.Obstacle;
import objects.obstacles.RedSwitch;
import objects.obstacles.SpikeBlock;
import objects.obstacles.Switch;
import objects.obstacles.SwitchedDoubleDeflector;
import objects.obstacles.Target;

public class Level {
	private int levelNum;
	private int ballStartR;
	private int time;
	private Tutorial tute;
	private List<Obstacle> obstacles;
	
	public Level(int levelNum, int ballStartR, int time, Tutorial tute, String... obstacles) {
		this.levelNum = levelNum;
		this.ballStartR = ballStartR;
		this.time = time;
		this.tute = tute;
		this.obstacles = new ArrayList<>();
		processObstacles(obstacles);
	}
	
	public Level(Level lvl) {
		this.levelNum = lvl.levelNum;
		this.ballStartR = lvl.ballStartR;
		this.time = lvl.time;
		this.tute = lvl.tute;
		this.obstacles = new ArrayList<>();
		for(Obstacle o : lvl.obstacles) {
			obstacles.add(o.copy());
		}
	}
	
	private void processObstacles(String[] obstacles) {
		for(String o : obstacles) {
			String[] spl = o.split(": ");
			String name = spl[0];
			int oCol = Integer.parseInt(spl[1].split(", ")[0]);
			int oRow = Integer.parseInt(spl[1].split(", ")[1]);
			this.obstacles.add(selectObstacle(name.toUpperCase(), oRow, oCol));
		}
	}
	
	private Obstacle selectObstacle(String name, int oRow, int oCol) {
		switch(name) {
		case "DEFLECTOR_L":
			return new Deflector(oRow, oCol, Deflector.Type.LEFT);
		case "DEFLECTOR_R":
			return new Deflector(oRow, oCol, Deflector.Type.RIGHT);
		case "DEFLECTOR_RR":
			return new DoubleDeflector(oRow, oCol, DoubleDeflector.Type.RIGHT_RIGHT);
		case "DEFLECTOR_RL":
			return new DoubleDeflector(oRow, oCol, DoubleDeflector.Type.RIGHT_LEFT);
		case "DEFLECTOR_LL":
			return new DoubleDeflector(oRow, oCol, DoubleDeflector.Type.LEFT_LEFT);
		case "DEFLECTOR_LR":
			return new DoubleDeflector(oRow, oCol, DoubleDeflector.Type.LEFT_RIGHT);
		case "TARGET":
			return new Target(oRow, oCol);
		case "BOUNCER_LR":
			return new Bouncer(oRow, oCol, Bouncer.Type.LR);
		case "BOUNCER_UD":
			return new Bouncer(oRow, oCol, Bouncer.Type.UD);
		case "DOUBLETARGET":
			return new DoubleTarget(oRow, oCol);
		case "SPIKEBLOCK":
			return new SpikeBlock(oRow, oCol);
		case "SWITCH":
			return new Switch(oRow, oCol);
		case "REDSWITCH":
			return new RedSwitch(oRow, oCol);
		case "REMOTEDEFLECTOR_RL":
			return new SwitchedDoubleDeflector(oRow, oCol, DoubleDeflector.Type.RIGHT_LEFT);
		case "REMOTEDEFLECTOR_LL":
			return new SwitchedDoubleDeflector(oRow, oCol, DoubleDeflector.Type.LEFT_LEFT);
		default:
			throw new IllegalArgumentException("Obstacle " + name + " does not exist");
		}
	}
	
	public List<Obstacle> getObstacles() {
		return new ArrayList<Obstacle>(obstacles);
	}
	
	public int getNum() {
		return levelNum;
	}
	
	public int getBallStart() {
		return ballStartR;
	}
	
	public int getTime() {
		return time;
	}
	
	public boolean hasTutorial() {
		System.out.println(tute);
		return tute != null;
	}
	
	public Tutorial getTutorial() {
		return tute;
	}
}
