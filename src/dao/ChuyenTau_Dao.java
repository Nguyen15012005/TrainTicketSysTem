package dao;
import entity.ChuyenTau;
import entity.LichTrinh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChuyenTau_Dao {
    private Connection connection;

    public ChuyenTau_Dao(Connection connection) {
        this.connection = connection;
    }

    public ChuyenTau getByMa(String maChuyenTau) {
        ChuyenTau ct = null;
        try {
            String sql = "SELECT * FROM ChuyenTau WHERE maChuyenTau = ? AND trangThai = 1";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maChuyenTau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ct = new ChuyenTau();
                ct.setMaChuyenTau(rs.getString("maChuyenTau"));
                ct.setMaLoaiTau(rs.getString("MaLoaiTau"));
                ct.setTenChuyenTau(rs.getString("tenChuyenTau"));
                ct.setTrangThai(rs.getBoolean("trangThai"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ct;
    }
}