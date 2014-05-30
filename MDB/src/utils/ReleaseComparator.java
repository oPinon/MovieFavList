package utils;

import java.util.Comparator;

import javafx.scene.Node;
import application.Tile;
import element.Movie;
import element.Series;

public class ReleaseComparator implements Comparator<Node> {
	boolean isAscending;
	boolean isMovieSorting;

	public ReleaseComparator(boolean isMovieSorting, boolean isAscending) {
		this.isAscending = isAscending;
		this.isMovieSorting = isMovieSorting;
	}


	@Override
	public int compare(Node arg0, Node arg1) {
		int coeff = isAscending ? 1 : -1;
		if(isMovieSorting){
			Tile<Movie> m1 = (Tile<Movie>) arg0;
			Tile<Movie> m2 = (Tile<Movie>) arg1;
			return coeff*m1.getElement().releaseDate.compareToIgnoreCase(m2.getElement().releaseDate);
		} else {
			Tile<Series> s1 = (Tile<Series>) arg0;
			Tile<Series> s2 = (Tile<Series>) arg1;
			return coeff*s1.getElement().firstAirDate.compareToIgnoreCase(s2.getElement().firstAirDate);
		}
	}
}