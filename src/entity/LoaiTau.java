
package entity;

import java.io.Serializable;

public class LoaiTau implements Serializable {
    private String maLoaiTau; // VARCHAR(10), PK
    private String tenLoaiTau; // NVARCHAR(50), NOT NULL

    public LoaiTau() {}

    public LoaiTau(String maLoaiTau, String tenLoaiTau) {
        this.maLoaiTau = maLoaiTau;
        this.tenLoaiTau = tenLoaiTau;
    }

    public String getMaLoaiTau() { return maLoaiTau; }
    public void setMaLoaiTau(String maLoaiTau) { this.maLoaiTau = maLoaiTau; }
    public String getTenLoaiTau() { return tenLoaiTau; }
    public void setTenLoaiTau(String tenLoaiTau) { this.tenLoaiTau = tenLoaiTau; }

    @Override
    public String toString() {
        return "LoaiTau{maLoaiTau='" + maLoaiTau + "', tenLoaiTau='" + tenLoaiTau + "'}";
    }
}
