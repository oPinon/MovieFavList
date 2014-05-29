package application;

import java.util.List;
import java.util.Map;

import element.Movie;
import element.Series;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SeriesDetails {

	public SeriesDetails(Series series) {
		Stage stage = new Stage();
		stage.setTitle(series.name);
		stage.initModality(Modality.APPLICATION_MODAL);

		Accordion details = new Accordion();
		Scene scene = new Scene(details, 400,600);

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
		creators.wrappingWidthProperty().bind(scene.widthProperty());
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

		TitledPane infoPane = new TitledPane("Info",info);
		TitledPane posterPane = new TitledPane("Poster", imgView);
		TitledPane crewPane = new TitledPane("Creators", crewScrollPane);
		TitledPane castPane = new TitledPane("Actors", castScrollPane);
		TitledPane plotPane = new TitledPane("Overview", overview);
		TitledPane commentPane = new TitledPane("Comments", comments);

		details.getPanes().addAll(infoPane, posterPane, crewPane, castPane, plotPane, commentPane);
		
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();

	}
}
