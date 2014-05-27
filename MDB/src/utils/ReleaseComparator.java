package utils;

import java.util.Comparator;

import application.MovieTile;
import javafx.scene.Node;

public class ReleaseComparator implements Comparator<Node> {
	boolean isAscending;
	
	public ReleaseComparator(boolean isAscending) {
		this.isAscending = isAscending;
	}


	@Override
	public int compare(Node arg0, Node arg1) {
		MovieTile m1 = (MovieTile) arg0;
		MovieTile m2 = (MovieTile) arg1;
		int coeff = isAscending ? 1 : -1;
		return coeff*m1.getMovie().releaseDate.compareToIgnoreCase(m2.getMovie().releaseDate);
	}
}