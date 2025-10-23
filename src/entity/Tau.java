package entity;

public class Tau {
    private String maChuyenTau;
    private String maLoaiTau;
    private String tenChuyenTau;
    private boolean trangThai;

    public Tau() {}

    public Tau(String maChuyenTau, String maLoaiTau, String tenChuyenTau, boolean trangThai) {
        this.maChuyenTau = maChuyenTau;
        this.maLoaiTau = maLoaiTau;
        this.tenChuyenTau = tenChuyenTau;
        this.trangThai = trangThai;
    }

	public String getMaChuyenTau() {
		return maChuyenTau;
	}

	public void setMaChuyenTau(String maChuyenTau) {
		this.maChuyenTau = maChuyenTau;
	}

	public String getMaLoaiTau() {
		return maLoaiTau;
	}

	public void setMaLoaiTau(String maLoaiTau) {
		this.maLoaiTau = maLoaiTau;
	}

	public String getTenChuyenTau() {
		return tenChuyenTau;
	}

	public void setTenChuyenTau(String tenChuyenTau) {
		this.tenChuyenTau = tenChuyenTau;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

    // Getters & Setters
    
}
