package model.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckEventTableExist {
    public static void main(String[] args) throws SQLException {

        final String DB_NAME = "UniLinkDB";

        // IMPORTANT: table name is uppercase
        final String TABLE_NAME = "EVENT";

        // use try-with-resources Statement
        try (Connection con = ConnectionTest.getConnection(DB_NAME)) {

            DatabaseMetaData dbm = con.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);

            if(tables != null) {
                if (tables.next()) {
                    System.out.println("Event Table " + TABLE_NAME + " exists.");
                }
                else {
                    System.out.println("Event Table " + TABLE_NAME + " does not exist.");
                }
                tables.close();
            } else {
                System.out.println(("Problem with retrieving database metadata"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

