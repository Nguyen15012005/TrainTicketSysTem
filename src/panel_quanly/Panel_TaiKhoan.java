package panel_quanly;

import com.toedter.calendar.JDateChooser;

import dao.Dao_NhanVien;
import dao.Dao_TaiKhoan;
import entity.NhanVien;
import entity.TaiKhoan;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Panel_TaiKhoan extends JPanel {
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboMaNhanVien;
    private JButton btnTaoTK;
    private JButton btnLamMoi;
    private JDateChooser dateChooser;
    private JCheckBox chkLocNgay;
    private JTextField txtTimKiem;
    private JButton btnTim;
    private JTable tableTaiKhoan;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JButton btnLuu;
    private JButton btnXoa;
    private Dao_TaiKhoan taiKhoanDAO;
    private Dao_NhanVien nhanVienDao;
    private JCheckBox chkTimTheoMa;
    private JToggleButton btnTogglePassword;

    public Panel_TaiKhoan() {
        taiKhoanDAO = new Dao_TaiKhoan();
        nhanVienDao = new Dao_NhanVien();
        initComponents();
        setupLayout();
        addEvents();
        loadMaNhanVien();
        loadAllTaiKhoan();
    }
    
  

    private void setupLayout() {
        this.setLayout(new BorderLayout()); 
        JPanel mainContentPanel = new JPanel(new BorderLayout(0, 10));
        mainContentPanel.setBackground(new Color(240, 240, 240));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelTaoTaiKhoan = taoPanelTaoTaiKhoan();
        JPanel panelDanhSach = taoPanelDanhSach();
        JPanel panelNutHanhDong = taoPanelNutHanhDong();
        
        // **ĐÃ XÓA** JSplitPane và panelThongKe
        // Thay vào đó, thêm panelDanhSach trực tiếp vào trung tâm

        mainContentPanel.add(panelTaoTaiKhoan, BorderLayout.NORTH);
        mainContentPanel.add(panelDanhSach, BorderLayout.CENTER); // <-- THAY ĐỔI
        mainContentPanel.add(panelNutHanhDong, BorderLayout.SOUTH);

//        add(createMasterHeaderPanel(), BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }
    
    
    private void initComponents() {
        khoiTaoComponents();
        txtTimKiem.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                if (txtTimKiem.getText().equals("Nhập mã hoặc từ khóa...")) {
                    txtTimKiem.setText("");
                    txtTimKiem.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent evt) {
                if (txtTimKiem.getText().isEmpty()) {
                    txtTimKiem.setForeground(Color.GRAY);
                    txtTimKiem.setText("Nhập mã hoặc từ khóa...");
                }
            }
        });
    }

    // **ĐÃ XÓA** phương thức taoPanelThongKe()

    private void khoiTaoComponents() {
        txtTenDangNhap = new JTextField(25);
        txtTenDangNhap.setEditable(false); 
        txtTenDangNhap.setBackground(new Color(235, 235, 235)); 
        
        txtMatKhau = new JPasswordField(18);
        btnTogglePassword = new JToggleButton("👁️");
        btnTogglePassword.setPreferredSize(new Dimension(40, 25));
        btnTogglePassword.setBorder(BorderFactory.createEmptyBorder());
        btnTogglePassword.setContentAreaFilled(false);
        btnTogglePassword.setFocusPainted(false);
        btnTogglePassword.setToolTipText("Hiện/Ẩn mật khẩu");
        
        cboMaNhanVien = new JComboBox<>();
        cboMaNhanVien.setPreferredSize(new Dimension(200, 25));
        btnTaoTK = taoNut("Tạo Tài Khoản", new Color(70, 130, 180));
        btnLamMoi = taoNut("Làm mới",new Color(70, 130, 180));
        
        dateChooser = new JDateChooser();
        dateChooser.setPreferredSize(new Dimension(200, 25));
        dateChooser.setDateFormatString("dd/MM/yyyy");
        chkLocNgay = new JCheckBox();
        
        chkTimTheoMa = new JCheckBox("Tìm theo mã");
        txtTimKiem = new JTextField(20);
        txtTimKiem.setForeground(Color.GRAY);
        txtTimKiem.setText("Nhập mã hoặc từ khóa...");
        
        btnTim = taoNut("Tìm", new Color(70, 130, 180));
        
        String[] columnNames = {"Mã Tài khoản", "Mã nhân viên", "Tên đăng nhập", "Trạng thái", "Vai trò", "Ngày tạo"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tableTaiKhoan = new JTable(tableModel);
        tableTaiKhoan.setRowHeight(25);
        tableTaiKhoan.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tableTaiKhoan.getTableHeader().setBackground(new Color(220, 220, 220));
        
        tableTaiKhoan.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        canChinhCot();
        
        scrollPane = new JScrollPane(tableTaiKhoan);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        btnLuu = taoNut("Lưu", new Color(46, 204, 113));
        btnXoa = taoNut("Xóa", new Color(231, 76, 60));
    }

    private void canChinhCot() {
        TableColumnModel columnModel = tableTaiKhoan.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(120);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(100);
        columnModel.getColumn(5).setPreferredWidth(110);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < tableTaiKhoan.getColumnCount(); i++) {
            tableTaiKhoan.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private JPanel taoPanelTaoTaiKhoan() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JPanel headerTaoTK = createHeaderPanel("Tạo tài khoản");

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Color labelColor = new Color(40, 40, 40);

        JLabel lblTenDangNhap = new JLabel("TÊN ĐĂNG NHẬP:");
        lblTenDangNhap.setFont(labelFont);
        lblTenDangNhap.setForeground(labelColor);

        JLabel lblMatKhau = new JLabel("MẬT KHẨU:");
        lblMatKhau.setFont(labelFont);
        lblMatKhau.setForeground(labelColor);
        
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.add(txtMatKhau, BorderLayout.CENTER);
        passwordPanel.add(btnTogglePassword, BorderLayout.EAST);

        JLabel lblMaNhanVien = new JLabel("MÃ NHÂN VIÊN:");
        lblMaNhanVien.setFont(labelFont);
        lblMaNhanVien.setForeground(labelColor);

        formPanel.add(lblTenDangNhap);
        formPanel.add(txtTenDangNhap);
        formPanel.add(lblMatKhau);
        formPanel.add(passwordPanel); 
        formPanel.add(lblMaNhanVien);
        formPanel.add(cboMaNhanVien);
        formPanel.add(btnTaoTK);
        formPanel.add(btnLamMoi);

        panel.add(headerTaoTK, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel taoPanelDanhSach() {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setBackground(Color.WHITE);
        JPanel headerDanhSach = createHeaderPanel("Danh sách tài khoản");
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        filterPanel.setBackground(Color.WHITE);

        JLabel lblLocNgay = new JLabel("LỌC THEO NGÀY:");
        lblLocNgay.setFont(new Font("Arial", Font.BOLD, 12));
        lblLocNgay.setForeground(new Color(40, 40, 40));
        filterPanel.add(lblLocNgay);

        filterPanel.add(dateChooser);
        filterPanel.add(chkLocNgay);
        filterPanel.add(Box.createHorizontalStrut(20));
        
        filterPanel.add(txtTimKiem);
        filterPanel.add(chkTimTheoMa);
        filterPanel.add(btnTim);

        JPanel contentWrapper = new JPanel(new BorderLayout(0, 5));
        contentWrapper.setBackground(Color.WHITE);
        contentWrapper.add(filterPanel, BorderLayout.NORTH);
        contentWrapper.add(scrollPane, BorderLayout.CENTER);
        panel.add(headerDanhSach, BorderLayout.NORTH);
        panel.add(contentWrapper, BorderLayout.CENTER);
        return panel;
    }

    private JPanel taoPanelNutHanhDong() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.setBackground(new Color(240, 240, 240));
        panel.add(btnLuu);
        panel.add(btnXoa);
        return panel;
    }
    
    
    private void addEvents() {
        btnTaoTK.addActionListener(e -> themTaiKhoan());
        btnLamMoi.addActionListener(e -> lamMoi());
        btnTim.addActionListener(e -> timKiemTaiKhoan());
        btnLuu.addActionListener(e -> capNhatTaiKhoan());
        btnXoa.addActionListener(e -> xoaTaiKhoan());
        btnTogglePassword.addActionListener(e -> {
            if (btnTogglePassword.isSelected()) {
                txtMatKhau.setEchoChar((char) 0);
                btnTogglePassword.setText("👁️\u0336"); 
            } else {
                txtMatKhau.setEchoChar('•');
                btnTogglePassword.setText("👁️");
            }
        });

        tableTaiKhoan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hienThiThongTinLenForm();
            }
        });
        
        cboMaNhanVien.addActionListener(e -> {
            String selectedMaNV = (String) cboMaNhanVien.getSelectedItem();
            if (selectedMaNV != null) {
                String email = nhanVienDao.getEmailByMaNV(selectedMaNV);

                txtTenDangNhap.setText(email);
            } else {
                txtTenDangNhap.setText(""); 
            }
        });
        tableTaiKhoan.addMouseListener(new MouseAdapter() {
        });
    }

    
    
    private JPanel createHeaderPanel(String title) {
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Path2D path = new Path2D.Double();
                path.moveTo(0, 0);
                path.lineTo(getWidth(), 0);
                path.lineTo(getWidth(), getHeight());
                path.lineTo(0, getHeight());
                path.closePath();
                g2d.setColor(new Color(164, 194, 224));
                g2d.fill(path);
                g2d.dispose();
            }
        };
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 8));
        headerPanel.setPreferredSize(new Dimension(0, 35));
        JLabel lblTitle = new JLabel(title.toUpperCase());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitle.setForeground(new Color(40, 40, 40));
        headerPanel.add(lblTitle);
        return headerPanel;
    }

    private JButton taoNut(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(140, 30));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(bgColor);
            }
        });
        return btn;
    }

    private void loadDuLieuLenBang(List<TaiKhoan> danhSach) {
        tableModel.setRowCount(0);
        for (TaiKhoan tk : danhSach) {
            // Nếu NhanVien null thì hiển thị "Chưa gán"
            String maNV = (tk.getNhanVien() != null && tk.getNhanVien().getMaNhanVien() != null) 
                            ? tk.getNhanVien().getMaNhanVien() 
                            : "Chưa gán";
            String hoTen = (tk.getNhanVien() != null && tk.getNhanVien().getHoTen() != null) 
                            ? tk.getNhanVien().getHoTen() 
                            : "Chưa gán";

            Object[] row = {
                    tk.getUserID(),
                    maNV,   // hoặc hoTen nếu bạn muốn hiển thị tên
                    tk.getUserName(),
                    tk.getEmail(),
                    tk.getRole(),
                    tk.getCreatedDate()
            };
            tableModel.addRow(row);
        }
    }


    private void loadAllTaiKhoan() {
        List<TaiKhoan> ds = taiKhoanDAO.layDanhSachTaiKhoan();
        loadDuLieuLenBang(ds);
    }

    private void loadMaNhanVien() {
        cboMaNhanVien.removeAllItems();
        List<String> danhSachMaNV = nhanVienDao.layDanhSachMaNhanVien();
        for (String maNV : danhSachMaNV) {
            cboMaNhanVien.addItem(maNV);
        }
        if (cboMaNhanVien.getItemCount() > 0) {
            cboMaNhanVien.setSelectedIndex(-1);
        }
    }

    private void lamMoi() {
        clearForm();
        loadMaNhanVien();
        loadAllTaiKhoan();
        JOptionPane.showMessageDialog(this, "Đã làm mới dữ liệu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void themTaiKhoan() {
        String tenDN = txtTenDangNhap.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        String maNV = (String) cboMaNhanVien.getSelectedItem();

        if (tenDN.isEmpty() || matKhau.isEmpty() || maNV == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (taiKhoanDAO.kiemTraTonTai(tenDN)) {
            JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String maTK = taiKhoanDAO.timMaTaiKhoanDauTienConTrong();
        TaiKhoan tkMoi = new TaiKhoan(ABORT, matKhau, maNV, maTK, null, getFocusTraversalKeysEnabled(), null, null);

        if (taiKhoanDAO.themTaiKhoan(tkMoi)) {
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thành công!");
            loadAllTaiKhoan();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm tài khoản thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void timKiemTaiKhoan() {
        String tuKhoa = txtTimKiem.getText().trim();

        List<TaiKhoan> ketQua = new ArrayList<>();

        // Nếu lọc theo ngày
        if (chkLocNgay.isSelected() && dateChooser.getDate() != null) {
            LocalDate localDate = dateChooser.getDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            ketQua = taiKhoanDAO.timTheoNgayTao(localDate); // bạn cần tạo phương thức DAO
        }
        // Nếu tìm theo mã nhân viên
        else if (chkTimTheoMa.isSelected()) {
            if (tuKhoa.isEmpty() || tuKhoa.equals("Nhập mã hoặc từ khóa...")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên cần tìm.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ketQua = taiKhoanDAO.timTheoMaNhanVien(tuKhoa); // DAO trả List<TaiKhoan>
        }
        // Tìm theo email hoặc tên đăng nhập
        else {
            if (!tuKhoa.isEmpty() && !tuKhoa.equals("Nhập mã hoặc từ khóa...")) {
                ketQua = taiKhoanDAO.timKiem(tuKhoa); // DAO trả List<TaiKhoan>, tìm theo email hoặc tên
            } else {
                ketQua = taiKhoanDAO.layDanhSachTaiKhoan();
            }
        }

        loadDuLieuLenBang(ketQua);
    }


    private void capNhatTaiKhoan() {
        int selectedRow = tableTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để cập nhật.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maTK = (Integer) tableModel.getValueAt(selectedRow, 0);
        TaiKhoan tkCanCapNhat = taiKhoanDAO.timTheoMa(maTK); // đảm bảo gọi đúng hàm DAO

        if (tkCanCapNhat != null) {
            // Cập nhật mật khẩu nếu người dùng nhập
            String matKhauMoi = new String(txtMatKhau.getPassword());
            if (!matKhauMoi.isEmpty()) {
                tkCanCapNhat.setPassword(matKhauMoi);
            }

            // Cập nhật nhân viên nếu người dùng chọn khác
            String maNV = (String) cboMaNhanVien.getSelectedItem();
            if (maNV != null && !maNV.isEmpty()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(maNV);
                tkCanCapNhat.setNhanVien(nv);
            }

            // Gọi DAO để cập nhật
            if (taiKhoanDAO.capNhatTaiKhoan(tkCanCapNhat)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadAllTaiKhoan();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void xoaTaiKhoan() {
        int selectedRow = tableTaiKhoan.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tài khoản để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa tài khoản này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            int maTK = (Integer) tableModel.getValueAt(selectedRow, 0);
            if (taiKhoanDAO.xoaTaiKhoan(maTK)) {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thành công!");
                loadAllTaiKhoan();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa tài khoản thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hienThiThongTinLenForm() {
        int selectedRow = tableTaiKhoan.getSelectedRow();
        if (selectedRow != -1) {
            String tenDN = (String) tableModel.getValueAt(selectedRow, 2);
            String maNV = (String) tableModel.getValueAt(selectedRow, 1);

            txtTenDangNhap.setText(tenDN);
            cboMaNhanVien.setSelectedItem(maNV);
            txtMatKhau.setText("");
        }
    }


    private void clearForm() {
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        cboMaNhanVien.setSelectedIndex(-1);
        txtTimKiem.setText("Nhập mã hoặc từ khóa...");
        txtTimKiem.setForeground(Color.GRAY);
        dateChooser.setDate(null);
        chkLocNgay.setSelected(false);
        chkTimTheoMa.setSelected(false);
        tableTaiKhoan.clearSelection();
    }
}