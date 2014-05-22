package application;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MoviePreviewTile extends Group{
	public Movie movie;

	public MoviePreviewTile(final Movie movie, MoviePreviewPane moviePreviewPane, final MoviePane mp) {
		super();
		this.movie = movie;

		final HBox hbox = new HBox(2);
		//	hbox.setPrefHeight(500);
		hbox.setMinWidth(300);
		hbox.setMinHeight(200);
		hbox.setMaxWidth(400);
		hbox.setMaxHeight(300);

		//		final DoubleProperty paneHeight = mp.prefHeightProperty(); 
		//		DoubleBinding tileHeight = new DoubleBinding() {
		//			{ 
		//				bind(paneHeight); 
		//			} 
		//			@Override 
		//			protected double computeValue() {
		//				if(paneHeight.getValue()/4 > hbox.getMinHeight()+30) return paneHeight.getValue()/4;
		//				if(paneHeight.getValue()/3 > hbox.getMinHeight()+30) return paneHeight.getValue()/3;
		//				if(paneHeight.getValue()/2 > hbox.getMinHeight()+30) return paneHeight.getValue()/2;
		//				else return paneHeight.getValue();
		//			} 
		//		};
		//
		hbox.prefHeightProperty().bind(moviePreviewPane.prefHeightProperty().divide(3));
		//	hbox.prefWidthProperty().bind(mp.prefWidthProperty().divide(1));
		//hbox.prefHeightProperty().bind(mp.prefHeightProperty().divide(2).subtract(30));	
		//hbox.setAlignment(Pos.CENTER);

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

		Text nameField = new Text();		
		nameField.setTextOrigin(VPos.TOP);
		nameField.setStroke(Color.BLACK);
		nameField.setText(movie.title);
		Text dateField = new Text();		
		dateField.setTextOrigin(VPos.TOP);
		dateField.setStroke(Color.GRAY);
		dateField.textProperty().bind(new SimpleStringProperty(" (").concat(movie.releaseDate).concat(")"));
		
		
		Button addButton = new Button();
		addButton.setGraphic(new ImageView(new Image("file:images/plus-4-16.png")));
		addButton.setAlignment(Pos.BOTTOM_RIGHT);
		VBox.setVgrow(addButton, Priority.ALWAYS);
		addButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				
				mp.add(movie);
				

			}
		});

		vbox.getChildren().addAll(nameField,dateField,addButton);

		hbox.getChildren().addAll(imgView,vbox);

		Rectangle r = new Rectangle();
		r.setFill(Color.color(0,0,0,0));
		r.setStroke(Color.BLACK);
		r.widthProperty().bind(hbox.widthProperty());
		r.heightProperty().bind(hbox.heightProperty());
		this.getChildren().addAll(hbox);

	}


}
