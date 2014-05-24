package application;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

public class MoviePane extends TilePane {

	private ObservableList<Node> favs = FXCollections.observableArrayList();
	ReadOnlyBooleanProperty emptyProperty;

	public MoviePane() {
		//	setPrefColumns(2);

		setVgap(0);
		setHgap(10);

		Bindings.bindContentBidirectional(favs,this.getChildren());
		emptyProperty = new SimpleListProperty<Node>(favs).emptyProperty();

	}

	public void add(Movie movie){
		for(Node mt: favs){
			if(mt instanceof MovieTile){
				if(((MovieTile) mt).movie.id==movie.id){
					return;
				}	
			}		
		}	
		MovieTile mt = new MovieTile(movie,this);
		favs.add(mt);
		FadeTransition ft = new FadeTransition(Duration.millis(1000), mt);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		TilePane.setAlignment(mt, Pos.TOP_LEFT);
	}

	public void remove(Movie movie) {
		for(final Node mt: favs){
			if(mt instanceof MovieTile){
				if(((MovieTile) mt).movie.id==movie.id){
					FadeTransition ft = new FadeTransition(Duration.millis(500), mt);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.play();
					ft.setOnFinished(new EventHandler<ActionEvent>(){
			            public void handle(ActionEvent arg0) {
			                	favs.remove(mt);
			            }
			        });
					return;
				}	
			}		
		}	
	}

	public void clear() {
		favs.clear();
	}

	public ObservableList<Movie> getMovies() {
		ObservableList<Movie> toReturn = FXCollections.observableArrayList();;
		for(Node mt: favs){
			if(mt instanceof MovieTile){			
				toReturn.add( ((MovieTile) mt).movie );
			}	
		}			
		return toReturn;
	}



}
