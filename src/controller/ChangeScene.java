package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.entity.*;

import java.io.IOException;

public class ChangeScene {
    public void changeScenes(ActionEvent actionEvent, String viewName, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //get the stage from the event that was passed in
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    //Change scenes to event detailed
    public void changeScenes(ActionEvent actionEvent, String viewName, String title, Event event, EventDetailed eventDetailed) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //access the controller class and preloaded the volunteer data
        eventDetailed = loader.getController();
        eventDetailed.preLoadData(event);

        //get the stage from the event that was passed in
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    //Change scenes to sale detailed
    public void changeScenes(ActionEvent actionEvent, String viewName, String title, Sale sale, SaleDetailed saleDetailed) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //access the controller class and preloaded the volunteer data
        saleDetailed = loader.getController();
        saleDetailed.preLoadData(sale);

        //get the stage from the event that was passed in
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    //Change scenes to event detailed
    public void changeScenes(ActionEvent actionEvent, String viewName, String title, Job job, JobDetailed jobDetailed) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);

        //access the controller class and preloaded the volunteer data
        jobDetailed = loader.getController();
        jobDetailed.preLoadData(job);

        //get the stage from the event that was passed in
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
