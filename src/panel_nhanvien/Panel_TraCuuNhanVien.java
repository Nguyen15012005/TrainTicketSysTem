package panel_nhanvien;

import dao.*;
import entity.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Panel_TraCuuNhanVien extends JPanel {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Panel_TraCuuNhanVien() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        navPanel.setBackground(Color.WHITE);
        navPanel.setBorder(new EmptyBorder(10, 20, 0, 20));

        String[] tabNames = {"Lịch sử bán vé", "Thông tin khách hàng", "Thông tin hóa đơn"};
        JButton[] tabButtons = new JButton[tabNames.length];

        for (int i = 0; i < tabNames.length; i++) {
            tabButtons[i] = createFlatTab(tabNames[i]);
            navPanel.add(tabButtons[i]);
            if (i < tabNames.length - 1) {
                JLabel slash = new JLabel(" \\ ");
                slash.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                slash.setForeground(new Color(130, 130, 130));
                navPanel.add(slash);
            }
        }
        add(navPanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(10, 25, 25, 25));
        add(contentPanel, BorderLayout.CENTER);

        contentPanel.add(new PanelLichSuBanVeTau(), "LichSu");
        contentPanel.add(new PanelKhachHang(), "KhachHang");
        contentPanel.add(new PanelHoaDon(), "HoaDon");

        tabButtons[0].addActionListener(e -> switchTab(tabButtons, 0, "LichSu"));
        tabButtons[1].addActionListener(e -> switchTab(tabButtons, 1, "KhachHang"));
        tabButtons[2].addActionListener(e -> switchTab(tabButtons, 2, "HoaDon"));
        switchTab(tabButtons, 0, "LichSu");
    }

    private JButton createFlatTab(String text) {
        JButton btn = new JButton(text);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(new Color(90, 90, 90));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn.isEnabled()) btn.setForeground(new Color(60, 120, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn.isEnabled()) btn.setForeground(new Color(90, 90, 90));
            }
        });
        return btn;
    }

    private void switchTab(JButton[] tabs, int index, String cardName) {
        for (int i = 0; i < tabs.length; i++) {
            tabs[i].setEnabled(i != index);
            tabs[i].setForeground(i == index ? new Color(20, 90, 200) : new Color(90, 90, 90));
            tabs[i].setFont(new Font("Segoe UI", i == index ? Font.BOLD : Font.PLAIN, 13));
        }
        cardLayout.show(contentPanel, cardName);
    }

    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.setRowHeight(26);
        table.setGridColor(new Color(230, 230, 230));
        table.setSelectionBackground(new Color(215, 230, 255));
        table.getTableHeader().setBackground(new Color(245, 247, 250));
    }

    // ===== PANEL LỊCH SỬ BÁN VÉ =====
    class PanelLichSuBanVeTau extends JPanel {
        private JTable table;
        private List<VeTau> danhSachVeTau;
        private JTextField txtSearch;
        private JSpinner dateFrom, dateTo;

        public PanelLichSuBanVeTau() {
            setLayout(new BorderLayout(8, 8));
            setBackground(Color.WHITE);

            JPanel topBar = createTopBar("LỊCH SỬ BÁN VÉ", true, "Mã vé");
            txtSearch = (JTextField) topBar.getClientProperty("txtSearch");
            dateFrom = (JSpinner) topBar.getClientProperty("dateFrom");
            dateTo = (JSpinner) topBar.getClientProperty("dateTo");
            JButton btnFilter = (JButton) topBar.getClientProperty("btnFilter");
            JButton btnReset = (JButton) topBar.getClientProperty("btnReset");

            btnFilter.addActionListener(e -> filterData());
            btnReset.addActionListener(e -> loadData());

            add(topBar, BorderLayout.NORTH);

            table = new JTable(new DefaultTableModel(new Object[][]{},
                    new String[]{"Mã vé", "Tên hành khách", "Ngày sinh", "Giá vé", "Tình trạng"}));
            styleTable(table);

            // Click xem chi tiết vé (y như bản quản lý)
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row >= 0) showVeTauDetail(danhSachVeTau.get(row));
                }
            });

            add(new JScrollPane(table), BorderLayout.CENTER);
            loadData();
        }

        private void loadData() {
            danhSachVeTau = new Dao_VeTau().getAll();
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);
            for (VeTau v : danhSachVeTau)
                m.addRow(new Object[]{
                        v.getMaVeTau(), v.getTenHanhKhach(),
                        v.getNgaySinh() != null ? v.getNgaySinh().format(FMT) : "",
                        String.format("%,.0f", v.getGiaVeTau()),
                        v.getTinhTrangVeTau()
                });
        }

        private void filterData() {
            String keyword = txtSearch.getText().trim().toLowerCase();
            LocalDate from = ((Date) dateFrom.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate to = ((Date) dateTo.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<VeTau> filtered = danhSachVeTau.stream()
                    .filter(v -> v.getMaVeTau().toLowerCase().contains(keyword))
                    .filter(v -> v.getNgaySinh() != null &&
                            !v.getNgaySinh().isBefore(from) && !v.getNgaySinh().isAfter(to))
                    .collect(Collectors.toList());
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);
            for (VeTau v : filtered)
                m.addRow(new Object[]{
                        v.getMaVeTau(), v.getTenHanhKhach(),
                        v.getNgaySinh() != null ? v.getNgaySinh().format(FMT) : "",
                        String.format("%,.0f", v.getGiaVeTau()), v.getTinhTrangVeTau()
                });
        }

        private void showVeTauDetail(VeTau v) {
            JDialog d = new JDialog((Frame) null, "Chi tiết vé " + v.getMaVeTau(), true);
            d.setSize(600, 450);
            d.setLocationRelativeTo(null);
            d.setLayout(new BorderLayout(10, 10));
            d.getContentPane().setBackground(Color.WHITE);

            JLabel header = new JLabel(" Thông tin vé", SwingConstants.CENTER);
            header.setFont(new Font("Segoe UI", Font.BOLD, 18));
            header.setForeground(new Color(40, 90, 160));
            header.setBorder(new EmptyBorder(10, 0, 5, 0));

            JPanel card = new JPanel(new GridLayout(0, 2, 15, 10));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                    "Chi tiết hành khách",
                    TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 13),
                    new Color(70, 70, 70)));

            card.add(new JLabel("Mã vé: " + v.getMaVeTau()));
            card.add(new JLabel("Tên hành khách: " + v.getTenHanhKhach()));
            card.add(new JLabel("Ngày sinh: " + (v.getNgaySinh() != null ? v.getNgaySinh().format(FMT) : "")));
            card.add(new JLabel("Giá vé: " + String.format("%,.0f VND", v.getGiaVeTau())));
            card.add(new JLabel("Tình trạng: " + v.getTinhTrangVeTau()));
            card.add(new JLabel("Mã KH: " + v.getMaKH()));

            HoaDon hd = new Dao_HoaDon().findByVeTau(v.getMaVeTau());
            JPanel cardHD = new JPanel(new GridLayout(0, 2, 15, 10));
            cardHD.setBackground(Color.WHITE);
            cardHD.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                    "Thông tin hóa đơn",
                    TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 13),
                    new Color(70, 70, 70)));

            if (hd != null) {
                cardHD.add(new JLabel("Mã hóa đơn: " + hd.getMaHD()));
                cardHD.add(new JLabel("Ngày lập: " + hd.getNgayLapHoaDon().format(FMT)));
                cardHD.add(new JLabel("Tổng tiền: " + String.format("%,.0f VND", hd.getTongTien())));
            } else {
                cardHD.add(new JLabel("❌ Vé chưa có hóa đơn thanh toán."));
            }

            JButton close = new JButton("Đóng");
            close.setBackground(new Color(60, 120, 200));
            close.setForeground(Color.WHITE);
            close.setFont(new Font("Segoe UI", Font.BOLD, 13));
            close.addActionListener(e -> d.dispose());

            JPanel bottom = new JPanel();
            bottom.setBackground(Color.WHITE);
            bottom.add(close);

            JPanel center = new JPanel(new GridLayout(2, 1, 10, 10));
            center.setBackground(Color.WHITE);
            center.setBorder(new EmptyBorder(10, 20, 10, 20));
            center.add(card);
            center.add(cardHD);

            d.add(header, BorderLayout.NORTH);
            d.add(center, BorderLayout.CENTER);
            d.add(bottom, BorderLayout.SOUTH);
            d.setVisible(true);
        }
    }

    // ===== PANEL KHÁCH HÀNG =====
    class PanelKhachHang extends JPanel {
        private JTable table;
        private List<KhachHang> danhSachKH;
        private JTextField txtSearch;

        public PanelKhachHang() {
            setLayout(new BorderLayout(8, 8));
            setBackground(Color.WHITE);

            JPanel topBar = createTopBar("THÔNG TIN KHÁCH HÀNG", false, "Mã KH");
            txtSearch = (JTextField) topBar.getClientProperty("txtSearch");
            JButton btnFilter = (JButton) topBar.getClientProperty("btnFilter");
            JButton btnReset = (JButton) topBar.getClientProperty("btnReset");

            btnFilter.addActionListener(e -> filterData());
            btnReset.addActionListener(e -> loadData());

            add(topBar, BorderLayout.NORTH);

            table = new JTable(new DefaultTableModel(new Object[][]{},
                    new String[]{"Mã KH", "Tên KH", "CCCD", "SĐT", "Email"}));
            styleTable(table);

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row >= 0) showKhachHangDetail(danhSachKH.get(row));
                }
            });

            add(new JScrollPane(table), BorderLayout.CENTER);
            loadData();
        }

        private void loadData() {
            danhSachKH = new Dao_KhachHang().getAll();
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);
            for (KhachHang kh : danhSachKH)
                m.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSoCCCD(), kh.getSdt(), kh.getEmail()});
        }

        private void filterData() {
            String keyword = txtSearch.getText().trim().toLowerCase();
            List<KhachHang> filtered = danhSachKH.stream()
                    .filter(k -> k.getMaKH().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);
            for (KhachHang kh : filtered)
                m.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSoCCCD(), kh.getSdt(), kh.getEmail()});
        }

        private void showKhachHangDetail(KhachHang kh) {
            JDialog d = new JDialog((Frame) null, "Chi tiết khách hàng " + kh.getTenKH(), true);
            d.setSize(800, 550);
            d.setLocationRelativeTo(null);
            d.setLayout(new BorderLayout(10, 10));
            d.getContentPane().setBackground(Color.WHITE);

            JLabel header = new JLabel(" Thông tin khách hàng", SwingConstants.CENTER);
            header.setFont(new Font("Segoe UI", Font.BOLD, 18));
            header.setForeground(new Color(30, 100, 160));
            header.setBorder(new EmptyBorder(10, 0, 5, 0));

            JPanel card = new JPanel(new GridLayout(0, 2, 15, 10));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                    "Thông tin cá nhân",
                    TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 13),
                    new Color(70, 70, 70)));

            card.add(new JLabel("Mã KH: " + kh.getMaKH()));
            card.add(new JLabel("Tên KH: " + kh.getTenKH()));
            card.add(new JLabel("CCCD: " + kh.getSoCCCD()));
            card.add(new JLabel("SĐT: " + kh.getSdt()));
            card.add(new JLabel("Email: " + kh.getEmail()));

            List<VeTau> VeTauList = new Dao_VeTau().getByMaKH(kh.getMaKH());
            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Mã vé", "Ngày sinh", "Giá vé", "Tình trạng"}, 0);
            for (VeTau v : VeTauList)
                model.addRow(new Object[]{
                        v.getMaVeTau(),
                        v.getNgaySinh() != null ? v.getNgaySinh().format(FMT) : "",
                        String.format("%,.0f", v.getGiaVeTau()),
                        v.getTinhTrangVeTau()
                });
            JTable tbl = new JTable(model);
            tbl.setRowHeight(25);
            JScrollPane scroll = new JScrollPane(tbl);
            scroll.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                    "Danh sách vé đã mua (" + VeTauList.size() + ")",
                    TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 13),
                    new Color(70, 70, 70)));

            JButton close = new JButton("Đóng");
            close.setBackground(new Color(60, 120, 200));
            close.setForeground(Color.WHITE);
            close.setFont(new Font("Segoe UI", Font.BOLD, 13));
            close.addActionListener(e -> d.dispose());
            JPanel bottom = new JPanel();
            bottom.setBackground(Color.WHITE);
            bottom.add(close);

            JPanel center = new JPanel(new BorderLayout(0, 10));
            center.setBackground(Color.WHITE);
            center.setBorder(new EmptyBorder(10, 20, 10, 20));
            center.add(card, BorderLayout.NORTH);
            center.add(scroll, BorderLayout.CENTER);

            d.add(header, BorderLayout.NORTH);
            d.add(center, BorderLayout.CENTER);
            d.add(bottom, BorderLayout.SOUTH);
            d.setVisible(true);
        }
    }

    // ===== PANEL HÓA ĐƠN =====
    class PanelHoaDon extends JPanel {
        private JTable table;
        private List<HoaDon> danhSachHD;
        private JTextField txtSearch;
        private JSpinner dateFrom, dateTo;

        public PanelHoaDon() {
            setLayout(new BorderLayout(8, 8));
            setBackground(Color.WHITE);

            JPanel topBar = createTopBar("THÔNG TIN HÓA ĐƠN", true, "Mã HĐ");
            txtSearch = (JTextField) topBar.getClientProperty("txtSearch");
            dateFrom = (JSpinner) topBar.getClientProperty("dateFrom");
            dateTo = (JSpinner) topBar.getClientProperty("dateTo");
            JButton btnFilter = (JButton) topBar.getClientProperty("btnFilter");
            JButton btnReset = (JButton) topBar.getClientProperty("btnReset");

            btnFilter.addActionListener(e -> filterData());
            btnReset.addActionListener(e -> loadData());

            add(topBar, BorderLayout.NORTH);

            table = new JTable(new DefaultTableModel(new Object[][]{},
                    new String[]{"Mã HĐ", "Mã KH", "Mã NV", "Ngày lập", "Tổng tiền"}));
            styleTable(table);

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.getSelectedRow();
                    if (row >= 0) showHoaDonDetail(danhSachHD.get(row));
                }
            });

            add(new JScrollPane(table), BorderLayout.CENTER);
            loadData();
        }

        private void loadData() {
            danhSachHD = new Dao_HoaDon().getAll();
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);
            for (HoaDon hd : danhSachHD)
                m.addRow(new Object[]{
                        hd.getMaHD(), hd.getMaKH(), hd.getMaNV(),
                        hd.getNgayLapHoaDon().format(FMT), String.format("%,.0f", hd.getTongTien())
                });
        }

        private void filterData() {
            String keyword = txtSearch.getText().trim().toLowerCase();
            LocalDate from = ((Date) dateFrom.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate to = ((Date) dateTo.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<HoaDon> filtered = danhSachHD.stream()
                    .filter(h -> h.getMaHD().toLowerCase().contains(keyword))
                    .filter(h -> !h.getNgayLapHoaDon().isBefore(from) && !h.getNgayLapHoaDon().isAfter(to))
                    .collect(Collectors.toList());
            DefaultTableModel m = (DefaultTableModel) table.getModel();
            m.setRowCount(0);
            for (HoaDon hd : filtered)
                m.addRow(new Object[]{
                        hd.getMaHD(), hd.getMaKH(), hd.getMaNV(),
                        hd.getNgayLapHoaDon().format(FMT), String.format("%,.0f", hd.getTongTien())
                });
        }

        private void showHoaDonDetail(HoaDon hd) {
            JDialog d = new JDialog((Frame) null, "Chi tiết hóa đơn " + hd.getMaHD(), true);
            d.setSize(800, 550);
            d.setLocationRelativeTo(null);
            d.setLayout(new BorderLayout(10, 10));
            d.getContentPane().setBackground(Color.WHITE);

            JLabel header = new JLabel(" Thông tin hóa đơn", SwingConstants.CENTER);
            header.setFont(new Font("Segoe UI", Font.BOLD, 18));
            header.setForeground(new Color(20, 90, 160));
            header.setBorder(new EmptyBorder(10, 0, 5, 0));

            JPanel card = new JPanel(new GridLayout(0, 2, 15, 10));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                    "Chi tiết hóa đơn",
                    TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 13),
                    new Color(70, 70, 70)));

            card.add(new JLabel("Mã hóa đơn: " + hd.getMaHD()));
            card.add(new JLabel("Mã KH: " + hd.getMaKH()));
            card.add(new JLabel("Mã NV: " + hd.getMaNV()));
            card.add(new JLabel("Ngày lập: " + hd.getNgayLapHoaDon().format(FMT)));
            card.add(new JLabel("Tổng tiền: " + String.format("%,.0f VND", hd.getTongTien())));

            List<VeTau> VeTauList = new Dao_VeTau().getByHoaDon(hd.getMaHD());
            DefaultTableModel model = new DefaultTableModel(
                    new String[]{"Mã vé", "Hành khách", "Giá vé", "Tình trạng"}, 0);
            for (VeTau v : VeTauList)
                model.addRow(new Object[]{
                        v.getMaVeTau(), v.getTenHanhKhach(),
                        String.format("%,.0f", v.getGiaVeTau()), v.getTinhTrangVeTau()
                });
            JTable tbl = new JTable(model);
            tbl.setRowHeight(25);
            JScrollPane scroll = new JScrollPane(tbl);
            scroll.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true),
                    "Danh sách vé trong hóa đơn (" + VeTauList.size() + ")",
                    TitledBorder.LEFT, TitledBorder.TOP,
                    new Font("Segoe UI", Font.BOLD, 13),
                    new Color(70, 70, 70)));

            JButton close = new JButton("Đóng");
            close.setBackground(new Color(60, 120, 200));
            close.setForeground(Color.WHITE);
            close.setFont(new Font("Segoe UI", Font.BOLD, 13));
            close.addActionListener(e -> d.dispose());

            JPanel bottom = new JPanel();
            bottom.setBackground(Color.WHITE);
            bottom.add(close);

            JPanel center = new JPanel(new BorderLayout(0, 10));
            center.setBackground(Color.WHITE);
            center.setBorder(new EmptyBorder(10, 20, 10, 20));
            center.add(card, BorderLayout.NORTH);
            center.add(scroll, BorderLayout.CENTER);

            d.add(header, BorderLayout.NORTH);
            d.add(center, BorderLayout.CENTER);
            d.add(bottom, BorderLayout.SOUTH);
            d.setVisible(true);
        }
    }

    // ===== PANEL LỌC CHUNG =====
    private JPanel createTopBar(String title, boolean coNgay, String placeholder) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(Color.WHITE);

        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        top.add(lbl, BorderLayout.WEST);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        filterPanel.setBackground(Color.WHITE);

        JTextField txtSearch = new JTextField(10);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setBorder(BorderFactory.createTitledBorder(placeholder));

        JSpinner dateFrom = null, dateTo = null;
        if (coNgay) {
            dateFrom = new JSpinner(new SpinnerDateModel());
            dateFrom.setEditor(new JSpinner.DateEditor(dateFrom, "dd/MM/yyyy"));
            dateFrom.setBorder(BorderFactory.createTitledBorder("Từ ngày"));
            dateFrom.setValue(new Date(System.currentTimeMillis() - 7 * 86400000L));

            dateTo = new JSpinner(new SpinnerDateModel());
            dateTo.setEditor(new JSpinner.DateEditor(dateTo, "dd/MM/yyyy"));
            dateTo.setBorder(BorderFactory.createTitledBorder("Đến ngày"));
            dateTo.setValue(new Date());

            filterPanel.add(dateFrom);
            filterPanel.add(dateTo);
        }

        JButton btnFilter = new JButton("Lọc");
        JButton btnReset = new JButton("Làm mới");
        for (JButton b : new JButton[]{btnFilter, btnReset}) {
            b.setFocusPainted(false);
            b.setFont(new Font("Segoe UI", Font.BOLD, 13));
            b.setForeground(Color.WHITE);
            b.setBackground(new Color(60, 120, 200));
            b.setBorder(new EmptyBorder(5, 12, 5, 12));
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            filterPanel.add(b);
        }

        filterPanel.add(txtSearch);
        top.add(filterPanel, BorderLayout.EAST);

        top.putClientProperty("txtSearch", txtSearch);
        top.putClientProperty("dateFrom", dateFrom);
        top.putClientProperty("dateTo", dateTo);
        top.putClientProperty("btnFilter", btnFilter);
        top.putClientProperty("btnReset", btnReset);

        return top;
    }

}
