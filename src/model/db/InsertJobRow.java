package model.db;

import java.sql.Connection;
import java.sql.Statement;

public class InsertJobRow {

    public static void main() {
        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "JOB";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {    // Generate 6 rows for post table


            int result5 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('JOB001', 'Moving House', 'Need a person to help me move my belongings to a new house','s5','OPEN','image/defaultImage.png',190)");
            int result6 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('JOB002', 'Cleaning House', 'Need a person to clean my house','s6','OPEN','image/diceImage.png',120)");

            con.commit();

            System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
            System.out.println(result5 + " row(s) affected");
            System.out.println(result6 + " row(s) affected");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
