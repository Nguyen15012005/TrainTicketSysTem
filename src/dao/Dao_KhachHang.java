package dao;

import connect_database.DatabaseConnection;
import entity.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao_KhachHang {

    private Connection con;

    public Dao_KhachHang() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Đảm bảo connection luôn mở.
     */
    private Connection ensureOpen() throws SQLException {
        if (con == null || con.isClosed()) {
            DatabaseConnection.getInstance().connect();
            con = DatabaseConnection.getInstance().getConnection();
            System.out.println("🔁 KhachHangDAO reconnected.");
        }
        return con;
    }

    /**
     * Lấy toàn bộ danh sách khách hàng.
     */
    public List<KhachHang> getAll() {
        List<KhachHang> list = new ArrayList<>();
        String sql = "SELECT MaKH, TenKH, SoCCCD, SDT, Email FROM KhachHang";

        try {
            Connection c = ensureOpen();
            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(new KhachHang(
                            rs.getString("MaKH"),
                            rs.getString("TenKH"),
                            rs.getString("SoCCCD"),
                            rs.getString("SDT"),
                            rs.getString("Email")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi truy vấn KhachHangDAO.getAll(): " + e.getMessage());
        }
        return list;
    }

    /**
     * Đóng kết nối.
     */
    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("🔒 KhachHangDAO connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi đóng kết nối KhachHangDAO: " + e.getMessage());
        }
    }
}
