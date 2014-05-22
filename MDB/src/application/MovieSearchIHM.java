package application;

import java.util.List;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MovieSearchIHM {
	MoviePane list;

	public MovieSearchIHM(MoviePane main) {
		this.list=main;
	}


	public void searchMovie(){

		final Stage stage = new Stage();
		BorderPane pane = new BorderPane();	

		Scene scene = new Scene(pane,600,600);
		scene.setFill(Color.GRAY);
		
		pane.getStylesheets().add(IHM.class.getResource("application.css").toExternalForm());
		pane.getStylesheets().add(IHM.class.getResource("SearchBox.css").toExternalForm());

		final ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		sp.setFitToWidth(true);
		sp.setFitToHeight(false);
		sp.prefWidthProperty().bind(scene.widthProperty());
		sp.setId("searchPane");

		final MoviePreviewPane ml = new MoviePreviewPane(list);
		sp.setContent(ml);

		HBox sbox = new HBox(2);
    	final SearchBox searchField=new SearchBox();
    	searchField.prefWidthProperty().bind(scene.widthProperty().divide(1));	  	
    	Button searchButton = new Button("Search");
		searchButton.setMaxHeight(Integer.MAX_VALUE);
		searchButton.setMaxWidth(Integer.MAX_VALUE);
		searchButton.setPrefWidth(200);
		searchButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Searcher searcher = new Searcher();
				ml.clear();

				String criteria = searchField.getText();
				if(criteria.length()==0) return;

				List<Map<String, Object>> results = searcher.searchMovie(criteria.replace(" ","+"));
				for(Map<String, Object> entry: results){
					ml.add(new Movie(entry));
				}
			}
		});

		sbox.getChildren().addAll(searchField,searchButton);	
		
		pane.setTop(sbox);
		pane.setCenter(sp);

		HBox box = new HBox(1);
		Button closeButton = new Button("Close");
		closeButton.setMaxHeight(Integer.MAX_VALUE);
		closeButton.setMaxWidth(Integer.MAX_VALUE);
		closeButton.prefWidthProperty().bind(scene.widthProperty().divide(1));
		closeButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {


				stage.close();

			}
		});

		box.getChildren().addAll(closeButton);
		pane.setBottom(box);

		stage.setScene(scene);
		stage.setTitle("Movie Search");
		stage.setMinHeight(250);
		stage.setMinWidth(300);
		stage.show();

	}

}
