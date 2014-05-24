package application;

import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MovieSearchIHM {
	MoviePane list;

	public MovieSearchIHM(MoviePane main) {
		this.list = main;
	}

	public void searchMovies(){

		final Stage stage = new Stage();
		BorderPane pane = new BorderPane();	
		pane.setId("searchPane");

		Scene scene = new Scene(pane,600,600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		final ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setFitToWidth(true);
		sp.setFitToHeight(false);
		sp.prefWidthProperty().bind(scene.widthProperty());
		sp.setId("scrollPane");

		final MoviePreviewPane ml = new MoviePreviewPane(list);
		sp.setContent(ml);

		HBox sbox = new HBox(2);
    	final SearchBox searchField = new SearchBox();
    	Button searchButton = new Button("Search");   
    	searchButton.setGraphic(new ImageView(new Image("file:images/search-2-32.png")));
		searchButton.setPrefWidth(100);
		searchButton.setMaxHeight(Integer.MAX_VALUE);
		searchButton.setMaxWidth(Integer.MAX_VALUE);
        searchButton.getStyleClass().add("circleButton");
        
        EventHandler<ActionEvent> searchEvent = new EventHandler<ActionEvent>(){
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
		};
        
		searchField.getTextField().setOnAction(searchEvent);
		searchButton.setOnAction(searchEvent);
		
		Button closeButton = new Button("Close");
		closeButton.setGraphic(new ImageView(new Image("file:images/close-window-32.png")));
		closeButton.setPrefWidth(100);
		closeButton.setMaxHeight(Integer.MAX_VALUE);
		closeButton.setMaxWidth(Integer.MAX_VALUE);
        closeButton.getStyleClass().add("circleButton");
		closeButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				stage.close();
			}
		});
    	searchField.prefWidthProperty().bind(scene.widthProperty().subtract(searchButton.prefWidthProperty()).subtract(closeButton.prefWidthProperty()));
		sbox.getChildren().addAll(searchField, searchButton, closeButton);	
		
		pane.setTop(sbox);
		pane.setCenter(sp);

		stage.setScene(scene);
		stage.setTitle("Movie Search");
		stage.setMinHeight(250);
		stage.setMinWidth(300);
		stage.show();

	}

}
