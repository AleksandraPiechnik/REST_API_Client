package connection;

import actorsAndmovies.Actor;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ActorByNameFinder {
    private HttpURLConnection connection;
    private BufferedReader reader;
    private String line;
    private StringBuffer responseContent= new StringBuffer();
    private String name;


    public ActorByNameFinder(String name) {
        String temp[]=name.split(" ");
        name="";
        for (int i = 0; i <temp.length ; i++) {
            if(i!=temp.length-1)name+=temp[i]+="%20";
            else name+=temp[i];
        }
        this.name = name;
    }

    public boolean connect() {
        try {
            URL url = new URL("https://java.kisim.eu.org/actors/search/"+name);
            System.out.println("https://java.kisim.eu.org/actors/search/"+name);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                while ((line = reader.readLine()) != null) { responseContent.append(line); }
                reader.close();
                return false;
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) { responseContent.append(line); }
                reader.close();

                if(isContentEmpty())return false;
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("ActorByNameFinder problem occurred");
            return false;
        }
        finally {
            connection.disconnect();
        }

    }
    public Actor parse(){
        JSONArray albums= new JSONArray(responseContent.toString());
        JSONObject album= albums.getJSONObject(0);
        Actor actor=new Actor(album.getString("id"),album.getString("name"));
        return actor;
    }
    private boolean isContentEmpty(){
        JSONArray albums= new JSONArray(responseContent.toString());
       if(albums.isEmpty())  return true;
       else return false;
    }
}
