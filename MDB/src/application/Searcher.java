package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javafx.scene.image.Image;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Searcher {

	// key required to use TMDB
	private static final String KEY = "c004bc36ea17626b2bee62a7b7d5b0fb";
	private static final String BASE_API = "http://api.themoviedb.org/3/";
	private static final String MOVIE = "movie/";
	private static final String CREDITS = "/credits";
	private static final String IMAGE_API = "http://image.themoviedb.org/3/";

	public List<Map<String, Object>> searchMovie( String query ) {
		String url = BASE_API + "search/movie?query=" + query + "&api_key=" + KEY;
		return search(url);
	}

	public List<Map<String, Object>> searchTVSeries( String query ) {
		String url = BASE_API + "search/tv?query=" + query + "&api_key=" + KEY;
		return search(url);
	}

	static public Image searchPoster( int query ) {
		String url = BASE_API + MOVIE + query + "?api_key=" + KEY;
		String posterurl = recupererLePoster(url);
		if(posterurl == null) return null;
		else return new Image( "http://image.tmdb.org/t/p/w500" + posterurl );
	}

	private List<Map<String, Object>> search(String url) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> rawMap = mapper.readValue(new URL(url), Map.class);
			return (List) rawMap.get("results");
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

	private static String recupererLePoster(String url) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> rawMap = mapper.readValue(new URL(url), Map.class);
			for(String key: rawMap.keySet()) {
				if (key.equals("poster_path")) {
					if(rawMap.get(key)==null) return null;
					else return rawMap.get(key).toString();
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
	
	public static Map<String, Object> getMovie(int ID) {
		try {
			String url = BASE_API + MOVIE + ID + "?api_key=" + KEY;
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Object> rawMap = mapper.readValue(new URL(url), Map.class);
			/*for(String key: rawMap.keySet()) {
				System.out.println(key + " : "+ rawMap.get(key));
			}*/
			return rawMap;
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
	
	public static Map<String, Object> getCredits(int ID) {
		try {
			String url = BASE_API + MOVIE + ID + CREDITS + "?api_key=" + KEY;
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(new URL(url), Map.class);
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
	
	public static String getImageURL(String image) {
		return "http://image.tmdb.org/t/p/w154"+image;
		// replace "original" by "w92", "w154","w185","w342","w500","w780"
	}
}
