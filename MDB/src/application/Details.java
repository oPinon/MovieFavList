package application;

import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import element.Element;
import element.Movie;
import element.Series;

public class Details<T extends Element> {

	public Details(T element) {
		Stage stage = new Stage();	
		stage.initModality(Modality.APPLICATION_MODAL);

		Accordion details = new Accordion();
		Scene scene = new Scene(details, 400,600);		
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		if(element instanceof Movie){
			final Movie movie = (Movie) element;

			stage.setTitle(movie.title);

			// Basic info about the movie
			VBox info = new VBox();

			info.getStyleClass().add("tp");
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

			title.wrappingWidthProperty().bind(scene.widthProperty());
			originalTitle.wrappingWidthProperty().bind(scene.widthProperty());
			tagline.wrappingWidthProperty().bind(scene.widthProperty());
			prodCompanies.wrappingWidthProperty().bind(scene.widthProperty());
			prodCountries.wrappingWidthProperty().bind(scene.widthProperty());
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
			imgView.fitHeightProperty().bind(scene.heightProperty().divide(1.5));

			// the movie Plot
			Text plot = new Text(movie.plot);
			plot.setTranslateX(3);
			plot.wrappingWidthProperty().bind(scene.widthProperty().subtract(20));

			// some comments about the movie
			TextArea comments = new TextArea();
			comments.textProperty().bindBidirectional(movie.comments);
			comments.setEditable(true);

			// a web view of the trailer
			final WebView trailer = new WebView();
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

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {
					trailer.getEngine().load(null);
				}
			});

		}

		if(element instanceof Series){
			final Series series = (Series) element;

			stage.setTitle(series.name);

			// Basic info about the series
			VBox info = new VBox();
			info.setAlignment(Pos.CENTER_LEFT);
			info.setTranslateX(3);
			Text id = new Text("ID : " + series.id);
			Text name = new Text("Name : " + series.name);
			Text originalName = new Text("Original name : " + series.originalName);
			Text firstAir = new Text("First air date : " + series.firstAirDate);
			Text lastAir = new Text("Last air date : " + series.lastAirDate);
			Text episodes = new Text("\nNumber of episodes : " + series.episodes);
			Text seasons = new Text("Number of seasons : " + series.seasons);
			Text runTime = new Text("Episode run time (min): " + series.episodeRunTime.toString()
					.replace("[", "").replace("]", "")
					);
			Text creators = new Text("\nCreator(s) : " + series.creators.toString()
					.replace("[", "").replace("]", "")
					);
			Text originCountry = new Text("Origin country(ies) : " + series.originCountry.toString()
					.replace("[", "").replace("]", "")
					);
			Text network = new Text("Network(s) : " + series.networks.toString()
					.replace("[", "").replace("]", "")
					);
			Text languages = new Text("Language(s) : " + series.languages.toString()
					.replace("[", "").replace("]", "")
					);
			Text homepage = new Text("Homepage : " + series.homepage);
			FlowPane genre = new FlowPane();
			for(String s : series.genres) { genre.getChildren().add(new Button(s)); }	
			Text rating = new Text("\nPersonal rating : " + series.rating.getValue());
			Text internetRating = new Text("Internet rating : "+ series.internetRating);

			name.wrappingWidthProperty().bind(scene.widthProperty());
			originalName.wrappingWidthProperty().bind(scene.widthProperty());
			creators.wrappingWidthProperty().bind(scene.widthProperty());
			originCountry.wrappingWidthProperty().bind(scene.widthProperty());
			network.wrappingWidthProperty().bind(scene.widthProperty());
			languages.wrappingWidthProperty().bind(scene.widthProperty());
			homepage.wrappingWidthProperty().bind(scene.widthProperty());

			info.getChildren().addAll(id,name,originalName,firstAir,lastAir,genre,episodes,seasons,
					runTime,creators,originCountry,network,languages,homepage,rating,internetRating);

			// the team that created the series
			ScrollPane crewScrollPane = new ScrollPane();
			VBox crew = new VBox();
			//crew.setAlignment(Pos.CENTER);
			crew.setTranslateX(3);
			if(series.credits!=null) {
				for(Map<String,String> member : (List<Map<String,String>>) series.credits.get("crew")) {
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
			//cast.setAlignment(Pos.CENTER);
			cast.setTranslateX(3);
			if(series.credits!=null) {
				for(Map<String,String> member : (List<Map<String,String>>) series.credits.get("cast")) {
					HBox memberInfo = new HBox();
					memberInfo.getChildren().add(new Text(member.get("character")+" : "));
					memberInfo.getChildren().add(new Text((String) member.get("name")));
					cast.getChildren().add(memberInfo);
				}
			}
			castScrollPane.setContent(cast);

			// the series Poster
			ImageView imgView = new ImageView();
			if(series.poster.getHeight()==0) imgView.setImage(new Image("file:images/placeholder.jpg"));
			else imgView.setImage(series.poster);
			imgView.setPreserveRatio(true);
			imgView.fitWidthProperty().bind(scene.widthProperty().divide(1.5));

			// the series Overview
			Text overview = new Text(series.overview);
			overview.wrappingWidthProperty().bind(scene.widthProperty().subtract(20));

			// some comments about the series
			TextArea comments = new TextArea();
			comments.textProperty().bindBidirectional(series.comments);
			comments.setEditable(true);

			TitledPane infoPane = new TitledPane("Info", info);
			TitledPane posterPane = new TitledPane("Poster", imgView);
			TitledPane crewPane = new TitledPane("Creators", crewScrollPane);
			TitledPane castPane = new TitledPane("Actors", castScrollPane);
			TitledPane plotPane = new TitledPane("Overview", overview);
			TitledPane commentPane = new TitledPane("Comments", comments);

			details.getPanes().addAll(infoPane, posterPane, crewPane, castPane, plotPane, commentPane);
		}
		stage.setScene(scene);
	//	stage.setMinHeight(400);
	//	stage.setMinWidth(600);
		stage.show();

	}
}
