package common;

public enum LoaiToa {
    GC("Ghế cứng"),
    GM("Ghế mềm điều hòa"),
    GN4("Giường nằm khoang 4"),
    GN6("Giường nằm khoang 6"),
    TV("Toa VIP");

    private final String displayName;

    LoaiToa(String displayName) {
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
