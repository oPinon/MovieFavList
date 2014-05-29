package utils;

import java.util.Comparator;

import application.MovieTile;
import application.SeriesTile;
import javafx.scene.Node;

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
			MovieTile m1 = (MovieTile) arg0;
			MovieTile m2 = (MovieTile) arg1;
			return coeff*Double.compare(m1.getMovie().internetRating,m2.getMovie().internetRating);
		} else {
			SeriesTile s1 = (SeriesTile) arg0;
			SeriesTile s2 = (SeriesTile) arg1;
			return coeff*Double.compare(s1.getSeries().internetRating,s2.getSeries().internetRating);
		}
	}
}