package dao;

import entity.LichTrinh;
import connect_database.DatabaseConnection;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.*;

public class Dao_ThongKe {

    private final Connection con;

    public Dao_ThongKe() {
        this.con = DatabaseConnection.getInstance().getConnection();
    }

    /** LẤY DOANH THU HÔM NAY */
    public double getDailyRevenue() {
        String sql = "SELECT SUM(TongTien) as total FROM HoaDon WHERE NgayLapHoaDon = CAST(GETDATE() AS DATE)";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getBigDecimal("total") != null ? rs.getBigDecimal("total").doubleValue() : 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /** LẤY SỐ HÀNH KHÁCH HÔM NAY */
    public int getPassengerCount() {
        String sql = "SELECT COUNT(cthd.MaVe) as count " +
                     "FROM ChiTietHoaDon cthd " +
                     "JOIN HoaDon hd ON cthd.MaHD = hd.MaHD " +
                     "WHERE hd.NgayLapHoaDon = CAST(GETDATE() AS DATE)";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /** LẤY DOANH THU THÁNG HIỆN TẠI */
    public double getMonthlyRevenue() {
        String sql = "SELECT SUM(TongTien) as total FROM HoaDon " +
                     "WHERE MONTH(NgayLapHoaDon) = MONTH(GETDATE()) " +
                     "AND YEAR(NgayLapHoaDon) = YEAR(GETDATE())";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getBigDecimal("total") != null ? rs.getBigDecimal("total").doubleValue() : 0.0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /** LẤY DANH SÁCH THÔNG BÁO */
    public List<String> getRecentNotifications(int limit) {
        List<String> notifications = new ArrayList<>();
        notifications.add("• Tính năng thông báo đang được phát triển.");
        return notifications;
    }

    /** LẤY LỊCH TRÌNH SẮP TỚI */
 // LẤY LỊCH TRÌNH SẮP TỚI
    public List<LichTrinh> getUpcomingSchedules(int limit) {
        List<LichTrinh> schedules = new ArrayList<>();
        
        // ⚠️ TOP ? không dùng được trong PreparedStatement với SQL Server
        String sql = "SELECT TOP " + limit + " * FROM LichTrinh " +
                     "WHERE ThoiGianKhoiHanh >= GETDATE() AND TrangThai = 1 " +
                     "ORDER BY ThoiGianKhoiHanh";

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LichTrinh lt = new LichTrinh();
                lt.setMaLichTrinh(rs.getString("MaLichTrinh"));
                lt.setMaChuyenTau(rs.getString("MaChuyenTau"));
                lt.setGaDi(rs.getString("GaDi"));
                lt.setGaDen(rs.getString("GaDen"));
                lt.setThoiGianKhoiHanh(rs.getTimestamp("ThoiGianKhoiHanh").toLocalDateTime());
                lt.setThoiGianDuKienDen(rs.getTimestamp("ThoiGianDuKienDen").toLocalDateTime());

                // Nếu bảng có cột LoaiTau, LoaiToa lưu dạng chuỗi (ENUM)
                try {
                    lt.setLoaiTau(common.LoaiTau.valueOf(rs.getString("LoaiTau")));
                } catch (Exception e) {
                    lt.setLoaiTau(null);
                }

                try {
                    lt.setLoaiToa(common.LoaiToa.valueOf(rs.getString("LoaiToa")));
                } catch (Exception e) {
                    lt.setLoaiToa(null);
                }

                lt.setTrangThai(rs.getBoolean("TrangThai"));

                schedules.add(lt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return schedules;
    }


    /** LẤY DOANH THU THEO TUẦN */
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

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(startOfWeek));
            stmt.setDate(2, java.sql.Date.valueOf(endOfWeek));

            try (ResultSet rs = stmt.executeQuery()) {
                Arrays.fill(weeklyRevenue, 0);
                while (rs.next()) {
                    int dayNum = rs.getInt("day_num");
                    double total = rs.getBigDecimal("total") != null ? rs.getBigDecimal("total").doubleValue() : 0.0;

                    int index = (dayNum == 1) ? 6 : dayNum - 2; // Mapping: Sunday -> 6, Monday->0

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
