package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class KhuyenMai {

    private String maKM;
    private String moTa;
    private String loaiKM;
    private LocalDate ngayApDung;
    private LocalDate ngayKetThuc;
    private float mucKM;
    private boolean trangThai;

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ===================== CONSTRUCTORS =====================
    public KhuyenMai() {}

    public KhuyenMai(String maKM) {
        setMaKM(maKM);
    }

    public KhuyenMai(String maKM, String moTa, String loaiKM,
                          LocalDate ngayApDung, LocalDate ngayKetThuc,
                          float mucKM, boolean trangThai) {
        setMaKM(maKM);
        setMoTa(moTa);
        setLoaiKM(loaiKM);
        setNgayApDung(ngayApDung);
        setNgayKetThuc(ngayKetThuc);
        setMucKM(mucKM);
        setTrangThai(trangThai);
    }

    // ===================== GETTERS / SETTERS =====================

    public String getMaKM() { return maKM; }

    public void setMaKM(String maKM) {
        if (maKM == null || maKM.trim().isEmpty())
            throw new IllegalArgumentException("❌ Mã khuyến mãi không được để trống!");

        // Cho phép cả định dạng ngắn (KM001) và đầy đủ (KM2025101701)
        if (!maKM.matches("^KM(\\d{3,}|\\d{10,})$"))
            throw new IllegalArgumentException("❌ Mã khuyến mãi không đúng định dạng (VD: KM001 hoặc KM2025101701)!");

        this.maKM = maKM.trim();
    }

    public String getMoTa() { return moTa; }

    public void setMoTa(String moTa) {
        if (moTa == null || moTa.trim().isEmpty())
            throw new IllegalArgumentException("❌ Mô tả bắt buộc phải có!");
        this.moTa = moTa.trim();
    }

    public String getLoaiKM() { return loaiKM; }

    public void setLoaiKM(String loaiKM) {
//        if (loaiKM == null || loaiKM.trim().isEmpty()) {
//            this.loaiKM = "Khuyến mãi cố định"; // mặc định
//        } else if (!loaiKM.equals("Khuyến mãi cố định") && !loaiKM.equals("Khuyến mãi theo sự kiện")) {
//            System.err.println("⚠ Loại khuyến mãi không hợp lệ: " + loaiKM + " — gán mặc định 'Khuyến mãi cố định'");
//            this.loaiKM = "Khuyến mãi cố định";
//        } else {
//            this.loaiKM = loaiKM;
//        }
    }


    public LocalDate getNgayApDung() { return ngayApDung; }

    public void setNgayApDung(LocalDate ngayApDung) {
        if (ngayApDung == null)
            throw new IllegalArgumentException(" Ngày bắt đầu không được để trống!");
        if (this.ngayKetThuc != null && ngayApDung.isAfter(this.ngayKetThuc))
            throw new IllegalArgumentException(" Ngày bắt đầu không được sau ngày kết thúc!");
        this.ngayApDung = ngayApDung;
    }

    public LocalDate getNgayKetThuc() { return ngayKetThuc; }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        if (ngayKetThuc == null)
            throw new IllegalArgumentException("❌ Ngày kết thúc không được để trống!");
        if (this.ngayApDung != null && ngayKetThuc.isBefore(this.ngayApDung))
            throw new IllegalArgumentException("❌ Ngày kết thúc phải sau ngày bắt đầu!");
        this.ngayKetThuc = ngayKetThuc;
    }

    public float getMucKM() { return mucKM; }

    public void setMucKM(float mucKM) {
        if (mucKM < 0 || mucKM > 1)
            throw new IllegalArgumentException("❌ Mức giảm giá phải nằm trong khoảng [0, 1]!");
        this.mucKM = mucKM;
    }

    public boolean isTrangThai() { return trangThai; }

    public void setTrangThai(Boolean trangThai) {
        if (trangThai == null)
            throw new IllegalArgumentException("❌ Trạng thái không hợp lệ!");
        this.trangThai = trangThai;
    }

    // ===================== TIỆN ÍCH =====================
    public String getNgayApDungStr() {
        return ngayApDung != null ? ngayApDung.format(FMT) : "";
    }

    public String getNgayKetThucStr() {
        return ngayKetThuc != null ? ngayKetThuc.format(FMT) : "";
    }

    @Override
    public String toString() {
        return String.format("KM[%s] - %s | %s - %s | %.0f%% | %s | %s",
                maKM, moTa,
                getNgayApDungStr(), getNgayKetThucStr(),
                mucKM * 100,
                loaiKM,
                trangThai ? "Hoạt động" : "Ngừng");
    }

    // ===================== TỰ SINH MÃ =====================
    public static String taoMaTuDong() {
        String ngay = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int soNgauNhien = (int) (Math.random() * 90 + 10);
        return "KM" + ngay + soNgauNhien;
    }
}
