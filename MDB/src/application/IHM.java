package application;


import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class IHM extends Application{
	public void start(final Stage stage) {
		BorderPane pane = new BorderPane();

		ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setFitToWidth(true);



		Scene scene = new Scene(pane,600,600);
		scene.setFill(Color.WHITE);


		Movie m1 = new Movie("The Hobbit", "12/14","Fantasy");
		Movie m2 = new Movie("Godzilla", "05/14","Action");

		TilePane tp = new TilePane();
		tp.setPrefColumns(2);

		for(int i=0;i<2;i++){
			Node n1 = new MovieTile(m1,scene);
			Node n2 = new MovieTile(m2,scene);
			tp.getChildren().add(n1);
			tp.getChildren().add(n2);
			TilePane.setAlignment(n1,Pos.TOP_LEFT);
			TilePane.setAlignment(n2,Pos.TOP_LEFT);
		}


		sp.setContent(tp);
		pane.setCenter(sp);

		HBox box = new HBox(3);
		final TextField nameField = new TextField();
		nameField.setPromptText("Name");
		nameField.prefWidthProperty().bind(scene.widthProperty().divide(3));
		final TextField dateField = new TextField();
		dateField.setPromptText("Release Date");
		dateField.prefWidthProperty().bind(scene.widthProperty().divide(3));

		Button add = new Button("Add new Movie");
		add.setMaxHeight(Integer.MAX_VALUE);
		add.setMaxWidth(Integer.MAX_VALUE);
		add.prefWidthProperty().bind(scene.widthProperty().divide(3));
		add.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
			}
		});

		box.getChildren().addAll(nameField,dateField,add);
		pane.setBottom(box);

		stage.setScene(scene);
		stage.setTitle("Movie List");
		stage.setMinHeight(250);
		stage.setMinWidth(300);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
