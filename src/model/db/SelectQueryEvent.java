package model.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectQueryEvent {
    public static void main(String[] args) {
        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "EVENT";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            String query = "SELECT * FROM " + TABLE_NAME;

            try (ResultSet resultSet = stmt.executeQuery(query)) {
                while (resultSet.next()) {
                    System.out.printf("ID: %s | Title: %s | Description: %s | CreatorID: %s | Status: %s | Image: %s | Venue: %s | Date: %s | Capacity: %d | AttendeeCount: %d ",
                            resultSet.getString("id"), resultSet.getString("title"),
                            resultSet.getString("description"),resultSet.getString("creatorId"), resultSet.getString("status"),resultSet.getString("imageFile"), resultSet.getString("venue"),resultSet.getString("date"),
                            resultSet.getInt("capacity"),resultSet.getInt("attendeeCount"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
