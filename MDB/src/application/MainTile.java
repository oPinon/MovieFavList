package application;

import javafx.beans.binding.When;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import element.Element;
import element.Movie;
import element.Series;

public class MainTile<T extends Element> extends Group implements Tile<T>{
	public T element;

	public MainTile(final T element, final MainPane<T> mainPane) {
		super();
		this.element = element;

		if(element instanceof Movie){
			final Movie movie = (Movie) element;

			final HBox hbox = new HBox(2);
			hbox.setMinWidth(300);
			hbox.setMaxWidth(500);

			hbox.setPrefHeight(200);
			hbox.prefWidthProperty().bind(hbox.prefHeightProperty().multiply(3));

			ImageView imgView = new ImageView();
			if(movie.poster.getHeight()==0) imgView.setImage(new Image("file:images/placeholder.jpg"));
			else imgView.setImage(movie.poster);

			imgView.fitHeightProperty().bind(hbox.prefHeightProperty());
			imgView.setPreserveRatio(true);
			imgView.setSmooth(true);	
			imgView.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
						if(mouseEvent.getClickCount() == 2){
							new Details<Movie>(movie);
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
			nameField.setText(movie.title);
			Text dateField = new Text();		
			dateField.setTextOrigin(VPos.TOP);
			dateField.setStroke(Color.GRAY);
			dateField.textProperty().bind(new SimpleStringProperty(" (").concat(movie.releaseDate).concat(")"));
			titleBox.getChildren().addAll(nameField,dateField);
			Text directorField = new Text(movie.director);

			HBox buttonBox = new HBox(2);
			Button infoButton = new Button("Info");
			infoButton.getStyleClass().add("movieButton");
			infoButton.setAlignment(Pos.CENTER_RIGHT);
			infoButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {			
					new Details<Movie>(movie);
				}
			});

			Button delButton = new Button("Remove");
			delButton.getStyleClass().add("movieButton");
			delButton.setAlignment(Pos.CENTER_RIGHT);
			delButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {			
					mainPane.remove(element);
				}
			});
			buttonBox.getChildren().addAll(infoButton,delButton);

			HBox rateBox = new HBox(2);
			Button minus = new Button("-");
			minus.getStyleClass().add("circleButton");
			minus.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {			
					movie.decreaseRating();
				}
			});

			HBox starBox = new HBox(5);	
			for (int i = 1; i <= 5; i++) { 
				ImageView starView = new ImageView(); 
				starView.imageProperty().bind(new When(movie.rating.greaterThanOrEqualTo(i*2)) 
				.then(new Image("file:images/star-full.png"))
				.otherwise(		
						new When(movie.rating.subtract(i*2).greaterThanOrEqualTo(-1))
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
					movie.increaseRating();
				}
			});
			Text rateField = new Text();		
			rateField.setTextOrigin(VPos.TOP);
			rateField.setText(" Internet rating: "+movie.internetRating);
			rateBox.getChildren().addAll(minus,starBox,plus,rateField);

			FlowPane genreField = new FlowPane();
			for(String s : movie.genres) { genreField.getChildren().add(new Button(s)); }

			TextArea comField = new TextArea();	
			VBox.setVgrow(comField,Priority.ALWAYS);
			comField.textProperty().bindBidirectional(movie.comments);
			comField.setEditable(true);
			comField.setWrapText(true);
			comField.setId("commentText");

			vbox.getChildren().addAll(titleBox,directorField,genreField,buttonBox,rateBox,comField);
			vbox.setSpacing(8);

			HBox.setHgrow(comField,Priority.ALWAYS);
			hbox.getChildren().addAll(imgView,vbox);
			hbox.setSpacing(8);

			this.getChildren().addAll(hbox);

		}

		if(element instanceof Series){
			final Series series = (Series) element;

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
							new Details<Series>(series);
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
			infoButton.setAlignment(Pos.CENTER_RIGHT);
			infoButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {			
					new Details<Series>(series);
				}
			});

			Button delButton = new Button("Remove");
			delButton.getStyleClass().add("movieButton");
			delButton.setAlignment(Pos.CENTER_RIGHT);
			delButton.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {			
					mainPane.remove(element);
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

			this.getChildren().addAll(hbox);
		}
	}

	public T getElement() {
		return element;
	}
}