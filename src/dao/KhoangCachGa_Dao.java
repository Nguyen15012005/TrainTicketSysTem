package dao;
import entity.ChuyenTau;
import entity.LichTrinh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class KhoangCachGa_Dao {
    private Connection connection;

    public KhoangCachGa_Dao(Connection connection) {
        this.connection = connection;
    }

    public double getKhoangCach(String maGaDi, String maGaDen) {
        double kc = 0;
        try {
            String sql = "SELECT KhoangCach FROM KhoangCachGa WHERE maGaDi = ? AND maGaDen = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maGaDi);
            ps.setString(2, maGaDen);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kc = rs.getDouble("KhoangCach");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kc;
    }
}