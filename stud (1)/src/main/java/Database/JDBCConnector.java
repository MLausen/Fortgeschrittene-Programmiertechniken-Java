package Database;

import Helper.ErrorDialog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Team 10
 */
public class JDBCConnector {
    public static String TABLE_PRODUCTS_FPT = "jdbc:postgresql://java.is.uni-due.de/ws1011";
    public static String TABLE_USERNAME = "username";
    public static String TABLE_PW = "pw";

    public JDBCConnector() {
    }

    private void readUrl(String table, String name, String pw) {
        try (Connection connection = DriverManager.getConnection(table, name, pw)) {
            DatabaseMetaData dmd = connection.getMetaData();
            String url = dmd.getURL();
            // edit
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorDialog.error("Database connection failed");
        }
    }

    private void readUserName(String table, String name, String pw) {
        /*
        *
            The correct method should be DatabaseMetaData.getUserName(),
            but as you demonstrate not all databases implement that correctly.
            Another way would be to use the JDBC function escape USER() eg
            (eg SELECT {fn USER()} FROM DUAL), but not all drivers implement all JDBC escapes,
            and it could just be that this returns the same as the DatabaseMetaData.
            You could also try the SQL standard defined CURRENT_USER (or USER) in a query,
            but there you have two problems: 1) some databases require you to select from a
            table (eg DUAL in Oracle, some don't) and 2) not all databases implement all parts of the SQL standards,
            so the CURRENT_USER or USER context variable might be absent.

            But as you have the DataSource object, you could try to get the user property from the datasource
            (it is defined in table 9.1 of the JDBC 4.1 specification, although the property names described
            there are not all required).

            So for example:
            Method getter = new PropertyDescriptor("user", ds.getClass()).getReadMethod();
            String value = (String) getter.invoke(ds);

            This assume that 1) the DataSource ds has a getUser() and 2) that it is actually set (for example with SQL Server integrated security the datasource doesn't need to know about a user).
        * */
        List<String> result = new ArrayList<String>();
        try {
            Connection con = DriverManager.getConnection(table, name, pw);
            DatabaseMetaData dmd = con.getMetaData();
            ResultSet set = dmd.getCatalogs();

            // not always implemented
            dmd.getUserName();

            while(set.next()){
                //TODO
                result.add(set.getString("TABLE_X"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            ErrorDialog.error("error");
        }


        // TODO edit sample https://jdbc.postgresql.org/documentation/80/connect.html
        String url = "jdbc:postgresql://localhost/test";
        Properties props = new Properties();
        props.setProperty("user", "fred");
        props.setProperty("password", "secret");
        props.setProperty("ssl", "true");
        try {
            Connection conn = DriverManager.getConnection(url, props);
            // equals
            url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
        }
    }

    private void getAllTablesFromDB(String table, String name, String pw) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(table, name, pw);

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet res = meta.getTables(null, null, null,
                    new String[]{"TABLE"});
            System.out.println("List of tables: ");

            /*
             TODO this is a sample
             */
            while (res.next()) {
                // edit me
                System.out.println(
                        "   " + res.getString("TABLE_CAT")
                                + ", " + res.getString("TABLE_SCHEM")
                                + ", " + res.getString("TABLE_NAME")
                                + ", " + res.getString("TABLE_TYPE")
                                + ", " + res.getString("REMARKS"));
            }
            res.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                // wtf: connection.close();
            }
        }
    }
}
