package model.db;


import java.sql.Connection;
import java.sql.Statement;

public class InsertEventRow {
    public static void main() {
        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "EVENT";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {    // Generate 6 rows for post table
            int result1 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('EVE001', 'Programming Study Group Wednesday night', 'Lets meet this Wednesday afternoon to finish the programming assignment!', 's1','OPEN','image/defaultImage.png','RMIT Library','15/04/2020',5,0)");

            int result2 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('EVE002', 'Movie on Sunday', 'See a movie this Sunday with a ticket discount applied to all group members.','s2','OPEN','image/diceImage.png','Village Cinema','26/03/2020',4,0)");

            con.commit();

            System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
            System.out.println(result1 + " row(s) affected");
            System.out.println(result2 + " row(s) affected");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
