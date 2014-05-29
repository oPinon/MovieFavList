package application;

import element.Series;
import javafx.collections.ObservableList;

public interface SeriesPane {
	public void add(Series series);
	public void remove(Series series);
	public void clear();
	public ObservableList<Series> getSeries();
}
