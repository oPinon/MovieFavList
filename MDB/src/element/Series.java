package element;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import utils.Searcher;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Series {
	public int id;
	public String name, originalName;
	public String firstAirDate, lastAirDate;
	public List<String> genres;
	public int episodes, seasons;
	public List<Integer> episodeRunTime;
	public List<String> creators, originCountry;
	public List<String> networks, languages;
	public String homepage;

	public Map<String, Object> credits;
	public String overview;
	public double internetRating;
	public Image poster;
	
	public IntegerProperty rating;
	public StringProperty comments;
	
	public Series(int id) {
		this.id = id;
		initFields(id);
		
		this.rating = new SimpleIntegerProperty(5);
		this.comments = new SimpleStringProperty("");
	}

	public Series(int id, int rating, String comments) {
		this.id = id;
		initFields(id); 

		this.rating = new SimpleIntegerProperty(rating);
		this.comments = new SimpleStringProperty(comments);
	}

	public Series(Map<String,Object> preview) {
		this.id = (int) preview.get("id");
		this.name = (String) preview.get("name");
		this.firstAirDate = (String) preview.get("first_air_date");
		this.poster = new Image(Searcher.getImageURL((String) preview.get("poster_path"), true));
		this.creators = mapToList((ArrayList<Map<String,String>>) Searcher.getSeries(id).get(("created_by")));

		if(firstAirDate == null) firstAirDate = "";
	}

	static private ArrayList<String> mapToList(ArrayList<Map<String,String>> map) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for(Map<String,String> entry : map) {
			toReturn.add(entry.get("name"));
		}
		if(toReturn.size()==0) toReturn.add("");
		return toReturn;
	}
	
	private void initFields(int id){
		Map<String,Object> details = Searcher.getSeries(id);

		this.name = (String) details.get("name");
		this.originalName = (String) details.get("original_name");
		this.firstAirDate = (String) details.get("first_air_date");
		this.lastAirDate = (String) details.get("last_air_date");

		this.episodes = (int) details.get("number_of_episodes");
		this.seasons = (int) details.get("number_of_seasons");
		this.episodeRunTime = (List<Integer>) details.get("episode_run_time");	

		this.creators = mapToList((ArrayList<Map<String,String>>) details.get(("created_by")));
		this.originCountry = (List<String>) details.get("origin_country");
		this.credits = Searcher.getSeriesCredits(id);
		this.networks = mapToList((ArrayList<Map<String,String>>) details.get(("networks")));
		this.languages = (List<String>) details.get("languages");
		this.homepage = (String) details.get("homepage");	

		this.genres = mapToList((ArrayList<Map<String,String>>) details.get(("genres")));
		this.overview = (String) details.get("overview");	 
	
		this.internetRating = (Double) details.get("vote_average");
		this.poster = new Image(Searcher.getImageURL((String) details.get("poster_path"), false));

		if(firstAirDate == null) firstAirDate = "";
		if(lastAirDate == null) lastAirDate = "";	
		if(originalName == null) originalName = "";	
	}

	public void increaseRating(){
		rating.setValue(Math.min(10,rating.getValue()+1));
	}

	public void decreaseRating(){
		rating.setValue(Math.max(0,rating.getValue()-1));
	}

}


