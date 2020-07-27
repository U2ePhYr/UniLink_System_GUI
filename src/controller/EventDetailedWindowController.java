package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.db.ConnectionTest;
import model.entity.Event;
import model.entity.EventDetailed;
import model.entity.Reply;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class EventDetailedWindowController implements Initializable, EventDetailed {

    @FXML
    private Button editSaveButton;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField ownerTextField;
    @FXML
    private TextField statusTextField;
    @FXML
    private TextField venueTextField;
    @FXML
    private TextField dateTextField;
    @FXML
    private TextField capacityTextField;
    @FXML
    private TextField attendeeCountTextField;
    @FXML
    private TextField imageFileTextField;
    @FXML
    private ImageView photoImageView;
    @FXML
    private Label errorLabel;
    @FXML
    private Button editImageButton;
    @FXML
    private Button backButton;
    @FXML
    private Button deleteEventButton;
    @FXML
    private Button closeEventButton;

    @FXML
    private TableColumn<Reply, String> replyIdColumn;
    @FXML
    private TableColumn<Reply, String> postIdColumn;
    @FXML
    private TableColumn<Reply, Integer> valueColumn;
    @FXML
    private TableColumn<Reply, String> responderIdColumn;
    @FXML
    private TableView<Reply> replyTable;


    private File imageFile;

    private boolean imageFileChanged;

    private Event event;

    // go back to main window
    @FXML
    public void backButtonPushed(ActionEvent actionEvent) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/view/mainWindow.fxml"));

        Scene mainViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }

    // choose image from local
    @FXML
    public void chooseImageButtonPushed(ActionEvent event) {
        //get the Stage to open a new window (or Stage in JavaFX)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        //Instantiate a FileChooser object
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        //open the file dialog window
        File tmpImageFile = fileChooser.showOpenDialog(stage);

        if (tmpImageFile != null) {
            imageFile = tmpImageFile;

            //update the ImageView with the new image
            if (imageFile.isFile()) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(imageFile);
                    Image img = SwingFXUtils.toFXImage(bufferedImage, null);
                    photoImageView.setImage(img);
                    imageFileChanged = true;
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.setDisable(true);
        imageFileTextField.setDisable(true);
        attendeeCountTextField.setDisable(true);
        ownerTextField.setDisable(true);

        imageFileChanged = false; //initially the image has not changed, use the default

        errorLabel.setText("");    //set the error message to be empty to start

        //load the defautl image for the avatar
        try {
            loadReplies();
            imageFile = new File("image/defaultImage.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(image);


        } catch (IOException | SQLException e) {
            System.err.println(e.getMessage());
        }

        // configure the reply table columns
        replyIdColumn.setCellValueFactory(new PropertyValueFactory<Reply, String>("replyId"));
        postIdColumn.setCellValueFactory(new PropertyValueFactory<Reply, String>("postId"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Reply, Integer>("value"));
        responderIdColumn.setCellValueFactory(new PropertyValueFactory<Reply, String>("responderId"));
    }

    // save event
    @FXML
    public void saveEventButtonPushed(ActionEvent actionEvent) {

        try {
            if (event != null) //we need to edit/update an existing event
            {
                updateEvent();
                event.updateIntoEvent();
            } else    //we need to create a new event
            {
                if (imageFileChanged) //create a event with a custom image
                {
                    event = new Event(idTextField.getText(), nameTextField.getText(),
                            descriptionTextField.getText(), ownerTextField.getText(), statusTextField.getText(), imageFile,
                            dateTextField.getText(), venueTextField.getText(), Integer.parseInt(capacityTextField.getText()), Integer.parseInt(attendeeCountTextField.getText()));
                } else  //create a event with a default image
                {
                    event = new Event(idTextField.getText(), nameTextField.getText(),
                            descriptionTextField.getText(), ownerTextField.getText(), statusTextField.getText(),
                            dateTextField.getText(), venueTextField.getText(), Integer.parseInt(capacityTextField.getText()), Integer.parseInt(attendeeCountTextField.getText()));
                }
                errorLabel.setText("");    //do not show errors if creating event was successful
                event.insertIntoEvent();
            }


        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    // update event
    public void updateEvent() throws IOException {
        event.setTitle(nameTextField.getText());
        event.setDescription(descriptionTextField.getText());
        event.setCreatorId(ownerTextField.getText());
        event.setDate(dateTextField.getText());
        event.setVenue(venueTextField.getText());
        event.setCapacity(Integer.parseInt(capacityTextField.getText()));
        event.setImageFile(imageFile);
        event.copyImageFile();

    }

    // preload event data
    @Override
    public void preLoadData(Event event) {
        this.event = event;
        this.idTextField.setText(event.getId());
        this.nameTextField.setText(event.getTitle());
        this.descriptionTextField.setText(event.getDescription());
        this.ownerTextField.setText(event.getCreatorId());
        this.statusTextField.setText(event.getStatus());
        this.imageFileTextField.setText(event.getImageFile().getName());
        this.dateTextField.setText(event.getDate());
        this.venueTextField.setText(event.getVenue());
        this.capacityTextField.setText(String.valueOf(event.getCapacity()));
        this.attendeeCountTextField.setText(String.valueOf(event.getAttendeeCount()));
        //load the image
        try {
            String imgLocation = "image/" + event.getImageFile().getName();
            imageFile = new File(imgLocation);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image img = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(img);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

    //load replies table data
    public void loadReplies() throws SQLException {
        ObservableList<Reply> replies = FXCollections.observableArrayList();

        // Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "REPLIES";
            //1. Connect to the database
            Connection con = ConnectionTest.getConnection(DB_NAME);
            //2.  create a statement object
            statement = con.createStatement();

            //3.  create the SQL query
            resultSet = statement.executeQuery("SELECT * FROM REPLIES");

            //4.  create event objects from each record
            while (resultSet.next()) {
                Reply newReply = new Reply(resultSet.getString("replyId"),
                        resultSet.getString("postId"),
                        resultSet.getInt("value"),
                        resultSet.getString("responderId"));

                replies.add(newReply);
            }

            replyTable.getItems().addAll(replies);
            con.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }
}



