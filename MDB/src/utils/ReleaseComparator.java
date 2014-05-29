package utils;

import java.util.Comparator;

import application.MovieTile;
import application.SeriesTile;
import javafx.scene.Node;

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
			MovieTile m1 = (MovieTile) arg0;
			MovieTile m2 = (MovieTile) arg1;
			return coeff*m1.getMovie().releaseDate.compareToIgnoreCase(m2.getMovie().releaseDate);
		} else {
			SeriesTile s1 = (SeriesTile) arg0;
			SeriesTile s2 = (SeriesTile) arg1;
			return coeff*s1.getSeries().firstAirDate.compareToIgnoreCase(s2.getSeries().firstAirDate);
		}
	}
}