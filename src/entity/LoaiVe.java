
package entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class LoaiVe implements Serializable {
    private String maLoaiVe; // VARCHAR(10), PK
    private String tenLoaiVe; // NVARCHAR(50), NOT NULL
    private BigDecimal mucGiamGia; // DECIMAL(3,2), NOT NULL, CHECK (0.00 <= MucGiamGia <= 0.25)

    public LoaiVe() {}

    public LoaiVe(String maLoaiVe, String tenLoaiVe, BigDecimal mucGiamGia) {
        this.maLoaiVe = maLoaiVe;
        this.tenLoaiVe = tenLoaiVe;
        this.mucGiamGia = mucGiamGia;
    }

    public String getMaLoaiVe() { return maLoaiVe; }
    public void setMaLoaiVe(String maLoaiVe) { this.maLoaiVe = maLoaiVe; }
    public String getTenLoaiVe() { return tenLoaiVe; }
    public void setTenLoaiVe(String tenLoaiVe) { this.tenLoaiVe = tenLoaiVe; }
    public BigDecimal getMucGiamGia() { return mucGiamGia; }
    public void setMucGiamGia(BigDecimal mucGiamGia) { this.mucGiamGia = mucGiamGia; }

    @Override
    public String toString() {
        return "LoaiVe{maLoaiVe='" + maLoaiVe + "', tenLoaiVe='" + tenLoaiVe + "', mucGiamGia=" + mucGiamGia + "}";
    }
}