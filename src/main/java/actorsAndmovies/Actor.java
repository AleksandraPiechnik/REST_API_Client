package actorsAndmovies;

public class Actor {
    public String id;
    public String name;

    @Override
    public String toString() {
        return "Actor{" + name +'}';
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Actor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        Actor vertex = (Actor) obj;
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Actor)) {
            return false;
        }
        if (vertex.id.equals(this.id)) return true;
        else return false;
    }

}
