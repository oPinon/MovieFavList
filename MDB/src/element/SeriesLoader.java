package element;

import java.util.Map;

import javafx.concurrent.Task;

public class SeriesLoader extends Task<Series>{
	int id;
	int rating;
	String comments;
	Map<String, Object> entry;
	
	public SeriesLoader(int id) {
		this.id = id;
		
	}
	public SeriesLoader(int id, int rating, String comments) {
		this.id = id;
		this.rating = rating;
		this.comments = comments;
		
	}
	public SeriesLoader(Map<String, Object> entry) {
		this.entry = entry;
	}

	protected Series call() throws Exception {
		if(comments == null && entry == null) return new Series(id);
		if(entry == null) return new Series(id, rating, comments);
		else return new Series(entry);
	}

}