package dao;
import entity.ChuyenTau;
import entity.LichTrinh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class LoaiToa_Dao {
    private Connection connection;

    public LoaiToa_Dao(Connection connection) {
        this.connection = connection;
    }

    public String getTenLoaiToaByMa(String maLoaiToa) {
        String ten = null;
        try {
            String sql = "SELECT TenLoaiToa FROM LoaiToa WHERE MaLoaiToa = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maLoaiToa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ten = rs.getString("TenLoaiToa");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ten;
    }
}