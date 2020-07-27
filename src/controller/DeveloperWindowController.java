package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DeveloperWindowController implements Initializable {

    @FXML
    private Button backButton;
    @FXML
    private Label nameLabel;
    @FXML
    private Label showNameLabel;

    @FXML
    private Button showNamwButton;

    //go back to main window
    @FXML
    public void backMainWindow(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));

        Scene mainViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        }

}