package application;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Searcher {

	// key required to use TMDB
	private static final String KEY = "c004bc36ea17626b2bee62a7b7d5b0fb";
	private static final String BASE_API = "http://api.themoviedb.org/3/";

	public List<Map<String, Object>> searchMovie( String query ) {
		String url = BASE_API + "search/movie?query=" + query + "&api_key=" + KEY;
		return search(url);
	}
	
	public List<Map<String, Object>> searchTVSeries( String query ) {
		String url = BASE_API + "search/tv?query=" + query + "&api_key=" + KEY;
		return search(url);
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
}
