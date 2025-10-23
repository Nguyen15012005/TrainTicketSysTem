package entity;


public class GaTau {
    private String maGa;
    private String tenGa;
    private boolean trangThaiGa;

    public GaTau() {}

    public GaTau(String maGa, String tenGa, boolean trangThaiGa) {
        this.maGa = maGa;
        this.tenGa = tenGa;
        this.trangThaiGa = trangThaiGa;
    }

	public String getMaGa() {
		return maGa;
	}

	public void setMaGa(String maGa) {
		this.maGa = maGa;
	}

	public String getTenGa() {
		return tenGa;
	}

	public void setTenGa(String tenGa) {
		this.tenGa = tenGa;
	}

	public boolean isTrangThaiGa() {
		return trangThaiGa;
	}

	public void setTrangThaiGa(boolean trangThaiGa) {
		this.trangThaiGa = trangThaiGa;
	}

    // Getters & Setters
    
}
