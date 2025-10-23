package dao;

import entity.Ga;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Ga_Dao {
    private Connection connection;
    
    public Ga_Dao(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Lấy tất cả ga đang hoạt động
     */
    public List<Ga> getAllGaHoatDong() {
        List<Ga> list = new ArrayList<>();
        String sql = "SELECT MaGa, TenGa, trangThaiGa FROM Ga WHERE trangThaiGa = 1";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Ga ga = new Ga(
                    rs.getString("MaGa"),
                    rs.getString("TenGa"),
                    rs.getBoolean("trangThaiGa")
                );
                list.add(ga);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Lấy ga theo mã
     */
    public Ga getGaByMa(String maGa) {
        String sql = "SELECT MaGa, TenGa, trangThaiGa FROM Ga WHERE MaGa = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, maGa);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Ga(
                    rs.getString("MaGa"),
                    rs.getString("TenGa"),
                    rs.getBoolean("trangThaiGa")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Lấy danh sách tên ga để hiển thị trong ComboBox
     */
    public List<String> getTenGaList() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT TenGa FROM Ga WHERE trangThaiGa = 1 ORDER BY TenGa";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                list.add(rs.getString("TenGa"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Tìm mã ga theo tên ga
     */
    public String getMaGaByTenGa(String tenGa) {
        String sql = "SELECT MaGa FROM Ga WHERE TenGa = ? AND trangThaiGa = 1";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, tenGa);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getString("MaGa");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}