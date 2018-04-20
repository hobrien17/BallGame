package levelcreator;

import java.awt.MouseInfo;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import objects.Board;
import objects.obstacles.*;

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
						for(int j = 0; j < obs.rowsOccupied(); j++) {
							view.clearPlacedObs(view.getCell((int)event.getSceneX() + i), 
									view.getCell((int)event.getSceneY() + j));
						}
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
			case T:
				selected = new Target(-1, -1);
				break;
			case Y:
				selected = new Target(-1, -1, 2);
				break;
			case U:
				selected = new Target(-1, -1, 3);
				break;
			case DIGIT1:
				selected = new Deflector(-1, -1, Deflector.Type.LEFT);
				break;
			case DIGIT2:
				selected = new LeftDoubleDeflector(-1, -1, LeftDoubleDeflector.Type.LEFT);
				break;
			case DIGIT3:
				selected = new RightDoubleDeflector(-1, -1, RightDoubleDeflector.Type.LEFT);
				break;
			case DIGIT4:
				selected = new LeftVDeflector(-1, -1, VDeflector.Type.UP);
				break;
			case DIGIT5:
				selected = new RightVDeflector(-1, -1, VDeflector.Type.UP);
				break;
			case DIGIT6:
				selected = new Bouncer(-1, -1, Bouncer.Type.LR);
				break;
			case DIGIT7:
				selected = new Rotator(-1, -1, Rotator.Type.NW);
				break;
			case S:
				selected = new BallStart(-1, -1);
				break;
			case R:
				if(selected != null) {
					selected.change();
				}
				break;
			case Z:
				selected = new SpikeBlock(-1, -1);
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
