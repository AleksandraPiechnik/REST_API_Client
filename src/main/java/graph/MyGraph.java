package graph;

import actorsAndmovies.Actor;
import actorsAndmovies.Movie;
import connection.MoviesFinder;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.SimpleGraph;
import java.util.ArrayList;
import java.util.List;

public class MyGraph {
    private Graph<Actor, Movie> g = new SimpleGraph(Movie.class);
    private Actor actorA;
    private Actor actorB;
    private VertexQueue queue = new VertexQueue();
    boolean flag = true;

    public MyGraph(Actor actorA, Actor actorB) {
        this.actorA = actorA;
        this.actorB = actorB;

    }

    public void addTopsAndEdges() {

        g.addVertex(actorA);
        queue.push(actorA);

        while (queue.queueSize() > 0 && !g.containsVertex(actorB)) {
            ArrayList<Movie> movieArr;
            MoviesFinder finder1 = new MoviesFinder(queue.getActor().id);
            movieArr = finder1.start();

            addToGraph(movieArr);
            queue.pop();
        }

    }

    private void addToGraph(final ArrayList<Movie> movieArr) {

        for (int i = 0; i < movieArr.size(); i++) {

            movieArr.get(i).getActors();
            for (int j = 0; j < movieArr.get(i).actorArrayList.size(); j++) {

                if (!g.containsVertex(movieArr.get(i).actorArrayList.get(j)) && !(queue.getActor().equals(movieArr.get(i).actorArrayList.get(j)))) {
                    g.addVertex(movieArr.get(i).actorArrayList.get(j));
                    Movie movie = movieArr.get(i);
                    if (g.containsEdge(movie)) {
                        movie = (Movie) (movieArr.get(i)).clone();
                    }
                    g.addEdge(queue.getActor(), movieArr.get(i).actorArrayList.get(j), movie);
                    queue.push(movieArr.get(i).actorArrayList.get(j));
                    if (((movieArr.get(i).actorArrayList.get(j)).equals(actorB))) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag == false) break;
        }
    }

    public String findShortestPath() {
        String answer = "";
        BellmanFordShortestPath<Actor, Movie> bfsp = new BellmanFordShortestPath<>(g);
        GraphPath<Actor, Movie> shortestPath = bfsp.getPath(actorA, actorB);

        List<Movie> edges = shortestPath.getEdgeList();
        List<Actor> actors = shortestPath.getVertexList();

        for (int i = 0; i < actors.size(); ++i) {
            if (i == actors.size() - 1) answer += actors.get(i);
            else answer += actors.get(i) + "\n -> \n" + edges.get(i).toString() + "\n ->\n";
        }
        return answer;
    }
}
