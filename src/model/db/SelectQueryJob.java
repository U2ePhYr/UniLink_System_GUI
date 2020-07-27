package model.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectQueryJob {

    public static void main(String[] args) {
        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "JOB";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            String query = "SELECT * FROM " + TABLE_NAME;

            try (ResultSet resultSet = stmt.executeQuery(query)) {
                while (resultSet.next()) {
                    System.out.printf("ID: %s | Title: %s | Description: %s | CreatorID: %s | Status: %s | imageFile: %s | proposedPrice: %d ",
                            resultSet.getString("id"), resultSet.getString("title"),
                            resultSet.getString("description"), resultSet.getString("creatorId"), resultSet.getString("status"), resultSet.getString("imageFile"), resultSet.getInt("proposedPrice"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
