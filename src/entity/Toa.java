
package entity;

import java.io.Serializable;

public class Toa implements Serializable {
    private String maToa; // VARCHAR(10), PK
    private int soToa; // INT, NOT NULL, CHECK (0 <= SoToa <= 12)
    private String loaiToa; // VARCHAR(5), NOT NULL, FK to LoaiToa
    private int soLuongGhe; // INT, NOT NULL, CHECK (SoLuongGhe > 0)
    private String chuyenTau; // VARCHAR(10), NOT NULL, FK to ChuyenTau

    public Toa() {}

    public Toa(String maToa, int soToa, String loaiToa, int soLuongGhe, String chuyenTau) {
        this.maToa = maToa;
        this.soToa = soToa;
        this.loaiToa = loaiToa;
        this.soLuongGhe = soLuongGhe;
        this.chuyenTau = chuyenTau;
    }

    public String getMaToa() { return maToa; }
    public void setMaToa(String maToa) { this.maToa = maToa; }
    public int getSoToa() { return soToa; }
    public void setSoToa(int soToa) { this.soToa = soToa; }
    public String getLoaiToa() { return loaiToa; }
    public void setLoaiToa(String loaiToa) { this.loaiToa = loaiToa; }
    public int getSoLuongGhe() { return soLuongGhe; }
    public void setSoLuongGhe(int soLuongGhe) { this.soLuongGhe = soLuongGhe; }
    public String getChuyenTau() { return chuyenTau; }
    public void setChuyenTau(String chuyenTau) { this.chuyenTau = chuyenTau; }

    @Override
    public String toString() {
        return "Toa{maToa='" + maToa + "', soToa=" + soToa + ", loaiToa='" + loaiToa +
                "', soLuongGhe=" + soLuongGhe + ", chuyenTau='" + chuyenTau + "'}";
    }
}