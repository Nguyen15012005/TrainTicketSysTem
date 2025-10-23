package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connect_database.DatabaseConnection;
import entity.HoaDon;

public class Dao_HoaDon {

    private Connection con;

    public Dao_HoaDon() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Đảm bảo connection luôn mở.
     */
    private Connection ensureOpen() throws SQLException {
        if (con == null || con.isClosed()) {
            DatabaseConnection.getInstance().connect();
            con = DatabaseConnection.getInstance().getConnection();
            System.out.println("🔁 HoaDonDAO reconnected.");
        }
        return con;
    }

    /**
     * Lấy toàn bộ hóa đơn.
     */
    public List<HoaDon> getAll() {
        List<HoaDon> list = new ArrayList<>();
        String sql = "SELECT MaHD, MaKH, MaNV, NgayLapHoaDon, TongTien FROM HoaDon";
        try {
            Connection c = ensureOpen();
            try (PreparedStatement ps = c.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Date d = rs.getDate("NgayLapHoaDon");

                    list.add(new HoaDon(
                        rs.getString("MaHD"),
                        rs.getString("MaNV"),
                        rs.getString("MaKH"),
                        (d != null ? d.toLocalDate() : null),
                        rs.getString("MaKM"),
                        rs.getBigDecimal("TongTien"),
                        rs.getBigDecimal("TongGiamGia")
                    ));

                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi truy vấn HoaDonDAO.getAll(): " + e.getMessage());
        }
        return list;
    }

    /**
     * Tìm hóa đơn chứa vé.
     */
    public HoaDon findByVeTau(String maVeTau) {
        String sql = """
                SELECT h.MaHD, h.MaKH, h.MaNV, h.NgayLapHoaDon, h.TongTien
                FROM HoaDon h JOIN ChiTietHoaDon c ON h.MaHD = c.MaHD
                WHERE c.MaVeTau = ?
                """;
        try {
            Connection c = ensureOpen();
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, maVeTau);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Date d = rs.getDate("NgayLapHoaDon");
                        return new HoaDon(
                                rs.getString("MaHD"),
                                rs.getString("MaNV"),
                                rs.getString("MaKH"),
                                (d != null ? d.toLocalDate() : null),
                                null, // maKM tạm để null
                                rs.getBigDecimal("TongTien"), // chuyển sang BigDecimal
                                BigDecimal.ZERO // tongGiamGia tạm là 0
                        );

                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi truy vấn HoaDonDAO.findByVeTau(): " + e.getMessage());
        }
        return null;
    }
}
