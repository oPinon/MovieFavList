package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Test extends Application{
	public void start(final Stage stage) {

		BorderPane pane = new BorderPane();

		Scene scene = new Scene(pane,600,600);
		//scene.setFill(Color.WHITE);
		

		ImageView imgView = new ImageView();
		imgView.setImage(new Image("file:The.jpg"));
		//imgView.setPreserveRatio(true);
		//imgView.setFitHeight(50);
		imgView.setSmooth(true);

		pane.setCenter(imgView);

		stage.setScene(scene);
		stage.setTitle("Movie List");
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
