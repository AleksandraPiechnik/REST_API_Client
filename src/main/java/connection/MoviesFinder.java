package connection;

import actorsAndmovies.Movie;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MoviesFinder {
    private String actorId;
    private static HttpURLConnection connection;
    private BufferedReader reader;
    private String line;
    private StringBuffer responseContent= new StringBuffer();

    public MoviesFinder(String actorId) {
        this.actorId = actorId;
    }
    public ArrayList<Movie> start(){
        connect();
        return createArray();
    }

    private ArrayList<Movie> createArray() {
        ArrayList<Movie> temp= new ArrayList<>();
        if(isContentEmpty())return null;
        else{

            JSONArray albums= new JSONArray(responseContent.toString());
            for (int i = 0; i <albums.length() ; i++) {
                JSONObject album= albums.getJSONObject(i);
                temp.add(new Movie(album.getString("id")));
            }

        }
        return temp;
    }

    private void connect(){
        try {
            URL url = new URL("https://java.kisim.eu.org/actors/"+actorId+"/movies");
            System.out.println("https://java.kisim.eu.org/actors/"+actorId+"/movies");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) { responseContent.append(line); }
                reader.close();

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) { responseContent.append(line); }
                reader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ActorByNameFinder problem occurred");

        }
        finally {
            connection.disconnect();
        }
    }
    private boolean isContentEmpty(){
        JSONArray albums= new JSONArray(responseContent.toString());
        if(albums.isEmpty())  return true;
        else return false;
    }
}
