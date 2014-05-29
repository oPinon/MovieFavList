package utils;

import java.util.Comparator;
import java.util.List;

import application.MovieTile;
import application.SeriesTile;
import javafx.scene.Node;

public class DirectorComparator implements Comparator<Node> {
	boolean isAscending;
	boolean isMovieSorting;

	public DirectorComparator(boolean isMovieSorting, boolean isAscending) {
		this.isAscending = isAscending;
		this.isMovieSorting = isMovieSorting;
	}

	@Override
	public int compare(Node arg0, Node arg1) {
		int coeff = isAscending ? 1 : -1;
		if(isMovieSorting){
			MovieTile m1 = (MovieTile) arg0;
			MovieTile m2 = (MovieTile) arg1;
			return coeff*m1.getMovie().director.compareToIgnoreCase(m2.getMovie().director);
		} else {
			SeriesTile s1 = (SeriesTile) arg0;
			SeriesTile s2 = (SeriesTile) arg1;
			return coeff*s1.getSeries().creators.get(0).compareToIgnoreCase(s2.getSeries().creators.get(0));
		}
	}
}