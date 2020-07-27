package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.db.ConnectionTest;
import model.entity.Event;
import model.entity.Job;
import model.entity.Sale;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;


public class MainWindowController implements Initializable {
    @FXML
    private ComboBox uniLinkComboBox;
    @FXML
    private ComboBox dataComboBox;
    @FXML
    private Button newEventButton;
    @FXML
    private Button newSaleButton;
    @FXML
    private Button newJobButton;

    @FXML
    private TableView<Event> eventTable;

    @FXML
    private TableView<Sale> saleTable;

    @FXML
    private TableView<Job> jobTable;

    @FXML
    private TableColumn<Event, String> eventIdColumn;
    @FXML
    private TableColumn<Event, String> titleColumn;
    @FXML
    private TableColumn<Event, String> descriptionColumn;
    @FXML
    private TableColumn<Event, String> creatorIdColumn;
    @FXML
    private TableColumn<Event, String> statusColumn;
    @FXML
    private TableColumn<Event, String> imageFileColumn;
    @FXML
    private TableColumn<Event, String> venueColumn;
    @FXML
    private TableColumn<Event, String> dateColumn;
    @FXML
    private TableColumn<Event, Integer> capacityColumn;
    @FXML
    private TableColumn<Event, Integer> attendeeCountColumn;

    @FXML
    private TableColumn<Sale, String> saleIdColumn;
    @FXML
    private TableColumn<Sale, String> title1Column;
    @FXML
    private TableColumn<Sale, String> description1Column;
    @FXML
    private TableColumn<Sale, String> creatorId1Column;
    @FXML
    private TableColumn<Sale, String> status1Column;
    @FXML
    private TableColumn<Sale, String> imageFile1Column;
    @FXML
    private TableColumn<Sale, Integer> askingPriceColumn;
    @FXML
    private TableColumn<Sale, Integer> minimumRaiseColumn;

    @FXML
    private TableColumn<Job, String> jobIdColumn;
    @FXML
    private TableColumn<Job, String> title2Column;
    @FXML
    private TableColumn<Job, String> description2Column;
    @FXML
    private TableColumn<Job, String> creatorId2Column;
    @FXML
    private TableColumn<Job, String> status2Column;
    @FXML
    private TableColumn<Job, String> imageFile2Column;
    @FXML
    private TableColumn<Job, Integer> proposedPriceColumn;


    @FXML
    private Button editEventButton;

    @FXML
    private Button editSaleButton;

    @FXML
    private Button editJobButton;

    private DeveloperWindowController developerWindowController;

    private EventDetailedWindowController eventDetailedWindowController;

    // combo box controller
    @FXML
    public void comboBoxWasUpdated(ActionEvent actionEvent) throws IOException {
        if (uniLinkComboBox.getValue().toString() == "Quit UniLink") {
            Parent mainViewParent = FXMLLoader.load(getClass().getResource("/view/loginWindow.fxml"));

            Scene mainViewScene = new Scene(mainViewParent);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainViewScene);
            window.show();
        } else if (uniLinkComboBox.getValue().toString() == "Developer Information") {

            Parent mainViewParent = FXMLLoader.load(getClass().getResource("/view/developerWindow.fxml"));

            Scene mainViewScene = new Scene(mainViewParent);

            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(mainViewScene);
            window.show();


        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Disable edit button till chose item from the list
        editEventButton.setDisable(true);
        editSaleButton.setDisable(true);
        editJobButton.setDisable(true);

        //this items are for configuring the ComboBox
        uniLinkComboBox.getItems().addAll("Developer Information", "Quit UniLink");
        uniLinkComboBox.setVisibleRowCount(2);
        uniLinkComboBox.setEditable(true);
        dataComboBox.getItems().addAll("Export", "Import");
        dataComboBox.setVisibleRowCount(2);
        dataComboBox.setEditable(true);

        // configure the event table columns
        eventIdColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("description"));
        creatorIdColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("creatorId"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("status"));
        imageFileColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("imageFile"));
        venueColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("venue"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("date"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<Event, Integer>("capacity"));
        attendeeCountColumn.setCellValueFactory(new PropertyValueFactory<Event, Integer>("attendeeCount"));

        // configure the sale table columns
        saleIdColumn.setCellValueFactory(new PropertyValueFactory<Sale, String>("id"));
        title1Column.setCellValueFactory(new PropertyValueFactory<Sale, String>("title"));
        description1Column.setCellValueFactory(new PropertyValueFactory<Sale, String>("description"));
        creatorId1Column.setCellValueFactory(new PropertyValueFactory<Sale, String>("creatorId"));
        status1Column.setCellValueFactory(new PropertyValueFactory<Sale, String>("status"));
        imageFile1Column.setCellValueFactory(new PropertyValueFactory<Sale, String>("imageFile"));
        askingPriceColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("askingPrice"));
        minimumRaiseColumn.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("minimumRaise"));

        // configure the job table columns
        jobIdColumn.setCellValueFactory(new PropertyValueFactory<Job, String>("id"));
        title2Column.setCellValueFactory(new PropertyValueFactory<Job, String>("title"));
        description2Column.setCellValueFactory(new PropertyValueFactory<Job, String>("description"));
        creatorId2Column.setCellValueFactory(new PropertyValueFactory<Job, String>("creatorId"));
        status2Column.setCellValueFactory(new PropertyValueFactory<Job, String>("status"));
        imageFile2Column.setCellValueFactory(new PropertyValueFactory<Job, String>("imageFile"));
        proposedPriceColumn.setCellValueFactory(new PropertyValueFactory<Job, Integer>("proposedPrice"));

        try {
            loadEvents();
            loadSales();
            loadJobs();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    // load event data
    public void loadEvents() throws SQLException {
        ObservableList<Event> events = FXCollections.observableArrayList();

        // Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "EVENT";
            //1. Connect to the database
            Connection con = ConnectionTest.getConnection(DB_NAME);
            //2.  create a statement object
            statement = con.createStatement();

            //3.  create the SQL query
            resultSet = statement.executeQuery("SELECT * FROM EVENT");

            //4.  create event objects from each record
            while (resultSet.next()) {
                Event newEvent = new Event(resultSet.getString("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("creatorId"),
                        resultSet.getString("status"),
                        resultSet.getString("venue"),
                        resultSet.getString("date"),
                        resultSet.getInt("capacity"),
                        resultSet.getInt("attendeeCount"));
                newEvent.setImageFile(new File(resultSet.getString("imageFile")));

                events.add(newEvent);
            }

            eventTable.getItems().addAll(events);
            con.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    // load sale data
    public void loadSales() throws SQLException {
        ObservableList<Sale> sales = FXCollections.observableArrayList();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "SALE";
            //1. Connect to the database
            Connection con = ConnectionTest.getConnection(DB_NAME);
            //2.  create a statement object
            statement = con.createStatement();

            //3.  create the SQL query
            resultSet = statement.executeQuery("SELECT * FROM SALE");

            //4.  create sale objects from each record
            while (resultSet.next()) {
                Sale newSale = new Sale(resultSet.getString("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("creatorId"),
                        resultSet.getString("status"),
                        resultSet.getInt("askingPrice"),
                        resultSet.getInt("minimumRaise"));
                //newVolunteer.setVolunteerID(resultSet.getInt("VolunteerID"));
                newSale.setImageFile(new File(resultSet.getString("imageFile")));

                sales.add(newSale);
            }

            saleTable.getItems().addAll(sales);
            con.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    // load job data
    public void loadJobs() throws SQLException {
        ObservableList<Job> jobs = FXCollections.observableArrayList();

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "JOB";
            //1. Connect to the database
            Connection con = ConnectionTest.getConnection(DB_NAME);
            //2.  create a statement object
            statement = con.createStatement();

            //3.  create the SQL query
            resultSet = statement.executeQuery("SELECT * FROM JOB");

            //4.  create volunteer objects from each record
            while (resultSet.next()) {
                Job newJob = new Job(resultSet.getString("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("creatorId"),
                        resultSet.getString("status"),
                        resultSet.getInt("proposedPrice"));
                //newVolunteer.setVolunteerID(resultSet.getInt("VolunteerID"));
                newJob.setImageFile(new File(resultSet.getString("imageFile")));

                jobs.add(newJob);
            }

            jobTable.getItems().addAll(jobs);
            con.close();
            statement.close();
            resultSet.close();
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
    }

    // get export directory
    public String getDirectory(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setTitle("Select the path");

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory.isDirectory()) {
            File file = new File(selectedDirectory.getAbsolutePath());
        }
        return (selectedDirectory.getAbsolutePath().toString());
    }

    // set export file
    public void getFile(ActionEvent actionEvent) {
        File file = new File(getDirectory(actionEvent) + "/" + "Export_data.txt");
        try {
            final String DB_NAME = "UniLinkDB";
            Connection con = ConnectionTest.getConnection(DB_NAME);
            Statement statement = con.createStatement();
            PrintWriter output = new PrintWriter(file);
            StringBuilder sb = new StringBuilder();
            ResultSet rs = null;

            String query1 = "select * from EVENT";
            PreparedStatement ps1 = con.prepareStatement(query1);
            rs = ps1.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("id"));
                sb.append(",");
                sb.append(rs.getString("title"));
                sb.append(",");
                sb.append(rs.getString("description"));
                sb.append(",");
                sb.append(rs.getString("creatorId"));
                sb.append(",");
                sb.append(rs.getString("status"));
                sb.append(",");
                sb.append(rs.getString("imageFile"));
                sb.append(",");
                sb.append(rs.getString("venue"));
                sb.append(",");
                sb.append(rs.getString("date"));
                sb.append(",");
                sb.append(rs.getString("capacity"));
                sb.append(",");
                sb.append(rs.getString("attendeeCount"));
                sb.append(",");
                sb.append("\r\n");
            }

            String query2 = "select * from SALE";
            PreparedStatement ps2 = con.prepareStatement(query2);
            rs = ps2.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("id"));
                sb.append(",");
                sb.append(rs.getString("title"));
                sb.append(",");
                sb.append(rs.getString("description"));
                sb.append(",");
                sb.append(rs.getString("creatorId"));
                sb.append(",");
                sb.append(rs.getString("status"));
                sb.append(",");
                sb.append(rs.getString("imageFile"));
                sb.append(",");
                sb.append(rs.getInt("askingPrice"));
                sb.append(",");
                sb.append(rs.getInt("minimumRaise"));
                sb.append(",");
                sb.append("\r\n");
            }

            String query3 = "select * from JOB";
            PreparedStatement ps3 = con.prepareStatement(query3);
            rs = ps3.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("id"));
                sb.append(",");
                sb.append(rs.getString("title"));
                sb.append(",");
                sb.append(rs.getString("description"));
                sb.append(",");
                sb.append(rs.getString("creatorId"));
                sb.append(",");
                sb.append(rs.getString("status"));
                sb.append(",");
                sb.append(rs.getString("imageFile"));
                sb.append(",");
                sb.append(rs.getInt("proposedPrice"));
                sb.append(",");
                sb.append("\r\n");
            }


            String query4 = "select * from REPLIES";
            PreparedStatement ps4 = con.prepareStatement(query4);
            rs = ps4.executeQuery();

            while (rs.next()) {
                sb.append(rs.getString("replyId"));
                sb.append(",");
                sb.append(rs.getString("postId"));
                sb.append(",");
                sb.append(rs.getString("value"));
                sb.append(",");
                sb.append(rs.getString("responderId"));
                sb.append("\r\n");
            }
            output.write(sb.toString());
            output.close();
            System.out.println("finished");
            output.close(); //don't forget this method
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // export data
    public void exportFile(ActionEvent actionEvent) {

        getFile(actionEvent);
    }

    // get import file
    public String getImportFile(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open import file");

        File file = fileChooser.showOpenDialog(stage);

        if (file.isFile()) {
            System.out.println(file.getAbsolutePath().toString());

        }
        return (file.getAbsolutePath().toString());
    }

    // read import file
    public int readFile(ActionEvent actionEvent) {
        int line = 0;
        File file = new File(getImportFile(actionEvent));

        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                line++;
                System.out.println(input.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return line;
    }

    // insert data from import file
    public void insertData(ActionEvent actionEvent) {
        final String DB_NAME = "uniLinkDB";
        final String FILE_NAME = getImportFile(actionEvent);
        // use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement statement = con.createStatement();
             Scanner scanner = new Scanner(new FileInputStream(FILE_NAME));) {

            PreparedStatement ps;

            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                System.out.println(currentLine + "  read");
                String details[] = currentLine.split(",");

                if (details.length == readFile(actionEvent)) {
                    ps = con.prepareStatement("INSERT INTO POST VALUES (?, ?, ?,?,?,?,?,?,?,?,?,?);");
                    for (int i = 0; i < readFile(actionEvent); i++) {
                        ps.setString(i + 1, details[i].trim());
                    }

                    ps.executeUpdate();
                    System.out.println(currentLine + "  inserted");
                }

            }

            //checking if lines were executed

            ResultSet resultSet = statement.executeQuery("SELECT * FROM POST;");

            while (resultSet.next()) {
                System.out.print(resultSet.getString("id") + "\t");
                System.out.print(resultSet.getString("title") + "\t");
                System.out.print(resultSet.getString("description") + "\t");
                System.out.print(resultSet.getString("creatorId") + "\t");
                System.out.print(resultSet.getString("status") + "\t");
                System.out.print(resultSet.getString("venue") + "\t");
                System.out.print(resultSet.getString("date") + "\t");
                System.out.print(resultSet.getString("capacity") + "\t");
                System.out.print(resultSet.getString("attendeeCount") + "\t");
                System.out.print(resultSet.getString("proposedPrice") + "\t");
                System.out.print(resultSet.getString("askingPrice") + "\t");
                System.out.print(resultSet.getString("minimumRaise"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // combo box controller
    @FXML
    public void comboBoxIsUpdated(ActionEvent actionEvent) throws IOException {
        if (dataComboBox.getValue().toString() == "Export") {

            exportFile(actionEvent);
        } else if (dataComboBox.getValue().toString() == "Import") {

            insertData(actionEvent);
        }
    }


    // go new event window
    @FXML
    public void pushNewEventButton(ActionEvent actionEvent) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/view/newEventWindow.fxml"));

        Scene mainViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }

    // go new sale window
    @FXML
    public void pushNewSaleButton(ActionEvent actionEvent) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/view/newSaleWindow.fxml"));

        Scene mainViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }

    // go new job window
    @FXML
    public void pushNewJobButton(ActionEvent actionEvent) throws IOException {
        Parent mainViewParent = FXMLLoader.load(getClass().getResource("/view/newJobWindow.fxml"));

        Scene mainViewScene = new Scene(mainViewParent);

        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainViewScene);
        window.show();
    }

    // enable edit buttons when click tables
    public void selectTables() {
        editEventButton.setDisable(false);
        editSaleButton.setDisable(false);
        editJobButton.setDisable(false);

    }

    // event edit button controller
    @FXML
    public void eventEditButtonPushed(ActionEvent actionEvent) throws IOException {

        ChangeScene cs = new ChangeScene();
        Event event = this.eventTable.getSelectionModel().getSelectedItem();
        EventDetailedWindowController eventDetailedWindowController = new EventDetailedWindowController();

        cs.changeScenes(actionEvent, "/view/eventDetailedWindow.fxml", "Edit Volunteer", event, eventDetailedWindowController);
    }

    // sale edit button controller
    @FXML
    public void saleEditButtonPushed(ActionEvent actionEvent) throws IOException {

        ChangeScene cs = new ChangeScene();
        Sale sale = this.saleTable.getSelectionModel().getSelectedItem();
        SaleDetailedWindowController saleDetailedWindowController = new SaleDetailedWindowController();

        cs.changeScenes(actionEvent, "/view/saleDetailedWindow.fxml", "Edit Sale", sale, saleDetailedWindowController);
    }

    // job edit button controller
    public void jobEditButtonPushed(ActionEvent actionEvent) throws IOException {

        ChangeScene cs = new ChangeScene();
        Job job = this.jobTable.getSelectionModel().getSelectedItem();
        JobDetailedWindowController jobDetailedWindowController = new JobDetailedWindowController();

        cs.changeScenes(actionEvent, "/view/jobDetailedWindow.fxml", "Edit Job", job, jobDetailedWindowController);
    }
}


