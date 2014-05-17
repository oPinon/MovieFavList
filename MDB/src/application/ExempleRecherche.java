package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Cet exemple effectue quelques recherches simples dans la base de donn�es, et affiche de d�tail des r�sultats dans la console.
 * @author REMI
 *
 */
public class ExempleRecherche {

	// tous les �l�ves utiliseront cette cl�. Elle nous a �t� fournie par les admnistrateurs de TMDB
	private static final String KEY = "c004bc36ea17626b2bee62a7b7d5b0fb";
			
	private static final String BASE_API = "http://api.themoviedb.org/3/";
	
	
	public List<Map<String, Object>> rechercherDansLesFilms(String recherche) {
		String url = BASE_API + "search/movie?query=" + recherche + "&api_key=" + KEY;
		return afficherLesResultats(url);
	}
	
	public List<Map<String, Object>> rechercherDansLesSeriesTV(String recherche) {
		String url = BASE_API + "search/tv?query=" + recherche + "&api_key=" + KEY;
		return afficherLesResultats(url);
	}
	
	private List<Map<String, Object>> afficherLesResultats(String url) {
		try {
			// Cet objet permet d'extraire les informations retourn�es par le serveur et de les organiser sous forme de paires
			// {cl�/valeur}. Exemple: "original_title" / "Star Wars: Episode IV - A New Hope"
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> rawMap = mapper.readValue(new URL(url), Map.class);
			// lorsqu'on effectue une recherche, le mapper va permettre d'acceder � liste des r�sultats.
			// cette liste s'obtient via la cl�e "results".
			List<Map<String, Object>> results = (List) rawMap.get("results");
			// chaque r�sultat de la recherche est organis� sous forme d'une liste de paires cl�s/valeurs.
			for(Map<String, Object> entry: results) {
				for(String key : entry.keySet()) {
					// ici, on se contente d'affiche le r�sultat dans la console. A vous d'en faire un usage plus "avanc�"...
					// IMPORTANT: parmi les diff�rentes cl�s se trouve l'id, qui permet ensuite beaucoup plus facilement d'obtenir des d�tails sur
					// le film ou la s�rie.
					System.out.println("key = " + key + " / value =  " + entry.get(key));
					
				} 
			}
			System.out.println();
			return results;
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
		ExempleRecherche searcher = new ExempleRecherche();
		String film1 = "Star+Wars";
		searcher.rechercherDansLesFilms(film1);
		String filmVengeance = "Revenge";
		searcher.rechercherDansLesFilms(filmVengeance);
		
		String serie1 = "Game";
		searcher.rechercherDansLesSeriesTV(serie1);
		
	}
	
}
