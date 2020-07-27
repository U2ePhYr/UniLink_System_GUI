package model.db;

import java.sql.Connection;
import java.sql.Statement;

public class InsertSaleRow {
    public static void main() {
        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "SALE";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {    // Generate 6 rows for post table

            int result3 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('SAL001', 'IPad Air 2 64GB', 'Excellent working condition, comes with box, cable and charger.','s3','OPEN','image/defaultImage.png',300,15)");

            int result4 = stmt.executeUpdate("INSERT INTO " + TABLE_NAME +
                    " VALUES ('SAL002', '13-inch Macbook Pro 2018 sale', 'In good condition, ready to use','s4','OPEN','image/diceImage.png',1850,40)");
            con.commit();

            System.out.println("Insert into table " + TABLE_NAME + " executed successfully");
            System.out.println(result3 + " row(s) affected");
            System.out.println(result4 + " row(s) affected");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
