package dao;

import connect_database.DatabaseConnection;
import entity.NhanVien;
import entity.TaiKhoan;
import common.Role;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Dao_TaiKhoan {

    private final Connection con;

    public Dao_TaiKhoan() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    public String timMaTaiKhoanDauTienConTrong() {
        String sql = "SELECT userID FROM TaiKhoan ORDER BY userID";
        Set<Integer> soDaCo = new HashSet<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                soDaCo.add(rs.getInt("userID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "TK" + System.currentTimeMillis() % 10000;
        }
        int soTiepTheo = 1;
        while (soDaCo.contains(soTiepTheo)) {
            soTiepTheo++;
        }
        return String.format("TK%03d", soTiepTheo);
    }

    public List<TaiKhoan> timTheoMaNhanVien(String maNV) {
        List<TaiKhoan> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan WHERE maNhanVien = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    danhSach.add(mapResultSetToTaiKhoan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public boolean themTaiKhoan(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (userID, userName, email, password, role, status, createdDate, maNhanVien) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, tk.getUserID());
            stmt.setString(2, tk.getUserName());
            stmt.setString(3, tk.getEmail());
            stmt.setString(4, tk.getPassword());
            stmt.setString(5, tk.getRole() != null ? tk.getRole().name() : null);
            stmt.setBoolean(6, tk.isStatus());
            stmt.setDate(7, Date.valueOf(tk.getCreatedDate()));
            // Giả sử tk có getter maNhanVien nếu muốn lưu
            stmt.setString(8, tk.getNhanVien() != null ? tk.getNhanVien().getMaNhanVien() : null);


            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<TaiKhoan> timTheoNgayTao(LocalDate ngay) {
        List<TaiKhoan> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan WHERE createdDate = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(ngay));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    danhSach.add(mapResultSetToTaiKhoan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }


    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET userName = ?, email = ?, password = ?, role = ?, status = ?, maNhanVien = ? WHERE userID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tk.getUserName());
            stmt.setString(2, tk.getEmail());
            stmt.setString(3, tk.getPassword());
            stmt.setString(4, tk.getRole() != null ? tk.getRole().name() : null);
            stmt.setBoolean(5, tk.isStatus());
            stmt.setString(6, tk.getNhanVien() != null ? tk.getNhanVien().getMaNhanVien() : null);

            stmt.setInt(7, tk.getUserID());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoaTaiKhoan(int userID) {
        String sql = "DELETE FROM TaiKhoan WHERE userID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoan timTheoMa(int userID) {
        String sql = "SELECT * FROM TaiKhoan WHERE userID = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTaiKhoan(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TaiKhoan timTheoEmail(String email) {
        String sql = "SELECT * FROM TaiKhoan WHERE email = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTaiKhoan(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TaiKhoan> layDanhSachTaiKhoan() {
        List<TaiKhoan> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                danhSach.add(mapResultSetToTaiKhoan(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<TaiKhoan> timKiem(String tuKhoa) {
        List<TaiKhoan> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan WHERE CAST(userID AS VARCHAR) LIKE ? OR userName LIKE ? OR email LIKE ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            String keyword = "%" + tuKhoa + "%";
            stmt.setString(1, keyword);
            stmt.setString(2, keyword);
            stmt.setString(3, keyword);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    danhSach.add(mapResultSetToTaiKhoan(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public boolean kiemTraTonTai(String email) {
        return timTheoEmail(email) != null;
    }

    private TaiKhoan mapResultSetToTaiKhoan(ResultSet rs) throws SQLException {
        int userID = rs.getInt("userID");
        String userName = rs.getString("userName");
        String email = rs.getString("email");
        String password = rs.getString("password");
        common.Role role = null;
        String roleStr = rs.getString("role");
        if (roleStr != null) {
            role = common.Role.valueOf(roleStr);
        }
        boolean status = rs.getBoolean("status");
        LocalDate createdDate = rs.getDate("createdDate").toLocalDate();

        // Lấy maNhanVien từ CSDL
        String maNV = rs.getString("maNhanVien");
        NhanVien nv = null;
        if (maNV != null) {
            nv = new NhanVien();
            nv.setMaNhanVien(maNV);
            // Nếu muốn, có thể query thêm để lấy đầy đủ thông tin NhanVien
        }

        TaiKhoan tk = new TaiKhoan();
        tk.setUserID(userID);
        tk.setUserName(userName);
        tk.setEmail(email);
        tk.setPassword(password);
        tk.setRole(role);
        tk.setStatus(status);
        tk.setCreatedDate(createdDate);
        tk.setNhanVien(nv);

        return tk;
    }

}
