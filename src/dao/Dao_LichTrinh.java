/**
 *
 * @author
 * Nguyễn Nam Trung
 */

package dao;

import connect_database.DatabaseConnection;
import entity.LichTrinh;
import common.LoaiTau;
import common.LoaiToa;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Dao_LichTrinh {
    private final Connection con;

    public Dao_LichTrinh(Connection connection) {
        con = DatabaseConnection.getInstance().getConnection();
    }

    // 🔹 Lấy tất cả lịch trình
    public List<LichTrinh> getAll() {
        List<LichTrinh> list = new ArrayList<>();
        String sql = "SELECT * FROM LichTrinh";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LichTrinh lt = new LichTrinh(
                        rs.getString("MaLichTrinh"),
                        rs.getString("MaChuyenTau"),
                        rs.getString("GaDi"),
                        rs.getString("GaDen"),
                        rs.getTimestamp("ThoiGianKhoiHanh").toLocalDateTime(),
                        rs.getTimestamp("ThoiGianDuKienDen").toLocalDateTime(),
                        parseLoaiTau(rs.getString("LoaiTau")),
                        parseLoaiToa(rs.getString("LoaiToa")),
                        rs.getBoolean("TrangThai")
                );
                list.add(lt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔹 Lấy lịch trình theo mã
    public LichTrinh getById(String maLichTrinh) {
        String sql = "SELECT * FROM LichTrinh WHERE MaLichTrinh = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLichTrinh);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new LichTrinh(
                            rs.getString("MaLichTrinh"),
                            rs.getString("MaChuyenTau"),
                            rs.getString("GaDi"),
                            rs.getString("GaDen"),
                            rs.getTimestamp("ThoiGianKhoiHanh").toLocalDateTime(),
                            rs.getTimestamp("ThoiGianDuKienDen").toLocalDateTime(),
                            parseLoaiTau(rs.getString("LoaiTau")),
                            parseLoaiToa(rs.getString("LoaiToa")),
                            rs.getBoolean("TrangThai")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Thêm mới lịch trình
    public boolean insert(LichTrinh lt) {
        String sql = "INSERT INTO LichTrinh (MaLichTrinh, MaChuyenTau, GaDi, GaDen, ThoiGianKhoiHanh, ThoiGianDuKienDen, LoaiTau, LoaiToa, TrangThai) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, lt.getMaLichTrinh());
            ps.setString(2, lt.getMaChuyenTau());
            ps.setString(3, lt.getGaDi());
            ps.setString(4, lt.getGaDen());
            ps.setTimestamp(5, Timestamp.valueOf(lt.getThoiGianKhoiHanh()));
            ps.setTimestamp(6, Timestamp.valueOf(lt.getThoiGianDuKienDen()));
            ps.setString(7, lt.getLoaiTau().name());
            ps.setString(8, lt.getLoaiToa().name());
            ps.setBoolean(9, lt.isTrangThai());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Cập nhật lịch trình
    public boolean update(LichTrinh lt) {
        String sql = "UPDATE LichTrinh SET MaChuyenTau=?, GaDi=?, GaDen=?, ThoiGianKhoiHanh=?, ThoiGianDuKienDen=?, LoaiTau=?, LoaiToa=?, TrangThai=? WHERE MaLichTrinh=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, lt.getMaChuyenTau());
            ps.setString(2, lt.getGaDi());
            ps.setString(3, lt.getGaDen());
            ps.setTimestamp(4, Timestamp.valueOf(lt.getThoiGianKhoiHanh()));
            ps.setTimestamp(5, Timestamp.valueOf(lt.getThoiGianDuKienDen()));
            ps.setString(6, lt.getLoaiTau().name());
            ps.setString(7, lt.getLoaiToa().name());
            ps.setBoolean(8, lt.isTrangThai());
            ps.setString(9, lt.getMaLichTrinh());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Xóa lịch trình theo mã
    public boolean delete(String maLichTrinh) {
        String sql = "DELETE FROM LichTrinh WHERE MaLichTrinh=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maLichTrinh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ Hàm phụ để parse enum an toàn
    private LoaiTau parseLoaiTau(String value) {
        if (value == null) return null;
        try {
            return LoaiTau.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private LoaiToa parseLoaiToa(String value) {
        if (value == null) return null;
        try {
            return LoaiToa.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    public List<LichTrinh> getLichTrinhByGaAndNgay(String maGaDi, String maGaDen, java.util.Date ngay) {
        List<LichTrinh> list = new ArrayList<>();
        try {
            java.sql.Date sqlNgay = new java.sql.Date(ngay.getTime());
            String sql = "SELECT * FROM LichTrinh WHERE gaDi = ? AND gaDen = ? AND CONVERT(DATE, ThoiGianKhoiHanh) = ? AND TrangThai = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maGaDi);
            ps.setString(2, maGaDen);
            ps.setDate(3, sqlNgay);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LichTrinh lt = new LichTrinh();
                lt.setMaLichTrinh(rs.getString("MaLichTrinh"));
                lt.setMaChuyenTau(rs.getString("maChuyenTau"));
                lt.setGaDi(rs.getString("gaDi"));
                lt.setGaDen(rs.getString("gaDen"));
                lt.setThoiGianKhoiHanh(null);
                lt.setThoiGianDuKienDen(null);
                lt.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(lt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
