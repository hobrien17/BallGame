package leveldata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import objects.obstacles.*;

public class Level {

	private int lno;
	private int start; //ball starting row
	private int time;
	private String tute;
	private List<Obstacle> obstacles;
	
	public Level(int lno, int start, int time, String tute, List<Obstacle> obstacles) {
		this.lno = lno;
		this.start = start;
		this.time = time;
		this.tute = tute;
		this.obstacles = obstacles;
	}
	
	public List<Obstacle> getObstacles() {
		return new ArrayList<Obstacle>(obstacles);
	}
	
	public int getNum() {
		return lno;
	}
	
	public int getBallStart() {
		return start;
	}
	
	public int getTime() {
		return time;
	}
	
	public boolean hasTutorial() {
		System.out.println(tute);
		return tute != null;
	}
	
	public Tutorial getTutorial() {
		return null; //TODO: implement
	}
	
	private static Obstacle convertObstacle(JsonObject obs) {
		int row = obs.get("row").getAsInt();
		int col = obs.get("col").getAsInt();
		
		String type = obs.get("type").getAsString();
		switch(type) {
		case "DEFLECTOR_L":
			return new Deflector(row, col, Deflector.Type.LEFT);
		case "DEFLECTOR_R":
			return new Deflector(row, col, Deflector.Type.RIGHT);
		case "DEFLECTOR_RR":
			return new RightDoubleDeflector(row, col, DoubleDeflector.Type.RIGHT);
		case "DEFLECTOR_RL":
			return new RightDoubleDeflector(row, col, DoubleDeflector.Type.LEFT);
		case "DEFLECTOR_LL":
			return new LeftDoubleDeflector(row, col, DoubleDeflector.Type.LEFT);
		case "DEFLECTOR_LR":
			return new LeftDoubleDeflector(row, col, DoubleDeflector.Type.RIGHT);
		case "DEFLECTOR_RU":
			return new RightVDeflector(row, col, VDeflector.Type.UP);
		case "DEFLECTOR_RD":
			return new RightVDeflector(row, col, VDeflector.Type.DOWN);
		case "DEFLECTOR_LU":
			return new LeftVDeflector(row, col, VDeflector.Type.UP);
		case "DEFLECTOR_LD":
			return new LeftVDeflector(row, col, VDeflector.Type.DOWN);
		case "BOUNCER_LR":
			return new Bouncer(row, col, Bouncer.Type.LR);
		case "BOUNCER_UD":
			return new Bouncer(row, col, Bouncer.Type.UD);
		case "TARGET":
			return new Target(row, col);
		case "ROTATOR_NW":
			return new Rotator(row, col, Rotator.Type.NW);
		case "ROTATOR_NE":
			return new Rotator(row, col, Rotator.Type.NE);
		case "ROTATOR_SE":
			return new Rotator(row, col, Rotator.Type.SE);
		case "ROTATOR_SW":
			return new Rotator(row, col, Rotator.Type.SW);
		case "SPIKEBLOCK":
			return new SpikeBlock(row, col);
		/*case "SPIKEBLOCK":
			return new SpikeBlock(oRow, oCol);
		case "SWITCH":
			return new Switch(oRow, oCol);
		case "REDSWITCH":
			return new RedSwitch(oRow, oCol);
		case "REMOTEDEFLECTOR_RL":
			return new SwitchedDoubleDeflector(oRow, oCol, DoubleDeflector.Type.RIGHT_LEFT);
		case "REMOTEDEFLECTOR_LL":
			return new SwitchedDoubleDeflector(oRow, oCol, DoubleDeflector.Type.LEFT_LEFT);
		case "GATE":
			return new Gate(oRow, oCol, Gate.Type.CLOSED);*/
		default:
			if(type.startsWith("TARGET:")) {
				String[] spl = type.split(":");
				int hits = Integer.parseInt(spl[1]);
				return new Target(row, col, hits);
			}
			throw new IllegalArgumentException("Obstacle " + obs.get("type").getAsString() + " does not exist");
		}
	}
	
	public static List<Level> getLevels(String file) {
		List<Level> result = new ArrayList<>();
		JsonParser parser = new JsonParser();
		try {
			JsonArray all = parser.parse(new FileReader(file)).getAsJsonArray();
			for(JsonElement elem : all) {
				JsonObject level = elem.getAsJsonObject();
				JsonArray ob = level.get("data").getAsJsonArray();
				List<Obstacle> obs = new ArrayList<>();
				for(JsonElement o : ob) {
					obs.add(Level.convertObstacle(o.getAsJsonObject()));
				}
				result.add(new Level(level.get("lno").getAsInt(),
									 level.get("start").getAsInt(),
									 level.get("time").getAsInt(),
									 level.get("tute").getAsString(),
									 obs));
			}
		} catch (JsonIOException | JsonSyntaxException ex) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException("Corrupt level file");
		} catch (FileNotFoundException ex) {
			throw new IllegalArgumentException("Level file not found");
		}
		return result;
	}
}
