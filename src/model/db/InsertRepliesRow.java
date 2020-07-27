package model.db;


import java.sql.Connection;
import java.sql.Statement;

public class InsertRepliesRow {
    public static void main() {
        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "REPLIES";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {

            int result1 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('1', 'EVE001', 1.0, 's3')");

            int result2 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('2', 'EVE002', 1.0, 's4')");

            int result3 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('3', 'SAL001', 150.6, 's5')");

            int result4 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('4', 'SAL002', 1900.0, 's6')");

            int result5 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('5', 'JOB001', 165.0, 's1')");

            int result6 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('6', 'JOB002', 110.0, 's2')");

            int result7 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('7', 'EVE001', 1.0, 's7')");

            int result8 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('8', 'EVE002', 1.0, 's8')");

            int result9 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('9', 'SAL001', 330.0, 's9')");

            int result10 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('10', 'SAL002', 1480.0, 's10')");

            int result11 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('11', 'JOB001', 155.5, 's11')");

            int result12 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('12', 'JOB002', 90.0, 's12')");


            con.commit();

            System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
            System.out.println(result1 + " row(s) affected");
            System.out.println(result2 + " row(s) affected");
            System.out.println(result3 + " row(s) affected");
            System.out.println(result4 + " row(s) affected");
            System.out.println(result5 + " row(s) affected");
            System.out.println(result6 + " row(s) affected");
            System.out.println(result7 + " row(s) affected");
            System.out.println(result8 + " row(s) affected");
            System.out.println(result9 + " row(s) affected");
            System.out.println(result10 + " row(s) affected");
            System.out.println(result11 + " row(s) affected");
            System.out.println(result12 + " row(s) affected");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
