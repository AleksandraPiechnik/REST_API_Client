package applicationManager;

import actorsAndmovies.Actor;
import connection.ActorByNameFinder;

public class NameExistenceChecker {
    private String name;
    private ActorByNameFinder finder;

    public NameExistenceChecker(String nameA) {
        name=nameA;
    }
    public boolean exist(){
    finder =new ActorByNameFinder(name);
    return finder.connect();

    }
    public Actor getIdAndName(){
        return finder.parse();
    }

}
