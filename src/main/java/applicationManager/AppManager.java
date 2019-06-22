package applicationManager;

import graph.MyGraph;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class AppManager {
    private String actorNameA;
    private String actorNameB;
    private NameExistenceChecker A;
    private NameExistenceChecker B;
    private Label label;
    private TextArea textArea;
    public AppManager(String actorNameA, String actorNameB, Label label, TextArea textArea) {
        this.actorNameA = actorNameA;
        this.actorNameB = actorNameB;
        A= new NameExistenceChecker(actorNameA);
        B= new NameExistenceChecker(actorNameB);
        this.label=label;
        this.textArea=textArea;
    }

    public void start(){
        label.setText("Finding links...");
        label.setVisible(true);
        checkNamesAndSetLabel();
        if(label.getText().equals("Finding links..."));
        MyGraph graph=new MyGraph(A.getIdAndName(),B.getIdAndName());
        graph.addTopsAndEdges();
        label.setText("Found links");
        textArea.setText(graph.findShortestPath());
    }
    private void checkNamesAndSetLabel(){
        if(A.exist()&&B.exist()){
            if(A.getIdAndName().equals(B.getIdAndName())) label.setText("This is the same person");
            else label.setText("Finding links...");
        }
        else if(A.exist()&&!B.exist()) label.setText("Second actor does not exist or he/she is not in in IMDb database");
        else if (!A.exist()&&B.exist())label.setText("First actor does not exist or he/she is not in in IMDb database");
        else label.setText("Actors doesnt exist or they are not in IMDb database");
        label.setVisible(true);
    }
}
