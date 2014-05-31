package application;

import javafx.application.Application;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{

	static Text message;
	
	public void start(final Stage stage) {
		VBox mainBox = new VBox(2);
		
		TabPane tabPane = new TabPane();
		Tab tab1 = new Tab("Favorites");
		Tab tab2 = new Tab("Search");
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		VBox.setVgrow(tabPane, Priority.ALWAYS);

		final Scene scene = new Scene(mainBox,600,600);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		tabPane.tabMinWidthProperty().bind(scene.widthProperty().divide(3));

		FavoritesTab favTab = new FavoritesTab(stage);
		SearchTab searchTab = new SearchTab(favTab);

		tab1.setContent(favTab);
		tab2.setContent(searchTab);
		tabPane.getTabs().addAll(tab1,tab2);
		
		ToolBar messageBar = new ToolBar();
		message = new Text();
		messageBar.prefWidthProperty().bind(scene.widthProperty());
		messageBar.setId("optionBar");
		message.setText("Welcome !");
		messageBar.getItems().add(message);

		mainBox.getChildren().addAll(tabPane,messageBar);

		stage.setScene(scene);
		stage.titleProperty().bind(new When(favTab.fileName.isNotNull())
		.then( new SimpleStringProperty("Movie List - ").concat(favTab.fileName))
		.otherwise( new SimpleStringProperty("Movie List - No title")) );				
		stage.setMinHeight(350);
		stage.setMinWidth(550);
		stage.show();
		
		favTab.init();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static void print(String s) {
		message.setText(s);
	}
}
