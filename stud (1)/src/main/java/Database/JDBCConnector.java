package Database;

import Helper.ErrorDialog;
import fpt.com.Product;

import java.sql.*;


/**
 * Created by Team 10
 * singleton pattern  - DB connection/access with JDBC
 * */
public class JDBCConnector {
    // constants for DB connection
    private static final String TABLE_PRODUCTS_FPT = "jdbc:postgresql://java.is.uni-due.de/ws1011";
    private static final String TABLE_USERNAME = "ws1011";
    private static final String TABLE_PW = "ftpw10";

    private static JDBCConnector instance;

    private Connection con = null;

    public JDBCConnector() {
        try {
            DatabaseMetaData dbmd = createConnection().getMetaData();

            //URL and username
            System.out.println("Successfully Connected to the database!"
                    + "\nDB-Url: " + dbmd.getURL().split("//")[1]
                    + "\nUserName: " + dbmd.getUserName());

            //all database table
            String[] types = {"TABLE"};
            ResultSet resultSet = dbmd.getTables(null, null, "%", types);

            while (resultSet.next()) {
                String tableName = resultSet.getString(3);
                String tableCatalog = resultSet.getString(1);
                String tableSchema = resultSet.getString(2);
                System.out.println("Table : " + tableName + " , " + "nCatalog : " + tableCatalog + " , " + "nSchema : " + tableSchema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // sinlge instance to connect db
    public static JDBCConnector getInstance() {
        if (JDBCConnector.instance == null) {
            JDBCConnector.instance = new JDBCConnector();
        }
        return JDBCConnector.instance;
    }

    // returns db connection
    public Connection createConnection() throws SQLException {
        // database connection should not be set twice
        if (con != null) {
            return con;
        } else {
            try {
                con = DriverManager.getConnection(TABLE_PRODUCTS_FPT, TABLE_USERNAME, TABLE_PW);
                return con;
            } catch (SQLException e) {
                e.printStackTrace();
                ErrorDialog.error("Database connection failed");
            }
            return null;
        }
    }

    // insert new entry
    public long insert(String name, double price, int quantity) throws SQLException {
        long id = -1L;
        //pst will automatically close
        try (PreparedStatement pst = createConnection()
                .prepareStatement("INSERT INTO products(name,price,quantity) VALUES (?,?,?)",
                        Statement.RETURN_GENERATED_KEYS); ResultSet rs = pst.getGeneratedKeys();) {
            pst.setString(1, name);
            pst.setDouble(2, price);
            pst.setInt(3, quantity);

            pst.executeUpdate();


            if (rs.next()) {
                id = rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    // insert new product into DB by calling overloaded method insert(String s, double p, int q)
    public void insert(Product product) throws SQLException {
        long id = this.insert(product.getName(), product.getPrice(), product.getQuantity());
        product.setId(id);
    }

    public Product read(long id) {
        Product product = new Model.Product();
        //prst will automatically close
        try (PreparedStatement prst = createConnection().prepareStatement("SELECT id,name,price,quantity FROM products WHERE id=?");
         ResultSet rs = prst.executeQuery()) {
            prst.setLong(1, id);

            while (rs.next()) {
                product.setId(Long.parseLong(rs.getString(1)));
                product.setName(rs.getString(2));
                product.setPrice(Double.parseDouble(rs.getString(3)));
                product.setQuantity(Integer.parseInt(rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    // used as finally
    public void close() {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
