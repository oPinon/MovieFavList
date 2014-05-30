package element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import utils.Searcher;

public class Movie extends Element {

	public String title, originalTitle, tagline;
	public String releaseDate;
	public String director;
	public List<String> productionCompanies, productionCountries;
	public List<String> languages;
	public int budget, revenue;
	public String trailerURL;
	public String plot;

	public Movie(int id) {
		this.id = id;
		initFields(id);

		this.rating = new SimpleIntegerProperty(5);
		this.comments = new SimpleStringProperty("");
	}

	public Movie(int id, int rating, String comments) {
		this.id = id;
		initFields(id);

		this.rating = new SimpleIntegerProperty(rating);
		this.comments = new SimpleStringProperty(comments);
	}

	public Movie(Map<String,Object> preview) {
		this.id = (int) preview.get("id");
		this.releaseDate = (String) preview.get("release_date");
		this.title = (String) preview.get("title");	
		comments = new SimpleStringProperty();
		this.director = getDirector( (List<Map<String,String>>) Searcher.getMovieCredits(id).get("crew"));
		this.poster = new Image(Searcher.getImageURL((String) preview.get("poster_path"), true));

	}

	static private ArrayList<String> mapToList(ArrayList<Map<String,String>> map) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for(Map<String,String> entry : map) {
			toReturn.add(entry.get("name"));
		}
		if(toReturn.size()==0) toReturn.add("");
		return toReturn;
	}

	static String getDirector(List<Map<String,String>> crew) {
		for(Map<String,String> member : crew) {
			if(member.get("job").equals("Director"))  { return member.get("name"); }
		}
		return "";
	}

	static String getTrailerURL(List<Map<String,String>> videos) {
		for(Map<String,String> video : videos) { 
			if("YouTube".equalsIgnoreCase(video.get("site"))) { 
				// Enter parameters here, such as "?autoplay=1" 
				return "http://www.youtube.com/embed/" + video.get("key") +"?theme=light&autohide=1&rel=0";
			}
		}
		return "";	
	}

	private void initFields(int id){
		Map<String,Object> details = Searcher.getMovie(id);

		this.title = (String) details.get("title");
		this.originalTitle = (String) details.get("original_title");
		this.tagline = (String) details.get("tagline");	
		this.releaseDate = (String) details.get("release_date");

		this.director = getDirector( (List<Map<String,String>>) Searcher.getMovieCredits(id).get("crew"));
		this.productionCompanies = mapToList((ArrayList<Map<String,String>>) details.get("production_companies") );
		this.productionCountries = mapToList((ArrayList<Map<String,String>>) details.get("production_countries") );
		this.credits = Searcher.getMovieCredits(id);
		this.languages = mapToList((ArrayList<Map<String,String>>) details.get("spoken_languages") );
		this.homepage = (String) details.get("homepage");
		this.trailerURL = getTrailerURL( Searcher.getMovieTrailers(id) );

		this.budget = (int) details.get("budget");
		this.revenue = (int) details.get("revenue");

		this.genres = mapToList((ArrayList<Map<String,String>>) details.get(("genres")));
		this.plot = (String) details.get("overview");

		this.internetRating = (Double) details.get("vote_average");
		this.poster = new Image(Searcher.getImageURL((String) details.get("poster_path"), false));
	}

	public void increaseRating(){
		rating.setValue(Math.min(10,rating.getValue()+1));
	}

	public void decreaseRating(){
		rating.setValue(Math.max(0,rating.getValue()-1));
	}

}


