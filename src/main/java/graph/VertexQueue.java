package graph;


import actorsAndmovies.Actor;

import java.util.ArrayList;
import java.util.List;

public class VertexQueue{

    private List<Actor> list;
    public VertexQueue() {
        list= new ArrayList();
    }
    public void push(Actor actor){
        list.add(actor);
    }
    public void pop(){
        if(list.size()>0)list.remove(0);
    }
    public int queueSize(){
       return list.size();
    }
    public Actor getActor(){
        if(list.size()>0)return list.get(0);
        else return null;
    }
}
