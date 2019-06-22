package actorsAndmovies;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Movie extends org.jgrapht.graph.DefaultEdge {
    public String id;
    private HttpURLConnection connection;
    private int status;
    private String name;
    public ArrayList<Actor> actorArrayList=new ArrayList<>();
    private StringBuffer responseContent= new StringBuffer();
    private BufferedReader reader;
    private String line;


    @Override
    public String toString() {
        return "Movie{" + name +'}';
    }

    public Movie(String id) {
        this.id = id;
    }
    public void getActors(){
        connect();
        createActorArrayList();

    }

    private void connect() {
        try {

            URL url = new URL("https://java.kisim.eu.org/movies/"+id);
            System.out.println("https://java.kisim.eu.org/movies/"+id);
            connection=null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            status = connection.getResponseCode();
            appendResponseContent();

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

    private void appendResponseContent() {
        try{
        if (status > 299) {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            while ((line = reader.readLine()) != null) { responseContent.append(line); }
            reader.close();

        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) { responseContent.append(line); }
            reader.close();
        }} catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createActorArrayList() {
        ArrayList<Actor> temp= new ArrayList<>();
        if(isContentEmpty())actorArrayList=null;
        else{
            JSONObject obj = new JSONObject(responseContent.toString());
            name=obj.getString("title");
            JSONArray array = obj.getJSONArray("actors");
            for(int i = 0 ; i < array.length() ; i++){
                temp.add(new Actor(array.getJSONObject(i).getString("id"),array.getJSONObject(i).getString("name")));
            }
        }
        actorArrayList=temp;
    }
    private boolean isContentEmpty(){
        JSONObject obj = new JSONObject(responseContent.toString());
        if(obj.isEmpty())  return true;
        else return false;
    }
}
