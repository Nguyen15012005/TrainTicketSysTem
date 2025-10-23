
package entity;

import java.io.Serializable;

public class LoaiToa implements Serializable {
    private String maLoaiToa; // VARCHAR(5), PK
    private String tenLoaiToa; // NVARCHAR(50), NOT NULL

    public LoaiToa() {}

    public LoaiToa(String maLoaiToa, String tenLoaiToa) {
        this.maLoaiToa = maLoaiToa;
        this.tenLoaiToa = tenLoaiToa;
    }

    public String getMaLoaiToa() { return maLoaiToa; }
    public void setMaLoaiToa(String maLoaiToa) { this.maLoaiToa = maLoaiToa; }
    public String getTenLoaiToa() { return tenLoaiToa; }
    public void setTenLoaiToa(String tenLoaiToa) { this.tenLoaiToa = tenLoaiToa; }

    @Override
    public String toString() {
        return "LoaiToa{maLoaiToa='" + maLoaiToa + "', tenLoaiToa='" + tenLoaiToa + "'}";
    }
}
