package application;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.TilePane;

public class MoviePreviewPane extends TilePane {

	ObservableList<Node> favs = FXCollections.observableArrayList();
	MoviePane mp;

	public MoviePreviewPane(MoviePane main) {
		this.mp = main;

		//	setPrefColumns(2);

		setVgap(0);
		setHgap(10);


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

	public void add(Movie m){
		MoviePreviewTile mt = new MoviePreviewTile(m,this, mp);
		favs.add(mt);
		TilePane.setAlignment(mt, Pos.TOP_LEFT);
	}

	public void clear() {
		favs.clear();

	}



}
