package application;


import data.UserIO;
import javafx.application.Application;
import javafx.stage.Stage;
import util.SharedElements;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		UserIO uio = new UserIO();
		if(uio.isFirstTime() || uio.getAdminsCount() == 0) {
			SharedElements.initialView(uio);
			uio.update();
			LoginStage lgs = new LoginStage();
			lgs.view(primaryStage, uio);
		}else {
			LoginStage lgs = new LoginStage();
			lgs.view(primaryStage, uio);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
