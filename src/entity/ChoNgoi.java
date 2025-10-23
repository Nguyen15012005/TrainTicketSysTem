package entity;

public class ChoNgoi {
    private String maChoNgoi;
    private int tang;
    private int khoang;
    private String toa;
    private int soGhe;
    private String loaiChoNgoi;

    public ChoNgoi() {}

    public ChoNgoi(String maChoNgoi, int tang, int khoang, String toa, int soGhe, String loaiChoNgoi) {
        this.maChoNgoi = maChoNgoi;
        this.tang = tang;
        this.khoang = khoang;
        this.toa = toa;
        this.soGhe = soGhe;
        this.loaiChoNgoi = loaiChoNgoi;
    }

	public String getMaChoNgoi() {
		return maChoNgoi;
	}

	public void setMaChoNgoi(String maChoNgoi) {
		this.maChoNgoi = maChoNgoi;
	}

	public int getTang() {
		return tang;
	}

	public void setTang(int tang) {
		this.tang = tang;
	}

	public int getKhoang() {
		return khoang;
	}

	public void setKhoang(int khoang) {
		this.khoang = khoang;
	}

	public String getToa() {
		return toa;
	}

	public void setToa(String toa) {
		this.toa = toa;
	}

	public int getSoGhe() {
		return soGhe;
	}

	public void setSoGhe(int soGhe) {
		this.soGhe = soGhe;
	}

	public String getLoaiChoNgoi() {
		return loaiChoNgoi;
	}

	public void setLoaiChoNgoi(String loaiChoNgoi) {
		this.loaiChoNgoi = loaiChoNgoi;
	}

    
}
