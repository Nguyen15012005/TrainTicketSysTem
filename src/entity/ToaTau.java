package entity;

public class ToaTau {
    private String maToa;
    private int soToa;
    private String loaiToa;
    private int soLuongGhe;
    private String chuyenTau;

    public ToaTau() {}

    public ToaTau(String maToa, int soToa, String loaiToa, int soLuongGhe, String chuyenTau) {
        this.maToa = maToa;
        this.soToa = soToa;
        this.loaiToa = loaiToa;
        this.soLuongGhe = soLuongGhe;
        this.chuyenTau = chuyenTau;
    }

	public String getMaToa() {
		return maToa;
	}

	public void setMaToa(String maToa) {
		this.maToa = maToa;
	}

	public int getSoToa() {
		return soToa;
	}

	public void setSoToa(int soToa) {
		this.soToa = soToa;
	}

	public String getLoaiToa() {
		return loaiToa;
	}

	public void setLoaiToa(String loaiToa) {
		this.loaiToa = loaiToa;
	}

	public int getSoLuongGhe() {
		return soLuongGhe;
	}

	public void setSoLuongGhe(int soLuongGhe) {
		this.soLuongGhe = soLuongGhe;
	}

	public String getChuyenTau() {
		return chuyenTau;
	}

	public void setChuyenTau(String chuyenTau) {
		this.chuyenTau = chuyenTau;
	}

    // Getters & Setters
    
}
