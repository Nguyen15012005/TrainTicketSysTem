package dao;

import entity.NhanVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connect_database.DatabaseConnection;

public class Dao_NhanVien {
    private final Connection con;

    public Dao_NhanVien() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    // ============================
    // LẤY TẤT CẢ NHÂN VIÊN
    // ============================
    public List<NhanVien> getAllNhanVien() {
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien ORDER BY maNhanVien DESC";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                danhSachNhanVien.add(new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("hoTen"),
                        rs.getDate("ngaySinh"),
                        rs.getDate("NgayVaoLam"),
                        rs.getString("SoCCCD"),
                        rs.getBoolean("GioiTinh"),
                        rs.getString("SoDienThoai"),
                        rs.getString("Email"),
                        rs.getString("ChucVu"),
                        rs.getString("TrangThai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachNhanVien;
    }

    // ============================
    // THÊM NHÂN VIÊN
    // ============================
    public boolean themNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNhanVien, hoTen, GioiTinh, NgayVaoLam, NgaySinh, SoDienThoai, Email, SoCCCD, ChucVu, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nv.getMaNhanVien());
            pstmt.setString(2, nv.getHoTen());
            pstmt.setBoolean(3, nv.isGioiTinh());
            pstmt.setDate(4, new java.sql.Date(nv.getNgayVaoLam().getTime()));
            pstmt.setDate(5, new java.sql.Date(nv.getNgaySinh().getTime()));
            pstmt.setString(6, nv.getSoDienThoai());
            pstmt.setString(7, nv.getEmail());
            pstmt.setString(8, nv.getSoCCCD());
            pstmt.setString(9, nv.getChucVu());
            pstmt.setString(10, nv.getTrangThai());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ============================
    // SỬA NHÂN VIÊN
    // ============================
    public boolean suaNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET hoTen = ?, GioiTinh = ?, NgayVaoLam = ?, NgaySinh = ?, SoDienThoai = ?, " +
                     "Email = ?, SoCCCD = ?, ChucVu = ?, TrangThai = ? WHERE maNhanVien = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nv.getHoTen());
            pstmt.setBoolean(2, nv.isGioiTinh());
            pstmt.setDate(3, new java.sql.Date(nv.getNgayVaoLam().getTime()));
            pstmt.setDate(4, new java.sql.Date(nv.getNgaySinh().getTime()));
            pstmt.setString(5, nv.getSoDienThoai());
            pstmt.setString(6, nv.getEmail());
            pstmt.setString(7, nv.getSoCCCD());
            pstmt.setString(8, nv.getChucVu());
            pstmt.setString(9, nv.getTrangThai());
            pstmt.setString(10, nv.getMaNhanVien());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ============================
    // XÓA NHÂN VIÊN
    // ============================
    public boolean xoaNhanVien(String maNV) {
        String sqlDeleteTaiKhoan = "DELETE FROM TaiKhoan WHERE maNhanVien = ?";
        String sqlDeleteHoaDon = "DELETE FROM HoaDon WHERE MaNV = ?";
        String sqlDeleteNhanVien = "DELETE FROM NhanVien WHERE maNhanVien = ?";

        try {
            con.setAutoCommit(false);

            try (PreparedStatement pstmtTK = con.prepareStatement(sqlDeleteTaiKhoan);
                 PreparedStatement pstmtHD = con.prepareStatement(sqlDeleteHoaDon);
                 PreparedStatement pstmtNV = con.prepareStatement(sqlDeleteNhanVien)) {

                pstmtTK.setString(1, maNV);
                pstmtTK.executeUpdate();

                pstmtHD.setString(1, maNV);
                pstmtHD.executeUpdate();

                pstmtNV.setString(1, maNV);
                int rows = pstmtNV.executeUpdate();

                if (rows > 0) {
                    con.commit();
                    return true;
                } else {
                    con.rollback();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    // ============================
    // LỌC NHÂN VIÊN
    // ============================
    public List<NhanVien> locNhanVien(String maNV, String gioiTinh, String chucVu, String trangThai) {
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM NhanVien WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (maNV != null && !maNV.trim().isEmpty()) {
            sql.append(" AND maNhanVien LIKE ?");
            params.add("%" + maNV + "%");
        }
        if (gioiTinh != null && !"Tất cả".equals(gioiTinh)) {
            sql.append(" AND GioiTinh = ?");
            params.add(gioiTinh.equals("Nam") ? 1 : 0);
        }
        if (chucVu != null && !"Tất cả".equals(chucVu)) {
            sql.append(" AND ChucVu = ?");
            params.add(chucVu);
        }
        if (trangThai != null && !"Tất cả".equals(trangThai)) {
            sql.append(" AND TrangThai = ?");
            params.add(trangThai);
        }

        sql.append(" ORDER BY maNhanVien DESC");

        try (PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    danhSachNhanVien.add(new NhanVien(
                            rs.getString("maNhanVien"),
                            rs.getString("hoTen"),
                            rs.getDate("ngaySinh"),
                            rs.getDate("NgayVaoLam"),
                            rs.getString("SoCCCD"),
                            rs.getBoolean("GioiTinh"),
                            rs.getString("SoDienThoai"),
                            rs.getString("Email"),
                            rs.getString("ChucVu"),
                            rs.getString("TrangThai")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachNhanVien;
    }

    // ============================
    // TẠO MÃ NHÂN VIÊN MỚI
    // ============================
    public String taoMaNhanVienMoi() {
        String maNVMoi = "NV001";
        String sql = "SELECT TOP 1 maNhanVien FROM NhanVien ORDER BY maNhanVien DESC";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String maNVCuoi = rs.getString("maNhanVien");
                int soHienTai = Integer.parseInt(maNVCuoi.substring(2));
                maNVMoi = String.format("NV%03d", soHienTai + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maNVMoi;
    }

    // ============================
    // LẤY DANH SÁCH MÃ NHÂN VIÊN
    // ============================
    public List<String> layDanhSachMaNhanVien() {
        List<String> danhSach = new ArrayList<>();
        String sql = "SELECT MaNhanVien FROM NhanVien ORDER BY MaNhanVien";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                danhSach.add(rs.getString("MaNhanVien"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy danh sách mã nhân viên: " + e.getMessage());
        }

        return danhSach;
    }

    // ============================
    // KIỂM TRA MÃ NHÂN VIÊN TỒN TẠI
    // ============================
    public boolean kiemTraMaNhanVienTonTai(String maNhanVien) {
        String sql = "SELECT COUNT(*) FROM NhanVien WHERE MaNhanVien = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maNhanVien);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ============================
    // LẤY EMAIL THEO MÃ NHÂN VIÊN
    // ============================
    public String getEmailByMaNV(String maNV) {
        String email = null;
        String sql = "SELECT Email FROM NhanVien WHERE maNhanVien = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    email = rs.getString("Email");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }
}
