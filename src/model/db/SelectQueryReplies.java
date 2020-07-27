package model.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectQueryReplies {
    public static void main(String[] args) {
        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "REPLIES";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            String query = "SELECT * FROM " + TABLE_NAME;

            try (ResultSet resultSet = stmt.executeQuery(query)) {
                while (resultSet.next()) {
                    System.out.printf("ReplyID: %s |PostID: %s |Value: %d | ResponderID: %s ",
                            resultSet.getString("replyId"), resultSet.getString("postId"), resultSet.getInt("value"),
                            resultSet.getString("responderId"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
