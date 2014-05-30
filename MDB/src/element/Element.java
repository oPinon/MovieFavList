package element;

import java.util.List;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public abstract class Element {
	public int id;	
	
	public List<String> genres;
	public String homepage;
	public Map<String, Object> credits;
	
	public double internetRating;
	public Image poster;
	
	public IntegerProperty rating;
	public StringProperty comments;
}
