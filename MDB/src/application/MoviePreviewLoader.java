package application;

import java.util.Map;

import javafx.concurrent.Task;

public class MoviePreviewLoader extends Task<Movie>{
	Map<String, Object> preview;
	
	public MoviePreviewLoader(Map<String,Object> preview) {
		this.preview = preview;
		updateMessage("Ready to load");
	}
	@Override
	protected Movie call() throws Exception {
		updateMessage("Loading...");
		Movie movie = new Movie(preview);
		updateMessage("Movie loaded");
		return movie;
	}
}