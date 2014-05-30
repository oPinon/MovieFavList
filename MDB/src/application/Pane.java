package application;

import javafx.collections.ObservableList;
import element.Element;

public interface Pane<T extends Element> {
	public void add(T element);
	public void remove(T element);
	public void clear();
	public ObservableList<T> getElements();
}
