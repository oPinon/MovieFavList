package application;

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
import element.Series;

public class SeriesPreviewPane extends TilePane implements SeriesPane {

	ObservableList<Node> favs = FXCollections.observableArrayList();
	SeriesMainPane smp;
	ReadOnlyBooleanProperty emptyProperty;

	public SeriesPreviewPane(SeriesMainPane smp) {
		this.smp = smp;

		setVgap(0);
		setHgap(10);

		Bindings.bindContentBidirectional(favs, this.getChildren());
		emptyProperty = new SimpleListProperty<Node>(favs).emptyProperty();
	}

	public void add(Series series){
		SeriesPreviewTile mt = new SeriesPreviewTile(series,smp);
		favs.add(mt);
		FadeTransition ft = new FadeTransition(Duration.millis(1000), mt);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();
		
		TilePane.setAlignment(mt, Pos.TOP_LEFT);
	}

	public void remove(Series series) {
		for(final Node mt: favs){
			if(mt instanceof SeriesMainTile){
				if(((SeriesMainTile) mt).series.id==series.id){
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

	public ObservableList<Series> getSeries() {
		ObservableList<Series> toReturn = FXCollections.observableArrayList();;
		for(Node mt: favs){
			if(mt instanceof SeriesMainTile){			
				toReturn.add( ((SeriesMainTile) mt).series );
			}	
		}			
		return toReturn;
	}



}
