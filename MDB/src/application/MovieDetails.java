package application;

import java.util.List;
import java.util.Map;

import element.Movie;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
		info.setAlignment(Pos.CENTER_LEFT);
		info.setTranslateX(3);
		Text id = new Text("ID : " + movie.id);
		Text title = new Text("Title : " + movie.title);
		Text originalTitle = new Text("Original title : " + movie.originalTitle);
		Text tagline = new Text("Tagline : " + movie.tagline);
		Text release = new Text("Release date : " + movie.releaseDate);
		Text director = new Text("\nDirector : " + movie.director);
		Text prodCompanies = new Text("Production companies : " + movie.productionCompanies.toString()
				.replace("[", "").replace("]", "")
				);

		Text prodCountries = new Text("Production countries : " + movie.productionCountries.toString()
				.replace("[", "").replace("]", "")
				);
		Text languages = new Text("Spoken languages : " + movie.languages.toString()
				.replace("[", "").replace("]", "")
				);
		Text homepage = new Text("Homepage : " + movie.homepage);
		Text budget = new Text("Budget : " + String.format("%,d", movie.budget) + " $");
		Text revenue = new Text("Revenue : " + String.format("%,d", movie.revenue) + " $");
		FlowPane genre = new FlowPane();
		for(String s : movie.genres) { genre.getChildren().add(new Button(s)); }
		Text rating = new Text("\nPersonal rating : " + movie.rating.getValue());
		Text internetRating = new Text("Internet rating : "+ movie.internetRating);
		prodCompanies.wrappingWidthProperty().bind(scene.widthProperty());
		homepage.wrappingWidthProperty().bind(scene.widthProperty());
		info.getChildren().addAll(id,title,originalTitle,tagline,release,genre,director,prodCompanies,
				prodCountries,languages,homepage,budget,revenue,rating,internetRating);

		// the team that created the movie
		ScrollPane crewScrollPane = new ScrollPane();
		VBox crew = new VBox();
		crew.setAlignment(Pos.CENTER);
		crew.setTranslateX(3);
		if(movie.credits!=null) {
			for(Map<String,String> member : (List<Map<String,String>>) movie.credits.get("crew")) {
				HBox memberInfo = new HBox();
				memberInfo.getChildren().add(new Text(member.get("job")+" : "));
				memberInfo.getChildren().add(new Text((String) member.get("name")));
				crew.getChildren().add(memberInfo);
			}
		}
		crewScrollPane.setContent(crew);

		// a list of the actors
		ScrollPane castScrollPane = new ScrollPane();
		VBox cast = new VBox();
		cast.setAlignment(Pos.CENTER);
		cast.setTranslateX(3);
		if(movie.credits!=null) {
			for(Map<String,String> member : (List<Map<String,String>>) movie.credits.get("cast")) {
				HBox memberInfo = new HBox();
				memberInfo.getChildren().add(new Text(member.get("character")+" : "));
				memberInfo.getChildren().add(new Text((String) member.get("name")));
				cast.getChildren().add(memberInfo);
			}
		}
		castScrollPane.setContent(cast);

		// the movie Poster
		ImageView imgView = new ImageView();
		if(movie.poster.getHeight()==0) imgView.setImage(new Image("file:images/placeholder.jpg"));
		else imgView.setImage(movie.poster);
		imgView.setPreserveRatio(true);
		imgView.fitWidthProperty().bind(scene.widthProperty().divide(1.5));

		// the movie Plot
		Text plot = new Text(movie.plot);
		plot.setTranslateX(3);
		plot.wrappingWidthProperty().bind(scene.widthProperty().subtract(20));

		// some comments about the movie
		TextArea comments = new TextArea();
		comments.textProperty().bindBidirectional(movie.comments);
		comments.setEditable(true);

		// a web view of the trailer
		WebView trailer = new WebView();
		trailer.getEngine().load(movie.trailerURL);
		trailer.prefWidthProperty().bind(stage.widthProperty());
		
		TitledPane infoPane = new TitledPane("Info",info);
		TitledPane posterPane = new TitledPane("Poster", imgView);
		TitledPane trailerPane = new TitledPane("Trailer", trailer);
		TitledPane crewPane = new TitledPane("Creators", crewScrollPane);
		TitledPane castPane = new TitledPane("Actors", castScrollPane);
		TitledPane plotPane = new TitledPane("Plot", plot);
		TitledPane commentPane = new TitledPane("Comments", comments);

		details.getPanes().addAll(infoPane, posterPane, crewPane, trailerPane, castPane, plotPane, commentPane);

		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}
}
