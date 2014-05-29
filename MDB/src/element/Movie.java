package movie;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import utils.Searcher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Movie {
	public int id;	
	public String title;
	public String releaseDate;
	public List<String> genres;
	public StringProperty comments;
	public double internetRating;
	public IntegerProperty rating;
	public String plot;
	public Image poster;
	public String director;
	public Map<String, Object> credits;


	public Movie(int id) {
		this.id = id;

		Map<String,Object> details = Searcher.getMovie(id);

		this.title = (String) details.get("title");
		this.releaseDate = (String) details.get("release_date");
		this.genres = importGenres((ArrayList<Map<String,String>>) details.get(("genres")));
		this.comments = new SimpleStringProperty("");
		this.plot = (String) details.get("overview");	 
		this.internetRating = (Double) details.get("vote_average");
		this.credits = Searcher.getCredits(id);
		this.poster = new Image(Searcher.getImageURL((String) details.get("poster_path"), false));
		this.director = getDirector( (List<Map<String,String>>) Searcher.getCredits(id).get("crew"));
		this.rating = new SimpleIntegerProperty(5);
	}

	public Movie(int id, int rating, String comments) {
		this.id = id;

		Map<String,Object> details = Searcher.getMovie(id);

		this.title = (String) details.get("title");
		this.releaseDate = (String) details.get("release_date");
		this.genres = importGenres((ArrayList<Map<String,String>>) details.get(("genres")));
		this.comments = new SimpleStringProperty(comments);
		this.plot = (String) details.get("overview");	 
		this.internetRating = (Double) details.get("vote_average");
		this.credits = Searcher.getCredits(id);
		this.poster = new Image(Searcher.getImageURL((String) details.get("poster_path"), false));
		this.director = getDirector( (List<Map<String,String>>) Searcher.getCredits(id).get("crew"));
		this.rating = new SimpleIntegerProperty(rating);
	}

	public Movie(Map<String,Object> preview) {
		this.id = (int) preview.get("id");
		this.releaseDate = (String) preview.get("release_date");
		this.title = (String) preview.get("title");	
		comments = new SimpleStringProperty();
		this.director = getDirector( (List<Map<String,String>>) Searcher.getCredits(id).get("crew"));
		this.poster = new Image(Searcher.getImageURL((String) preview.get("poster_path"), true));

	}

	static private ArrayList<String> importGenres(ArrayList<Map<String,String>> genres) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for(Map<String,String> entry : genres) {
			toReturn.add(entry.get("name"));
		}
		return toReturn;
	}

	static String getDirector(List<Map<String,String>> crew) {
		for(Map<String,String> member : crew) {
			if(member.get("job").equals("Director"))  { return member.get("name"); }
		}
		return "";
	}
	
	public void increaseRating(){
		rating.setValue(Math.min(10,rating.getValue()+1));
	}
	
	public void decreaseRating(){
		rating.setValue(Math.max(0,rating.getValue()-1));
	}

}


