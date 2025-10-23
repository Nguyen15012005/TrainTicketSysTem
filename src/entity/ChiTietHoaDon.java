
package entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ChiTietHoaDon implements Serializable {
    private String maHD; // VARCHAR(15), PK, FK to HoaDon
    private String maVe; // CHAR(14), PK, FK to Ve
    private BigDecimal giaVeSauGiam; // DECIMAL(15,2), NOT NULL

    public ChiTietHoaDon() {}

    public ChiTietHoaDon(String maHD, String maVe, BigDecimal giaVeSauGiam) {
        this.maHD = maHD;
        this.maVe = maVe;
        this.giaVeSauGiam = giaVeSauGiam;
    }

    public String getMaHD() { return maHD; }
    public void setMaHD(String maHD) { this.maHD = maHD; }
    public String getMaVe() { return maVe; }
    public void setMaVe(String maVe) { this.maVe = maVe; }
    public BigDecimal getGiaVeSauGiam() { return giaVeSauGiam; }
    public void setGiaVeSauGiam(BigDecimal giaVeSauGiam) { this.giaVeSauGiam = giaVeSauGiam; }

    @Override
    public String toString() {
        return "ChiTietHoaDon{maHD='" + maHD + "', maVe='" + maVe + "', giaVeSauGiam=" + giaVeSauGiam + "}";
    }
}
