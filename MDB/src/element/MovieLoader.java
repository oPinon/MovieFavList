package element;

import java.util.Map;

import javafx.concurrent.Task;

public class MovieLoader extends Task<Movie>{
	int id;
	int rating;
	String comments;
	Map<String, Object> entry;
	
	public MovieLoader(int id) {
		this.id = id;
		
	}
	public MovieLoader(int id, int rating, String comments) {
		this.id = id;
		this.rating = rating;
		this.comments = comments;
		
	}
	public MovieLoader(Map<String, Object> entry) {
		this.entry = entry;
	}

	protected Movie call() throws Exception {
		if(comments == null && entry == null) return new Movie(id);
		if(entry == null) return new Movie(id, rating, comments);
		else return new Movie(entry);
	}

}