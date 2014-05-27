package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MovieTile extends Group{
	public Movie movie;

	public MovieTile(final Movie movie, final MoviePane mainPane) {
		super();
		this.movie = movie;

		final HBox hbox = new HBox(2);
		hbox.setMinWidth(300);
		hbox.setMinHeight(200);
		hbox.setMaxWidth(500);
		hbox.setMaxHeight(400);

		hbox.prefHeightProperty().bind(mainPane.prefHeightProperty().divide(2));
		hbox.prefWidthProperty().bind(hbox.prefHeightProperty().multiply(2));

		ImageView imgView;
		if(movie.poster.getHeight()!=0) imgView = new ImageView(movie.poster);
		else imgView = new ImageView(new Image("file:images/placeholder.jpg"));

	//	imgView.fitWidthProperty().bind(hbox.widthProperty().divide(1.5));
		imgView.fitHeightProperty().bind(hbox.heightProperty());
		imgView.setPreserveRatio(true);
		imgView.setSmooth(true);	
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		ds.setOffsetX(-5);
		ds.setOffsetY(2);
		ds.setColor(Color.color(0,0,0,0.3));
		imgView.setEffect(ds);

		
		VBox vbox = new VBox(2);

		HBox titleBox = new HBox(3);
		Text nameField = new Text();		
		nameField.setTextOrigin(VPos.TOP);
		nameField.setStroke(Color.BLACK);
		nameField.setText(movie.title);
		Text dateField = new Text();		
		dateField.setTextOrigin(VPos.TOP);
		dateField.setStroke(Color.GRAY);
		dateField.textProperty().bind(new SimpleStringProperty(" (").concat(movie.releaseDate).concat(")"));
		
		Button infoButton = new Button();
		infoButton.setGraphic(new ImageView(new Image("file:images/info-16.png")));
		infoButton.setAlignment(Pos.CENTER_RIGHT);
		//VBox.setVgrow(delButton, Priority.ALWAYS);
		infoButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {			
				new MovieDetails(movie);
			}
		});
		
		Button delButton = new Button();
		delButton.setGraphic(new ImageView(new Image("file:images/x-mark-3-16.png")));
		delButton.setAlignment(Pos.CENTER_RIGHT);
		//VBox.setVgrow(delButton, Priority.ALWAYS);
		delButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {			
				mainPane.remove(movie);
			}
		});
		titleBox.getChildren().addAll(nameField,dateField,infoButton,delButton);

		HBox rateBox = new HBox(2);
		ImageView starView = new ImageView(new Image("file:images/star-6-16.png"));
		Text rateField = new Text();		
		rateField.setTextOrigin(VPos.TOP);
		rateField.setText("Rating: "+movie.rating);
		rateBox.getChildren().addAll(starView,rateField);

		Text genreField = new Text();
		if(movie.genres != null) genreField.setText(movie.genres.toString());

		TextArea comField = new TextArea();	
		VBox.setVgrow(comField,Priority.ALWAYS);
		comField.textProperty().bindBidirectional(movie.comments);
		comField.setEditable(true);
		comField.setWrapText(true);
		comField.setId("commentText");

		vbox.getChildren().addAll(titleBox,rateBox,genreField,comField);

		HBox.setHgrow(comField,Priority.ALWAYS);
		hbox.getChildren().addAll(imgView,vbox);

		Rectangle r = new Rectangle();
		r.setFill(Color.color(0,0,0,0));
		r.setStroke(Color.BLACK);
		r.widthProperty().bind(hbox.widthProperty());
		r.heightProperty().bind(hbox.heightProperty());
		this.getChildren().addAll(hbox);

	}
}