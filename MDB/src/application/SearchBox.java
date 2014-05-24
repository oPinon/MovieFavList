package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SearchBox extends Region {
	private TextField textBox;
	private Button clearButton;

	public SearchBox() {
        setId("SearchBox");
        getStyleClass().add("searchBox");
		setMinHeight(24);
		setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		
		textBox = new TextField();
		textBox.setMaxWidth(Integer.MAX_VALUE);
		textBox.setPromptText("Search by title");

		clearButton = new Button();
		clearButton.setVisible(false);
		getChildren().addAll(textBox, clearButton);
		clearButton.setOnAction(new EventHandler<ActionEvent>() {                
			@Override public void handle(ActionEvent actionEvent) {
				textBox.setText("");
				textBox.requestFocus();
			}
		});

		textBox.textProperty().addListener(new ChangeListener<String>() {
			@Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				clearButton.setVisible(textBox.getText().length() != 0);
			}
		});
	}


	@Override
	protected void layoutChildren() {
		textBox.resize(getWidth(), getHeight());
		clearButton.resizeRelocate(getWidth() - 24, 6, 16, 16);
	}
	
	public TextField getTextField() {
		return textBox;
	}

	public String getText() {
		return textBox.getText();
	}
}