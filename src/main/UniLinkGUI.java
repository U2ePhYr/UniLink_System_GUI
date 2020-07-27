package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.db.*;

import java.io.IOException;
import java.sql.SQLException;

public class UniLinkGUI extends Application {

    Boolean login=true;

    @Override
    public void start(Stage stage) throws Exception {
        logIn(stage);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void logIn(Stage stage) throws IOException {
        if (login=true) {
            try {
                CreateEventTable.main();
                InsertEventRow.main();
                CreateSaleTable.main();
                InsertSaleRow.main();
                CreateJobTable.main();
                InsertJobRow.main();
                CreateRepliesTable.main();
                InsertRepliesRow.main();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginWindow.fxml"));

                Parent root = loader.load();
                Scene scene = new Scene(root, 600, 400);
                stage.setScene(scene);
                stage.setTitle("Welcome to login in Unilink system");
                stage.show();
                login =false;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }else if(login=false){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/loginWindow.fxml"));

            Parent root = loader.load();
            Scene scene = new Scene(root, 600, 400);
            stage.setScene(scene);
            stage.setTitle("Welcome to login in Unilink system");
            stage.show();
        }
    }
}

