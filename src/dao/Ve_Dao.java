package dao;
import entity.ChuyenTau;
import entity.LichTrinh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Ve_Dao {
    private Connection connection;

    public Ve_Dao(Connection connection) {
        this.connection = connection;
    }

    public int getSoldByLichTrinh(String maLichTrinh) {
        int sold = 0;
        try {
            String sql = "SELECT COUNT(*) AS Sold FROM Ve WHERE maLichTrinh = ? AND TinhTrangVe != N'Đã hủy'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maLichTrinh);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sold = rs.getInt("Sold");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sold;
    }

    public boolean isChoSold(String maLichTrinh, String maChoNgoi) {
        boolean sold = false;
        try {
            String sql = "SELECT COUNT(*) AS Count FROM Ve WHERE maLichTrinh = ? AND maSoCho = ? AND TinhTrangVe != N'Đã hủy'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maLichTrinh);
            ps.setString(2, maChoNgoi);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                sold = rs.getInt("Count") > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sold;
    }
}