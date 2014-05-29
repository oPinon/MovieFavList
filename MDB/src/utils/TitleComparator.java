package utils;

import java.util.Comparator;

import application.MovieTile;
import application.SeriesTile;
import javafx.scene.Node;

public class TitleComparator implements Comparator<Node> {
	boolean isAscending;
	boolean isMovieSorting;

	public TitleComparator(boolean isMovieSorting, boolean isAscending) {
		this.isAscending = isAscending;
		this.isMovieSorting = isMovieSorting;
	}

	@Override
	public int compare(Node arg0, Node arg1) {
		int coeff = isAscending ? 1 : -1;
		if(isMovieSorting){
			MovieTile m1 = (MovieTile) arg0;
			MovieTile m2 = (MovieTile) arg1;
			return coeff*m1.getMovie().title.compareToIgnoreCase(m2.getMovie().title);
		} else {
			SeriesTile s1 = (SeriesTile) arg0;
			SeriesTile s2 = (SeriesTile) arg1;
			return coeff*s1.getSeries().name.compareToIgnoreCase(s2.getSeries().name);
		}

	}
}