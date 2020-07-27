package model.entity;

import model.db.ConnectionTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;

public class Event extends Post {

    //	Instance Variables
    private String venue;
    private String date;
    private int capacity;
    private int attendeeCount;

    public static final double ANSWER_NO = 0;
    public static final double ANSWER_YES = 1;

//	Accessor

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAttendeeCount() {
        return attendeeCount;
    }

    public void setAttendeeCount(int attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    //	Constructor


    public Event(String id, String title, String description, String creatorId, String status, String venue, String date, int capacity, int attendeeCount) {

        super(id, title, description, creatorId, status);
        setId(id);
        setTitle(title);
        setDescription(description);
        setCreatorId(creatorId);
        setStatus("OPEN");
        setImageFile(new File("image/defaultImage.png"));
        setVenue(venue);
        setDate(date);
        setCapacity(capacity);
        setAttendeeCount(attendeeCount);

    }

    public Event(String id, String title, String description, String creatorId, String status, File imageFile, String venue, String date, int capacity, int attendeeCount) throws IOException {

        this(id, title, description, creatorId, status, venue, date, capacity, attendeeCount);
        setImageFile(imageFile);
        copyImageFile(); }


    @Override
    public String getPostDetails() {
        String s = "";
        s += super.getPostDetails();
        s += "\nVenue :" + venue;
        s += "\nDate :" + date;
        s += "\nCapacity :" + capacity;
        s += "\nAttendees :" + attendeeCount;
        return s.toString();

    }

    public boolean handleReply(Reply reply) { // Need Implement
        if (getStatus() == "CLOSED")
            return false;

        if (reply.getValue() == ANSWER_NO)
            return false;

        if (repliesMg.replyExists(reply))
            return false;

        if (attendeeCount >= capacity)
            return false;

        repliesMg.add(reply);
        attendeeCount++;

        if (attendeeCount >= capacity)
            setStatus("CLOSED");

        return true;

    }

    public String getReplyDetails() { // Need Implement
        StringBuilder sb = new StringBuilder();
        if (attendeeCount == 0) {
            sb.append("Attendee list: Empty\n");
        } else {
            sb.append("Attendee list: ");
            String attendeesC = repliesMg.getAllReplies().stream().map(reply -> reply.getResponderId())
                    .collect(Collectors.joining(","));
            sb.append(attendeesC);
            sb.append("\n");

        }
        return sb.toString();
    }

    public void insertIntoEvent() throws SQLException {
        Connection con = null;
        PreparedStatement pt = null;

        try {
            //1. Connect to the database
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "EVENT";
            //use try-with-resources Statement
            con = ConnectionTest.getConnection(DB_NAME);
            Statement stmt = con.createStatement();

            //2. Create a String that holds the query with ? as user inputs
            //String sql1 = "INSERT INTO " + TABLE_NAME + "(id, title, description, creatorId, status, imageFile, venue, date, capacity, attendeeCount)" +
            //"VALUES (?,?,?,?,?,?,?,?,?,?)";

            String sql = "INSERT INTO EVENT (id, title, description, creatorId, status, imageFile, venue, date, capacity, attendeeCount)"
                    + "VALUES (?,?,?,?,?,?,?,?,?,?)";


            //3. prepare the query
            // pt = conn.prepareStatement(sql);
            pt = con.prepareStatement(sql);
            //5. Bind the values to the parameters
            pt.setString(1, id);
            pt.setString(2, title);
            pt.setString(3, description);
            pt.setString(4, creatorId);
            pt.setString(5, status);
            pt.setString(6, imageFile.getName());
            pt.setString(7, venue);
            pt.setString(8, date);
            pt.setInt(9, capacity);
            pt.setInt(10, attendeeCount);


            pt.executeUpdate();

            System.out.println("success");
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            //System.out.println("Failed");
            e.printStackTrace();
        } finally {
            if (pt != null)
                pt.close();

            if (con != null)
                con.close();
        }
    }

    public void copyImageFile() throws IOException {
        //create a new Path to copy the image into a local directory
        Path sourcePath = imageFile.toPath();

        String selectFileName = imageFile.getName();

        Path targetPath = Paths.get("/Users/houumeume/IdeaProjects/Assignment2/image/" + selectFileName);

        //copy the file to the new directory
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

        //update the imageFile to point to the new File
        imageFile = new File(targetPath.toString());
    }

    public void updateIntoEvent() throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            //1. Connect to the database
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "EVENT";
            //use try-with-resources Statement
            con = ConnectionTest.getConnection(DB_NAME);
            //2.  create a String that holds our SQL update command with ? for user inputs
            String sql = "UPDATE EVENT SET title = ?, description=?, creatorId = ?, status = ?,imageFile = ?, venue = ?,date = ?, capacity = ?,attendeeCount = ?"
                    + "WHERE id = ?";

            //3. prepare the query against SQL injection
            preparedStatement = con.prepareStatement(sql);


            //5. bind the parameters
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, creatorId);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, imageFile.getName());
            preparedStatement.setString(6, venue);
            preparedStatement.setString(7, date);
            preparedStatement.setInt(8, capacity);
            preparedStatement.setInt(9, attendeeCount);
            preparedStatement.setString(10, id);


            //6. run the command on the SQL server
            preparedStatement.executeUpdate();
            System.out.println("Success");
            preparedStatement.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            if (con != null)
                con.close();
            if (preparedStatement != null)
                preparedStatement.close();
        }

    }


}

