
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class HoaDon implements Serializable {
    private String maHD; // VARCHAR(15), PK
    private String maNV; // VARCHAR(11), NOT NULL, FK to NhanVien
    private String maKH; // VARCHAR(10), NOT NULL, FK to KhachHang
    private LocalDate ngayLapHoaDon; // DATE, NOT NULL
    private String maKM; // VARCHAR(15), NOT NULL, FK to KhuyenMai
    private BigDecimal tongTien; // DECIMAL(15,2), NOT NULL
    private BigDecimal tongGiamGia; // DECIMAL(15,2), NOT NULL

    public HoaDon() {}

    public HoaDon(String maHD, String maNV, String maKH, LocalDate ngayLapHoaDon, String maKM,
                  BigDecimal tongTien, BigDecimal tongGiamGia) {
        this.maHD = maHD;
        this.maNV = maNV;
        this.maKH = maKH;
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.maKM = maKM;
        this.tongTien = tongTien;
        this.tongGiamGia = tongGiamGia;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public LocalDate getNgayLapHoaDon() { return ngayLapHoaDon; }
    public void setNgayLapHoaDon(LocalDate ngayLapHoaDon) { this.ngayLapHoaDon = ngayLapHoaDon; }
    public String getMaKM() { return maKM; }
    public void setMaKM(String maKM) { this.maKM = maKM; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public BigDecimal getTongGiamGia() { return tongGiamGia; }
    public void setTongGiamGia(BigDecimal tongGiamGia) { this.tongGiamGia = tongGiamGia; }

    @Override
    public String toString() {
        return "HoaDon{maHD='" + maHD + "', maNV='" + maNV + "', maKH='" + maKH +
                "', ngayLapHoaDon=" + ngayLapHoaDon + ", maKM='" + maKM +
                "', tongTien=" + tongTien + ", tongGiamGia=" + tongGiamGia + "}";
    }
}
