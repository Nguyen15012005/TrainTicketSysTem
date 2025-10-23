package entity;

import java.time.LocalDateTime;
import common.LoaiTau;
import common.LoaiToa;

public class LichTrinh {
    private String maLichTrinh;
    private String maChuyenTau;
    private String gaDi;
    private String gaDen;
    private LocalDateTime thoiGianKhoiHanh;
    private LocalDateTime thoiGianDuKienDen;
    private LoaiTau loaiTau;
    private LoaiToa loaiToa;
    private boolean trangThai;

    public LichTrinh() {}

    public LichTrinh(String maLichTrinh, String maChuyenTau, String gaDi, String gaDen,
                     LocalDateTime thoiGianKhoiHanh, LocalDateTime thoiGianDuKienDen,
                     LoaiTau loaiTau, LoaiToa loaiToa, boolean trangThai) {
        this.maLichTrinh = maLichTrinh;
        this.maChuyenTau = maChuyenTau;
        this.gaDi = gaDi;
        this.gaDen = gaDen;
        this.thoiGianKhoiHanh = thoiGianKhoiHanh;
        this.thoiGianDuKienDen = thoiGianDuKienDen;
        this.loaiTau = loaiTau;
        this.loaiToa = loaiToa;
        this.trangThai = trangThai;
    }

    public String getMaLichTrinh() {
        return maLichTrinh;
    }

    public void setMaLichTrinh(String maLichTrinh) {
        this.maLichTrinh = maLichTrinh;
    }

    public String getMaChuyenTau() {
        return maChuyenTau;
    }

    public void setMaChuyenTau(String maChuyenTau) {
        this.maChuyenTau = maChuyenTau;
    }

    public String getGaDi() {
        return gaDi;
    }

    public void setGaDi(String gaDi) {
        this.gaDi = gaDi;
    }

    public String getGaDen() {
        return gaDen;
    }

    public void setGaDen(String gaDen) {
        this.gaDen = gaDen;
    }

    public LocalDateTime getThoiGianKhoiHanh() {
        return thoiGianKhoiHanh;
    }

    public void setThoiGianKhoiHanh(LocalDateTime thoiGianKhoiHanh) {
        this.thoiGianKhoiHanh = thoiGianKhoiHanh;
    }

    public LocalDateTime getThoiGianDuKienDen() {
        return thoiGianDuKienDen;
    }

    public void setThoiGianDuKienDen(LocalDateTime thoiGianDuKienDen) {
        this.thoiGianDuKienDen = thoiGianDuKienDen;
    }

    public LoaiTau getLoaiTau() {
        return loaiTau;
    }

    public void setLoaiTau(LoaiTau loaiTau) {
        this.loaiTau = loaiTau;
    }

    public LoaiToa getLoaiToa() {
        return loaiToa;
    }

    public void setLoaiToa(LoaiToa loaiToa) {
        this.loaiToa = loaiToa;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}
