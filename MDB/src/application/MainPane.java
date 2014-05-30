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
import element.Element;

public class MainPane<T extends Element> extends TilePane implements Pane<T>{

	private ObservableList<Node> favs = FXCollections.observableArrayList();
	ReadOnlyBooleanProperty emptyProperty;

	public MainPane() {
		//	setPrefColumns(2);

		setVgap(0);
		setHgap(10);

		Bindings.bindContentBidirectional(favs,this.getChildren());
		emptyProperty = new SimpleListProperty<Node>(favs).emptyProperty();

	}

	public void add(T element){
		for(Node mt: favs){
			if(mt instanceof MainTile){
				if(((MainTile<T>) mt).element.id==element.id){
					return;
				}	
			}		
		}
		MainTile<T> mmt = new MainTile<T>(element,this);
		synchronized(this) {favs.add(mmt);}
		FadeTransition ft = new FadeTransition(Duration.millis(1000), mmt);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.play();

		TilePane.setAlignment(mmt, Pos.TOP_LEFT);
	}

	public void remove(T element) {
		for(final Node mt: favs){
			if(mt instanceof MainTile){
				if(((MainTile<T>) mt).element.id==element.id){
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

	public ObservableList<T> getElements() {
		ObservableList<T> toReturn = FXCollections.observableArrayList();;
		for(Node mt: favs){
			if(mt instanceof MainTile){			
				toReturn.add( ((MainTile<T>) mt).element );
			}	
		}			
		return toReturn;
	}

}
