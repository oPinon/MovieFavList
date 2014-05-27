package application;

import movie.Movie;
import javafx.collections.ObservableList;

public interface MoviePane {
	public void add(Movie movie);
	public void remove(Movie movie);
	public void clear();
	public ObservableList<Movie> getMovies();
}
