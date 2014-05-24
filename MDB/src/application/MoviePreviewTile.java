package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class MoviePreviewTile extends Group{
	public Movie movie;

	public MoviePreviewTile(final Movie movie, MoviePreviewPane moviePreviewPane, final MoviePane mp) {
		super();
		this.movie = movie;

		final HBox hbox = new HBox(2);
		hbox.setMinWidth(300);
		hbox.setMinHeight(200);
		hbox.setMaxWidth(450);
		hbox.setMaxHeight(300);

		hbox.prefHeightProperty().bind(moviePreviewPane.prefHeightProperty().divide(3));
		hbox.prefWidthProperty().bind(hbox.prefHeightProperty().multiply(1.5));

		ImageView imgView;
		if(movie.poster.getHeight()!=0) imgView = new ImageView(movie.poster);
		else imgView = new ImageView(new Image("file:images/placeholder.jpg"));

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
				mp.add(new Movie(movie.id));
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
