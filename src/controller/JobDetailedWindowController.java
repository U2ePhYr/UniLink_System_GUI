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
import model.entity.Job;
import model.entity.JobDetailed;
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

public class JobDetailedWindowController implements Initializable, JobDetailed {
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
    private TextField proposedPriceTextField;
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

    private Job job;

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
        idTextField.setDisable(true);
        imageFileTextField.setDisable(true);
        ownerTextField.setDisable(true);

        imageFileChanged = false; //initially the image has not changed, use the default

        errorLabel.setText("");    //set the error message to be empty to start

        //load the defautl image for the avatar
        try {
            imageFile = new File("image/defaultImage.png");
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(image);
            loadReplies();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // configure the reply table columns
        replyIdColumn.setCellValueFactory(new PropertyValueFactory<Reply, String>("replyId"));
        postIdColumn.setCellValueFactory(new PropertyValueFactory<Reply, String>("postId"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Reply, Integer>("value"));
        responderIdColumn.setCellValueFactory(new PropertyValueFactory<Reply, String>("responderId"));
    }

    // preload job data
    @Override
    public void preLoadData(Job job) {
        this.job = job;
        this.idTextField.setText(job.getId());
        this.nameTextField.setText(job.getTitle());
        this.descriptionTextField.setText(job.getDescription());
        this.ownerTextField.setText(job.getCreatorId());
        this.statusTextField.setText(job.getStatus());
        this.imageFileTextField.setText(job.getImageFile().getName());
        this.proposedPriceTextField.setText(String.valueOf(job.getProposedPrice()));
        //load the image
        try {
            String imgLocation = "image/" + job.getImageFile().getName();
            imageFile = new File(imgLocation);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image img = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(img);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    // save the job
    @FXML
    public void saveJobButtonPushed(ActionEvent actionEvent) {

        try {
            if (job != null) //we need to edit/update an existing job
            {
                updateJob();
                job.updateIntoJob();
            } else    //we need to create a new job
            {
                if (imageFileChanged) //create a job with a custom image
                {
                    job = new Job(idTextField.getText(), nameTextField.getText(),
                            descriptionTextField.getText(), ownerTextField.getText(), statusTextField.getText(), imageFile,
                            Integer.parseInt(proposedPriceTextField.getText()));
                } else  //create a job with a default image
                {
                    job = new Job(idTextField.getText(), nameTextField.getText(),
                            descriptionTextField.getText(), ownerTextField.getText(), statusTextField.getText(),
                            Integer.parseInt(proposedPriceTextField.getText()));
                }
                errorLabel.setText("");    //do not show errors if creating job was successful
                job.insertIntoJob();
            }


        } catch (Exception e) {
            errorLabel.setText(e.getMessage());
        }
    }

    // update job data
    public void updateJob() throws IOException {
        job.setTitle(nameTextField.getText());
        job.setDescription(descriptionTextField.getText());
        job.setCreatorId(ownerTextField.getText());
        job.setStatus(statusTextField.getText());
        job.setProposedPrice(Integer.parseInt(proposedPriceTextField.getText()));
        job.setImageFile(imageFile);
        job.copyImageFile();

    }

    // load replies data
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

            //4.  create volunteer objects from each record
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


