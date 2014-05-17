package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Cette exemple va r�cup�rer l'identifiant d'une image dans la base des images, et affiche le r�sultat dans la fen�tre.
 * @author REMI
 *
 */
public class ExempleDetail extends Application{

	// tous les �l�ves utiliseront cette cl�. Elle nous a �t� fournie par les admnistrateurs de TMDB
	private static final String KEY = "c004bc36ea17626b2bee62a7b7d5b0fb";
	// remarque: cet ID a �t� obtenu en faisant une recherche sur cette base de donn�es
	private static final String ID = "11";
	private static final String BASE_API = "http://api.themoviedb.org/3/";
	private static final String MOVIE = "movie/";
	private static final String IMAGE_API = "http://image.themoviedb.org/3/";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,500,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			String url = BASE_API + MOVIE + ID + "?api_key=" + KEY;
		    String imgURL = recupererLePoster(url);
		    String urlImg = "http://image.tmdb.org/t/p/w500" + imgURL;
		    Image img = new Image(urlImg);
		    ImageView view = new ImageView(img);
		    root.setCenter(view);
		    primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private String recupererLePoster(String url) {
		try {
			// Cet objet permet d'extraire les informations retourn�es par le serveur et de les organiser sous forme de paires
			// {cl�/valeur}. Exemple: "original_title" / "Star Wars: Episode IV - A New Hope"
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> rawMap = mapper.readValue(new URL(url), Map.class);

			// contrairement � l'exemple de la recherche, quand on requ�te un �l�ment pr�cis (par son ID), on obtient directement la liste des cl�s/valeurs.
			for(String key: rawMap.keySet()) {
				if (key.equals("poster_path")) {
					return rawMap.get(key).toString();
				}
			} 
			System.out.println();
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		launch(args);		
	}
	
}
