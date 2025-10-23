package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.ChoNgoi;
import entity.ChuyenTau;
import entity.LichTrinh;
import entity.Toa;
public class ChoNgoi_Dao {
    private Connection connection;

    public ChoNgoi_Dao(Connection connection) {
        this.connection = connection;
    }

    public List<ChoNgoi> getByToa(String maToa) {
        List<ChoNgoi> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ChoNgoi WHERE Toa = ? ORDER BY Khoang, SoGhe ASC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maToa);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChoNgoi cn = new ChoNgoi();
                cn.setMaChoNgoi(rs.getString("MaChoNgoi"));
                cn.setTang(rs.getInt("Tang"));
                cn.setKhoang(rs.getInt("Khoang"));
                cn.setToa(rs.getString("Toa"));
                cn.setSoGhe(rs.getInt("SoGhe"));
                cn.setLoaiChoNgoi(rs.getString("LoaiChoNgoi"));
                list.add(cn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}