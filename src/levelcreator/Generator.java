package levelcreator;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import objects.Board;

public class Generator {
	
	public static void generate(CreatorController cont) {
		LevelObject lvl = new LevelObject();
		for(int i = 0; i < Board.COLS; i++) {
			for(int j = 0; j < Board.ROWS; j++) {
				if(cont.getPlaced()[i][j] instanceof BallStart) {
					lvl.start = j;
				} else if(cont.getPlaced()[i][j] != null) {
					ObstacleObject obs = new ObstacleObject();
					System.out.println(obs);
					obs.type = cont.getPlaced()[i][j].toString();
					obs.col = i;
					obs.row = j;
					lvl.data.add(obs);
				}
			}
		}
		
		if(lvl.start < 0) {
			cont.showError("Please add a ball start object using hotkey S");
			return;
		}
		int time = cont.getTime();
		if(time < 0) {
			return;
		}
		lvl.time = time;
		boolean confirm = cont.showGenConfirmation();
		if(!confirm) {
			return;
		}
		
		try (Writer writer = new FileWriter("genLevel.json")) {
			new Gson().toJson(lvl, writer);
			cont.showGenEnd();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	private static class ObstacleObject {
		public String type = null;
		public int row = 0;
		public int col = 0;
	}
	
	private static class LevelObject {
		public int lno = -1;
		public int start = -1;
		public int time = 0;
		public String tute = "";
		public List<ObstacleObject> data = new ArrayList<>();
	}
}
