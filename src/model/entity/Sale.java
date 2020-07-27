package model.entity;

import model.db.ConnectionTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Sale extends Post {

    private int askingPrice;
    private int minimumRaise;

    public Sale(String id, String title, String description, String creatorId, String status, File imageFile, int askingPrice, int minimumRaise) throws IOException {
        this(id, title, description, creatorId, status, askingPrice, minimumRaise);
        setImageFile(imageFile);
         copyImageFile();


    }

    public Sale(String id, String title, String description, String creatorId, String status, int askingPrice, int minimumRaise) {
        super(id, title, description, creatorId, status);
        setAskingPrice(askingPrice);
        setMinimumRaise(minimumRaise);
        setImageFile(new File("image/defaultImage.png"));

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


    private String getUniqueFileName(String oldFileName) {
        String newName;

        //create a Random Number Generator
        SecureRandom rng = new SecureRandom();

        //loop until we have a unique file name
        do {
            newName = "";

            //generate 32 random characters
            for (int count = 1; count <= 32; count++) {
                int nextChar;

                do {
                    nextChar = rng.nextInt(123);
                } while (!validCharacterValue(nextChar));

                newName = String.format("%s%c", newName, nextChar);
            }
            newName += oldFileName;

        } while (!uniqueFileInDirectory(newName));

        return newName;
    }

    public boolean validCharacterValue(int asciiValue) {

        //0-9 = ASCII range 48 to 57
        if (asciiValue >= 48 && asciiValue <= 57)
            return true;

        //A-Z = ASCII range 65 to 90
        if (asciiValue >= 65 && asciiValue <= 90)
            return true;

        //a-z = ASCII range 97 to 122
        if (asciiValue >= 97 && asciiValue <= 122)
            return true;

        return false;
    }

    public boolean uniqueFileInDirectory(String fileName) {
        File directory = new File("/image/");

        File[] dir_contents = directory.listFiles();

        for (File file : dir_contents) {
            if (file.getName().equals(fileName))
                return false;
        }
        return true;
    }

    public int getAskingPrice() {

        return askingPrice;
    }

    public int getMinimumRaise() {
        return minimumRaise;
    }

    public double getHighestReplyValue() { // Need Implement
        return repliesMg.getHighestReplyValue();
    }

    public void setAskingPrice(int askingPrice) {
        this.askingPrice = askingPrice;
    }

    public void setMinimumRaise(int minimumRaise) {
        this.minimumRaise = minimumRaise;
    }


    @Override
    public String getPostDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getPostDetails());
        sb.append(String.format("%-15s $%.2f\n", "Minimum raise:", minimumRaise));
        if (repliesMg.getRepliesCount() == 0) {
            sb.append(String.format("%-15s %s\n", "Highest offer:", "NO OFFER"));
        } else {
            sb.append(String.format("%-15s %.2f\n", "Highest offer:", repliesMg.getHighestReplyValue()));
        }
        return sb.toString();
    }

    @Override
    public boolean handleReply(Reply reply) {
        if (getStatus() == "CLOSED")
            return false;

        if (repliesMg.getHighestReply() == null && reply.getValue() < minimumRaise) {
            return false;
        }

        if (repliesMg.getHighestReply() != null && reply.getValue() < repliesMg.getHighestReplyValue() + minimumRaise) {
            return false;
        }

        if (repliesMg.replyExists(reply))
            return false;

        repliesMg.add(reply);

        if (askingPrice <= repliesMg.getHighestReplyValue()) {
            setStatus("CLOSED");
        }
        return true;
    }

    @Override
    public String getReplyDetails() {
        return repliesMg.getReplyDetails(false);
    }


    public void insertIntoSale() throws SQLException {
        Connection con = null;
        PreparedStatement pt = null;

        try {
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "SALE";
            //1. Connect to the database
            //conn = DriverManager.getConnection("/Users/houumeume/IdeaProjects/Assignment2/libraries/hsqldb.jar");
            con = ConnectionTest.getConnection(DB_NAME);
            //2. Create a String that holds the query with ? as user inputs
            String sql = "INSERT INTO SALE (id, title, description, creatorId, status, imageFile, askingPrice, minimumRaise)"
                    + "VALUES (?,?,?,?,?,?,?,?)";

            //3. prepare the query
            pt = con.prepareStatement(sql);

            //5. Bind the values to the parameters
            pt.setString(1, id);
            pt.setString(2, title);
            pt.setString(3, description);
            pt.setString(4, creatorId);
            pt.setString(5, status);
            pt.setString(6, imageFile.getName());
            pt.setInt(7, askingPrice);
            pt.setInt(8, minimumRaise);

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

    public void updateIntoSale() throws SQLException {
        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            //1. Connect to the database
            final String DB_NAME = "UniLinkDB";
            final String TABLE_NAME = "SALE";
            //use try-with-resources Statement
            con = ConnectionTest.getConnection(DB_NAME);
            //2.  create a String that holds our SQL update command with ? for user inputs
            String sql = "UPDATE SALE SET title = ?, description=?, creatorId = ?, status = ?,imageFile = ?, askingPrice = ?, minimumRaise = ?"
                    + "WHERE id = ?";

            //3. prepare the query against SQL injection
            preparedStatement = con.prepareStatement(sql);


            //5. bind the parameters
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, creatorId);
            preparedStatement.setString(4, status);
            preparedStatement.setString(5, imageFile.getName());
            preparedStatement.setInt(6, askingPrice);
            preparedStatement.setInt(7, minimumRaise);
            preparedStatement.setString(8, id);


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
