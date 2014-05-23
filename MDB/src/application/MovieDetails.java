package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MovieDetails extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		Movie movie = new Movie(11);
        
        VBox info = new VBox();
        Text release = new Text("Release Date : " + movie.releaseDate);
        	release.wrappingWidthProperty().bind(stage.widthProperty());
        Text director = new Text("Director : ");
        	director.wrappingWidthProperty().bind(stage.widthProperty());
        	FlowPane genre = new FlowPane();
        	for(String s : movie.genres) { genre.getChildren().add(new Button(s)); }
        info.getChildren().addAll(release,director,genre);
        
        ImageView poster = new ImageView(movie.poster);
        poster.setPreserveRatio(true);
        poster.fitWidthProperty().bind(stage.widthProperty());
        
        Text plot = new Text(movie.plot);
        plot.wrappingWidthProperty().bind(stage.widthProperty());
        
        TextArea comments = new TextArea();
        comments.textProperty().bind(movie.comments);
        
        
        Text title = new Text(movie.title);
        
        TitledPane infoPane = new TitledPane("Info",info);
        TitledPane posterPane = new TitledPane("Poster", poster);
        //posterPane.setAlignment(Pos.CENTER_LEFT);
        TitledPane plotPane = new TitledPane("Plot", plot);
        TitledPane commentPane = new TitledPane("Comments", comments);
        
        ScrollPane scroll = new ScrollPane();
        scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
        	VBox details = new VBox();
        	details.getChildren().addAll(infoPane,posterPane,plotPane,commentPane);
        scroll.setContent(details);
        scroll.setFitToWidth(true);
        
        VBox layout = new VBox();
        layout.getChildren().addAll(title,scroll);
        VBox.setVgrow(scroll,Priority.ALWAYS);
        
        stage.setScene(new Scene(layout, 400,600));
        stage.show();
	}

}
