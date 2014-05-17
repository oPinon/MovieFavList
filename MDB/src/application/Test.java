package application;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

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
