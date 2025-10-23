/**
 * @author Nguyễn Nam Trung Nguyên
 */

package dao;

import connect_database.DatabaseConnection;
import entity.Tau;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao_Tau {
    private final Connection con;

    public Dao_Tau() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    /**
     * 🔹 Lấy tất cả chuyến tàu
     */
    public List<Tau> getAll() {
        List<Tau> list = new ArrayList<>();
        String sql = "SELECT maChuyenTau, maLoaiTau, tenChuyenTau, trangThai FROM ChuyenTau ORDER BY maChuyenTau";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tau ct = new Tau(
                        rs.getString("maChuyenTau"),
                        rs.getString("maLoaiTau"),
                        rs.getString("tenChuyenTau"),
                        rs.getBoolean("trangThai")
                );
                list.add(ct);
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi lấy danh sách chuyến tàu: " + e.getMessage());
        }

        return list;
    }

    /**
     * 🔹 Tìm chuyến tàu theo mã
     */
    public Tau getById(String maChuyenTau) {
        String sql = "SELECT maChuyenTau, maLoaiTau, tenChuyenTau, trangThai FROM ChuyenTau WHERE maChuyenTau = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maChuyenTau);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Tau(
                            rs.getString("maChuyenTau"),
                            rs.getString("maLoaiTau"),
                            rs.getString("tenChuyenTau"),
                            rs.getBoolean("trangThai")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi tìm chuyến tàu theo mã: " + e.getMessage());
        }
        return null;
    }

    /**
     * 🔹 Thêm chuyến tàu mới
     */
    public boolean insert(Tau ct) {
        String sql = "INSERT INTO ChuyenTau (maChuyenTau, maLoaiTau, tenChuyenTau, trangThai) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ct.getMaChuyenTau());
            ps.setString(2, ct.getMaLoaiTau());
            ps.setString(3, ct.getTenChuyenTau());
            ps.setBoolean(4, ct.isTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi thêm chuyến tàu: " + e.getMessage());
        }
        return false;
    }

    /**
     * 🔹 Cập nhật thông tin chuyến tàu
     */
    public boolean update(Tau ct) {
        String sql = "UPDATE ChuyenTau SET maLoaiTau=?, tenChuyenTau=?, trangThai=? WHERE maChuyenTau=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ct.getMaLoaiTau());
            ps.setString(2, ct.getTenChuyenTau());
            ps.setBoolean(3, ct.isTrangThai());
            ps.setString(4, ct.getMaChuyenTau());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi cập nhật chuyến tàu: " + e.getMessage());
        }
        return false;
    }

    /**
     * 🔹 Xóa chuyến tàu theo mã
     */
    public boolean delete(String maChuyenTau) {
        String sql = "DELETE FROM ChuyenTau WHERE maChuyenTau=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maChuyenTau);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi khi xóa chuyến tàu: " + e.getMessage());
        }
        return false;
    }
}

