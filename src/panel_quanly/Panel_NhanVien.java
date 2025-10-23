package panel_quanly;

import com.toedter.calendar.JDateChooser;
import entity.NhanVien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JTextFieldDateEditor;

import dao.Dao_DangNhap;
import dao.Dao_NhanVien;

public class Panel_NhanVien extends javax.swing.JPanel {
    private JTextField txtTen, txtSoDienThoai, txtSoCCCD, txtEmail;
    private JDateChooser dateNgayVaoLam, dateNgaySinh;
    private JComboBox<String> cboGioiTinh, cboChucVu, cboTrangThai;
    private JComboBox<String> cboLocGioiTinh, cboLocChucVu, cboLocTrangThai;
    private JTable tblDanhSachNhanVien;
    private DefaultTableModel modelDanhSach;
    private JTextField txtLocMaNV;
    private JButton btnLuu, btnHuyBo, btnLoc, btnLamMoi, btnThemMoi, btnXoa;
    private JLabel lblKetQua;

    private Dao_NhanVien nhanVienDAO;
    private String maNhanVienDaChon = null;

    public Panel_NhanVien() {
    	nhanVienDAO = new Dao_NhanVien();
        initComponents();
        loadDataToTable(nhanVienDAO.getAllNhanVien());
    }

    private void initComponents() {
        setBackground(new Color(245, 245, 245));
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        JPanel infoPanel = createInfoPanel();
        JPanel middlePanel = new JPanel(new BorderLayout(10, 0));
        middlePanel.setBackground(Color.WHITE);
        middlePanel.add(createTableDSNV(), BorderLayout.CENTER);
        middlePanel.add(createFilterPanel(), BorderLayout.EAST);
        JPanel buttonPanel = createButtonPanel();

        contentPanel.add(infoPanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(middlePanel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(buttonPanel);

//        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    
    
    
    
//    private JPanel createHeaderPanel() {
//        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
//        headerPanel.setBackground(Color.WHITE);
//        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
//        
//        // Icon
//        JLabel iconLabel = new JLabel("👤");
//        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
//        
//        // Title
//        JLabel lblHeader = new JLabel("Quản Lí Nhân Viên");
//        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 30));
//        lblHeader.setForeground(new Color(60, 60, 60));
//        
//        headerPanel.add(iconLabel);
//        headerPanel.add(lblHeader);
//        
//        return headerPanel;
//    }
    
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 185));
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        headerPanel.setBackground(new Color(152, 193, 217));
        JLabel lblHeader = new JLabel("THÊM NHÂN VIÊN");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblHeader.setForeground(new Color(40, 40, 40));
        headerPanel.add(lblHeader);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Định nghĩa style chung để dễ quản lý
        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 12);
        Dimension fieldSize = new Dimension(200, 30);

        // ====== HÀNG 1 ======
        gbc.gridy = 0;

        // TÊN
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblTen = createLabel("TÊN");
        lblTen.setFont(labelFont);
        formPanel.add(lblTen, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        txtTen = createTextField("Nguyễn Văn Nam");
        txtTen.setFont(fieldFont);
        txtTen.setPreferredSize(fieldSize);
        formPanel.add(txtTen, gbc);

        // NGÀY VÀO LÀM
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblNgayVaoLam = createLabel("NGÀY VÀO LÀM");
        lblNgayVaoLam.setFont(labelFont);
        formPanel.add(lblNgayVaoLam, gbc);

        gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        dateNgayVaoLam = new JDateChooser();
        dateNgayVaoLam.setDateFormatString("dd/MM/yyyy");
        dateNgayVaoLam.setPreferredSize(fieldSize);
        JTextFieldDateEditor editorNVL = (JTextFieldDateEditor) dateNgayVaoLam.getDateEditor();
        editorNVL.setEditable(false);
        editorNVL.setFont(fieldFont);
        formPanel.add(dateNgayVaoLam, gbc);

        // GIỚI TÍNH
        gbc.gridx = 4; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblGioiTinh = createLabel("GIỚI TÍNH");
        lblGioiTinh.setFont(labelFont);
        formPanel.add(lblGioiTinh, gbc);
        
        gbc.gridx = 5; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"});
        cboGioiTinh.setFont(fieldFont);
        cboGioiTinh.setPreferredSize(fieldSize);
        formPanel.add(cboGioiTinh, gbc);

        // ====== HÀNG 2 ======
        gbc.gridy = 1;

        // SỐ ĐIỆN THOẠI
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblSdt = createLabel("SỐ ĐIỆN THOẠI");
        lblSdt.setFont(labelFont);
        formPanel.add(lblSdt, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        txtSoDienThoai = createTextField("Nhập số điện thoại");
        txtSoDienThoai.setFont(fieldFont);
        txtSoDienThoai.setPreferredSize(fieldSize);
        formPanel.add(txtSoDienThoai, gbc);

        // NGÀY SINH
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblNgaySinh = createLabel("NGÀY SINH");
        lblNgaySinh.setFont(labelFont);
        formPanel.add(lblNgaySinh, gbc);
        
        gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        dateNgaySinh = new JDateChooser();
        dateNgaySinh.setDateFormatString("dd/MM/yyyy");
        dateNgaySinh.setPreferredSize(fieldSize);
        JTextFieldDateEditor editorNS = (JTextFieldDateEditor) dateNgaySinh.getDateEditor();
        editorNS.setEditable(false);
        editorNS.setFont(fieldFont);
        formPanel.add(dateNgaySinh, gbc);
        
        // EMAIL
        gbc.gridx = 4; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblEmail = createLabel("EMAIL");
        lblEmail.setFont(labelFont);
        formPanel.add(lblEmail, gbc);
        
        gbc.gridx = 5; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        txtEmail = createTextField("example@gmail.com");
        txtEmail.setFont(fieldFont);
        txtEmail.setPreferredSize(fieldSize);
        formPanel.add(txtEmail, gbc);
        
        // ====== HÀNG 3 ======
        gbc.gridy = 2;

        // SỐ CCCD
        gbc.gridx = 0; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblCccd = createLabel("SỐ CCCD");
        lblCccd.setFont(labelFont);
        formPanel.add(lblCccd, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        txtSoCCCD = createTextField("Nhập số cccd");
        txtSoCCCD.setFont(fieldFont);
        txtSoCCCD.setPreferredSize(fieldSize);
        formPanel.add(txtSoCCCD, gbc);
        
        // CHỨC VỤ
        gbc.gridx = 2; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblChucVu = createLabel("CHỨC VỤ");
        lblChucVu.setFont(labelFont);
        formPanel.add(lblChucVu, gbc);
        
        gbc.gridx = 3; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        cboChucVu = new JComboBox<>(new String[]{"NhanVienBanVeTau", "QuanLyGa"});
        cboChucVu.setFont(fieldFont);
        cboChucVu.setPreferredSize(fieldSize);
        formPanel.add(cboChucVu, gbc);
        
        // TRẠNG THÁI
        gbc.gridx = 4; gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.0; // Sửa lỗi co cụm
        JLabel lblTrangThai = createLabel("TRẠNG THÁI");
        lblTrangThai.setFont(labelFont);
        formPanel.add(lblTrangThai, gbc);
        
        gbc.gridx = 5; gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0; // Sửa lỗi co cụm
        cboTrangThai = new JComboBox<>(new String[]{"DangLam", "NghiViec", "TamNghi"});
        cboTrangThai.setFont(fieldFont);
        cboTrangThai.setPreferredSize(fieldSize);
        formPanel.add(cboTrangThai, gbc);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTableDSNV() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        headerPanel.setBackground(new Color(152, 193, 217));
        JLabel lblHeader = new JLabel("DANH SÁCH NHÂN VIÊN");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblHeader.setForeground(new Color(40, 40, 40));
        headerPanel.add(lblHeader);
        
        // Table
        String[] columns = {"Mã NV", "Tên", "Giới Tính", "Ngày vào làm", 
                           "Ngày Sinh", "SĐT", "Email", "Số CCCD", "Chức Vụ", "Trạng Thái"};
        modelDanhSach = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        
        tblDanhSachNhanVien = new JTable(modelDanhSach);
        tblDanhSachNhanVien.setRowHeight(24);
        tblDanhSachNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblDanhSachNhanVien.setShowGrid(true);
        tblDanhSachNhanVien.setGridColor(new Color(230, 230, 230));
        tblDanhSachNhanVien.setIntercellSpacing(new Dimension(1, 1));
        
        JTableHeader header = tblDanhSachNhanVien.getTableHeader();
        header.setBackground(new Color(245, 245, 245));
        header.setFont(new Font("Segoe UI", Font.BOLD, 10));
        header.setPreferredSize(new Dimension(0, 28));
        
        tblDanhSachNhanVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiThongTinLenForm();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tblDanhSachNhanVien);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    
    
    
    
    private JPanel createFilterPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        headerPanel.setBackground(new Color(152, 193, 217));
        JLabel lblHeader = new JLabel("TÌM KIẾM");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblHeader.setForeground(new Color(40, 40, 40));
        headerPanel.add(lblHeader);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 4, 6, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;
        
        // Mã
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã nhân viên:"), gbc);
        gbc.gridy = 1;
        txtLocMaNV = new JTextField();
        txtLocMaNV.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(txtLocMaNV, gbc);

        // Giới tính
        gbc.gridy = 2;
        formPanel.add(new JLabel("Giới tính:"), gbc);
        gbc.gridy = 3;
        cboLocGioiTinh = new JComboBox<>(new String[]{"Tất cả", "Nam", "Nữ"});
        cboLocGioiTinh.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(cboLocGioiTinh, gbc);

        // Chức vụ
        gbc.gridy = 4;
        formPanel.add(new JLabel("Chức vụ:"), gbc);
        gbc.gridy = 5;
        cboLocChucVu = new JComboBox<>(new String[]{"Tất cả", "NhanVienBanVeTau", "QuanLyGa"});
        cboLocChucVu.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(cboLocChucVu, gbc);

        // Trạng thái
        gbc.gridy = 6;
        formPanel.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridy = 7;
        cboLocTrangThai = new JComboBox<>(new String[]{"Tất cả", "DangLam", "NghiViec", "TamNghi"});
        cboLocTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(cboLocTrangThai, gbc);

        // Buttons Lọc và Làm mới
        gbc.gridy = 8;
        gbc.insets = new Insets(16, 4, 6, 4);
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 0));
        btnPanel.setBackground(Color.WHITE);

        btnLoc = new JButton("Tìm");
        btnLoc.setPreferredSize(new Dimension(90, 28));
        btnLoc.setBackground(new Color(91, 155, 213));
        btnLoc.setForeground(Color.WHITE);
        btnLoc.setFont(new Font("Segoe UI", Font.BOLD, 11));
        // === SỬA Ở ĐÂY: Gọi đúng hàm locDuLieu() ===
        btnLoc.addActionListener(e -> locDuLieu());

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(90, 28));
        btnLamMoi.setBackground(new Color(169, 169, 169));
        btnLamMoi.setForeground(Color.WHITE);
        btnLamMoi.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnLamMoi.addActionListener(e -> lamMoiLoc());

        btnPanel.add(btnLoc);
        btnPanel.add(btnLamMoi);
        formPanel.add(btnPanel, gbc);

        // Panel kết quả
        JPanel resultPanel = createResultPanel();
        resultPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 6, 0));

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(resultPanel, BorderLayout.SOUTH);

        return panel;
    }
    
    

    
    private JPanel createResultPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        JLabel lblTitle = new JLabel("Kết quả:");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTitle.setForeground(new Color(60, 60, 60));
        
        lblKetQua = new JLabel("0 nhân viên");
        lblKetQua.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblKetQua.setForeground(new Color(220, 53, 69));
        
        panel.add(lblTitle);
        panel.add(lblKetQua);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));

        // Nút Thêm mới (INSERT)
        btnThemMoi = new JButton("Thêm mới");
        btnThemMoi.setPreferredSize(new Dimension(110, 32));
        btnThemMoi.setBackground(new Color(40, 167, 69)); // Xanh lá
        btnThemMoi.setForeground(Color.WHITE);
        btnThemMoi.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnThemMoi.addActionListener(e -> themNhanVien()); // Gọi hàm themNhanVien()
        panel.add(btnThemMoi);

        // Nút Lưu thay đổi (UPDATE)
        btnLuu = new JButton("Lưu thay đổi");
        btnLuu.setPreferredSize(new Dimension(120, 32));
        btnLuu.setBackground(new Color(23, 162, 184)); // Xanh lam
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLuu.addActionListener(e -> suaNhanVien()); // Gọi hàm suaNhanVien()
        panel.add(btnLuu);

        // Nút Xóa (DELETE)
        btnXoa = new JButton("Xóa");
        btnXoa.setPreferredSize(new Dimension(100, 32));
        btnXoa.setBackground(new Color(220, 53, 69)); // Đỏ
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnXoa.addActionListener(e -> xoaNhanVien()); // Gọi hàm xoaNhanVien()
        panel.add(btnXoa);
        
        // Nút Làm trống form
        btnHuyBo = new JButton("Làm trống");
        btnHuyBo.setPreferredSize(new Dimension(110, 32));
        btnHuyBo.setBackground(new Color(108, 117, 125)); // Xám
        btnHuyBo.setForeground(Color.WHITE);
        btnHuyBo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnHuyBo.addActionListener(e -> huyBo()); // Gọi hàm huyBo()
        panel.add(btnHuyBo);

        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }
    
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        textField.setForeground(Color.GRAY);
        
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().trim().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
        
        return textField;
    }
    
    // ===== XỬ LÝ DATABASE SỬ DỤNG DatabaseConnection =====
    
    private void loadDataToTable(List<NhanVien> danhSach) { 
        modelDanhSach.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (NhanVien nv : danhSach) { // Giờ có thể duyệt qua danh sách NhanVien
            modelDanhSach.addRow(new Object[]{
                nv.getMaNhanVien(), 
                nv.getHoTen(),
                nv.isGioiTinh() ? "Nam" : "Nữ",
                sdf.format(nv.getNgayVaoLam()), 
                sdf.format(nv.getNgaySinh()),
                nv.getSoDienThoai(), 
                nv.getEmail(), 
                nv.getSoCCCD(),
                nv.getChucVu(), 
                nv.getTrangThai()
            });
        }
        // Cập nhật kết quả nếu có
        if (lblKetQua != null) {
            lblKetQua.setText(danhSach.size() + " nhân viên");
        }
    }

    private void hienThiThongTinLenForm() {
        int selectedRow = tblDanhSachNhanVien.getSelectedRow();
        if (selectedRow >= 0) {
            maNhanVienDaChon = modelDanhSach.getValueAt(selectedRow, 0).toString();
            txtTen.setText(modelDanhSach.getValueAt(selectedRow, 1).toString());
            cboGioiTinh.setSelectedItem(modelDanhSach.getValueAt(selectedRow, 2).toString());
            try {
                Date ngayVaoLam = new SimpleDateFormat("dd/MM/yyyy").parse(modelDanhSach.getValueAt(selectedRow, 3).toString());
                dateNgayVaoLam.setDate(ngayVaoLam);
                Date ngaySinh = new SimpleDateFormat("dd/MM/yyyy").parse(modelDanhSach.getValueAt(selectedRow, 4).toString());
                dateNgaySinh.setDate(ngaySinh);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            txtSoDienThoai.setText(modelDanhSach.getValueAt(selectedRow, 5).toString());
            txtEmail.setText(modelDanhSach.getValueAt(selectedRow, 6).toString());
            txtSoCCCD.setText(modelDanhSach.getValueAt(selectedRow, 7).toString());
            cboChucVu.setSelectedItem(modelDanhSach.getValueAt(selectedRow, 8).toString());
            cboTrangThai.setSelectedItem(modelDanhSach.getValueAt(selectedRow, 9).toString());
        }
    }
    
    private NhanVien layDuLieuTuForm() {
        String ma = (maNhanVienDaChon != null) ? maNhanVienDaChon : nhanVienDAO.taoMaNhanVienMoi();
                return new NhanVien(
                ma,
                txtTen.getText().trim(),
                dateNgaySinh.getDate(),
                dateNgayVaoLam.getDate(),
                txtSoCCCD.getText().trim(),
                cboGioiTinh.getSelectedItem().toString().equals("Nam"),
                txtSoDienThoai.getText().trim(),
                txtEmail.getText().trim(),
                cboChucVu.getSelectedItem().toString(),
                cboTrangThai.getSelectedItem().toString()
        );
    }
    
    private void themNhanVien() {
        if (validateInput()) { // Chỉ thực hiện khi dữ liệu hợp lệ
            NhanVien nvMoi = layDuLieuTuForm();
            nvMoi.setMaNhanVien(nhanVienDAO.taoMaNhanVienMoi());
            if (nhanVienDAO.themNhanVien(nvMoi)) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                loadDataToTable(nhanVienDAO.getAllNhanVien());
                huyBo();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!");
            }
        }
    }
    
    private void suaNhanVien() {
        if (maNhanVienDaChon == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để cập nhật!");
            return;
        }
        NhanVien nvSua = layDuLieuTuForm();
        if (nhanVienDAO.suaNhanVien(nvSua)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadDataToTable(nhanVienDAO.getAllNhanVien());
            huyBo();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
        }
    }

    private void xoaNhanVien() {
        if (maNhanVienDaChon == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhân viên '" + maNhanVienDaChon + "' không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (nhanVienDAO.xoaNhanVien(maNhanVienDaChon)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadDataToTable(nhanVienDAO.getAllNhanVien());
                huyBo();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại! (Có thể do lỗi ràng buộc khóa ngoại)");
            }
        }
    }
    
    private void locDuLieu() {
        try {
            String maNV = txtLocMaNV.getText().trim(); // <--- LẤY MÃ NHÂN VIÊN
            String gioiTinh = cboLocGioiTinh.getSelectedItem().toString();
            String chucVu = cboLocChucVu.getSelectedItem().toString();
            String trangThai = cboLocTrangThai.getSelectedItem().toString();

            List<NhanVien> ketQua = nhanVienDAO.locNhanVien(maNV, gioiTinh, chucVu, trangThai);

          
            loadDataToTable(ketQua);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lọc dữ liệu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void lamMoiLoc() {
        cboLocGioiTinh.setSelectedIndex(0);
        cboLocChucVu.setSelectedIndex(0);
        cboLocTrangThai.setSelectedIndex(0);
        loadDataToTable(nhanVienDAO.getAllNhanVien());
        lblKetQua.setText("");
    }
    
    
    private boolean validateInput() {
        String ten = txtTen.getText().trim();
        if (ten.isEmpty() || ten.equals("Nguyễn Văn Nam")) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtTen.requestFocus();
            return false;
        }
        if (!ten.matches("^[\\p{L} .'-]+$")) {
            JOptionPane.showMessageDialog(this, "Tên nhân viên chỉ được chứa chữ cái và khoảng trắng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtTen.requestFocus();
            return false;
        }
        if (dateNgaySinh.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (dateNgayVaoLam.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Ngày vào làm không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (dateNgaySinh.getDate().after(dateNgayVaoLam.getDate())) {
            JOptionPane.showMessageDialog(this, "Ngày sinh phải trước ngày vào làm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sdt = txtSoDienThoai.getText().trim();
        if (sdt.isEmpty() || sdt.equals("Nhập số điện thoại")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoDienThoai.requestFocus();
            return false;
        }
        if (!sdt.matches("^0[0-9]{9}$")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ. (Phải bắt đầu bằng 0 và có 10 chữ số)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoDienThoai.requestFocus();
            return false;
        }

        String cccd = txtSoCCCD.getText().trim();
        if (cccd.isEmpty() || cccd.equals("Nhập số cccd")) {
            JOptionPane.showMessageDialog(this, "Số CCCD không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoCCCD.requestFocus();
            return false;
        }
        if (!cccd.matches("^[0-9]{12}$")) {
            JOptionPane.showMessageDialog(this, "Số CCCD không hợp lệ. (Phải có đúng 12 chữ số)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoCCCD.requestFocus();
            return false;
        }
        return true; 
    }
    
    private void huyBo() {
        maNhanVienDaChon = null;
        txtTen.setText("Nguyễn Văn Nam");
        txtTen.setForeground(Color.GRAY);
        
        txtSoDienThoai.setText("Nhập số điện thoại");
        txtSoDienThoai.setForeground(Color.GRAY);

        txtEmail.setText("example@gmail.com");
        txtEmail.setForeground(Color.GRAY);

        txtSoCCCD.setText("Nhập số cccd");
        txtSoCCCD.setForeground(Color.GRAY);
        
        dateNgayVaoLam.setDate(null);
        dateNgaySinh.setDate(null);
        cboGioiTinh.setSelectedIndex(0);
        cboChucVu.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        tblDanhSachNhanVien.clearSelection();
    }
}