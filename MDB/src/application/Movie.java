package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Movie {
	     public StringProperty title;
	     public StringProperty releaseDate;
	     public StringProperty genre;
	     public StringProperty comments;
	     public IntegerProperty rating;
	     
	     public Movie(String title, String releaseDate, String genre) {
			this.title = new SimpleStringProperty(title);
			this.releaseDate = new SimpleStringProperty(releaseDate);
			this.genre = new SimpleStringProperty(genre);
			comments = new SimpleStringProperty();
			rating = new SimpleIntegerProperty(0);

		}
	     

}


