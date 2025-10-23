package entity;

import java.time.LocalDate;

public class VeTau {
    private String maVeTau;
    private String tenHanhKhach;
    private LocalDate ngaySinh;
    private double giaVeTau;
    private String tinhTrangVeTau;
    private String maKH;
    private String maHD; // có thể null nếu vé chưa thanh toán

    public VeTau() {
    }

    public VeTau(String maVeTau, String tenHanhKhach, LocalDate ngaySinh, double giaVeTau,
                 String tinhTrangVeTau, String maKH, String maHD) {
        this.maVeTau = maVeTau;
        this.tenHanhKhach = tenHanhKhach;
        this.ngaySinh = ngaySinh;
        this.giaVeTau = giaVeTau;
        this.tinhTrangVeTau = tinhTrangVeTau;
        this.maKH = maKH;
        this.maHD = maHD;
    }

    public String getMaVeTau() {
        return maVeTau;
    }

    public void setMaVeTau(String maVeTau) {
        this.maVeTau = maVeTau;
    }

    public String getTenHanhKhach() {
        return tenHanhKhach;
    }

    public void setTenHanhKhach(String tenHanhKhach) {
        this.tenHanhKhach = tenHanhKhach;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public double getGiaVeTau() {
        return giaVeTau;
    }

    public void setGiaVeTau(double giaVeTau) {
        this.giaVeTau = giaVeTau;
    }

    public String getTinhTrangVeTau() {
        return tinhTrangVeTau;
    }

    public void setTinhTrangVeTau(String tinhTrangVeTau) {
        this.tinhTrangVeTau = tinhTrangVeTau;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    @Override
    public String toString() {
        return maVeTau + " - " + tenHanhKhach + " - " + giaVeTau;
    }
}
