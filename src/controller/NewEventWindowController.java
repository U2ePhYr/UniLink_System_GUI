package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.entity.Event;
import model.entity.EventDetailed;
import model.entity.Reply;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewEventWindowController implements Initializable, EventDetailed {
    @FXML
    private Button saveButton;
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
    private ImageView photoImageView;
    @FXML
    private Label errorLabel;
    @FXML
    private Button chooseImageButton;
    @FXML
    private TableView<Reply> replyTable;

    private File imageFile;

    private boolean imageFileChanged;

    private Event event;

    private Button backButton;

    // back to main window
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

        imageFileChanged = false; //initially the image has not changed, use the default

        errorLabel.setText("");    //set the error message to be empty to start

        //load the defautl image for the avatar
        try {
            imageFile = new File("image/defaultImage.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(image);

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // save event
    @FXML
    public void saveEventButtonPushed(ActionEvent actionEvent) {

        try {

            {
                if (imageFileChanged) //create a event with a custom image
                {
                    event = new Event(idTextField.getText(), nameTextField.getText(),
                            descriptionTextField.getText(), ownerTextField.getText(), statusTextField.getText(), imageFile,
                            dateTextField.getText(), venueTextField.getText(), Integer.parseInt(capacityTextField.getText()), Integer.parseInt(attendeeCountTextField.getText()));
                } else  //create a Volunteer with a default image
                {
                    event = new Event(idTextField.getText(), nameTextField.getText(),
                            descriptionTextField.getText(), ownerTextField.getText(), statusTextField.getText(), imageFile,
                            dateTextField.getText(), venueTextField.getText(), Integer.parseInt(capacityTextField.getText()), Integer.parseInt(attendeeCountTextField.getText()));
                }
                errorLabel.setText("");    //do not show errors if creating event was successful
                event.insertIntoEvent();
            }


        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
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
        this.dateTextField.setText(event.getDate());
        this.venueTextField.setText(event.getVenue());
        this.capacityTextField.setText(String.valueOf(event.getCapacity()));
        this.attendeeCountTextField.setText(String.valueOf(event.getAttendeeCount()));
        //load the image
        try {
            String imgLocation = "./image/" + event.getImageFile().getName();
            imageFile = new File(imgLocation);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image img = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(img);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

    }

}