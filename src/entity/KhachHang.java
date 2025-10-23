package entity;

public class KhachHang {
    private String maKH;
    private String tenKH;
    private String soCCCD;
    private String sdt;
    private String email;

    public KhachHang() {}

    public KhachHang(String maKH, String tenKH, String soCCCD, String sdt, String email) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.soCCCD = soCCCD;
        this.sdt = sdt;
        this.email = email;
    }

	public String getMaKH() {
		return maKH;
	}

	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}

	public String getTenKH() {
		return tenKH;
	}

	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}

	public String getSoCCCD() {
		return soCCCD;
	}

	public void setSoCCCD(String soCCCD) {
		this.soCCCD = soCCCD;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    // Getters & Setters
    
}
