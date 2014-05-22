package application;


import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import javafx.util.Callback;

public class IHM extends Application{
	public void start(final Stage stage) {
		final BorderPane pane = new BorderPane();
				
		final Scene scene = new Scene(pane,600,600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		scene.getStylesheets().add(getClass().getResource("SearchBox.css").toExternalForm());
		//scene.setFill(Color.BLUE);
		
		final ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setFitToWidth(true);
		sp.setFitToHeight(false);
		sp.prefWidthProperty().bind(scene.widthProperty());
		sp.setId("mainPane");

		final MoviePane mp = new MoviePane();
		sp.setContent(mp);

		pane.setCenter(sp);

		HBox box = new HBox(1);

		Button add = new Button("Add new Movie");
		add.setMaxHeight(Integer.MAX_VALUE);
		add.setMaxWidth(Integer.MAX_VALUE);
		add.prefWidthProperty().bind(scene.widthProperty().divide(1));
		add.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				MovieSearchIHM ms = new MovieSearchIHM(mp);
				ms.searchMovie();
			}
		});


		box.getChildren().addAll(add);
		pane.setBottom(box);

		//tp.layout();
		mp.add(new Movie(124905));
		mp.add(new Movie(929));
		mp.add(new Movie(1678));
		mp.add(new Movie(3001));
	//	mp.updateDisplay(false);

		stage.setScene(scene);
		stage.setTitle("Movie List");
		stage.setMinHeight(250);
		stage.setMinWidth(350);
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
