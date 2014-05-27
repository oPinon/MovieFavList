package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import movie.Movie;
import movie.MovieLoader;
import utils.ReleaseComparator;
import utils.Searcher;
import utils.TitleComparator;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieSearchIHM {
	MovieMainPane list;

	public MovieSearchIHM(MovieMainPane main) {
		this.list = main;
	}

	public void searchMovies(){
		final Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);

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

		final MoviePreviewPane mp = new MoviePreviewPane(list);
		sp.setContent(mp);

		VBox topBox = new VBox(2);
		topBox.setId("optionBar");

		HBox sbox = new HBox(2);


		final TextField searchField = new TextField();
		searchField.getStyleClass().add("searchBox");
		searchField.setMaxWidth(Integer.MAX_VALUE);
		searchField.setPromptText("Search by title");

		//	searchField.setAlignment(Pos.CENTER_LEFT);

		Button searchButton = new Button("Search");   
		searchButton.setGraphic(new ImageView(new Image("file:images/search-2-32.png")));
		searchButton.setPrefWidth(120);
		searchButton.setMaxHeight(Integer.MAX_VALUE);
		searchButton.setMaxWidth(Integer.MAX_VALUE);
		searchButton.getStyleClass().add("circleButton");

		EventHandler<ActionEvent> searchEvent = new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				Searcher searcher = new Searcher();
				mp.clear();

				String criteria = searchField.getText();
				if(criteria.length()==0) return;

				List<Map<String, Object>> results = searcher.searchMovie(criteria.replace(" ","+"));
				for(Map<String, Object> entry: results){
					final MovieLoader ml = new MovieLoader(entry);		
					ml.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						public void handle(WorkerStateEvent t) {
							mp.add(ml.getValue());
						}
					});
					new Thread(ml).start();
				}
			}
		};

		searchField.setOnAction(searchEvent);
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
		HBox.setHgrow(searchField, Priority.ALWAYS);
		searchField.setTranslateX(5);
		//searchField.setTranslateY(8);

		Separator sep = new Separator(Orientation.VERTICAL);

		VBox criteriaBox = new VBox(3);
		Label sortLabel = new Label("Sort by:");
		sortLabel.setTranslateX(5);
		Button titleFilter = new Button("Title");
		titleFilter.getStyleClass().add("sortButton");
		titleFilter.setTooltip(new Tooltip("Sort the movies by their title"));
		titleFilter.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Node> sortedList = new ArrayList<Node>(mp.getChildren());
				Collections.sort(sortedList, new TitleComparator(false));
				mp.getChildren().clear();
				mp.getChildren().addAll(sortedList);
			}
		});
		Button releaseFilter = new Button("Release date");
		releaseFilter.getStyleClass().add("sortButton");
		releaseFilter.setTooltip(new Tooltip("Sort the movies by their release date"));
		releaseFilter.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Node> sortedList = new ArrayList<Node>(mp.getChildren());
				Collections.sort(sortedList, new ReleaseComparator(false));
				mp.getChildren().clear();
				mp.getChildren().addAll(sortedList);
			}
		});
		criteriaBox.getChildren().addAll(sortLabel, titleFilter,releaseFilter);

		sbox.setAlignment(Pos.CENTER_LEFT);
		sbox.setSpacing(5);

		sbox.getChildren().addAll(criteriaBox,sep,searchField, searchButton, closeButton);	
		topBox.getChildren().addAll(sbox,new Separator(Orientation.HORIZONTAL));

		pane.setTop(topBox);
		pane.setCenter(sp);

		stage.setScene(scene);
		stage.setTitle("Movie Search");
		stage.setMinHeight(350);
		stage.setMinWidth(450);
		stage.show();

		searchField.requestFocus();

	}

}
