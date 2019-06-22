package sample;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import applicationManager.AppManager;


public class Controller {
    @FXML
    private TextField textFieldA,textFieldB;
    public Label answerLabel;
    public TextArea textArea;

    public  void pressedFindLinkButton()
    {
        answerLabel.setText("");
        answerLabel.setVisible(true);
        textArea.setText("");
        answerLabel.setText("Finding links...");

        AppManager manager= new AppManager(textFieldA.getText(),textFieldB.getText(),answerLabel,textArea);
        manager.start();
    }
    public void onCloseMenuItem(){
        Platform.exit();
    }
    public void aboutMenuItem(){
        Alert alertBox = new Alert(Alert.AlertType.INFORMATION);
        alertBox.setTitle("About");
        alertBox.setHeaderText("Six Degrees of Separation application");
        alertBox.setContentText("Six degrees of separation is the idea that all people are six, or fewer, social connections away from each other. As a result, a chain of \"a friend of a friend\" statements can be made to connect any two people in a maximum of six steps.");
        alertBox.show();
    }
}
