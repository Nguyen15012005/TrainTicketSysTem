/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */

package common;

public enum Role {
	QUANLY("Quản lý"), NHANVIEN("Nhân viên");

	private final String displayName;

	Role(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Override
	public String toString() {
		return displayName;
	}
}
