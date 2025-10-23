
package entity;

import java.io.Serializable;
import java.time.LocalTime;

public class ChiTietLichTrinh implements Serializable {
    private String maChiTietLichTrinh; // VARCHAR(20), PK
    private String maLichTrinh; // VARCHAR(15), NOT NULL, FK to LichTrinh
    private String maGa; // VARCHAR(10), NOT NULL, FK to Ga
    private int thuTuGa; // INT, NOT NULL, CHECK (thuTuGa >= 1)
    private LocalTime gioDi; // TIME, NULL
    private LocalTime gioDen; // TIME, NULL

    public ChiTietLichTrinh() {}

    public ChiTietLichTrinh(String maChiTietLichTrinh, String maLichTrinh, String maGa, int thuTuGa,
                            LocalTime gioDi, LocalTime gioDen) {
        this.maChiTietLichTrinh = maChiTietLichTrinh;
        this.maLichTrinh = maLichTrinh;
        this.maGa = maGa;
        this.thuTuGa = thuTuGa;
        this.gioDi = gioDi;
        this.gioDen = gioDen;
    }

    public String getMaChiTietLichTrinh() { return maChiTietLichTrinh; }
    public void setMaChiTietLichTrinh(String maChiTietLichTrinh) { this.maChiTietLichTrinh = maChiTietLichTrinh; }
    public String getMaLichTrinh() { return maLichTrinh; }
    public void setMaLichTrinh(String maLichTrinh) { this.maLichTrinh = maLichTrinh; }
    public String getMaGa() { return maGa; }
    public void setMaGa(String maGa) { this.maGa = maGa; }
    public int getThuTuGa() { return thuTuGa; }
    public void setThuTuGa(int thuTuGa) { this.thuTuGa = thuTuGa; }
    public LocalTime getGioDi() { return gioDi; }
    public void setGioDi(LocalTime gioDi) { this.gioDi = gioDi; }
    public LocalTime getGioDen() { return gioDen; }
    public void setGioDen(LocalTime gioDen) { this.gioDen = gioDen; }

    @Override
    public String toString() {
        return "ChiTietLichTrinh{maChiTietLichTrinh='" + maChiTietLichTrinh + "', maLichTrinh='" + maLichTrinh +
                "', maGa='" + maGa + "', thuTuGa=" + thuTuGa + ", gioDi=" + gioDi + ", gioDen=" + gioDen + "}";
    }
}
