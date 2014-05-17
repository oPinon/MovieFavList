package application;

import java.util.Map;

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
	     public String imageURL;
	     
	     public Movie(String title, String releaseDate, String genre) {
			this.title = new SimpleStringProperty(title);
			this.releaseDate = new SimpleStringProperty(releaseDate);
			this.genre = new SimpleStringProperty(genre);
			comments = new SimpleStringProperty();
			rating = new SimpleIntegerProperty(0);

		}
	     
	    public Movie(String id) {
	    	Map<String, Object> details = Searcher.getMovie(id);
	    	this.title = new SimpleStringProperty((String) details.get("title")); // ou "original_title"
	    	this.releaseDate = new SimpleStringProperty((String) details.get("release_date"));
	    	this.genre = new SimpleStringProperty(""+details.get("genres")); // this attribute is not a string but a Map !
	    	this.comments = new SimpleStringProperty((String) details.get("overview"));
	    	this.imageURL = Searcher.getImageURL((String) details.get("poster_path"));
	    	System.out.println(this.imageURL);
	    	
	    }
	     

}


