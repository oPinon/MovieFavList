package application;

import element.Movie;
import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

public class MoviePreviewPane extends TilePane implements MoviePane {

	ObservableList<Node> favs = FXCollections.observableArrayList();
	MovieMainPane mmp;
	ReadOnlyBooleanProperty emptyProperty;

	public MoviePreviewPane(MovieMainPane mmp) {
		this.mmp = mmp;

		setVgap(0);
		setHgap(10);

		Bindings.bindContentBidirectional(favs, this.getChildren());
		emptyProperty = new SimpleListProperty<Node>(favs).emptyProperty();
	}

	public void add(Movie m){
		MoviePreviewTile mt = new MoviePreviewTile(m,mmp);
		favs.add(mt);
		FadeTransition ft = new FadeTransition(Duration.millis(1000), mt);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		
		TilePane.setAlignment(mt, Pos.TOP_LEFT);
	}

	public void remove(Movie movie) {
		for(final Node mt: favs){
			if(mt instanceof MovieMainTile){
				if(((MovieMainTile) mt).movie.id==movie.id){
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
			if(mt instanceof MovieMainTile){			
				toReturn.add( ((MovieMainTile) mt).movie );
			}	
		}			
		return toReturn;
	}



}
