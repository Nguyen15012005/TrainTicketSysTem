package panel_nhanvien;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author Nguyễn Nam Trung Nguyên
 */
public class ThanhToan extends javax.swing.JPanel {
    
    // Components
    private JTable ticketInfoTable;
    private DefaultTableModel tableModel;
    
    // Thông tin hành khách
    private JTextField txtCCCD;
    private JTextField txtHoTen;
    private JTextField txtEmail;
    private JButton btnThemHanhKhach;
    
    // Thông tin hóa đơn
    private JTextField txtMaHoaDon;
    private JTextField txtNgayTao;
    private JTextField txtTienGiam;
    private JTextField txtKhachPhaiTra;
    private JTextField txtTienKhachDua;
    private JButton btnNhanDuTien;
    private JButton btnThanhToan;
    
    // Các nút điều khiển
    private JButton btnHuy;
    private JButton btnXoa;
    private JButton btnXoaTatCa;
    private JButton btnTroLai;
    
    private NumberFormat currencyFormat;
    
    public ThanhToan() {
        currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        initComponents();
        loadSampleData();
    }
    
    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // VÙNG 1: Bảng thông tin vé (phía trên) - chiếm 60% chiều cao
        JPanel panelTable = createTablePanel();
        
        // VÙNG 2 + 3: Panel dưới chứa thông tin và nút điều khiển - chiếm 40% chiều cao
        JPanel panelCenter = new JPanel(new BorderLayout(10, 0));
        panelCenter.setBackground(Color.WHITE);
        
        // Thông tin hành khách & hóa đơn
        JPanel panelThongTin = createInfoPanel();
        
        // Cụm nút điều khiển (bên phải)
        JPanel panelNut = createButtonPanel();
        
        panelCenter.add(panelThongTin, BorderLayout.CENTER);
        panelCenter.add(panelNut, BorderLayout.EAST);
        
        // Thêm vào layout chính
        add(panelTable, BorderLayout.CENTER);
        add(panelCenter, BorderLayout.SOUTH);
    }
    
    /**
     * Tạo panel bảng thông tin vé với custom renderer và editor
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Tạo bảng với 7 cột
        String[] columns = {
            "Hành Trình", 
            "Tên KH", 
            "Thông tin KH", 
            "Khuyến mãi", 
            "Thông tin ghế", 
            "Thuế", 
            "Thành tiền"
        };
        
        tableModel = new DefaultTableModel(columns, 3) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép edit cột "Tên KH" (1), "Thông tin KH" (2), "Khuyến mãi" (3)
                return column == 1 || column == 2 || column == 3;
            }
        };
        
        ticketInfoTable = new JTable(tableModel);
        ticketInfoTable.setRowHeight(55);
        ticketInfoTable.setFont(new Font("Arial", Font.PLAIN, 13));
        ticketInfoTable.setGridColor(new Color(200, 200, 200));
        ticketInfoTable.setSelectionBackground(new Color(173, 216, 230));
        ticketInfoTable.setSelectionForeground(Color.BLACK);
        
        // Định dạng header
        JTableHeader header = ticketInfoTable.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));
        
        // Set độ rộng cột
        TableColumnModel columnModel = ticketInfoTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(200); // Hành trình
        columnModel.getColumn(1).setPreferredWidth(140); // Tên KH
        columnModel.getColumn(2).setPreferredWidth(180); // Thông tin KH
        columnModel.getColumn(3).setPreferredWidth(150); // Khuyến mãi
        columnModel.getColumn(4).setPreferredWidth(180); // Thông tin ghế
        columnModel.getColumn(5).setPreferredWidth(100); // Thuế
        columnModel.getColumn(6).setPreferredWidth(130); // Thành tiền
        
        // Custom renderer cho cột "Thông tin KH" (cột 2)
        columnModel.getColumn(2).setCellRenderer(new ComboBoxCellRenderer());
        columnModel.getColumn(2).setCellEditor(new ComboBoxCellEditor());
        
        // Custom renderer cho cột "Khuyến mãi" (cột 3)
        columnModel.getColumn(3).setCellRenderer(new ComboBoxCellRenderer());
        columnModel.getColumn(3).setCellEditor(new KhuyenMaiCellEditor());
        
        // Custom renderer cho cột "Thành tiền" (cột 6) - căn phải, in đậm
        columnModel.getColumn(6).setCellRenderer(new CurrencyCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(ticketInfoTable);
        scrollPane.setPreferredSize(new Dimension(0, 400));
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Load dữ liệu mẫu vào bảng
     */
    private void loadSampleData() {
        // Dòng 1
        tableModel.setValueAt("Sài Gòn - Hà Nội - Tau001", 0, 0);
        tableModel.setValueAt("Nguyễn Văn A", 0, 1);
        tableModel.setValueAt("Người lớn", 0, 2);
        tableModel.setValueAt("Không", 0, 3);
        tableModel.setValueAt("Ghế 12 - Toa 2\n200.000đ", 0, 4);
        tableModel.setValueAt("20.000", 0, 5);
        tableModel.setValueAt("220.000", 0, 6);
        
        // Dòng 2
        tableModel.setValueAt("Sài Gòn - Hà Nội - Tau001", 1, 0);
        tableModel.setValueAt("Trần Thị B", 1, 1);
        tableModel.setValueAt("HSSV", 1, 2);
        tableModel.setValueAt("Giảm 10%", 1, 3);
        tableModel.setValueAt("Ghế 13 - Toa 2\n200.000đ", 1, 4);
        tableModel.setValueAt("20.000", 1, 5);
        tableModel.setValueAt("198.000", 1, 6);
        
        // Dòng 3
        tableModel.setValueAt("Sài Gòn - Hà Nội - Tau001", 2, 0);
        tableModel.setValueAt("Lê Văn C", 2, 1);
        tableModel.setValueAt("Trẻ em 6-14 tuổi", 2, 2);
        tableModel.setValueAt("Giảm 25%", 2, 3);
        tableModel.setValueAt("Ghế 14 - Toa 2\n200.000đ", 2, 4);
        tableModel.setValueAt("15.000", 2, 5);
        tableModel.setValueAt("165.000", 2, 6);
        
        tinhTongTien();
    }
    
    /**
     * Custom renderer cho ComboBox trong cell - hiển thị dấu dropdown
     */
    private class ComboBoxCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            
            JPanel panel = new JPanel(new BorderLayout());
            JLabel label = new JLabel(value != null ? value.toString() : "");
            label.setFont(new Font("Arial", Font.PLAIN, 12));
            label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
            
            JLabel arrow = new JLabel("▼");
            arrow.setFont(new Font("Arial", Font.PLAIN, 10));
            arrow.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));
            
            panel.add(label, BorderLayout.CENTER);
            panel.add(arrow, BorderLayout.EAST);
            
            if (isSelected) {
                panel.setBackground(new Color(173, 216, 230));
                label.setBackground(new Color(173, 216, 230));
            } else {
                panel.setBackground(new Color(245, 245, 245));
                label.setBackground(new Color(245, 245, 245));
            }
            
            panel.setOpaque(true);
            label.setOpaque(true);
            
            return panel;
        }
    }
    
    /**
     * Custom editor cho cột "Thông tin KH"
     */
    private class ComboBoxCellEditor extends DefaultCellEditor {
        private JComboBox<String> comboBox;
        
        public ComboBoxCellEditor() {
            super(new JComboBox<>());
            comboBox = (JComboBox<String>) getComponent();
            comboBox.addItem("Người lớn");
            comboBox.addItem("Trẻ em dưới 6 tuổi");
            comboBox.addItem("Trẻ em 6-14 tuổi");
            comboBox.addItem("HSSV");
            comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
            
            // Thêm listener để tự động tính lại tiền khi thay đổi loại khách hàng
            comboBox.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> tinhTongTien());
            });
        }
    }
    
    /**
     * Custom editor cho cột "Khuyến mãi"
     */
    private class KhuyenMaiCellEditor extends DefaultCellEditor {
        private JComboBox<String> comboBox;
        
        public KhuyenMaiCellEditor() {
            super(new JComboBox<>());
            comboBox = (JComboBox<String>) getComponent();
            comboBox.addItem("Không");
            comboBox.addItem("Giảm 10%");
            comboBox.addItem("Giảm 15%");
            comboBox.addItem("Giảm 25%");
            comboBox.addItem("Giảm 50.000đ");
            comboBox.addItem("Giảm 100.000đ");
            comboBox.setFont(new Font("Arial", Font.PLAIN, 12));
            
            // Thêm listener để tự động tính lại tiền khi thay đổi khuyến mãi
            comboBox.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> tinhTongTien());
            });
        }
    }
    
    /**
     * Custom renderer cho cột "Thành tiền" - hiển thị dạng tiền tệ, căn phải, in đậm
     */
    private class CurrencyCellRenderer extends DefaultTableCellRenderer {
        public CurrencyCellRenderer() {
            setHorizontalAlignment(JLabel.RIGHT);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            setFont(new Font("Arial", Font.BOLD, 13));
            setForeground(new Color(220, 20, 60)); // Màu đỏ cho số tiền
            setBackground(isSelected ? new Color(173, 216, 230) : Color.WHITE);
            
            // Format số tiền
            if (value != null) {
                try {
                    String text = value.toString().replaceAll("[^0-9]", "");
                    if (!text.isEmpty()) {
                        long amount = Long.parseLong(text);
                        setText(currencyFormat.format(amount) + "đ");
                    }
                } catch (NumberFormatException e) {
                    setText(value.toString());
                }
            }
            
            return c;
        }
    }
    
    /**
     * Tính tổng tiền tất cả vé
     */
    private void tinhTongTien() {
        long tongTien = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Object value = tableModel.getValueAt(i, 6); // Cột "Thành tiền"
            if (value != null) {
                try {
                    String text = value.toString().replaceAll("[^0-9]", "");
                    if (!text.isEmpty()) {
                        tongTien += Long.parseLong(text);
                    }
                } catch (NumberFormatException e) {
                    // Bỏ qua
                }
            }
        }
        
        if (txtKhachPhaiTra != null) {
            txtKhachPhaiTra.setText(currencyFormat.format(tongTien) + "đ");
        }
    }
    
    /**
     * Tạo panel thông tin (hành khách + hóa đơn)
     */
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 0));
        panel.setBackground(new Color(230, 230, 230));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        panel.setPreferredSize(new Dimension(0, 320));
        
        // Panel bên trái - Thông tin hành khách
        JPanel panelHanhKhach = createPassengerPanel();
        
        // Panel bên phải - Thông tin hóa đơn
        JPanel panelHoaDon = createInvoicePanel();
        
        panel.add(panelHanhKhach);
        panel.add(panelHoaDon);
        
        return panel;
    }
    
    /**
     * Tạo panel thông tin hành khách
     */
    private JPanel createPassengerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 230, 230));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Thông tin hành khách",
            0,
            0,
            new Font("Arial", Font.BOLD, 15),
            new Color(70, 130, 180)
        ));
        
        // CCCD
        JPanel pnlCCCD = createFieldRow("CCCD:");
        txtCCCD = (JTextField) pnlCCCD.getComponent(1);
        
        // Họ tên
        JPanel pnlHoTen = createFieldRow("Họ tên:");
        txtHoTen = (JTextField) pnlHoTen.getComponent(1);
        
        // Email
        JPanel pnlEmail = createFieldRow("Email:");
        txtEmail = (JTextField) pnlEmail.getComponent(1);
        
        // Nút thêm hành khách
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        pnlButton.setBackground(new Color(230, 230, 230));
        
        btnThemHanhKhach = new JButton("+ Thêm hành khách");
        btnThemHanhKhach.setFont(new Font("Arial", Font.BOLD, 14));
        btnThemHanhKhach.setBackground(new Color(34, 139, 34));
        btnThemHanhKhach.setForeground(Color.WHITE);
        btnThemHanhKhach.setFocusPainted(false);
        btnThemHanhKhach.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThemHanhKhach.setPreferredSize(new Dimension(200, 38));
        
        // Gắn sự kiện mở form thêm hành khách
        btnThemHanhKhach.addActionListener(e -> {
            FormThemHanhKhach formThemHK = new FormThemHanhKhach();
            formThemHK.setVisible(true);
        });
        
        pnlButton.add(btnThemHanhKhach);
        
        panel.add(Box.createVerticalStrut(12));
        panel.add(pnlCCCD);
        panel.add(Box.createVerticalStrut(12));
        panel.add(pnlHoTen);
        panel.add(Box.createVerticalStrut(12));
        panel.add(pnlEmail);
        panel.add(Box.createVerticalStrut(15));
        panel.add(pnlButton);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * Tạo panel thông tin hóa đơn
     */
    private JPanel createInvoicePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(230, 230, 230));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
            "Thông tin hóa đơn",
            0,
            0,
            new Font("Arial", Font.BOLD, 15),
            new Color(70, 130, 180)
        ));
        
        // Mã hóa đơn
        JPanel pnlMaHD = createFieldRow("Mã hóa đơn:");
        txtMaHoaDon = (JTextField) pnlMaHD.getComponent(1);
        txtMaHoaDon.setEditable(false);
        txtMaHoaDon.setBackground(Color.WHITE);
        txtMaHoaDon.setText("HD" + System.currentTimeMillis());
        
        // Ngày tạo
        JPanel pnlNgayTao = createFieldRow("Ngày tạo:");
        txtNgayTao = (JTextField) pnlNgayTao.getComponent(1);
        txtNgayTao.setEditable(false);
        txtNgayTao.setBackground(Color.WHITE);
        txtNgayTao.setText(new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(new java.util.Date()));
        
        // Tiền giảm
        JPanel pnlTienGiam = createFieldRow("Tiền giảm:");
        txtTienGiam = (JTextField) pnlTienGiam.getComponent(1);
        txtTienGiam.setEditable(false);
        txtTienGiam.setBackground(Color.WHITE);
        txtTienGiam.setText("0đ");
        
        // Khách phải trả
        JPanel pnlKhachPhaiTra = createFieldRow("Khách phải trả:");
        txtKhachPhaiTra = (JTextField) pnlKhachPhaiTra.getComponent(1);
        txtKhachPhaiTra.setEditable(false);
        txtKhachPhaiTra.setBackground(Color.WHITE);
        txtKhachPhaiTra.setForeground(new Color(220, 20, 60));
        txtKhachPhaiTra.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Tiền khách đưa
        JPanel pnlTienKhachDua = createFieldRow("Tiền khách đưa:");
        txtTienKhachDua = (JTextField) pnlTienKhachDua.getComponent(1);
        txtTienKhachDua.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tinhTienThua();
            }
        });
        
        // Panel chứa 2 nút
        JPanel pnlButtons = new JPanel(new GridLayout(1, 2, 10, 0));
        pnlButtons.setBackground(new Color(230, 230, 230));
        pnlButtons.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        btnNhanDuTien = new JButton("Nhận đủ tiền");
        btnNhanDuTien.setFont(new Font("Arial", Font.BOLD, 13));
        btnNhanDuTien.setBackground(new Color(180, 180, 180));
        btnNhanDuTien.setForeground(Color.WHITE);
        btnNhanDuTien.setFocusPainted(false);
        btnNhanDuTien.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNhanDuTien.addActionListener(e -> nhanDuTien());
        
        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setFont(new Font("Arial", Font.BOLD, 14));
        btnThanhToan.setBackground(new Color(34, 139, 34));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.setFocusPainted(false);
        btnThanhToan.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThanhToan.addActionListener(e -> {
            // Xác nhận thanh toán
            int confirm = JOptionPane.showConfirmDialog(this,
                "Xác nhận thanh toán và in vé?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                // In vé cho từng hành khách
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String hanhTrinh = (String) tableModel.getValueAt(i, 0);
                    String tenKH = (String) tableModel.getValueAt(i, 1);
                    String thongTinGhe = (String) tableModel.getValueAt(i, 4);
                    
                    // Parse thông tin
                    String[] htParts = hanhTrinh.split(" - ");
                    String gaDi = htParts[0];
                    String gaDen = htParts[1];
                    String maTau = htParts[2];
                    
                    String[] gheParts = thongTinGhe.split("\n")[0].split(" - ");
                    String cho = gheParts[0].replace("Ghế ", "");
                    String toa = gheParts[1].replace("Toa ", "");
                    
                    String maVe = "VE" + System.currentTimeMillis() + "_" + i;
                    String ngayDi = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date());
                    String gioDi = "08:00"; // Hoặc lấy từ dữ liệu thực
                    String hangVe = (String) tableModel.getValueAt(i, 2);
                    
                    // Gọi hàm in vé
                    VeTauPrinter.inVeTau(maVe, gaDi, gaDen, maTau, ngayDi, gioDi, toa, cho, hangVe);
                }
                
                JOptionPane.showMessageDialog(this, "Đã in " + tableModel.getRowCount() + " vé!");
            }
        });
        
        pnlButtons.add(btnNhanDuTien);
        pnlButtons.add(btnThanhToan);
        
        panel.add(Box.createVerticalStrut(12));
        panel.add(pnlMaHD);
        panel.add(Box.createVerticalStrut(10));
        panel.add(pnlNgayTao);
        panel.add(Box.createVerticalStrut(10));
        panel.add(pnlTienGiam);
        panel.add(Box.createVerticalStrut(10));
        panel.add(pnlKhachPhaiTra);
        panel.add(Box.createVerticalStrut(10));
        panel.add(pnlTienKhachDua);
        panel.add(Box.createVerticalStrut(15));
        panel.add(pnlButtons);
        panel.add(Box.createVerticalGlue());
        
        
       
        return panel;
        
        
    }
    
    /**
     * Nhận đủ tiền - tự động điền số tiền khách phải trả vào ô tiền khách đưa
     */
    private void nhanDuTien() {
        txtTienKhachDua.setText(txtKhachPhaiTra.getText());
    }
    
    /**
     * Tính tiền thừa (nếu cần)
     */
    private void tinhTienThua() {
        try {
            String tienDuaStr = txtTienKhachDua.getText().replaceAll("[^0-9]", "");
            String tienTraStr = txtKhachPhaiTra.getText().replaceAll("[^0-9]", "");
            
            if (!tienDuaStr.isEmpty() && !tienTraStr.isEmpty()) {
                long tienDua = Long.parseLong(tienDuaStr);
                long tienTra = Long.parseLong(tienTraStr);
                long tienThua = tienDua - tienTra;
                
                if (tienThua > 0) {
                    // Có thể hiển thị tiền thừa ở đâu đó nếu cần
                }
            }
        } catch (NumberFormatException e) {
            // Bỏ qua
        }
    }
    
    /**
     * Tạo panel cụm nút điều khiển (bên phải)
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(140, 0));
        
        // Tạo các nút (bỏ nút Hủy)
        btnXoa = createControlButton("Xóa", new Color(255, 140, 0));
        btnXoaTatCa = createControlButton("Xóa tất cả", new Color(255, 69, 0));
        btnTroLai = createControlButton("Trở lại", new Color(100, 100, 100));
        
        // Gắn sự kiện cho nút Trở lại
        btnTroLai.addActionListener(e -> {
            // Trở về giao diện bán vé
            Container parent = getParent();
            if (parent != null) {
                CardLayout layout = (CardLayout) parent.getLayout();
                layout.show(parent, "BanVe"); // Giả sử panel bán vé có tên "BanVe"
            }
        });
        
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnXoa);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnXoaTatCa);
        panel.add(Box.createVerticalGlue());
        panel.add(btnTroLai);
        panel.add(Box.createVerticalStrut(10));
        
        return panel;
    }
    
    /**
     * Tạo một nút điều khiển chuẩn
     */
    private JButton createControlButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setMaximumSize(new Dimension(120, 38));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    /**
     * Tạo một hàng field (label + textfield)
     */
    private JPanel createFieldRow(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(new Color(230, 230, 230));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(130, 28));
        
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(220, 28));
        
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        
        return panel;
    }
    
    
    // ========== GETTER METHODS ==========
    
    public JTable getTicketInfoTable() {
        return ticketInfoTable;
    }
    
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    
    public JTextField getTxtCCCD() {
        return txtCCCD;
    }
    
    public JTextField getTxtHoTen() {
        return txtHoTen;
    }
    
    public JTextField getTxtEmail() {
        return txtEmail;
    }
    
    public JButton getBtnThemHanhKhach() {
        return btnThemHanhKhach;
    }
    
    public JTextField getTxtMaHoaDon() {
        return txtMaHoaDon;
    }
    
    public JTextField getTxtNgayTao() {
        return txtNgayTao;
    }
    
    public JTextField getTxtTienGiam() {
        return txtTienGiam;
    }
    
    public JTextField getTxtKhachPhaiTra() {
        return txtKhachPhaiTra;
    }
    
    public JTextField getTxtTienKhachDua() {
        return txtTienKhachDua;
    }
    
    public JButton getBtnNhanDuTien() {
        return btnNhanDuTien;
    }
    
    public JButton getBtnThanhToan() {
        return btnThanhToan;
    }
    
    public JButton getBtnHuy() {
        return btnHuy;
    }
    
    public JButton getBtnXoa() {
        return btnXoa;
    }
    
    public JButton getBtnXoaTatCa() {
        return btnXoaTatCa;
    }
    
    public JButton getBtnTroLai() {
        return btnTroLai;
    }
    
}