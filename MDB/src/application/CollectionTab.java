package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import element.Movie;
import element.MovieLoader;
import element.Series;
import element.SeriesLoader;
import utils.DirectorComparator;
import utils.InternetRatingComparator;
import utils.RatingComparator;
import utils.ReleaseComparator;
import utils.TitleComparator;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CollectionTab extends BorderPane{
	Stage stage;

	MovieMainPane mp;
	SeriesMainPane sp;
	StringProperty fileName = new SimpleStringProperty();
	BooleanProperty areMoviesSelected = new SimpleBooleanProperty();

	public CollectionTab(final Stage stage) {
		super();
		this.setId("mainPane");

		this.stage = stage;

		final ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(false);
		scrollPane.setId("scrollPane");

		mp = new MovieMainPane();
		sp = new SeriesMainPane();
		scrollPane.contentProperty().bind(new When(areMoviesSelected)
		.then((TilePane) mp)
		.otherwise((TilePane) sp)
				);

		final Label placeholder = new Label("No element currently in the list");
		placeholder.setAlignment(Pos.CENTER_RIGHT);
		ObjectBinding<Node> b = new ObjectBinding<Node>() {
			{
				bind(areMoviesSelected, mp.emptyProperty, sp.emptyProperty);
			}

			@Override
			protected Node computeValue() {
				if( (areMoviesSelected.getValue() && !mp.emptyProperty.getValue()) ||
						(!areMoviesSelected.getValue() && !sp.emptyProperty.getValue()) )
					return scrollPane;
				else return placeholder;
			}
		};
		this.centerProperty().bind(b);

		ToolBar toolbar = new ToolBar();
		toolbar.setId("optionBar");

		Button load = new Button("Load");
		load.setGraphic(new ImageView(new Image("file:images/load-32.png")));
		load.setContentDisplay(ContentDisplay.TOP);
		load.setMaxHeight(Integer.MAX_VALUE);
		load.setMaxWidth(Integer.MAX_VALUE);
		load.getStyleClass().add("circleButton");
		load.setTooltip(new Tooltip("Load a list"));
		load.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Load list");
				fileChooser.setInitialDirectory( new File("lists/") ); 
				fileChooser.getExtensionFilters().add(
						new FileChooser.ExtensionFilter("TXT", "*.txt")
						);

				File file = fileChooser.showOpenDialog(stage);
				if(file != null) {
					load(file.getPath());
					fileName.setValue(file.getPath().substring(file.getPath().lastIndexOf('\\')+1));
				}
			}
		});

		final Button save = new Button("Save");	
		save.setGraphic(new ImageView(new Image("file:images/save-32.png")));
		save.setContentDisplay(ContentDisplay.TOP);
		save.setMaxHeight(Integer.MAX_VALUE);
		save.setMaxWidth(Integer.MAX_VALUE);
		save.getStyleClass().add("circleButton");
		save.setTooltip(new Tooltip("Save the list"));
		save.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {  
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save list");
				fileChooser.setInitialDirectory( new File("lists/") ); 
				fileChooser.getExtensionFilters().add(
						new FileChooser.ExtensionFilter("TXT", "*.txt")
						);

				File file = fileChooser.showSaveDialog(stage);
				if(file != null) {
					save(file.getPath());
					fileName.setValue(file.getPath().substring(file.getPath().lastIndexOf('\\')+1));
				}
			}
		});

		VBox modeBox = new VBox(2);
		ToggleButton tb1 = new ToggleButton("Movies");
		tb1.setMaxWidth(Integer.MAX_VALUE);
		tb1.prefWidthProperty().bind(modeBox.prefWidthProperty());
		ToggleButton tb2 = new ToggleButton("TV Series");
		tb2.setMaxWidth(Integer.MAX_VALUE);
		tb2.prefWidthProperty().bind(modeBox.prefWidthProperty());
		ToggleGroup group = new ToggleGroup();
		tb1.setToggleGroup(group);
		tb2.setToggleGroup(group);
		group.selectToggle(tb1);
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov,
					Toggle toggle, Toggle new_toggle) {
				if (new_toggle == null) {
					toggle.setSelected(true);                                    
				}
			}
		});

		areMoviesSelected.bind(group.selectedToggleProperty().isEqualTo(tb1));

		modeBox.getChildren().addAll(tb1,tb2);
		modeBox.setAlignment(Pos.CENTER);

		HBox sortBox = new HBox(4);
		VBox criteriaBox = new VBox(2);
		Label sortLabel = new Label("Sort by:");
		final ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList(
				"Title", "Director/Creator", "Release date", "Personal rating", "Internet rating")
				);		
		cb.getSelectionModel().select(0);
		criteriaBox.setAlignment(Pos.CENTER_LEFT);
		criteriaBox.getChildren().addAll(sortLabel,cb);

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
		sortBox.getChildren().addAll(criteriaBox,sortAscendingButton,sortDescendingButton,
				new Separator(Orientation.VERTICAL), new Label("   Show : "), modeBox);
		sortBox.setAlignment(Pos.CENTER_LEFT);
		sortBox.setTranslateX(5);
		HBox.setHgrow(sortBox, Priority.ALWAYS);

		toolbar.getItems().addAll(sortBox,load,save);
		this.setTop(toolbar);

	}

	private void sort(int criteriaIndex, boolean isAscending){
		TilePane toSort = areMoviesSelected.getValue() ? mp : sp;
		ArrayList<Node> sortedList = new ArrayList<Node>(toSort.getChildren());
		switch(criteriaIndex){
		case 0:			
			Collections.sort(sortedList, new TitleComparator(areMoviesSelected.getValue(),isAscending));
			break;
		case 1:			
			Collections.sort(sortedList, new DirectorComparator(areMoviesSelected.getValue(),isAscending));
			break;
		case 2:	
			Collections.sort(sortedList, new ReleaseComparator(areMoviesSelected.getValue(),isAscending));
			break;
		case 3:	
			Collections.sort(sortedList, new RatingComparator(areMoviesSelected.getValue(),isAscending));
			break;
		case 4:	
			Collections.sort(sortedList, new InternetRatingComparator(areMoviesSelected.getValue(),isAscending));
			break;

		}	
		toSort.getChildren().clear();
		toSort.getChildren().addAll(sortedList);


	}

	private void load(String fileName){
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String id;
			String rating;
			String comments = null;
			String line = br.readLine();

			//Loading movies
			if(!line.equals("MOVIES")){
				System.err.println("invalid file");
				br.close();
				return;
			}
			else line = br.readLine();
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

			//Loading series
			if(!line.equals("SERIES")){
				System.err.println("invalid file");
				br.close();
				return;
			}		
			else line = br.readLine();
			while (line != null && line.equals("#")) {
				line = br.readLine();
				id = line;
				line = br.readLine();
				rating = line;
				line = br.readLine();
				comments = line;
				final SeriesLoader sl = new SeriesLoader(Integer.parseInt(id), Integer.parseInt(rating), comments!=null ? comments.replace("\\n", "\n") : null);		
				sl.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
					public void handle(WorkerStateEvent t) {
						sp.add(sl.getValue());
					}
				});
				new Thread(sl).start();
				line = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void save(String fileName){
		try {
			StringBuilder builder = new StringBuilder();
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

			builder.append("MOVIES\n");
			for(Movie m: mp.getMovies()){
				builder.append("#\n");
				builder.append(m.id);
				builder.append("\n");
				builder.append(m.rating.getValue());
				builder.append("\n");
				builder.append(m.comments.getValue() != null ? 
						m.comments.getValue().replace("\n", "\\n") : 
							m.comments.getValue()
						);
				builder.append("\n");
			}

			builder.append("SERIES\n");
			for(Series s: sp.getSeries()){
				builder.append("#\n");
				builder.append(s.id);
				builder.append("\n");
				builder.append(s.rating.getValue());
				builder.append("\n");
				builder.append(s.comments.getValue() != null ? 
						s.comments.getValue().replace("\n", "\\n") : 
							s.comments.getValue()
						);
				builder.append("\n");
			}

			writer.write(builder.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
