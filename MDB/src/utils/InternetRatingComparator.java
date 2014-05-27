package utils;

import java.util.Comparator;

import application.MovieTile;
import javafx.scene.Node;

public class InternetRatingComparator implements Comparator<Node> {
	boolean isAscending;
	
	public InternetRatingComparator(boolean isAscending) {
		this.isAscending = isAscending;
	}

	@Override
	public int compare(Node arg0, Node arg1) {
		MovieTile m1 = (MovieTile) arg0;
		MovieTile m2 = (MovieTile) arg1;
		int coeff = isAscending ? 1 : -1;
		return coeff*Double.compare(m1.getMovie().internetRating,m2.getMovie().internetRating);
	}
}