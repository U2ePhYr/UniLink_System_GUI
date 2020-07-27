package model.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateSaleTable {

    public static void main() throws SQLException {

        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "SALE";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int result = stmt.executeUpdate("CREATE TABLE sale ("
                    + "id VARCHAR(40) NOT NULL,"
                    + "title VARCHAR(40) ,"
                    + "description VARCHAR(100) ,"
                    + "creatorId VARCHAR(20) ,"
                    + "status VARCHAR(8) ,"
                    + "imageFile VARCHAR(50) ,"
                    + "askingPrice INT ,"
                    + "minimumRaise INT,"
                    + "PRIMARY KEY (id))");
            if (result == 0) {
                System.out.println("Sale Table " + TABLE_NAME + " has been created successfully");
            } else {
                System.out.println("Sale Table " + TABLE_NAME + " is not created");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
