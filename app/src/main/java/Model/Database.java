package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String user = "android_app";
    private String pass = "SemiC@rrier264";
    private String url = "jdbc:mysql://10.0.2.2:3306/socialmedia";
    private Statement statement;

    public Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");// load driver
            Connection connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            System.out.println("✅ Database connected successfully!");
        } catch (Exception e) {
            System.err.println("❌ Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
