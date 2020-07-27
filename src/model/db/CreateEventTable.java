package model.db;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateEventTable {
    public static void main() throws SQLException {

        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "EVENT";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int result = stmt.executeUpdate("CREATE TABLE event ("
                    + "id VARCHAR(40) NOT NULL,"
                    + "title VARCHAR(40) NOT NULL,"
                    + "description VARCHAR(100) NOT NULL,"
                    + "creatorId VARCHAR(20) NOT NULL,"
                    + "status VARCHAR(8) NOT NULL,"
                    + "imageFile VARCHAR(50) NOT NULL,"
                    + "venue VARCHAR(20) NOT NULL,"
                    + "date VARCHAR(20) NOT NULL,"
                    + "capacity INT NOT NULL,"
                    + "attendeeCount INT,"
                    + "PRIMARY KEY (id))");
            if (result == 0) {
                System.out.println("Event Table " + TABLE_NAME + " has been created successfully");
            } else {
                System.out.println("Event Table " + TABLE_NAME + " is not created");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
