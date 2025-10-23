package dao;

import connect_database.DatabaseConnection;
import entity.VeTau;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Dao_VeTau {

    private Connection con;

    public Dao_VeTau() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Đảm bảo connection luôn mở.
     */
    private Connection ensureOpen() throws SQLException {
        if (con == null || con.isClosed()) {
            DatabaseConnection.getInstance().connect();
            con = DatabaseConnection.getInstance().getConnection();
            System.out.println("🔁 VeTauDAO reconnected.");
        }
        return con;
    }

    /**
     * Lấy toàn bộ vé.
     */
    public List<VeTau> getAll() {
        List<VeTau> list = new ArrayList<>();
        String sql = "SELECT MaVeTau, MaKH, TenHanhKhach, NgaySinh, GiaVeTau, TinhTrangVeTau FROM VeTau";

        try {
            Connection c = ensureOpen();
            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            handleSqlError("VeTauDAO.getAll", e);
        }
        return list;
    }

    /**
     * Lấy danh sách vé theo mã khách hàng.
     */
    public List<VeTau> getByMaKH(String maKH) {
        List<VeTau> list = new ArrayList<>();
        String sql = "SELECT MaVeTau, MaKH, TenHanhKhach, NgaySinh, GiaVeTau, TinhTrangVeTau FROM VeTau WHERE MaKH = ?";

        try {
            Connection c = ensureOpen();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, maKH);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapRow(rs));
                    }
                }
            }
        } catch (SQLException e) {
            handleSqlError("VeTauDAO.getByMaKH", e);
        }
        return list;
    }

    /**
     * Lấy danh sách vé theo mã hóa đơn.
     */
    public List<VeTau> getByHoaDon(String maHD) {
        List<VeTau> list = new ArrayList<>();
        String sql = """
                SELECT v.MaVeTau, v.MaKH, v.TenHanhKhach, v.NgaySinh, v.GiaVeTau, v.TinhTrangVeTau
                FROM VeTau v JOIN ChiTietHoaDon c ON v.MaVeTau = c.MaVeTau
                WHERE c.MaHD = ?
                """;

        try {
            Connection c = ensureOpen();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, maHD);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        list.add(mapRow(rs));
                    }
                }
            }
        } catch (SQLException e) {
            handleSqlError("VeTauDAO.getByHoaDon", e);
        }
        return list;
    }

    /**
     * Đóng connection khi không dùng nữa.
     */
    public void close() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("🔒 VeTauDAO connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi đóng kết nối VeTauDAO: " + e.getMessage());
        }
    }

    // ===== HÀM HỖ TRỢ =====

    /** Chuyển đổi 1 dòng ResultSet thành đối tượng VeTau */
  

        private VeTau mapRow(ResultSet rs) throws SQLException {
            Date d = rs.getDate("NgaySinh");
            LocalDate ngaySinh = (d != null) ? d.toLocalDate() : null;

            return new VeTau(
                rs.getString("MaVeTau"),            // maVeTau
                rs.getString("TenHanhKhach"),       // tenHanhKhach
                ngaySinh,                           // ngaySinh
                rs.getDouble("GiaVeTau"),           // giaVeTau
                rs.getString("TinhTrangVeTau"),     // tinhTrangVeTau
                rs.getString("MaKH"),               // maKH
                null                                // maHD (chưa có trong câu query)
            );
        }

    /** Ghi log và xử lý reconnect nếu cần */
    private void handleSqlError(String methodName, SQLException e) {
        String msg = e.getMessage() != null ? e.getMessage().toLowerCase() : "";
        if (msg.contains("connection is closed") || msg.contains("the connection is closed")) {
            System.err.println("⚠️ " + methodName + ": Connection closed, reconnecting...");
            try {
                DatabaseConnection.getInstance().connect();
                con = DatabaseConnection.getInstance().getConnection();
            } catch (SQLException e2) {
                System.err.println("❌ " + methodName + " reconnect failed: " + e2.getMessage());
            }
        } else {
            System.err.println("❌ Lỗi khi truy vấn " + methodName + ": " + e.getMessage());
        }
    }
}
