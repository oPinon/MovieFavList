package application;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class IHM extends Application{
	public void start(final Stage stage) {
		final BorderPane pane = new BorderPane();
		pane.setId("mainPane");

		final Scene scene = new Scene(pane,600,600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		final ScrollPane sp = new ScrollPane();
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setFitToWidth(true);
		sp.setFitToHeight(false);
		sp.prefWidthProperty().bind(scene.widthProperty());
		sp.setId("scrollPane");

		final MoviePane mp = new MoviePane();
		sp.setContent(mp);

		final Label placeholder = new Label("No movie currently in the list");
		placeholder.setAlignment(Pos.CENTER_RIGHT);
		ObjectBinding<Node> b = new ObjectBinding<Node>() {
			{
				bind(mp.emptyProperty);
			}

			@Override
			protected Node computeValue() {
				if(!mp.emptyProperty.getValue()) return sp;
				else return placeholder;
			}
		};
		pane.centerProperty().bind(b);

		HBox box = new HBox(3);
		Button load = new Button("Load");
		load.setGraphic(new ImageView(new Image("file:images/load-32.png")));
		load.setContentDisplay(ContentDisplay.TOP);
		load.setMaxHeight(Integer.MAX_VALUE);
		load.setMaxWidth(Integer.MAX_VALUE);
		//	load.prefWidthProperty().bind(scene.widthProperty().divide(3));
		load.getStyleClass().add("circleButton");
		load.setTooltip(new Tooltip("Load the list"));
		load.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				try {
					BufferedReader br = new BufferedReader(new FileReader("favs.txt"));
					String id;
					String comments = null;
					String line = br.readLine();

					while (line != null && line.equals("#")) {
						line = br.readLine();
						id = line;
						line = br.readLine();
						comments = line;
						mp.add(new Movie(Integer.parseInt(id), comments!=null ? comments.replace("\\n", "\n") : null));
						line = br.readLine();
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Button save = new Button("Save");
		save.setGraphic(new ImageView(new Image("file:images/save-32.png")));
		save.setContentDisplay(ContentDisplay.TOP);
		save.setMaxHeight(Integer.MAX_VALUE);
		save.setMaxWidth(Integer.MAX_VALUE);
		//	save.prefWidthProperty().bind(scene.widthProperty().divide(3));
		save.getStyleClass().add("circleButton");
		save.setTooltip(new Tooltip("Save the list"));
		save.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {  
				try {
					StringBuilder builder = new StringBuilder();
					BufferedWriter writer = new BufferedWriter( new FileWriter( "favs.txt"));

					for(Movie m: mp.getMovies()){
						builder.append("#\n");
						builder.append(m.id);
						builder.append("\n");
						builder.append(m.comments.getValue() != null ? m.comments.getValue().replace("\n", "\\n") : m.comments.getValue());
						builder.append("\n");
					}
					writer.write(builder.toString());
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		Button add = new Button("Add");
		add.setGraphic(new ImageView(new Image("file:images/video-add-48.png")));
		add.setContentDisplay(ContentDisplay.TOP);
		add.setMaxHeight(Integer.MAX_VALUE);
		add.setMaxWidth(Integer.MAX_VALUE);
		//add.prefWidthProperty().bind(scene.widthProperty().divide(3));
		add.getStyleClass().add("bigCircleButton");
		add.setTooltip(new Tooltip("Add new movies to the list"));
		add.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				MovieSearchIHM ms = new MovieSearchIHM(mp);
				ms.searchMovies();
			}
		});
		
		Button titleFilter = new Button("Title");
		titleFilter.setTooltip(new Tooltip("Sort the movies by their Title name"));
		titleFilter.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Node> sortedList = new ArrayList<Node>(mp.getChildren());
				Collections.sort(sortedList, new TitleComparator());
				mp.getChildren().clear();
				mp.getChildren().addAll(sortedList);
			}
		});
		Button releaseFilter = new Button("Release");
		releaseFilter.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Node> sortedList = new ArrayList<Node>(mp.getChildren());
				Collections.sort(sortedList, new ReleaseComparator());
				mp.getChildren().clear();
				mp.getChildren().addAll(sortedList);
			}
		});
		Button ratingFilter = new Button("Rating");
		ratingFilter.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				ArrayList<Node> sortedList = new ArrayList<Node>(mp.getChildren());
				Collections.sort(sortedList, new RatingComparator());
				mp.getChildren().clear();
				mp.getChildren().addAll(sortedList);
			}
		});

		box.getChildren().addAll(titleFilter,releaseFilter,ratingFilter,load,save,add);
		box.setAlignment(Pos.CENTER_RIGHT);
		pane.setTop(box);

		stage.setScene(scene);
		stage.setTitle("Movie List");
		stage.setMinHeight(250);
		stage.setMinWidth(350);
		stage.show();

	}
	
	public class TitleComparator implements Comparator<Node> {
		@Override
		public int compare(Node arg0, Node arg1) {
			MovieTile m1 = (MovieTile) arg0;
			MovieTile m2 = (MovieTile) arg1;
			return m1.movie.title.compareToIgnoreCase(m2.movie.title);
		}
	}
	public class ReleaseComparator implements Comparator<Node> {
		@Override
		public int compare(Node arg0, Node arg1) {
			MovieTile m1 = (MovieTile) arg0;
			MovieTile m2 = (MovieTile) arg1;
			return -m1.movie.releaseDate.compareToIgnoreCase(m2.movie.releaseDate);
		}
	}
	public class RatingComparator implements Comparator<Node> {
		@Override
		public int compare(Node arg0, Node arg1) {
			MovieTile m1 = (MovieTile) arg0;
			MovieTile m2 = (MovieTile) arg1;
			return -Double.compare(m1.movie.rating,m2.movie.rating);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
