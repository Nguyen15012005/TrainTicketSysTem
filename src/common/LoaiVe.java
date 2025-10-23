package common;

import java.math.BigDecimal;

public class LoaiVe {
    private String maLoaiVeTau;
    private String tenLoaiVeTau;
    private BigDecimal mucGiamGia;

    public LoaiVe() {}

    public LoaiVe(String maLoaiVeTau, String tenLoaiVeTau, BigDecimal mucGiamGia) {
        this.maLoaiVeTau = maLoaiVeTau;
        this.tenLoaiVeTau = tenLoaiVeTau;
        this.mucGiamGia = mucGiamGia;
    }

	public String getMaLoaiVeTau() {
		return maLoaiVeTau;
	}

	public void setMaLoaiVeTau(String maLoaiVeTau) {
		this.maLoaiVeTau = maLoaiVeTau;
	}

	public String getTenLoaiVeTau() {
		return tenLoaiVeTau;
	}

	public void setTenLoaiVeTau(String tenLoaiVeTau) {
		this.tenLoaiVeTau = tenLoaiVeTau;
	}

	public BigDecimal getMucGiamGia() {
		return mucGiamGia;
	}

	public void setMucGiamGia(BigDecimal mucGiamGia) {
		this.mucGiamGia = mucGiamGia;
	}

    // Getters & Setters
    
}

