package dao;

import entity.KhuyenMai;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connect_database.DatabaseConnection;

public class Dao_KhuyenMai {

    // ====================== LẤY DANH SÁCH ======================
    public List<KhuyenMai> getAll() {
        List<KhuyenMai> list = new ArrayList<>();
        String sql = "SELECT * FROM KhuyenMai";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date ngayApDung = rs.getDate("NgayApDung");
                Date ngayHetHan = rs.getDate("NgayHetHan");

                KhuyenMai km = new KhuyenMai(
                        rs.getString("MaKM"),
                        rs.getString("MoTa"),
                        rs.getString("LoaiKM"),
                        ngayApDung != null ? ngayApDung.toLocalDate() : null,
                        ngayHetHan != null ? ngayHetHan.toLocalDate() : null,
                        rs.getFloat("MucKM"),
                        rs.getBoolean("trangThaiKM")
                );
                list.add(km);
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy danh sách khuyến mãi: " + e.getMessage());
        }
        return list;
    }

    // ====================== THÊM MỚI ======================
    public boolean insert(KhuyenMai km) {
        String sql = "INSERT INTO KhuyenMai (MaKM, MoTa, LoaiKM, NgayApDung, NgayHetHan, MucKM, trangThaiKM) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, km.getMaKM());
            ps.setString(2, km.getMoTa());
            ps.setString(3, km.getLoaiKM());
            ps.setDate(4, Date.valueOf(km.getNgayApDung()));
            ps.setDate(5, Date.valueOf(km.getNgayKetThuc()));
            ps.setFloat(6, km.getMucKM());
            ps.setBoolean(7, km.isTrangThai());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm khuyến mãi: " + e.getMessage());
        }
        return false;
    }

    // ====================== CẬP NHẬT ======================
    public boolean update(KhuyenMai km) {
        String sql = "UPDATE KhuyenMai SET MoTa=?, LoaiKM=?, NgayApDung=?, NgayHetHan=?, MucKM=?, trangThaiKM=? WHERE MaKM=?";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, km.getMoTa());
            ps.setString(2, km.getLoaiKM());
            ps.setDate(3, Date.valueOf(km.getNgayApDung()));
            ps.setDate(4, Date.valueOf(km.getNgayKetThuc()));
            ps.setFloat(5, km.getMucKM());
            ps.setBoolean(6, km.isTrangThai());
            ps.setString(7, km.getMaKM());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cập nhật khuyến mãi: " + e.getMessage());
        }
        return false;
    }

    // ====================== XÓA ======================
    public boolean delete(String maKM) {
        String sql = "DELETE FROM KhuyenMai WHERE MaKM=?";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maKM);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xóa khuyến mãi: " + e.getMessage());
        }
        return false;
    }
}
