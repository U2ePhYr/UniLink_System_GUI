package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.db.ConnectionTest;

import java.io.IOException;
import java.sql.*;

public class LoginController {


    @FXML
    private TextField loginTextField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button loginButton;

    // login in window control
    @FXML
    private void loginButtonPushed(ActionEvent actionEvent) {
        Connection con = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;

        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        ResultSet resultSet3 = null;

        String loginId = loginTextField.getText();
        final String DB_NAME = "UniLinkDB";

        //use try-with-resources Statement

        try {
            //1.  connect to the DB
            con = ConnectionTest.getConnection(DB_NAME);
            Statement stmt = con.createStatement();

            //2.  create a query string with ? used instead of the values given by the user
            String sql1 = "SELECT * FROM EVENT WHERE creatorId = ?";
            String sql2 = "SELECT * FROM SALE WHERE creatorId = ?";
            String sql3 = "SELECT * FROM JOB WHERE creatorId = ?";


            //3.  prepare the statement
            ps1 = con.prepareStatement(sql1);
            ps2 = con.prepareStatement(sql2);
            ps3 = con.prepareStatement(sql3);

            //4.  bind the volunteerID to the ?
            ps1.setString(1, loginId);
            ps2.setString(1, loginId);
            ps3.setString(1, loginId);


            //5. execute the query
            resultSet1 = ps1.executeQuery();
            resultSet2 = ps2.executeQuery();
            resultSet3 = ps3.executeQuery();

            while (resultSet1.next() || resultSet2.next() || resultSet3.next()) {

                Parent mainViewParent = null;
                try {
                    mainViewParent = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Scene mainViewScene = new Scene(mainViewParent);

                Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                window.setScene(mainViewScene);
                window.show();

            }
            errorLabel.setText("Invalid user ID");

        } catch (SQLException throwables) {
            errorLabel.setText("Invalid user ID");
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            errorLabel.setText("Invalid user ID");
            e.printStackTrace();

        }
    }
}