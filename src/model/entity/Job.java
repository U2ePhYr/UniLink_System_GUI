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

public class Job extends Post {

    private int proposedPrice;

    public Job(String id, String title, String description, String creatorId, String status, File imageFile, int proposedPrice) throws IOException {
        this(id, title, description, creatorId, status, proposedPrice);
        setImageFile(imageFile);
        copyImageFile();
    }

    public Job(String id, String title, String description, String creatorId, String status, int proposedPrice) {
        super(id, title, description, creatorId, status);
        setProposedPrice(proposedPrice);
        setImageFile(new File("image/defaultImage.png"));
    }

    @Override
    public boolean handleReply(Reply reply) {
        if (getStatus() == "CLOSED")
            return false;

        if (reply.getValue() > proposedPrice) {
            return false;
        }

        if (repliesMg.getLowestReply() != null && reply.getValue() > repliesMg.getLowestReplyValue()) {
            return false;
        }

        if (repliesMg.replyExists(reply))
            return false;

        repliesMg.add(reply);

        return true;
    }

    @Override
    public String getPostDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getPostDetails());
        sb.append(String.format("%-15s $%.2f\n", "Proposed price:", proposedPrice));
        if (repliesMg.getRepliesCount() == 0) {
            sb.append(String.format("%-15s %s\n", "Lowest offer:", "NO OFFER"));
        } else {
            sb.append(String.format("%-15s %.2f\n", "Lowest offer:", getLowestReplyValue()));
        }
        return sb.toString();
    }

    public int getProposedPrice() {
        return proposedPrice;
    }

    public void setProposedPrice(int proposedPrice) {
        this.proposedPrice = proposedPrice;
    }

    public double getLowestReplyValue() {
        return repliesMg.getLowestReplyValue();
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

    @Override
    public String getReplyDetails() {
        return repliesMg.getReplyDetails(true);
    }

    public void insertIntoJob() throws SQLException {
        Connection con = null;
        PreparedStatement pt = null;

        try {
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "JOB";
            //1. Connect to the database
            //conn = DriverManager.getConnection("/Users/houumeume/IdeaProjects/Assignment2/libraries/hsqldb.jar");
            con = ConnectionTest.getConnection(DB_NAME);
            //2. Create a String that holds the query with ? as user inputs
            String sql = "INSERT INTO JOB (id, title, description, creatorId, status, imageFile, proposedPrice)"
                    + "VALUES (?,?,?,?,?,?,?)";

            //3. prepare the query
            pt = con.prepareStatement(sql);

            //5. Bind the values to the parameters
            pt.setString(1, id);
            pt.setString(2, title);
            pt.setString(3, description);
            pt.setString(4, creatorId);
            pt.setString(5, status);
            pt.setString(6, imageFile.getName());
            pt.setInt(7, proposedPrice);

            pt.executeUpdate();
            System.out.println("success");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (pt != null)
                pt.close();

            if (con != null)
                con.close();
        }
    }

    public void updateIntoJob() throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            //1. Connect to the database
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "JOB";
            //use try-with-resources Statement
            con = ConnectionTest.getConnection(DB_NAME);
            //2.  create a String that holds our SQL update command with ? for user inputs
            String sql = "UPDATE JOB SET title = ?, description=?, creatorId = ?, status = ?,imageFile = ?, proposedPrice = ?"
                    + "WHERE id = ?";

            //3. prepare the query against SQL injection
            preparedStatement = con.prepareStatement(sql);


            //5. bind the parameters
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, creatorId);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, imageFile.getName());
            preparedStatement.setInt(6, proposedPrice);
            preparedStatement.setString(7, id);


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


