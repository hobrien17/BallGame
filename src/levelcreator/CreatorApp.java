package levelcreator;

import javafx.application.Application;
import javafx.stage.Stage;

public class CreatorApp extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		CreatorView view = new CreatorView();
		CreatorController cont = new CreatorController(view);
		
		stage.setScene(view.getScene());
		stage.setTitle("Level creator");
		stage.setResizable(false);
		stage.show();
	}

}
