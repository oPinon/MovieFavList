package utils;

import java.util.Comparator;

import javafx.scene.Node;
import application.Tile;
import element.Movie;
import element.Series;

public class InternetRatingComparator implements Comparator<Node> {
	boolean isAscending;
	boolean isMovieSorting;

	public InternetRatingComparator(boolean isMovieSorting, boolean isAscending) {
		this.isAscending = isAscending;
		this.isMovieSorting = isMovieSorting;
	}

	@Override
	public int compare(Node arg0, Node arg1) {
		int coeff = isAscending ? 1 : -1;
		if(isMovieSorting){
			Tile<Movie> m1 = (Tile<Movie>) arg0;
			Tile<Movie> m2 = (Tile<Movie>) arg1;
			return coeff*Double.compare(m1.getElement().internetRating,m2.getElement().internetRating);
		} else {
			Tile<Series> s1 = (Tile<Series>) arg0;
			Tile<Series> s2 = (Tile<Series>) arg1;
			return coeff*Double.compare(s1.getElement().internetRating,s2.getElement().internetRating);
		}
	}
}