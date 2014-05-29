package application;

import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.stage.Stage;

public class Main extends Application{

	public void start(final Stage stage) {
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Collections");
		Tab tab2 = new Tab("Search");
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		final Scene scene = new Scene(tabPane,600,600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		CollectionTab colTab = new CollectionTab(stage);
		SearchTab searchTab = new SearchTab(colTab);

		tab1.setContent(colTab);
		tab2.setContent(searchTab);
		tabPane.getTabs().addAll(tab1,tab2);

		stage.setScene(scene);
		stage.titleProperty().bind(new When(colTab.fileName.isNotNull())
		.then( new SimpleStringProperty("Movie List - ").concat(colTab.fileName))
		.otherwise( new SimpleStringProperty("Movie List - No title")) );				
		stage.setMinHeight(350);
		stage.setMinWidth(550);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
