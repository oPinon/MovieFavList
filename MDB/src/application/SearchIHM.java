package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import element.Movie;
import element.MovieLoader;
import element.SeriesLoader;
import utils.DirectorComparator;
import utils.InternetRatingComparator;
import utils.RatingComparator;
import utils.ReleaseComparator;
import utils.Searcher;
import utils.TitleComparator;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.When;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SearchIHM {
	private MovieMainPane mmp;
	private SeriesMainPane smp;
	private MoviePreviewPane mpp;
	private SeriesPreviewPane spp;
	BooleanProperty areMoviesSelected = new SimpleBooleanProperty();

	public SearchIHM(MovieMainPane mmp, SeriesMainPane smp) {
		this.mmp = mmp;
		this.smp = smp;
		mpp = new MoviePreviewPane(mmp);
		spp = new SeriesPreviewPane(smp);
	}

	public void search(){
		final Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);

		BorderPane pane = new BorderPane();	
		pane.setId("searchPane");

		Scene scene = new Scene(pane,600,600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		final ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(false);
		scrollPane.prefWidthProperty().bind(scene.widthProperty());
		scrollPane.setId("scrollPane");
		scrollPane.contentProperty().bind(new When(areMoviesSelected)
		.then((TilePane) mpp)
		.otherwise((TilePane) spp)
				);

		final Label placeholder = new Label("No element currently in the list");
		placeholder.setAlignment(Pos.CENTER_RIGHT);
		ObjectBinding<Node> b = new ObjectBinding<Node>() {
			{
				bind(areMoviesSelected, mpp.emptyProperty, spp.emptyProperty);
			}

			@Override
			protected Node computeValue() {
				if( (areMoviesSelected.getValue() && !mpp.emptyProperty.getValue()) ||
						(!areMoviesSelected.getValue() && !spp.emptyProperty.getValue()) )
					return scrollPane;
				else return placeholder;
			}
		};
		pane.centerProperty().bind(b);

		ToolBar toolbar = new ToolBar();
		toolbar.setId("optionBar");

		final TextField searchField = new TextField();
		searchField.getStyleClass().add("searchBox");
		searchField.setMaxWidth(Integer.MAX_VALUE);
		searchField.setPromptText("Search by name");
		searchField.setTranslateX(5);
		HBox.setHgrow(searchField, Priority.ALWAYS);

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
				mpp.clear();
				spp.clear();

				String criteria = searchField.getText();
				if(criteria.length()==0) return;

				List<Map<String, Object>> results = searcher.searchMovies(criteria.replace(" ","+"));
				for(Map<String, Object> entry: results){
					final MovieLoader ml = new MovieLoader(entry);		
					ml.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						public void handle(WorkerStateEvent t) {
							mpp.add(ml.getValue());
						}
					});
					new Thread(ml).start();
				}
				
				List<Map<String, Object>> results1 = searcher.searchTVSeries(criteria.replace(" ","+"));
				for(Map<String, Object> entry: results1){				
					final SeriesLoader sl = new SeriesLoader(entry);		
					sl.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						public void handle(WorkerStateEvent t) {
							spp.add(sl.getValue());				
						}
					});
					new Thread(sl).start();
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
		areMoviesSelected.bind(group.selectedToggleProperty().isEqualTo(tb1));

		modeBox.getChildren().addAll(tb1,tb2);
		modeBox.setAlignment(Pos.CENTER);
		
		HBox sortBox = new HBox(4);
		VBox criteriaBox = new VBox(2);
		Label sortLabel = new Label("Sort by:");
		final ChoiceBox<String> cb = new ChoiceBox<String>(FXCollections.observableArrayList(
				"Title", "Director/Creator", "Release date")
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
		sortBox.getChildren().addAll(criteriaBox,sortAscendingButton,sortDescendingButton);
		sortBox.setAlignment(Pos.CENTER_LEFT);
		sortBox.setTranslateX(5);

		//sbox.setAlignment(Pos.CENTER_LEFT);
		//sbox.setSpacing(5);

		toolbar.getItems().addAll(sortBox,new Separator(Orientation.VERTICAL),modeBox,searchField, searchButton, closeButton);	
		
		pane.setTop(toolbar);

		stage.setScene(scene);
		stage.setTitle("Search");
		stage.setMinHeight(350);
		stage.setMinWidth(550);
		stage.show();

		searchField.requestFocus();

	}
	
	private void sort(int criteriaIndex, boolean isAscending){
		TilePane toSort = areMoviesSelected.getValue() ? mpp : spp;
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

		}	
		toSort.getChildren().clear();
		toSort.getChildren().addAll(sortedList);


	}

}
