package dao;

import entity.LichTrinh;
import java.sql.*;
import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import connectDB.DatabaseConnection;

public class DashBoardDao {

    // LẤY DOANH THU HÔM NAY: Tổng tiền từ bảng HoaDon trong ngày hôm nay
    public double getDailyRevenue() {
        String sql = "SELECT SUM(TongTien) as total FROM HoaDon WHERE NgayLapHoaDon = CAST(GETDATE() AS DATE)";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // LẤY SỐ HÀNH KHÁCH: Đếm số vé bán được trong ngày hôm nay
    public int getPassengerCount() {
        String sql = "SELECT COUNT(cthd.MaVe) as count FROM ChiTietHoaDon cthd " +
                     "JOIN HoaDon hd ON cthd.MaHD = hd.MaHD " +
                     "WHERE hd.NgayLapHoaDon = CAST(GETDATE() AS DATE)";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    
    // THÊM MỚI: LẤY DOANH THU THÁNG - Tổng tiền từ bảng HoaDon trong tháng hiện tại
    public double getMonthlyRevenue() {
        // Lấy tổng doanh thu từ đầu tháng đến hiện tại
        String sql = "SELECT SUM(TongTien) as total FROM HoaDon " +
                     "WHERE MONTH(NgayLapHoaDon) = MONTH(GETDATE()) " +
                     "AND YEAR(NgayLapHoaDon) = YEAR(GETDATE())";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // LẤY THÔNG BÁO: Tạm thời trả về danh sách rỗng (chờ phát triển)
    public List<String> getRecentNotifications(int limit) {
        List<String> notifications = new ArrayList<>();
        notifications.add("• Tính năng thông báo đang được phát triển.");
        return notifications;
    }
    
    
    
    // LẤY LỊCH TRÌNH SẮP TỚI: Lấy danh sách các chuyến tàu sắp khởi hành
    public List<LichTrinh> getUpcomingSchedules(int limit) {
        List<LichTrinh> schedules = new ArrayList<>();
        String sql = "SELECT TOP (?) " +
                     "ct.tenChuyenTau, " +
                     "gaDi.TenGa AS TenGaDi, " +
                     "gaDen.TenGa AS TenGaDen, " +
                     "lt.ThoiGianKhoiHanh, " +
                     "kc.KhoangCach " +
                     "FROM LichTrinh lt " +
                     "JOIN ChuyenTau ct ON lt.maChuyenTau = ct.maChuyenTau " +
                     "JOIN Ga gaDi ON lt.gaDi = gaDi.MaGa " +
                     "JOIN Ga gaDen ON lt.gaDen = gaDen.MaGa " +
                     "LEFT JOIN KhoangCachGa kc ON lt.gaDi = kc.maGaDi AND lt.gaDen = kc.maGaDen " +
                     "WHERE lt.ThoiGianKhoiHanh >= GETDATE() AND lt.TrangThai = 1 " +
                     "ORDER BY lt.ThoiGianKhoiHanh";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limit);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Timestamp khoiHanh = rs.getTimestamp("ThoiGianKhoiHanh");
                    
                    schedules.add(new LichTrinh(
                        rs.getString("tenChuyenTau"),
                        rs.getString("TenGaDi"),
                        rs.getString("TenGaDen"),
                        khoiHanh.toLocalDateTime().toLocalDate(),
                        khoiHanh.toLocalDateTime().toLocalTime(),
                        rs.getDouble("KhoangCach")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }
    
 // LẤY DOANH THU TUẦN: Lấy doanh thu theo từng ngày trong tuần (ĐÃ SỬA LỖI)
    public double[] getWeeklyRevenue(int weekOffset) {
        double[] weeklyRevenue = new double[7]; 
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        
        LocalDate targetDate = LocalDate.now().plusWeeks(weekOffset);
        LocalDate startOfWeek = targetDate.with(weekFields.dayOfWeek(), 1); 
        LocalDate endOfWeek = targetDate.with(weekFields.dayOfWeek(), 7);   
        String sql = "SELECT DATEPART(weekday, NgayLapHoaDon) as day_num, SUM(TongTien) as total " +
                     "FROM HoaDon " +
                     "WHERE NgayLapHoaDon BETWEEN ? AND ? " + 
                     "GROUP BY DATEPART(weekday, NgayLapHoaDon)";
                     
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, java.sql.Date.valueOf(startOfWeek));
            stmt.setDate(2, java.sql.Date.valueOf(endOfWeek));
            
            try (ResultSet rs = stmt.executeQuery()) {
                Arrays.fill(weeklyRevenue, 0);
                while (rs.next()) {
                    int dayNum = rs.getInt("day_num");
                    double total = rs.getDouble("total");
                    
                    int index = (dayNum == 1) ? 6 : dayNum - 2; 
                    
                    if (index >= 0 && index < 7) {
                        weeklyRevenue[index] = total;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return weeklyRevenue;
    }
}