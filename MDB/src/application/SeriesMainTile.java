package application;

import element.Series;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SeriesMainTile extends Group implements SeriesTile{
	public Series series;

	public SeriesMainTile(final Series series, final SeriesMainPane seriesMainPane) {
		super();
		this.series = series;

		final HBox hbox = new HBox(2);
		hbox.setMinWidth(300);
		hbox.setMaxWidth(500);

		hbox.setPrefHeight(200);
		hbox.prefWidthProperty().bind(hbox.prefHeightProperty().multiply(3));

		ImageView imgView = new ImageView();
		if(series.poster.getHeight()==0) imgView.setImage(new Image("file:images/placeholder.jpg"));
		else imgView.setImage(series.poster);

		imgView.fitHeightProperty().bind(hbox.prefHeightProperty());
		imgView.setPreserveRatio(true);
		imgView.setSmooth(true);	
		imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					if(mouseEvent.getClickCount() == 2){
						new SeriesDetails(series);
					}
				}
			}
		});

		DropShadow ds = new DropShadow();
		ds.setRadius(10);
		ds.setOffsetX(-5);
		ds.setOffsetY(2);
		ds.setColor(Color.color(0,0,0,0.3));
		imgView.setEffect(ds);

		VBox vbox = new VBox(6);

		HBox titleBox = new HBox(3);
		Text nameField = new Text();		
		nameField.setTextOrigin(VPos.TOP);
		nameField.setStroke(Color.BLACK);
		nameField.setText(series.name);
		Text dateField = new Text();		
		dateField.setTextOrigin(VPos.TOP);
		dateField.setStroke(Color.GRAY);
		dateField.textProperty().bind(new SimpleStringProperty(" (").concat(series.firstAirDate).concat(")"));
		titleBox.getChildren().addAll(nameField,dateField);
		Text directorField = new Text(series.creators.toString().replace("[", "").replace("]", ""));

		HBox buttonBox = new HBox(2);
		Button infoButton = new Button("Info");
		infoButton.getStyleClass().add("movieButton");
		//infoButton.setGraphic(new ImageView(new Image("file:images/info-16.png")));
		infoButton.setAlignment(Pos.CENTER_RIGHT);
		//VBox.setVgrow(delButton, Priority.ALWAYS);
		infoButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {			
				new SeriesDetails(series);
			}
		});

		Button delButton = new Button("Remove");
		delButton.getStyleClass().add("movieButton");
		//	delButton.setGraphic(new ImageView(new Image("file:images/x-mark-3-16.png")));
		delButton.setAlignment(Pos.CENTER_RIGHT);
		//VBox.setVgrow(delButton, Priority.ALWAYS);
		delButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {			
				seriesMainPane.remove(series);
			}
		});
		buttonBox.getChildren().addAll(infoButton,delButton);

		HBox rateBox = new HBox(2);
		Button minus = new Button("-");
		minus.getStyleClass().add("circleButton");
		minus.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {			
				series.decreaseRating();
			}
		});
		
		HBox starBox = new HBox(5);	
		for (int i = 1; i <= 5; i++) { 
			ImageView starView = new ImageView(); 
			starView.imageProperty().bind(new When(series.rating.greaterThanOrEqualTo(i*2)) 
			.then(new Image("file:images/star-full.png"))
			.otherwise(		
					new When(series.rating.subtract(i*2).greaterThanOrEqualTo(-1))
					.then(new Image("file:images/star-half.png"))
					.otherwise(new Image("file:images/star-empty.png"))
					)
			); 
			starBox.getChildren().add(starView);
		}
		starBox.setAlignment(Pos.CENTER);

		Button plus = new Button("+");
		plus.getStyleClass().add("circleButton");
		plus.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {			
				series.increaseRating();
			}
		});
		Text rateField = new Text();		
		rateField.setTextOrigin(VPos.TOP);
		rateField.setText(" Internet rating: "+series.internetRating);
		rateBox.getChildren().addAll(minus,starBox,plus,rateField);

		FlowPane genreField = new FlowPane();
		for(String s : series.genres) { genreField.getChildren().add(new Button(s)); }

		TextArea comField = new TextArea();	
		VBox.setVgrow(comField,Priority.ALWAYS);
		comField.textProperty().bindBidirectional(series.comments);
		comField.setEditable(true);
		comField.setWrapText(true);
		comField.setId("commentText");

		vbox.getChildren().addAll(titleBox,directorField,genreField,buttonBox,rateBox,comField);
		vbox.setSpacing(8);

		HBox.setHgrow(comField,Priority.ALWAYS);
		hbox.getChildren().addAll(imgView,vbox);
		hbox.setSpacing(8);

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