package entity;

import java.time.LocalDate;

public class TaiKhoan {

    private int userID;
    private String userName;
    private String email;
    private String password;
    private common.Role role;
    private boolean status;
    private LocalDate createdDate;

    // Liên kết 1-1 với NhanVien
    private NhanVien nhanVien;

    public TaiKhoan() {
        this.createdDate = LocalDate.now();
        this.status = true;
    }

    public TaiKhoan(int userID, String userName, String email, String password,
                    common.Role role, boolean status, LocalDate createdDate,
                    NhanVien nhanVien) {
        this.userID = userID;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdDate = createdDate;
        this.nhanVien = nhanVien;
    }

    // Getter & Setter cho NhanVien
    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public common.Role getRole() {
		return role;
	}

	public void setRole(common.Role role) {
		this.role = role;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

    // Các getter/setter khác giữ nguyên
    
}
