package application;

import element.Series;
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

public class SeriesPreviewTile extends Group implements SeriesTile{
	public Series series;

	public SeriesPreviewTile(final Series series, final SeriesMainPane smp) {
		super();
		this.series = series;

		final HBox hbox = new HBox(2);
		hbox.setMinWidth(150);
		hbox.setMaxWidth(300);

		hbox.setPrefHeight(150);
		hbox.prefWidthProperty().bind(hbox.prefHeightProperty().multiply(1.5));

		ImageView imgView = new ImageView();
		if(series.poster.getHeight()==0) imgView.setImage(new Image("file:images/placeholder.jpg"));
		else imgView.setImage(series.poster);
		
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
		nameField.setText(series.name);
		Text dateField = new Text();		
		dateField.setTextOrigin(VPos.TOP);
		dateField.setStroke(Color.GRAY);
		dateField.textProperty().bind(new SimpleStringProperty(" (").concat(series.firstAirDate).concat(")"));
		Text creatorsField = new Text();
		creatorsField.setText(series.creators.toString().replace("[", "").replace("]", ""));
		
		
		Button addButton = new Button("Add");
		addButton.getStyleClass().add("movieButton");
		addButton.setAlignment(Pos.BOTTOM_RIGHT);
		VBox.setVgrow(addButton, Priority.ALWAYS);
		addButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				smp.add(new Series(series.id));
			}
		});

		vbox.getChildren().addAll(nameField,dateField,creatorsField,addButton);

		hbox.getChildren().addAll(imgView,vbox);

		Rectangle r = new Rectangle();
		r.setFill(Color.color(0,0,0,0));
		r.setStroke(Color.BLACK);
		r.widthProperty().bind(hbox.widthProperty());
		r.heightProperty().bind(hbox.heightProperty());
		this.getChildren().addAll(hbox);

	}

	public Series getSeries() {
		return series;
	}
}