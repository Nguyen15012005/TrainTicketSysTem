
package entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class KhoangCachGa implements Serializable {
    private String maGaDi; // VARCHAR(10), PK, FK to Ga
    private String maGaDen; // VARCHAR(10), PK, FK to Ga
    private BigDecimal khoangCach; // DECIMAL(10,2), NOT NULL

    public KhoangCachGa() {}

    public KhoangCachGa(String maGaDi, String maGaDen, BigDecimal khoangCach) {
        this.maGaDi = maGaDi;
        this.maGaDen = maGaDen;
        this.khoangCach = khoangCach;
    }

    public String getMaGaDi() { return maGaDi; }
    public void setMaGaDi(String maGaDi) { this.maGaDi = maGaDi; }
    public String getMaGaDen() { return maGaDen; }
    public void setMaGaDen(String maGaDen) { this.maGaDen = maGaDen; }
    public BigDecimal getKhoangCach() { return khoangCach; }
    public void setKhoangCach(BigDecimal khoangCach) { this.khoangCach = khoangCach; }

    @Override
    public String toString() {
        return "KhoangCachGa{maGaDi='" + maGaDi + "', maGaDen='" + maGaDen + "', khoangCach=" + khoangCach + "}";
    }
}
