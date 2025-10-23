/**
 * @author Nguyễn Nam Trung Nguyên
 */

package dao;

import entity.GaTau;
import connect_database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao_GaTau {
    private final Connection con;

    public Dao_GaTau() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * Lấy tất cả ga
     */
    public List<GaTau> getAll() {
        List<GaTau> list = new ArrayList<>();
        String sql = "SELECT MaGa, TenGa, trangThaiGa FROM Ga ORDER BY MaGa";
        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                GaTau ga = new GaTau(
                        rs.getString("MaGa"),
                        rs.getString("TenGa"),
                        rs.getBoolean("trangThaiGa") // ✅ Đọc kiểu boolean
                );
                list.add(ga);
            }

        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy danh sách ga: " + e.getMessage());
        }
        return list;
    }

    /**
     * Lấy ga theo mã
     */
    public GaTau getById(String maGa) {
        String sql = "SELECT MaGa, TenGa, TrangThai FROM Ga WHERE MaGa=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maGa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new GaTau(
                            rs.getString("MaGa"),
                            rs.getString("TenGa"),
                            rs.getBoolean("TrangThai")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm ga theo mã: " + e.getMessage());
        }
        return null;
    }

    /**
     * Thêm ga mới
     */
    public boolean insert(GaTau ga) {
        String sql = "INSERT INTO Ga (MaGa, TenGa, TrangThai) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ga.getMaGa());
            ps.setString(2, ga.getTenGa());
            ps.setBoolean(3, ga.isTrangThaiGa()); // ✅ Boolean
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm ga: " + e.getMessage());
        }
        return false;
    }

    /**
     * Cập nhật ga
     */
    public boolean update(GaTau ga) {
        String sql = "UPDATE Ga SET TenGa=?, TrangThai=? WHERE MaGa=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ga.getTenGa());
            ps.setBoolean(2, ga.isTrangThaiGa()); // ✅ Boolean
            ps.setString(3, ga.getMaGa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cập nhật ga: " + e.getMessage());
        }
        return false;
    }

    /**
     * Xóa ga theo mã
     */
    public boolean delete(String maGa) {
        String sql = "DELETE FROM Ga WHERE MaGa=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maGa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xóa ga: " + e.getMessage());
        }
        return false;
    }
}
