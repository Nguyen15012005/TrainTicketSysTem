/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package connect_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private Connection connection;

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {
        try {
            connect();
            System.out.println("✅ Kết nối SQL Server thành công!");
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi kết nối SQL Server!");
            e.printStackTrace();
        }
    }

    public void connect() throws SQLException {
        try {
            // Nạp driver SQL Server
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Không tìm thấy driver SQL Server!");
            e.printStackTrace();
        }

        String url = "jdbc:sqlserver://localhost:1433;databaseName=TrainTicketSystem;encrypt=false";
        String user = "Nguyen";
        String password = "11092020";

        connection = DriverManager.getConnection(url, user, password);
    }

    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔒 Đã ngắt kết nối CSDL!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
