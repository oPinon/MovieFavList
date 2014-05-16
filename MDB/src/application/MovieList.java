package application;

import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MovieList extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	private class MovieEntry {
		private SimpleStringProperty title;
		private SimpleStringProperty releaseDate;
		private SimpleStringProperty director;
		
		public MovieEntry(String title, String dateDeSortie, String director) {
			this.title = new SimpleStringProperty(title);
			this.releaseDate = new SimpleStringProperty(dateDeSortie);
			this.director = new SimpleStringProperty(director);
		}
		
		public String getTitle() { return title.get(); }
		public String getReleaseDate() { return releaseDate.get(); }
		public String getDirector() { return director.get(); }
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Searcher searcher = new Searcher();
		String film = "Ponyo";
		List<Map<String, Object>> results = searcher.searchMovie(film);
		
		VBox layout = new VBox();
		
		TableView tableView = new TableView();
		tableView.setPrefWidth(500);
		tableView.setEditable(true);
		
		TableColumn titleColumn = new TableColumn("Title");
		titleColumn.setMinWidth(50);
		TableColumn releaseColumn = new TableColumn("Release");
		releaseColumn.setMinWidth(50);
		TableColumn directorColumn = new TableColumn("Director");
		directorColumn.setMinWidth(50);
		titleColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));
		releaseColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));
		directorColumn.prefWidthProperty().bind(tableView.widthProperty().divide(3));
		
		tableView.getColumns().addAll(titleColumn, releaseColumn, directorColumn);
		
		final ObservableList<MovieEntry> items = FXCollections.observableArrayList();
		for(Map<String, Object> entry: results) {
			items.add( new MovieEntry(""+entry.get("original_title"),""+entry.get("release_date"),""));
		}
		
		titleColumn.setCellValueFactory(
				new PropertyValueFactory<MovieEntry,String>("title")
		);
		releaseColumn.setCellValueFactory(
				new PropertyValueFactory<MovieEntry,String>("releaseDate")
		);
		directorColumn.setCellValueFactory(
				new PropertyValueFactory<MovieEntry,String>("director")
		);
		
		tableView.setItems(items);
		
		layout.getChildren().add(tableView);
		
		Scene scene = new Scene(layout);
		stage.setScene(scene);
		stage.show();
	}

}
