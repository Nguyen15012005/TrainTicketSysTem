
package entity;

import java.io.Serializable;

public class ChuyenTau implements Serializable {
    private String maChuyenTau; // VARCHAR(10), PK
    private String maLoaiTau; // VARCHAR(10), NOT NULL, FK to LoaiTau
    private String tenChuyenTau; // NVARCHAR(50), NOT NULL
    private boolean trangThai; // BIT, NOT NULL, DEFAULT 1

    public ChuyenTau() {}

    public ChuyenTau(String maChuyenTau, String maLoaiTau, String tenChuyenTau, boolean trangThai) {
        this.maChuyenTau = maChuyenTau;
        this.maLoaiTau = maLoaiTau;
        this.tenChuyenTau = tenChuyenTau;
        this.trangThai = trangThai;
    }

    public String getMaChuyenTau() { return maChuyenTau; }
    public void setMaChuyenTau(String maChuyenTau) { this.maChuyenTau = maChuyenTau; }
    public String getMaLoaiTau() { return maLoaiTau; }
    public void setMaLoaiTau(String maLoaiTau) { this.maLoaiTau = maLoaiTau; }
    public String getTenChuyenTau() { return tenChuyenTau; }
    public void setTenChuyenTau(String tenChuyenTau) { this.tenChuyenTau = tenChuyenTau; }
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return "ChuyenTau{maChuyenTau='" + maChuyenTau + "', maLoaiTau='" + maLoaiTau +
                "', tenChuyenTau='" + tenChuyenTau + "', trangThai=" + trangThai + "}";
    }
}
