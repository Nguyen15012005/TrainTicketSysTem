/**
 * @author Nguyễn Nam Trung Nguyên
 * Panel Đổi Vé - Cho phép khách hàng đổi vé sang chuyến tàu/ghế khác
 */
package panel_nhanvien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import connect_database.DatabaseConnection;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Connection;
import dao.*;
import entity.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Panel_DoiVe extends javax.swing.JPanel {

    private int selectedTicketIndex = -1;
    private int selectedTrainIndex = -1;
    private int selectedCoachIndex = -1;
    private int selectedSeatIndex = -1;
    
    private Connection connection;
    private Ve_Dao veDao;
    private Dao_LichTrinh lichTrinhDao;
    private ChuyenTau_Dao chuyenTauDao;
    private Dao_Toa toaDao;
    private ChoNgoi_Dao choNgoiDao;
    private KhachHang_Dao khachHangDao;
    
    private List<Ve> danhSachVeKhachHang;
    private List<LichTrinh> danhSachLichTrinh;
    private Ve veCanDoi;

    // Logic mới: Lưu sơ đồ ghế riêng cho từng toa (key: toaIndex)
    private Map<Integer, JPanel> mapSoDoGheTheoToa = new HashMap<>();

    public Panel_DoiVe() {
        initDatabase();
        initComponents();
    }

    private void initDatabase() {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            if (connection != null) {
                veDao = new Ve_Dao(connection);
                lichTrinhDao = new Dao_LichTrinh(connection);
                chuyenTauDao = new ChuyenTau_Dao(connection);
                toaDao = new Dao_Toa(connection);
                choNgoiDao = new ChoNgoi_Dao(connection);
                khachHangDao = new KhachHang_Dao(connection);
                System.out.println("✅ Khởi tạo các DAO thành công!");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Không thể kết nối đến database!", 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi khởi tạo database: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setBackground(new Color(250, 250, 250));
        setPreferredSize(new Dimension(1200, 700));

        // ========== PANEL 1: Tìm kiếm vé ==========
        JPanel panelTimKiem = new JPanel();
        panelTimKiem.setBackground(Color.WHITE);
        panelTimKiem.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTieuDe = new JLabel("Nhập thông tin để tìm vé");
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JLabel lblMaVe = new JLabel("Mã Vé:");
        lblMaVe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JTextField txtMaVe = new JTextField();
        txtMaVe.setPreferredSize(new Dimension(200, 35));
        txtMaVe.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        JButton btnTimKiem = new JButton("Tìm Kiếm");
        btnTimKiem.setBackground(new Color(70, 130, 180));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTimKiem.setPreferredSize(new Dimension(120, 35));
        btnTimKiem.addActionListener(e -> timKiemVe(txtMaVe.getText()));

        // Bảng danh sách vé
        String[] columnNames = {"Mã Vé", "Tên Người Mua", "Ngày Mua", "Loại Vé", "Trạng Thái"};
        DefaultTableModel modelVe = new DefaultTableModel(columnNames, 0);
        JTable tblDanhSachVe = new JTable(modelVe);
        tblDanhSachVe.setRowHeight(40);
        tblDanhSachVe.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblDanhSachVe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblDanhSachVe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedTicketIndex = tblDanhSachVe.getSelectedRow();
                if (selectedTicketIndex >= 0) {
                    chonVeCanDoi();
                }
            }
        });

        JScrollPane scrollVe = new JScrollPane(tblDanhSachVe);
        scrollVe.setPreferredSize(new Dimension(700, 150));

        GroupLayout layoutTimKiem = new GroupLayout(panelTimKiem);
        panelTimKiem.setLayout(layoutTimKiem);
        layoutTimKiem.setHorizontalGroup(
            layoutTimKiem.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblTieuDe)
            .addGroup(layoutTimKiem.createSequentialGroup()
                .addComponent(lblMaVe)
                .addGap(10)
                .addComponent(txtMaVe, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addGap(15)
                .addComponent(btnTimKiem, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
            .addComponent(scrollVe, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layoutTimKiem.setVerticalGroup(
            layoutTimKiem.createSequentialGroup()
            .addComponent(lblTieuDe)
            .addGap(15)
            .addGroup(layoutTimKiem.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblMaVe)
                .addComponent(txtMaVe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnTimKiem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(15)
            .addComponent(scrollVe, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        );

        // ========== PANEL 2: Danh sách chuyến tàu ==========
        JPanel panelChuyenTau = new JPanel();
        panelChuyenTau.setBackground(Color.WHITE);
        panelChuyenTau.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblChuyenTau = new JLabel("Danh Sách Chuyến Tàu");
        lblChuyenTau.setFont(new Font("Segoe UI", Font.BOLD, 14));

        String[] columnTrain = {"Tàu", "Giờ Đi", "Giờ Đến", "Ngày Đi", "Ngày Đến"};
        DefaultTableModel modelTrain = new DefaultTableModel(columnTrain, 0);
        JTable tblChuyenTau = new JTable(modelTrain);
        tblChuyenTau.setRowHeight(40);
        tblChuyenTau.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblChuyenTau.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblChuyenTau.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedTrainIndex = tblChuyenTau.getSelectedRow();
                if (selectedTrainIndex >= 0) {
                    loadDanhSachToa();
                }
            }
        });

        JScrollPane scrollTrain = new JScrollPane(tblChuyenTau);
        scrollTrain.setPreferredSize(new Dimension(700, 200));

        GroupLayout layoutTau = new GroupLayout(panelChuyenTau);
        panelChuyenTau.setLayout(layoutTau);
        layoutTau.setHorizontalGroup(
            layoutTau.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblChuyenTau)
            .addComponent(scrollTrain, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layoutTau.setVerticalGroup(
            layoutTau.createSequentialGroup()
            .addComponent(lblChuyenTau)
            .addGap(10)
            .addComponent(scrollTrain, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        );

        // ========== PANEL 3: Chọn toa và ghế ==========
        JPanel panelChoGhe = new JPanel();
        panelChoGhe.setBackground(new Color(250, 250, 250));

        // Panel Toa Tàu (danh sách toa với scroll nếu nhiều)
        JPanel panelToa = new JPanel();
        panelToa.setBackground(new Color(250, 250, 250));
        panelToa.setLayout(new BorderLayout());

        JLabel lblToa = new JLabel("Danh Sách Toa");
        lblToa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblToa.setHorizontalAlignment(SwingConstants.CENTER);
        panelToa.add(lblToa, BorderLayout.NORTH);

        JPanel panelDanhSachToa = new JPanel();
        panelDanhSachToa.setBackground(new Color(250, 250, 250));
        panelDanhSachToa.setLayout(new GridLayout(0, 2, 10, 10));  // 0 rows: tự động wrap nếu nhiều toa
        
        // Giả sử có nhiều toa (ví dụ 20 toa để test scroll), thực tế load từ DB
        int soLuongToa = 20;  // Thay bằng dữ liệu thực từ DB
        for (int i = 1; i <= soLuongToa; i++) {
            JButton btnToa = new JButton("Toa " + i);
            btnToa.setPreferredSize(new Dimension(120, 45));
            btnToa.setMinimumSize(new Dimension(120, 45));
            btnToa.setMaximumSize(new Dimension(120, 45));
            btnToa.setBackground(new Color(70, 130, 180));
            btnToa.setForeground(Color.WHITE);
            btnToa.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnToa.setFocusPainted(false);
            final int toaIndex = i - 1;
            btnToa.addActionListener(e -> {
                selectedCoachIndex = toaIndex;
                loadDanhSachGhe(toaIndex);  // Load ghế riêng cho toa này
            });
            panelDanhSachToa.add(btnToa);
        }

        JScrollPane scrollToa = new JScrollPane(panelDanhSachToa);
        scrollToa.setPreferredSize(new Dimension(280, 280));
        scrollToa.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollToa.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollToa.setBorder(null);
        panelToa.add(scrollToa, BorderLayout.CENTER);

        // Panel Ghế Ngồi (sử dụng CardLayout để switch sơ đồ ghế theo toa)
        JPanel panelGhe = new JPanel();
        panelGhe.setBackground(Color.WHITE);
        panelGhe.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblGhe = new JLabel("Sơ Đồ Ghế Ngồi");
        lblGhe.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // CardLayout để chứa nhiều sơ đồ ghế riêng biệt
        CardLayout cardLayoutGhe = new CardLayout();
        JPanel panelCardsGhe = new JPanel(cardLayoutGhe);
        panelCardsGhe.setBackground(Color.WHITE);

        // Tạo sơ đồ ghế mẫu cho từng toa (mỗi toa có 24 ghế riêng, trạng thái riêng)
        for (int i = 0; i < soLuongToa; i++) {
            JPanel soDoGheToa = taoSoDoGheRieng(i);  // Tạo panel ghế riêng
            mapSoDoGheTheoToa.put(i, soDoGheToa);
            panelCardsGhe.add(soDoGheToa, "Toa_" + i);
        }

        // Ban đầu hiển thị trống hoặc toa đầu
        cardLayoutGhe.show(panelCardsGhe, "Toa_0");

        GroupLayout layoutGhe = new GroupLayout(panelGhe);
        panelGhe.setLayout(layoutGhe);
        layoutGhe.setHorizontalGroup(
            layoutGhe.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(lblGhe)
            .addComponent(panelCardsGhe, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        layoutGhe.setVerticalGroup(
            layoutGhe.createSequentialGroup()
            .addComponent(lblGhe)
            .addGap(10)
            .addComponent(panelCardsGhe, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        GroupLayout layoutChoGhe = new GroupLayout(panelChoGhe);
        panelChoGhe.setLayout(layoutChoGhe);
        layoutChoGhe.setHorizontalGroup(
            layoutChoGhe.createSequentialGroup()
            .addGap(10)
            .addComponent(panelToa, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)
            .addGap(10)
            .addComponent(panelGhe, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
            .addGap(10)
        );
        layoutChoGhe.setVerticalGroup(
            layoutChoGhe.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelToa, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panelGhe, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        // ========== PANEL 4: Tính tiền và thao tác ==========
        JPanel panelThanhToan = new JPanel();
        panelThanhToan.setBackground(Color.WHITE);
        panelThanhToan.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblGiaVeCu = new JLabel("Giá Vé Cũ:");
        JLabel lblGiaVeMoi = new JLabel("Giá Vé Mới:");
        JLabel lblTienBuThem = new JLabel("Tiền Bù Thêm:");
        JLabel lblTienPhiDoi = new JLabel("Tiền Phí Đổi:");
        JLabel lblTienHoanTra = new JLabel("Tiền Hoàn Trả:");

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        lblGiaVeCu.setFont(labelFont);
        lblGiaVeMoi.setFont(labelFont);
        lblTienBuThem.setFont(labelFont);
        lblTienPhiDoi.setFont(labelFont);
        lblTienHoanTra.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTienHoanTra.setForeground(new Color(0, 128, 0));

        JTextField txtGiaVeCu = new JTextField("206000");
        JTextField txtGiaVeMoi = new JTextField("100000");
        JTextField txtTienBuThem = new JTextField("0");
        JTextField txtTienPhiDoi = new JTextField("20600");
        JTextField txtTienHoanTra = new JTextField("106000");

        Font textFont = new Font("Segoe UI", Font.PLAIN, 13);
        txtGiaVeCu.setFont(textFont);
        txtGiaVeMoi.setFont(textFont);
        txtTienBuThem.setFont(textFont);
        txtTienPhiDoi.setFont(textFont);
        txtTienHoanTra.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtTienHoanTra.setForeground(new Color(0, 128, 0));

        Dimension textFieldSize = new Dimension(150, 35);
        txtGiaVeCu.setPreferredSize(textFieldSize);
        txtGiaVeMoi.setPreferredSize(textFieldSize);
        txtTienBuThem.setPreferredSize(textFieldSize);
        txtTienPhiDoi.setPreferredSize(textFieldSize);
        txtTienHoanTra.setPreferredSize(textFieldSize);

        txtGiaVeCu.setEditable(false);
        txtGiaVeMoi.setEditable(false);
        txtTienBuThem.setEditable(false);
        txtTienPhiDoi.setEditable(false);
        txtTienHoanTra.setEditable(false);

        JButton btnDoiVe = new JButton("Đổi Vé");
        btnDoiVe.setBackground(new Color(34, 139, 34));
        btnDoiVe.setForeground(Color.WHITE);
        btnDoiVe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDoiVe.setPreferredSize(new Dimension(120, 40));
        btnDoiVe.addActionListener(e -> xuLyDoiVe());

        JButton btnHuyDoi = new JButton("Hủy Đổi");
        btnHuyDoi.setBackground(new Color(220, 53, 69));
        btnHuyDoi.setForeground(Color.WHITE);
        btnHuyDoi.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnHuyDoi.setPreferredSize(new Dimension(120, 40));
        btnHuyDoi.addActionListener(e -> xuLyHuyDoi());

        GroupLayout layoutThanhToan = new GroupLayout(panelThanhToan);
        panelThanhToan.setLayout(layoutThanhToan);
        layoutThanhToan.setHorizontalGroup(
            layoutThanhToan.createSequentialGroup()
            .addGroup(layoutThanhToan.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(lblGiaVeCu)
                .addComponent(lblGiaVeMoi)
                .addComponent(lblTienBuThem)
                .addComponent(lblTienPhiDoi)
                .addComponent(lblTienHoanTra))
            .addGap(20)
            .addGroup(layoutThanhToan.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(txtGiaVeCu, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtGiaVeMoi, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtTienBuThem, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtTienPhiDoi, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                .addComponent(txtTienHoanTra, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
            .addGap(30)
            .addGroup(layoutThanhToan.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addComponent(btnDoiVe, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnHuyDoi, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layoutThanhToan.setVerticalGroup(
            layoutThanhToan.createSequentialGroup()
            .addGroup(layoutThanhToan.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblGiaVeCu)
                .addComponent(txtGiaVeCu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDoiVe, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
            .addGap(10)
            .addGroup(layoutThanhToan.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblGiaVeMoi)
                .addComponent(txtGiaVeMoi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(btnHuyDoi, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
            .addGap(10)
            .addGroup(layoutThanhToan.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblTienBuThem)
                .addComponent(txtTienBuThem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(10)
            .addGroup(layoutThanhToan.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblTienPhiDoi)
                .addComponent(txtTienPhiDoi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
            .addGap(10)
            .addGroup(layoutThanhToan.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(lblTienHoanTra)
                .addComponent(txtTienHoanTra, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        // ========== LAYOUT CHÍNH ==========
        GroupLayout mainLayout = new GroupLayout(this);
        this.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addGap(10)
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panelTimKiem, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelChuyenTau, GroupLayout.PREFERRED_SIZE, 700, GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(panelChoGhe, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelThanhToan, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10))
        );
        mainLayout.setVerticalGroup(
            mainLayout.createSequentialGroup()
            .addGap(10)
            .addGroup(mainLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(mainLayout.createSequentialGroup()
                    .addComponent(panelTimKiem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(panelChuyenTau, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGroup(mainLayout.createSequentialGroup()
                    .addComponent(panelChoGhe, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                    .addGap(10)
                    .addComponent(panelThanhToan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
            .addGap(10)
        );
    }

    // Tạo sơ đồ ghế riêng cho từng toa (24 ghế, trạng thái riêng - thực tế load từ DB)
    private JPanel taoSoDoGheRieng(int toaIndex) {
        JPanel panelSoDoGhe = new JPanel();
        panelSoDoGhe.setBackground(Color.WHITE);
        panelSoDoGhe.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        int seatNumber = 1;
        
        // Hàng 1: Ghế 1-6
        for (int col = 0; col < 6; col++) {
            gbc.gridx = col;
            gbc.gridy = 0;
            JButton seat = createSeatButton(seatNumber++, toaIndex);
            panelSoDoGhe.add(seat, gbc);
        }

        // Hàng 2: Ghế 7-12
        for (int col = 0; col < 6; col++) {
            gbc.gridx = col;
            gbc.gridy = 1;
            JButton seat = createSeatButton(seatNumber++, toaIndex);
            panelSoDoGhe.add(seat, gbc);
        }

        // Lối đi
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.ipady = 20;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel loiDi = new JLabel("← Lối đi →", SwingConstants.CENTER);
        loiDi.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        loiDi.setForeground(new Color(100, 100, 100));
        panelSoDoGhe.add(loiDi, gbc);
        
        gbc.gridwidth = 1;
        gbc.ipady = 0;
        gbc.fill = GridBagConstraints.NONE;

        // Hàng 3: Ghế 13-18
        for (int col = 0; col < 6; col++) {
            gbc.gridx = col;
            gbc.gridy = 3;
            JButton seat = createSeatButton(seatNumber++, toaIndex);
            panelSoDoGhe.add(seat, gbc);
        }

        // Hàng 4: Ghế 19-24
        for (int col = 0; col < 6; col++) {
            gbc.gridx = col;
            gbc.gridy = 4;
            JButton seat = createSeatButton(seatNumber++, toaIndex);
            panelSoDoGhe.add(seat, gbc);
        }

        return panelSoDoGhe;
    }

    // Create seat button với toaIndex để phân biệt khi chọn (logic lấy dữ liệu riêng)
    private JButton createSeatButton(int seatNumber, int toaIndex) {
        JButton btn = new JButton(String.valueOf(seatNumber));
        btn.setPreferredSize(new Dimension(50, 50));
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
        btn.setFocusPainted(false);
        
        btn.addActionListener(e -> {
            // Reset chỉ trong sơ đồ hiện tại (toa này)
            JPanel currentSoDo = mapSoDoGheTheoToa.get(toaIndex);
            for (Component comp : currentSoDo.getComponents()) {
                if (comp instanceof JButton && comp != btn) {
                    JButton otherBtn = (JButton) comp;
                    if (otherBtn.getText().matches("\\d+")) {
                        otherBtn.setBackground(Color.LIGHT_GRAY);
                        otherBtn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
                    }
                }
            }
            
            selectedSeatIndex = seatNumber;
            btn.setBackground(new Color(144, 238, 144));
            btn.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 0), 2));
            System.out.println("Đã chọn ghế " + seatNumber + " ở toa " + toaIndex);
            
            // TODO: Load dữ liệu ghế từ DB dựa trên toaIndex và seatNumber
        });
        
        return btn;
    }

    private void timKiemVe(String maVe) {
        System.out.println("Tìm kiếm vé: " + maVe);
        // TODO: Implement tìm kiếm vé
    }

    private void chonVeCanDoi() {
        System.out.println("Chọn vé cần đổi tại index: " + selectedTicketIndex);
        // TODO: Implement chọn vé cần đổi
    }

    private void loadDanhSachToa() {
        System.out.println("Load danh sách toa cho chuyến tàu index: " + selectedTrainIndex);
        // TODO: Load thực từ DB, cập nhật panelDanhSachToa động
    }

    // Load ghế: switch card và reset nếu cần
    private void loadDanhSachGhe(int toaIndex) {
        System.out.println("Load danh sách ghế cho toa index: " + toaIndex);
        Container panelCardsGhe = null;
		CardLayout cl = (CardLayout) (panelCardsGhe.getLayout());  // panelCardsGhe cần là field hoặc tìm cách access
        cl.show(panelCardsGhe, "Toa_" + toaIndex);
        
        // TODO: Load trạng thái ghế thực từ DB cho toa này (cập nhật màu sắc, disable ghế đã đặt...)
        // Ví dụ: choNgoiDao.getTrangThaiGheByToa(toaIndex)...
    }

    private void xuLyDoiVe() {
        if (selectedTicketIndex < 0 || selectedTrainIndex < 0 || selectedCoachIndex < 0 || selectedSeatIndex < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn đầy đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Xác nhận đổi vé?\nToa: " + (selectedCoachIndex + 1) + "\nGhế: " + selectedSeatIndex,
            "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            // TODO: Update DB với toa và ghế mới
            JOptionPane.showMessageDialog(this, "Đổi vé thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            xuLyHuyDoi();
        }
    }

    private void xuLyHuyDoi() {
        selectedTicketIndex = -1;
        selectedTrainIndex = -1;
        selectedCoachIndex = -1;
        selectedSeatIndex = -1;
        veCanDoi = null;
        
        // Reset tất cả sơ đồ ghế
        for (JPanel soDo : mapSoDoGheTheoToa.values()) {
            for (Component comp : soDo.getComponents()) {
                if (comp instanceof JButton && ((JButton) comp).getText().matches("\\d+")) {
                    JButton btn = (JButton) comp;
                    btn.setBackground(Color.LIGHT_GRAY);
                    btn.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1));
                }
            }
        }
        
        System.out.println("Đã hủy đổi vé và reset form");
    }
}