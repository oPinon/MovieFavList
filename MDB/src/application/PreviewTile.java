package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.WorkerStateEvent;
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
import javafx.scene.text.Text;
import element.Element;
import element.Movie;
import element.MovieLoader;
import element.Series;
import element.SeriesLoader;

public class PreviewTile<T extends Element> extends Group implements Tile<T>{
	public T element;

	public PreviewTile(final T element, final MainPane<T> mainPane) {
		super();
		this.element = element;

		if(element instanceof Movie){
			final Movie movie = (Movie) element;


			final HBox hbox = new HBox(2);
			hbox.setMinWidth(150);
			hbox.setMaxWidth(300);

			hbox.setPrefHeight(150);
			hbox.prefWidthProperty().bind(hbox.prefHeightProperty().multiply(1.5));

			ImageView imgView = new ImageView();
			if(movie.poster.getHeight()==0) imgView.setImage(new Image("file:images/placeholder.jpg"));
			else imgView.setImage(movie.poster);

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
			Text director = new Text(movie.director);

			Button addButton = new Button("Add");
			addButton.getStyleClass().add("movieButton");
			addButton.setAlignment(Pos.BOTTOM_RIGHT);
			VBox.setVgrow(addButton, Priority.ALWAYS);
			addButton.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent event) {
					final MovieLoader ml = new MovieLoader(element.id);		
					ml.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						public void handle(WorkerStateEvent t) {
							mainPane.add((T) ml.getValue());
						}
					});
					new Thread(ml).start();
				}
			});

			vbox.getChildren().addAll(nameField,dateField,director,addButton);

			hbox.getChildren().addAll(imgView,vbox);
			this.getChildren().addAll(hbox);

		}

		if(element instanceof Series){
			final Series series = (Series) element;

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
				public void handle(ActionEvent event) {
					final SeriesLoader sl = new SeriesLoader(element.id);		
					sl.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
						public void handle(WorkerStateEvent t) {
							mainPane.add((T) sl.getValue());
						}
					});
					new Thread(sl).start();
				}
			});

			vbox.getChildren().addAll(nameField,dateField,creatorsField,addButton);

			hbox.getChildren().addAll(imgView,vbox);
			this.getChildren().addAll(hbox);
		}

	}

	public T getElement() {
		return element;
	}
}
