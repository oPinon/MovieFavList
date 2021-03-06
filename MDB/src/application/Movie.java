package application;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;


public class Movie {
	public int id;	
	public String title;
	public String releaseDate;
	public ArrayList<String> genres;
	public StringProperty comments;
	public double rating;
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
		comments = new SimpleStringProperty("");
		this.plot = (String) details.get("overview");	
		this.poster = new Image(Searcher.getImageURL((String) details.get("poster_path")));
		this.rating = (Double) details.get("vote_average");
		this.credits = Searcher.getCredits(id);
		this.director = getDirector( (List<Map<String,String>>) this.credits.get("crew"));
	}
	
	public Movie(int id, String comments) {
		this.id = id;

		Map<String,Object> details = Searcher.getMovie(id);
		this.title = (String) details.get("title");
		this.releaseDate = (String) details.get("release_date");
		this.genres = importGenres((ArrayList<Map<String,String>>) details.get(("genres")));
		this.comments = new SimpleStringProperty(comments);
		this.plot = (String) details.get("overview");	
		this.poster = new Image(Searcher.getImageURL((String) details.get("poster_path")));
		this.rating = (Double) details.get("vote_average");
		this.credits = Searcher.getCredits(id);
		this.director = getDirector( (List<Map<String,String>>) this.credits.get("crew"));

	}

	public Movie(Map<String,Object> preview) {
		this.id = (int) preview.get("id");
		this.releaseDate = (String) preview.get("release_date");
		this.title = (String) preview.get("title");	
		comments = new SimpleStringProperty();
		this.poster = new Image(Searcher.getImageURL((String) preview.get("poster_path")));
		this.director = getDirector( (List<Map<String,String>>) Searcher.getCredits(id).get("crew"));

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



}


