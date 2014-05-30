package application;

import element.Element;

public interface Tile<T extends Element> {
	public T getElement();
}
