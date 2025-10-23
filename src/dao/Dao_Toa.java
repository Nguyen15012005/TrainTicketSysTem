package dao;
import entity.ChuyenTau;
import entity.LichTrinh;
import entity.Toa;
import entity.ToaTau;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Dao_Toa {
    private Connection connection;

    public Dao_Toa(Connection connection) {
        this.connection = connection;
    }

    public List<ToaTau> getToaByChuyenTau(String maChuyenTau) {
        List<ToaTau> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Toa WHERE ChuyenTau = ? ORDER BY SoToa ASC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maChuyenTau);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ToaTau toa = new ToaTau();
                toa.setMaToa(rs.getString("MaToa"));
                toa.setSoToa(rs.getInt("SoToa"));
                toa.setLoaiToa(rs.getString("LoaiToa"));
                toa.setSoLuongGhe(rs.getInt("SoLuongGhe"));
                toa.setChuyenTau(rs.getString("ChuyenTau"));
                list.add(toa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalSeatsByChuyenTau(String maChuyenTau) {
        int total = 0;
        try {
            String sql = "SELECT SUM(SoLuongGhe) AS Total FROM Toa WHERE ChuyenTau = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, maChuyenTau);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }
}