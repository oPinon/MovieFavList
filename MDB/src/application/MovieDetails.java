package application;

import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MovieDetails {

	public MovieDetails(Movie movie) {
		Stage stage = new Stage();
		stage.setTitle(movie.title);
		stage.initModality(Modality.APPLICATION_MODAL);

		Accordion details = new Accordion();
		Scene scene = new Scene(details, 400,600);

		// Basic info about the movie
		VBox info = new VBox();
		info.setAlignment(Pos.CENTER);
		info.setTranslateX(5);
		Text id = new Text("ID : " + movie.id);
		id.wrappingWidthProperty().bind(scene.widthProperty());
		Text release = new Text("Release Date : " + movie.releaseDate);
		release.wrappingWidthProperty().bind(scene.widthProperty());
		Text director = new Text("Director : "+ movie.director);
		director.wrappingWidthProperty().bind(scene.widthProperty());
		FlowPane genre = new FlowPane();
		for(String s : movie.genres) { genre.getChildren().add(new Button(s)); }
		info.getChildren().addAll(id,release,director,genre);

		// the team that created the movie
		VBox crew = new VBox();
		crew.setAlignment(Pos.CENTER);
		crew.setTranslateX(5);
		if(movie.credits!=null) {
			for(Map<String,String> member : (List<Map<String,String>>) movie.credits.get("crew")) {
				HBox memberInfo = new HBox();
				memberInfo.getChildren().add(new Text(member.get("job")+" : "));
				memberInfo.getChildren().add(new Text((String) member.get("name")));
				crew.getChildren().add(memberInfo);
			}
		}

		// a list of the actors
		VBox cast = new VBox();
		cast.setAlignment(Pos.CENTER);
		cast.setTranslateX(5);
		if(movie.credits!=null) {
			for(Map<String,String> member : (List<Map<String,String>>) movie.credits.get("cast")) {
				HBox memberInfo = new HBox();
				memberInfo.getChildren().add(new Text(member.get("character")+" : "));
				memberInfo.getChildren().add(new Text((String) member.get("name")));
				cast.getChildren().add(memberInfo);
			}
		}

		// the movie Poster
		ImageView poster = new ImageView(movie.poster);
		poster.setPreserveRatio(true);
		poster.fitWidthProperty().bind(scene.widthProperty().divide(1.5));
	
		// the movie Plot
		Text plot = new Text(movie.plot);
		plot.setTranslateX(5);
		plot.wrappingWidthProperty().bind(scene.widthProperty().subtract(20));

		// some comments about the movie
		TextArea comments = new TextArea();
		comments.textProperty().bindBidirectional(movie.comments);
		comments.setEditable(true);

		TitledPane infoPane = new TitledPane("Info",info);
		TitledPane posterPane = new TitledPane("Poster", poster);
		TitledPane crewPane = new TitledPane("Creators", crew);
		TitledPane castPane = new TitledPane("Actors", cast);
		TitledPane plotPane = new TitledPane("Plot", plot);
		TitledPane commentPane = new TitledPane("Comments", comments);

		details.getPanes().addAll(infoPane, posterPane, crewPane, castPane, plotPane, commentPane);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}
}
