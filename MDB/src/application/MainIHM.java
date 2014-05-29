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

import movie.Movie;
import movie.MovieLoader;
import utils.DirectorComparator;
import utils.InternetRatingComparator;
import utils.RatingComparator;
import utils.ReleaseComparator;
import utils.TitleComparator;
import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class IHM extends Application{
	private MovieMainPane mp;

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

		mp = new MovieMainPane();
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

		VBox topBox = new VBox(2);
		HBox menuHBox = new HBox(2);
		topBox.setId("optionBar");

		HBox optionsBox = new HBox(3);
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
				load();
			}
		});

		final Button save = new Button("Save");	
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
				save();
			}
		});

		Button add = new Button("Add");
		add.setGraphic(new ImageView(new Image("file:images/video-add-48.png")));
		add.setContentDisplay(ContentDisplay.TOP);
		add.setMaxHeight(Integer.MAX_VALUE);
		add.setMaxWidth(Integer.MAX_VALUE);
		//add.prefWidthProperty().bind(scene.widthProperty().divide(3));
		add.getStyleClass().add("circleButton");
		add.setTooltip(new Tooltip("Add new movies to the list"));
		add.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				MovieSearchIHM ms = new MovieSearchIHM(mp);
				ms.searchMovies();
			}
		});

		HBox sortBox = new HBox(4);
		Label sortLabel = new Label("Sort by:  ");
		//	sortLabel.setGraphic(new ImageView(new Image("file:images/generic-sorting-32.png")));
		//	sortLabel.setContentDisplay(ContentDisplay.TOP);	
		final ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList(
				"Title", "Director", "Release date", "Personal rating", "Internet rating")
				);		
		cb.getSelectionModel().select(0);

		Button sortAscendingButton = new Button();
		sortAscendingButton.setGraphic(new ImageView(new Image("file:images/generic-sorting-2-32.png")));
		sortAscendingButton.getStyleClass().add("sortButton");
		sortAscendingButton.setTooltip(new Tooltip("Sort the movies in ascending order"));
		sortAscendingButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {	
				sort(cb.getSelectionModel().getSelectedIndex(),true);
			}
		});
		Button sortDescendingButton = new Button();
		sortDescendingButton.setGraphic(new ImageView(new Image("file:images/generic-sorting-32.png")));
		sortDescendingButton.getStyleClass().add("sortButton");
		sortDescendingButton.setTooltip(new Tooltip("Sort the movies in descending order"));
		sortDescendingButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {	
				sort(cb.getSelectionModel().getSelectedIndex(),false);
			}
		});
		sortBox.getChildren().addAll(sortLabel,cb,sortAscendingButton,sortDescendingButton);
		sortBox.setAlignment(Pos.CENTER_LEFT);
		sortBox.setTranslateX(5);

		optionsBox.getChildren().addAll(load,save,add);
		optionsBox.setAlignment(Pos.CENTER_RIGHT);
		HBox.setHgrow(sortBox, Priority.ALWAYS);
		menuHBox.getChildren().addAll(sortBox,optionsBox);

		topBox.getChildren().addAll(menuHBox,new Separator(Orientation.HORIZONTAL));
		pane.setTop(topBox);

		stage.setScene(scene);
		stage.setTitle("Movie List");
		stage.setMinHeight(350);
		stage.setMinWidth(550);
		stage.show();

		load();

	}

	private void sort(int criteriaIndex, boolean isAscending){
		ArrayList<Node> sortedList = new ArrayList<Node>(mp.getChildren());
		switch(criteriaIndex){
		case 0:			
			Collections.sort(sortedList, new TitleComparator(isAscending));
			break;
		case 1:			
			Collections.sort(sortedList, new DirectorComparator(isAscending));
			break;
		case 2:	
			Collections.sort(sortedList, new ReleaseComparator(isAscending));
			break;
		case 3:	
			Collections.sort(sortedList, new RatingComparator(isAscending));
			break;
		case 4:	
			Collections.sort(sortedList, new InternetRatingComparator(isAscending));
			break;

		}	
		mp.getChildren().clear();
		mp.getChildren().addAll(sortedList);

	}

	private void load(){
		try {

			BufferedReader br = new BufferedReader(new FileReader("favs.txt"));
			String id;
			String rating;
			String comments = null;
			String line = br.readLine();

			while (line != null && line.equals("#")) {
				line = br.readLine();
				id = line;
				line = br.readLine();
				rating = line;
				line = br.readLine();
				comments = line;
				final MovieLoader ml = new MovieLoader(Integer.parseInt(id), Integer.parseInt(rating), comments!=null ? comments.replace("\\n", "\n") : null);		
				ml.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					public void handle(WorkerStateEvent t) {
						mp.add(ml.getValue());
					}
				});
				new Thread(ml).start();
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void save(){
		try {
			StringBuilder builder = new StringBuilder();
			BufferedWriter writer = new BufferedWriter( new FileWriter( "favs.txt"));

			for(Movie m: mp.getMovies()){
				builder.append("#\n");
				builder.append(m.id);
				builder.append("\n");
				builder.append(m.rating.getValue());
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

	public static void main(String[] args) {
		launch(args);
	}
}
