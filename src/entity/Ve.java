
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Ve implements Serializable {
    private String maVe; // CHAR(14), PK
    private String maKH; // VARCHAR(10), NOT NULL, FK to KhachHang
    private String maLichTrinh; // VARCHAR(15), NOT NULL, FK to LichTrinh
    private String maLoaiVe; // VARCHAR(10), NOT NULL, FK to LoaiVe
    private String maSoCho; // VARCHAR(15), NOT NULL, FK to ChoNgoi
    private String tenHanhKhach; // NVARCHAR(100), NOT NULL
    private String soCCCD; // CHAR(12), NOT NULL
    private LocalDate ngaySinh; // DATE, NOT NULL
    private String tinhTrangVe; // NVARCHAR(20), NOT NULL, DEFAULT 'Đã bán'
    private boolean khuHoi; // BIT, NOT NULL, DEFAULT 0
    private BigDecimal giaVe; // DECIMAL(12,2), NOT NULL
    private BigDecimal giaGiam; // DECIMAL(12,2), NOT NULL

    public Ve() {}

    public Ve(String maVe, String maKH, String maLichTrinh, String maLoaiVe, String maSoCho,
              String tenHanhKhach, String soCCCD, LocalDate ngaySinh, String tinhTrangVe,
              boolean khuHoi, BigDecimal giaVe, BigDecimal giaGiam) {
        this.maVe = maVe;
        this.maKH = maKH;
        this.maLichTrinh = maLichTrinh;
        this.maLoaiVe = maLoaiVe;
        this.maSoCho = maSoCho;
        this.tenHanhKhach = tenHanhKhach;
        this.soCCCD = soCCCD;
        this.ngaySinh = ngaySinh;
        this.tinhTrangVe = tinhTrangVe;
        this.khuHoi = khuHoi;
        this.giaVe = giaVe;
        this.giaGiam = giaGiam;
    }

    public String getMaVe() { return maVe; }
    public void setMaVe(String maVe) { this.maVe = maVe; }
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public String getMaLichTrinh() { return maLichTrinh; }
    public void setMaLichTrinh(String maLichTrinh) { this.maLichTrinh = maLichTrinh; }
    public String getMaLoaiVe() { return maLoaiVe; }
    public void setMaLoaiVe(String maLoaiVe) { this.maLoaiVe = maLoaiVe; }
    public String getMaSoCho() { return maSoCho; }
    public void setMaSoCho(String maSoCho) { this.maSoCho = maSoCho; }
    public String getTenHanhKhach() { return tenHanhKhach; }
    public void setTenHanhKhach(String tenHanhKhach) { this.tenHanhKhach = tenHanhKhach; }
    public String getSoCCCD() { return soCCCD; }
    public void setSoCCCD(String soCCCD) { this.soCCCD = soCCCD; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(LocalDate ngaySinh) { this.ngaySinh = ngaySinh; }
    public String getTinhTrangVe() { return tinhTrangVe; }
    public void setTinhTrangVe(String tinhTrangVe) { this.tinhTrangVe = tinhTrangVe; }
    public boolean isKhuHoi() { return khuHoi; }
    public void setKhuHoi(boolean khuHoi) { this.khuHoi = khuHoi; }
    public BigDecimal getGiaVe() { return giaVe; }
    public void setGiaVe(BigDecimal giaVe) { this.giaVe = giaVe; }
    public BigDecimal getGiaGiam() { return giaGiam; }
    public void setGiaGiam(BigDecimal giaGiam) { this.giaGiam = giaGiam; }

    @Override
    public String toString() {
        return "Ve{maVe='" + maVe + "', maKH='" + maKH + "', maLichTrinh='" + maLichTrinh +
                "', maLoaiVe='" + maLoaiVe + "', maSoCho='" + maSoCho + "', tenHanhKhach='" + tenHanhKhach +
                "', soCCCD='" + soCCCD + "', ngaySinh=" + ngaySinh + ", tinhTrangVe='" + tinhTrangVe +
                "', khuHoi=" + khuHoi + ", giaVe=" + giaVe + ", giaGiam=" + giaGiam + "}";
    }
}
