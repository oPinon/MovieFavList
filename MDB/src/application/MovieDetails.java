package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MovieDetails {

	public MovieDetails(Movie movie) {
		Stage stage = new Stage();
		stage.setTitle(movie.title);

		Accordion details = new Accordion();
		Scene scene = new Scene(details, 400,600);

		VBox info = new VBox();
		info.setAlignment(Pos.CENTER);
		info.setTranslateX(5);
		Text release = new Text("Release Date : " + movie.releaseDate);
		release.wrappingWidthProperty().bind(scene.widthProperty());
		Text director = new Text("Director : ");
		director.wrappingWidthProperty().bind(scene.widthProperty());
		FlowPane genre = new FlowPane();
		for(String s : movie.genres) { genre.getChildren().add(new Button(s)); }
		info.getChildren().addAll(release,director,genre);


		ImageView poster = new ImageView(movie.poster);
		poster.setPreserveRatio(true);
		poster.fitWidthProperty().bind(scene.widthProperty().divide(1.5));
	

		Text plot = new Text(movie.plot);
		plot.setTranslateX(5);
		plot.wrappingWidthProperty().bind(scene.widthProperty().subtract(20));

		TextArea comments = new TextArea();
		comments.textProperty().bindBidirectional(movie.comments);
		comments.setEditable(true);


		//	Text title = new Text(movie.title);
		//	ScrollPane scroll = new ScrollPane();
		//	scroll.prefWidthProperty().bind(scene.widthProperty());
		TitledPane infoPane = new TitledPane("Info",info);
		TitledPane posterPane = new TitledPane("Poster", poster);
		TitledPane plotPane = new TitledPane("Plot", plot);
		TitledPane commentPane = new TitledPane("Comments", comments);

		//	scroll.setHbarPolicy(ScrollBarPolicy.NEVER);

		//	details.prefWidthProperty().bind(scene.widthProperty());
		details.getPanes().addAll(infoPane,posterPane,plotPane,commentPane);
		//	scroll.setContent(details);
		//	scroll.setFitToWidth(true);
		//layout.setCenter(details);

		stage.setScene(scene);
		stage.show();

	}

}
