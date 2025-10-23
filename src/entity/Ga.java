
package entity;

import java.io.Serializable;

public class Ga implements Serializable {
    private String maGa; // VARCHAR(10), PK
    private String tenGa; // NVARCHAR(50), NOT NULL
    private boolean trangThaiGa; // BIT, NOT NULL, DEFAULT 1

    public Ga() {}

    public Ga(String maGa, String tenGa, boolean trangThaiGa) {
        this.maGa = maGa;
        this.tenGa = tenGa;
        this.trangThaiGa = trangThaiGa;
    }

    public String getMaGa() { return maGa; }
    public void setMaGa(String maGa) { this.maGa = maGa; }
    public String getTenGa() { return tenGa; }
    public void setTenGa(String tenGa) { this.tenGa = tenGa; }
    public boolean isTrangThaiGa() { return trangThaiGa; }
    public void setTrangThaiGa(boolean trangThaiGa) { this.trangThaiGa = trangThaiGa; }

    @Override
    public String toString() {
        return "Ga{maGa='" + maGa + "', tenGa='" + tenGa + "', trangThaiGa=" + trangThaiGa + "}";
    }
}
