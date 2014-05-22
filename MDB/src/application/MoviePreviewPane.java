package application;

import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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
