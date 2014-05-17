package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MovieTile extends Group{
	public Movie movie;

	public MovieTile(Movie movie, Scene scene) {
		super();
		this.movie = movie;
		
		HBox hbox = new HBox(2);
	//	hbox.setPrefHeight(500);
		hbox.prefWidthProperty().bind(scene.widthProperty().divide(8));
		//hbox.prefHeightProperty().bind(scene.heightProperty().divide(2));
		
		ImageView imgView = new ImageView();
		imgView.setImage(new Image("file:"+movie.title.getValue()+".jpg"));
		imgView.fitWidthProperty().bind(hbox.widthProperty().divide(2));
	//	imgView.setPreserveRatio(true);
		imgView.setSmooth(true);	
		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		ds.setOffsetX(-5);
		ds.setOffsetY(2);
		ds.setColor(Color.color(0,0,0,0.3));
		imgView.setEffect(ds);
		
		VBox vbox = new VBox(2);
		
		HBox titleBox = new HBox(2);
		Text nameField = new Text();		
		nameField.setTextOrigin(VPos.TOP);
		nameField.setStroke(Color.BLACK);
		nameField.textProperty().bind(movie.title);
		Text dateField = new Text();		
		dateField.setTextOrigin(VPos.TOP);
		dateField.setStroke(Color.GRAY);
		dateField.textProperty().bind(new SimpleStringProperty(" (").concat(movie.releaseDate).concat(")"));
		titleBox.getChildren().addAll(nameField,dateField);
		
		Text genreField = new Text();
		//genreField.prefHeightProperty().bind(hbox.heightProperty().divide(5/1));
		genreField.textProperty().bind(movie.genre);
		
		TextField comField = new TextField();
		//comField.prefHeightProperty().bind(hbox.heightProperty().divide(5/2));
		comField.textProperty().bind(movie.comments);		
		vbox.getChildren().addAll(titleBox,genreField,comField);
		VBox.setVgrow(comField, Priority.ALWAYS);
		
		hbox.getChildren().addAll(imgView,vbox);
		this.getChildren().addAll(hbox);
		
	}
	
	
}
