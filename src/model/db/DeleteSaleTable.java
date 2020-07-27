package model.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteSaleTable {
    public static void main(String[] args) throws SQLException {

        final String DB_NAME = "UniLinkDB";
        final String TABLE_NAME = "SALE";

        //use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME);
             Statement stmt = con.createStatement();
        ) {
            int result = stmt.executeUpdate("DROP TABLE sale");

            if(result == 0) {
                System.out.println("Sale Table " + TABLE_NAME + " has been deleted successfully");
            } else {
                System.out.println("Sale Table " + TABLE_NAME + " was not deleted");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
