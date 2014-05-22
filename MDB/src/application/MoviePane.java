package application;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

public class MoviePane extends TilePane {

	private ObservableList<Node> favs = FXCollections.observableArrayList();

	public MoviePane() {
		//	setPrefColumns(2);

		setVgap(0);
		setHgap(10);
		
	//	setId("mainPane");


		Bindings.bindContentBidirectional(favs,this.getChildren());

	}



//	public void updateDisplay(boolean isPreview){
//		for(Movie m: favs){	
//			if(!isPreview){
//				MovieTile mt = new MovieTile(m,mp);
//				TilePane.setAlignment(mt, Pos.TOP_LEFT);
//				getChildren().add(mt);
//			}
//			else{
//				//MoviePreviewTile mt = new MoviePreviewTile(m,mp);
//				TilePane.setAlignment(mt, Pos.TOP_LEFT);
//				getChildren().add(mt);
//			}
//		}		
//	}

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
		TilePane.setAlignment(mt, Pos.TOP_LEFT);
	}

	public void remove(Movie movie) {
		for(Node mt: favs){
			if(mt instanceof MovieTile){
				if(((MovieTile) mt).movie.id==movie.id){
					favs.remove(mt);
					return;
				}	
			}		
		}	
	}



	public void clear() {
		favs.clear();

	}



}
