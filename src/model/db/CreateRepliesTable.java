package model.db;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateRepliesTable {
    public static void main() throws SQLException {

        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "REPLIES";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int result = stmt.executeUpdate("CREATE TABLE replies ("
                    + "replyId VARCHAR(20) NOT NULL,"
                    + "postId VARCHAR(20) NOT NULL,"
                    + "value INT NOT NULL,"
                    + "responderId VARCHAR(20) NOT NULL,"
                    + "PRIMARY KEY (replyId))");
            if (result == 0) {
                System.out.println("Table " + TABLE_NAME + " has been created successfully");
            } else {
                System.out.println("Table " + TABLE_NAME + " is not created");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
