package application;

import javafx.concurrent.Task;

public class MovieLoader extends Task<Movie>{
	int id;
	String comments;
	
	public MovieLoader(int id, String comments) {
		this.id = id;
		this.comments = comments;
		updateMessage("Ready to load");
	}
	@Override
	protected Movie call() throws Exception {
		updateMessage("Loading...");
		Movie movie = new Movie(id,comments);
		updateMessage("Movie loaded");
		return movie;
	}
}