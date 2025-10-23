
package entity;

import java.io.Serializable;

public class ChoNgoi implements Serializable {
    private String maChoNgoi; // VARCHAR(15), PK
    private int tang; // INT, NOT NULL, CHECK (Tang IN (1,2))
    private int khoang; // INT, NOT NULL, CHECK (1 <= Khoang <= 12)
    private String toa; // VARCHAR(10), NOT NULL, FK to Toa
    private int soGhe; // INT, NOT NULL, CHECK (SoGhe > 0)
    private String loaiChoNgoi; // NVARCHAR(50), NOT NULL

    public ChoNgoi() {}

    public ChoNgoi(String maChoNgoi, int tang, int khoang, String toa, int soGhe, String loaiChoNgoi) {
        this.maChoNgoi = maChoNgoi;
        this.tang = tang;
        this.khoang = khoang;
        this.toa = toa;
        this.soGhe = soGhe;
        this.loaiChoNgoi = loaiChoNgoi;
    }

    public String getMaChoNgoi() { return maChoNgoi; }
    public void setMaChoNgoi(String maChoNgoi) { this.maChoNgoi = maChoNgoi; }
    public int getTang() { return tang; }
    public void setTang(int tang) { this.tang = tang; }
    public int getKhoang() { return khoang; }
    public void setKhoang(int khoang) { this.khoang = khoang; }
    public String getToa() { return toa; }
    public void setToa(String toa) { this.toa = toa; }
    public int getSoGhe() { return soGhe; }
    public void setSoGhe(int soGhe) { this.soGhe = soGhe; }
    public String getLoaiChoNgoi() { return loaiChoNgoi; }
    public void setLoaiChoNgoi(String loaiChoNgoi) { this.loaiChoNgoi = loaiChoNgoi; }

    @Override
    public String toString() {
        return "ChoNgoi{maChoNgoi='" + maChoNgoi + "', tang=" + tang + ", khoang=" + khoang +
                ", toa='" + toa + "', soGhe=" + soGhe + ", loaiChoNgoi='" + loaiChoNgoi + "'}";
    }
}
