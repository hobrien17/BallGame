package levelcreator;

import java.awt.MouseInfo;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import objects.Board;
import objects.obstacles.Deflector;
import objects.obstacles.DoubleTarget;
import objects.obstacles.LeftDoubleDeflector;
import objects.obstacles.Obstacle;
import objects.obstacles.RightDoubleDeflector;
import objects.obstacles.Target;

public class CreatorController {
	
	private CreatorView view;
	private Obstacle selected;
	private Obstacle[][] placed;
	private int mouseX;
	private int mouseY;
	
	public CreatorController(CreatorView view) {
		this.view = view;
		mouseX = 0;
		mouseY = 0;
		
		init();
		
		view.addPressHandler(new KeyPressActionHandler());
		view.addMoveHandler(new MoveActionHandler());
		view.addClickHandler(new ClickActionHandler());
	}
	
	private void init() {
		placed = new Obstacle[Board.COLS][Board.ROWS];
		for(int i = 0; i < Board.COLS; i++) {
			for(int j = 0; j < Board.ROWS; j++) {
				placed[i][j] = null;
			}
		}
	}
	
	public Obstacle[][] getPlaced() {
		return placed;
	}
	
	public boolean showGenConfirmation() {
		return view.showConfirmation("Generating JSON...", "Click OK to proceed");
	}
	
	public boolean showClearConfirmation() {
		return view.showConfirmation("Are you sure you want to reset?", "");
	}
	
	public void showGenEnd() {
		view.showInfo("JSON successfully generated!", "JSON located in genLevel.json");
	}
	
	public void showError(String msg) {
		view.showError("Oops, something went wrong", msg);
	}
	
	public int getTime() {
		String result = view.showInputBox("0", "Enter countdown time", "");
		if(result == null) {
			return -1;
		}
		try {
			int time = Integer.parseInt(result);
			if(time < 0) {
				this.showError("Please enter a positive integer");
				return getTime();
			}
			return time;
		} catch (NumberFormatException ex) {
			this.showError("Please enter a valid integer");
			return getTime();
		}
	}
		
	private class MoveActionHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			mouseX = (int)event.getSceneX();
			mouseY = (int)event.getSceneY();
			if(selected != null) {
				view.drawObs(selected, mouseX, mouseY);
			}
		}
	}
	
	private class ClickActionHandler implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			if(event.isAltDown()) {
				Obstacle obs;
				if((obs = placed[view.getCell((int)event.getSceneX())]
						[view.getCell((int)event.getSceneY())]) != null) {
					placed[view.getCell((int)event.getSceneX())]
							[view.getCell((int)event.getSceneY())] = null;
					for(int i = 0; i < obs.colsOccupied(); i++) {
						view.clearPlacedObs(view.getCell((int)event.getSceneX() + i), 
								view.getCell((int)event.getSceneY()));
					}
				}
				
				
			} else if(selected != null) {
				placed[view.getCell((int)event.getSceneX())]
						[view.getCell((int)event.getSceneY())] = selected;
				view.placeObs(selected, (int)event.getSceneX(), (int)event.getSceneY());
				selected = null;
			}
		}
		
	}
	
	private class KeyPressActionHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent event) {
			System.out.println("press!");
			
			switch(event.getCode()) {
			case DIGIT1:
				selected = new Target(-1, -1);
				break;
			case DIGIT2:
				selected = new DoubleTarget(-1, -1);
				break;
			case DIGIT3:
				selected = new Deflector(-1, -1, Deflector.Type.LEFT);
				break;
			case DIGIT4:
				selected = new LeftDoubleDeflector(-1, -1, LeftDoubleDeflector.Type.LEFT);
				break;
			case DIGIT5:
				selected = new RightDoubleDeflector(-1, -1, RightDoubleDeflector.Type.LEFT);
				break;
			case S:
				selected = new BallStart(-1, -1);
				break;
			case R:
				if(selected != null) {
					selected.change();
				}
				break;
			case ESCAPE:
				selected = null;
				view.clearObs();
				break;
			case G:
				Generator.generate(CreatorController.this);
				break;
			case C:
				if(showClearConfirmation()) {
					selected = null;
					init();
					view.draw();
				}
			default:
				break;
			}
			if(selected != null) {
				view.drawObs(selected, mouseX, mouseY);
			}
		}
	}
	
}