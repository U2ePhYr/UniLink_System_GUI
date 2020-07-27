package model.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateJobTable {

    public static void main() throws SQLException {


        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "JOB";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int result = stmt.executeUpdate("CREATE TABLE job ("
                    + "id VARCHAR(20) NOT NULL,"
                    + "title VARCHAR(40) ,"
                    + "description VARCHAR(100) NOT NULL,"
                    + "creatorId VARCHAR(20) NOT NULL,"
                    + "status VARCHAR(8) NOT NULL,"
                    + "imageFile VARCHAR(50) NOT NULL,"
                    + "proposedPrice INT NOT NULL,"
                    + "PRIMARY KEY (id))");
            if (result == 0) {
                System.out.println("Job Table " + TABLE_NAME + " has been created successfully");
            } else {
                System.out.println("Job Table " + TABLE_NAME + " is not created");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
