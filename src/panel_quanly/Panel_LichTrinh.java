package panel_quanly;

import dao.Dao_GaTau;
import dao.Dao_LichTrinh;
import dao.Dao_Tau;
import entity.GaTau;
import entity.LichTrinh;
import entity.Tau;
import common.LoaiTau;
import common.LoaiToa;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Panel_LichTrinh extends JPanel {

    private final Dao_LichTrinh daoLichTrinh = new Dao_LichTrinh();
    private final Dao_GaTau daoGa = new Dao_GaTau();
    private final Dao_Tau daoChuyenTau = new Dao_Tau();

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaLichTrinh;
    private JComboBox<String> cboGaDi, cboGaDen, cboMaChuyenTau;
    private JComboBox<LoaiTau> cboLoaiTau;
    private JComboBox<LoaiToa> cboLoaiToa;
    private JCheckBox chkTrangThai;

    private JSpinner spNgayKH, spNgayDen;
    private JComboBox<Integer> cboGioKH, cboPhutKH;
    private JComboBox<Integer> cboGioDen, cboPhutDen;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Panel_LichTrinh() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // --- Panel bên trái (form + bảng)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(createInputPanel(), BorderLayout.CENTER);
        leftPanel.add(createTablePanel(), BorderLayout.SOUTH);

        // --- Panel bên phải (hiển thị bản đồ)
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        JLabel mapLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    ImageIcon icon = new ImageIcon(getClass().getResource("/icon/tuyentau.png"));
                    Image img = icon.getImage();
                    // Vẽ ảnh full kích thước panel bên phải
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                } catch (Exception e) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(0, 0, getWidth(), getHeight());
                    g.setColor(Color.BLACK);
                    g.drawString("Không tìm thấy ảnh bản đồ!", getWidth() / 2 - 70, getHeight() / 2);
                }
            }
        };
        rightPanel.add(mapLabel, BorderLayout.CENTER);

        // --- Tạo SplitPane chia đôi trái/phải
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerLocation(850); // phần bên trái
        splitPane.setResizeWeight(0.6); // 60% trái, 40% phải
        splitPane.setOneTouchExpandable(true);

        add(splitPane, BorderLayout.CENTER);

        // --- Load dữ liệu
        loadGaToComboBox();
        loadChuyenTauToComboBox();
        loadTableData();
    }

    // ====== FORM NHẬP LIỆU ======
    private JPanel createInputPanel() {
        JPanel p = new JPanel(new GridLayout(6, 4, 10, 10));
        p.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        p.setBackground(Color.WHITE);

        txtMaLichTrinh = new JTextField();
        cboMaChuyenTau = new JComboBox<>();
        cboGaDi = new JComboBox<>();
        cboGaDen = new JComboBox<>();
        cboLoaiTau = new JComboBox<>(LoaiTau.values());
        cboLoaiToa = new JComboBox<>(LoaiToa.values());
        chkTrangThai = new JCheckBox("Đang hoạt động");

        // ==== Ngày khởi hành ====
        spNgayKH = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorKH = new JSpinner.DateEditor(spNgayKH, "dd/MM/yyyy");
        spNgayKH.setEditor(editorKH);

        // ==== Ngày đến ====
        spNgayDen = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorDen = new JSpinner.DateEditor(spNgayDen, "dd/MM/yyyy");
        spNgayDen.setEditor(editorDen);

        // ==== Giờ & phút khởi hành ====
        cboGioKH = new JComboBox<>();
        cboPhutKH = new JComboBox<>();
        fillTimeCombos(cboGioKH, cboPhutKH);

        // ==== Giờ & phút đến ====
        cboGioDen = new JComboBox<>();
        cboPhutDen = new JComboBox<>();
        fillTimeCombos(cboGioDen, cboPhutDen);

        // --- Dòng 1 ---
        p.add(new JLabel("Mã lịch trình:"));
        p.add(txtMaLichTrinh);
        p.add(new JLabel("Mã chuyến tàu:"));
        p.add(cboMaChuyenTau);

        // --- Dòng 2 ---
        p.add(new JLabel("Ga đi:"));
        p.add(cboGaDi);
        p.add(new JLabel("Ga đến:"));
        p.add(cboGaDen);

        // --- Dòng 3 ---
        p.add(new JLabel("Ngày khởi hành:"));
        p.add(spNgayKH);
        p.add(new JLabel("Ngày dự kiến đến:"));
        p.add(spNgayDen);

        // --- Dòng 4 ---
        p.add(new JLabel("Giờ khởi hành:"));
        p.add(createTimePanel(cboGioKH, cboPhutKH));
        p.add(new JLabel("Giờ dự kiến đến:"));
        p.add(createTimePanel(cboGioDen, cboPhutDen));

        // --- Dòng 5 ---
        p.add(new JLabel("Loại tàu:"));
        p.add(cboLoaiTau);
        p.add(new JLabel("Loại toa:"));
        p.add(cboLoaiToa);

        // --- Nút thao tác ---
        JPanel actionPanel = new JPanel();
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Cập nhật");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        btnThem.addActionListener(e -> themLichTrinh());
        btnSua.addActionListener(e -> suaLichTrinh());
        btnXoa.addActionListener(e -> xoaLichTrinh());
        btnLamMoi.addActionListener(e -> lamMoi());

        actionPanel.add(btnThem);
        actionPanel.add(btnSua);
        actionPanel.add(btnXoa);
        actionPanel.add(btnLamMoi);
        actionPanel.add(chkTrangThai);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.add(p, BorderLayout.CENTER);
        container.add(actionPanel, BorderLayout.SOUTH);

        return container;
    }

    // ====== BẢNG HIỂN THỊ ======
    private JScrollPane createTablePanel() {
        String[] columns = {
                "Mã lịch trình", "Tên Tàu", "Ga đi", "Ga đến",
                "Khởi hành", "Dự kiến đến", "Loại tàu", "Loại toa", "Trạng thái"
        };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(25);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        table.getSelectionModel().addListSelectionListener(e -> fillFormFromTable());

        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(0, 250));
        return sp;
    }

    // ====== COMBO GIỜ/PHÚT ======
    private JPanel createTimePanel(JComboBox<Integer> gio, JComboBox<Integer> phut) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        p.setBackground(Color.WHITE);
        p.add(gio);
        p.add(new JLabel(" : "));
        p.add(phut);
        return p;
    }

    private void fillTimeCombos(JComboBox<Integer> cboGio, JComboBox<Integer> cboPhut) {
        for (int h = 0; h < 24; h++) cboGio.addItem(h);
        for (int m = 0; m < 60; m += 5) cboPhut.addItem(m);
        cboGio.setSelectedItem(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        cboPhut.setSelectedItem(0);
    }

    // ====== LOAD DỮ LIỆU ======
    private void loadGaToComboBox() {
        List<GaTau> list = daoGa.getAll();
        cboGaDi.removeAllItems();
        cboGaDen.removeAllItems();
        for (GaTau ga : list) {
            if (ga.isTrangThaiGa()) {
                cboGaDi.addItem(ga.getTenGa());
                cboGaDen.addItem(ga.getTenGa());
            }
        }
    }

    private void loadChuyenTauToComboBox() {
        List<Tau> list = daoChuyenTau.getAll();
        cboMaChuyenTau.removeAllItems();
        for (Tau ct : list) {
            if (ct.isTrangThai()) {
                cboMaChuyenTau.addItem(ct.getTenChuyenTau());
            }
        }
    }

    private void loadTableData() {
        model.setRowCount(0);
        List<LichTrinh> list = daoLichTrinh.getAll();
        for (LichTrinh lt : list) {
            model.addRow(new Object[]{
                    lt.getMaLichTrinh(),
                    lt.getMaChuyenTau(),
                    lt.getGaDi(),
                    lt.getGaDen(),
                    lt.getThoiGianKhoiHanh().format(formatter),
                    lt.getThoiGianDuKienDen().format(formatter),
                    lt.getLoaiTau().getDisplayName(),
                    lt.getLoaiToa().getDisplayName(),
                    lt.isTrangThai() ? "Đang chạy" : "Ngừng"
            });
        }
    }

    // ====== CÁC HÀNH ĐỘNG ======
    private void themLichTrinh() {
        try {
            LichTrinh lt = new LichTrinh(
                    txtMaLichTrinh.getText().trim(),
                    cboMaChuyenTau.getSelectedItem().toString(),
                    cboGaDi.getSelectedItem().toString(),
                    cboGaDen.getSelectedItem().toString(),
                    getDateTime(spNgayKH, cboGioKH, cboPhutKH),
                    getDateTime(spNgayDen, cboGioDen, cboPhutDen),
                    (LoaiTau) cboLoaiTau.getSelectedItem(),
                    (LoaiToa) cboLoaiToa.getSelectedItem(),
                    chkTrangThai.isSelected()
            );
            daoLichTrinh.insert(lt);
            loadTableData();
            JOptionPane.showMessageDialog(this, "✅ Thêm lịch trình thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi thêm: " + ex.getMessage());
        }
    }

    private void suaLichTrinh() {
        try {
            LichTrinh lt = new LichTrinh(
                    txtMaLichTrinh.getText().trim(),
                    cboMaChuyenTau.getSelectedItem().toString(),
                    cboGaDi.getSelectedItem().toString(),
                    cboGaDen.getSelectedItem().toString(),
                    getDateTime(spNgayKH, cboGioKH, cboPhutKH),
                    getDateTime(spNgayDen, cboGioDen, cboPhutDen),
                    (LoaiTau) cboLoaiTau.getSelectedItem(),
                    (LoaiToa) cboLoaiToa.getSelectedItem(),
                    chkTrangThai.isSelected()
            );
            daoLichTrinh.update(lt);
            loadTableData();
            JOptionPane.showMessageDialog(this, "🔄 Cập nhật thành công!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Lỗi khi cập nhật: " + ex.getMessage());
        }
    }

    private void xoaLichTrinh() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn lịch trình cần xóa!");
            return;
        }
        String maLT = model.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa lịch trình " + maLT + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            daoLichTrinh.delete(maLT);
            loadTableData();
            JOptionPane.showMessageDialog(this, "🗑️ Xóa thành công!");
        }
    }

    private void fillFormFromTable() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        txtMaLichTrinh.setText(model.getValueAt(row, 0).toString());
        cboMaChuyenTau.setSelectedItem(model.getValueAt(row, 1).toString());
        cboGaDi.setSelectedItem(model.getValueAt(row, 2).toString());
        cboGaDen.setSelectedItem(model.getValueAt(row, 3).toString());
    }

    private void lamMoi() {
        txtMaLichTrinh.setText("");
        cboMaChuyenTau.setSelectedIndex(-1);
        cboGaDi.setSelectedIndex(-1);
        cboGaDen.setSelectedIndex(-1);
        cboLoaiTau.setSelectedIndex(-1);
        cboLoaiToa.setSelectedIndex(-1);
        chkTrangThai.setSelected(false);
        table.clearSelection();
    }

    private LocalDateTime getDateTime(JSpinner dateSpinner, JComboBox<Integer> gio, JComboBox<Integer> phut) {
        Date date = (Date) dateSpinner.getValue();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int h = (int) gio.getSelectedItem();
        int m = (int) phut.getSelectedItem();
        return LocalDateTime.of(localDate, LocalTime.of(h, m));
    }
}
