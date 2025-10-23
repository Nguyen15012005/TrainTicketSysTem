/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */

package common;

public enum LoaiTau {
    TAU_NHANH("Tàu nhanh"),
    TAU_THONG_THUONG("Tàu thông thường"),
    TAU_CAO_CAP("Tàu cao cấp"),
    TAU_HANG("Tàu hàng");

    private final String displayName;

    LoaiTau(String displayName) {
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
