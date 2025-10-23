package panel_nhanvien;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Form thêm hành khách mới
 * @author Nguyễn Nam Trung Nguyên
 */
public class FormThemHanhKhach extends JFrame {
    
    private JTextField txtHoTenDem;
    private JTextField txtTen;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JTextField txtCCCD;
    private JDateChooser dateNgaySinh;
    
    private JButton btnLuu;
    private JButton btnTroLai;
    private JButton btnTimKiem;
    private JButton btnTroLaiTop;
    
    public FormThemHanhKhach() {
        setTitle("Tạo mới hành khách");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
        initUI();
        setupEvents();
    }
    
    private void initUI() {
        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // === PHẦN HEADER ===
        JPanel headerPanel = createHeaderPanel();
        
        // === PHẦN TIÊU ĐỀ CHÍNH ===
        JPanel titlePanel = createTitlePanel();
        
        // === PHẦN FORM NHẬP LIỆU ===
        JPanel formPanel = createFormPanel();
        
        // === PHẦN NÚT CUỐI ===
        JPanel buttonPanel = createButtonPanel();
        
        // Thêm các panel vào main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 245));
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(titlePanel, BorderLayout.CENTER);
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Tạo panel header với breadcrumb và nút trở lại
     */
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Breadcrumb
        JLabel lblBreadcrumb = new JLabel("Bán vé / Thêm hành khách");
        lblBreadcrumb.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblBreadcrumb.setForeground(new Color(120, 120, 120));
        
        // Nút trở lại góc phải
        btnTroLaiTop = new JButton("Trở lại (ESC)");
        btnTroLaiTop.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnTroLaiTop.setBackground(new Color(224, 224, 224));
        btnTroLaiTop.setForeground(Color.BLACK);
        btnTroLaiTop.setPreferredSize(new Dimension(130, 35));
        btnTroLaiTop.setFocusPainted(false);
        btnTroLaiTop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTroLaiTop.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        // Hover effect
        btnTroLaiTop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTroLaiTop.setBackground(new Color(204, 204, 204));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnTroLaiTop.setBackground(new Color(224, 224, 224));
            }
        });
        
        panel.add(lblBreadcrumb, BorderLayout.WEST);
        panel.add(btnTroLaiTop, BorderLayout.EAST);
        
        return panel;
    }
    
    /**
     * Tạo panel tiêu đề chính
     */
    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel lblTitle = new JLabel("Tạo mới hành khách");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(34, 34, 34));
        
        panel.add(lblTitle);
        
        return panel;
    }
    
    /**
     * Tạo panel form nhập liệu
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 20);
        
        // Dòng 1: Họ Tên Đệm
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(createLabel("Họ Tên Đệm:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtHoTenDem = createTextField("Nhập họ tên đệm");
        panel.add(txtHoTenDem, gbc);
        
        // Dòng 2: Tên
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(createLabel("Tên:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTen = createTextField("Nhập tên");
        panel.add(txtTen, gbc);
        
        // Dòng 3: Ngày Sinh
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(createLabel("Ngày Sinh:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        dateNgaySinh = new JDateChooser();
        dateNgaySinh.setDateFormatString("dd/MM/yyyy");
        dateNgaySinh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateNgaySinh.setPreferredSize(new Dimension(0, 38));
        dateNgaySinh.setBackground(Color.WHITE);
        dateNgaySinh.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panel.add(dateNgaySinh, gbc);
        
        // Dòng 4: Số Điện Thoại
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(createLabel("Số Điện Thoại:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtSDT = createTextField("Nhập số điện thoại");
        panel.add(txtSDT, gbc);
        
        // Dòng 5: Địa Chỉ Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(createLabel("Địa Chỉ Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEmail = createTextField("example@gmail.com");
        panel.add(txtEmail, gbc);
        
        // Dòng 6: Số CCCD (có nút Tìm kiếm)
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        panel.add(createLabel("Số CCCD:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel cccdPanel = new JPanel(new BorderLayout(10, 0));
        cccdPanel.setBackground(new Color(245, 245, 245));
        
        txtCCCD = createTextField("Nhập số CCCD");
        cccdPanel.add(txtCCCD, BorderLayout.CENTER);
        
        btnTimKiem = new JButton("🔍 Tìm kiếm");
        btnTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnTimKiem.setBackground(new Color(33, 150, 243));
        btnTimKiem.setForeground(Color.WHITE);
        btnTimKiem.setPreferredSize(new Dimension(120, 38));
        btnTimKiem.setFocusPainted(false);
        btnTimKiem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTimKiem.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        // Hover effect cho nút Tìm kiếm
        btnTimKiem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTimKiem.setBackground(new Color(25, 118, 210));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnTimKiem.setBackground(new Color(33, 150, 243));
            }
        });
        
        cccdPanel.add(btnTimKiem, BorderLayout.EAST);
        
        panel.add(cccdPanel, gbc);
        
        return panel;
    }
    
    /**
     * Tạo panel chứa các nút cuối
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        // Nút Lưu
        btnLuu = new JButton("✓ Lưu");
        btnLuu.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnLuu.setBackground(new Color(76, 175, 80));
        btnLuu.setForeground(Color.WHITE);
        btnLuu.setPreferredSize(new Dimension(150, 45));
        btnLuu.setFocusPainted(false);
        btnLuu.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLuu.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Hover effect cho nút Lưu
        btnLuu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLuu.setBackground(new Color(67, 160, 71));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnLuu.setBackground(new Color(76, 175, 80));
            }
        });
        
        // Nút Trở lại
        btnTroLai = new JButton("↩ Trở lại");
        btnTroLai.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnTroLai.setBackground(new Color(224, 224, 224));
        btnTroLai.setForeground(Color.BLACK);
        btnTroLai.setPreferredSize(new Dimension(150, 45));
        btnTroLai.setFocusPainted(false);
        btnTroLai.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTroLai.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Hover effect cho nút Trở lại
        btnTroLai.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnTroLai.setBackground(new Color(204, 204, 204));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                btnTroLai.setBackground(new Color(224, 224, 224));
            }
        });
        
        panel.add(btnLuu);
        panel.add(btnTroLai);
        
        return panel;
    }
    
    /**
     * Tạo JLabel chuẩn
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setForeground(new Color(85, 85, 85));
        label.setPreferredSize(new Dimension(140, 30));
        return label;
    }
    
    /**
     * Tạo JTextField chuẩn
     */
    private JTextField createTextField(String placeholder) {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(0, 38));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Placeholder effect
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        
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
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
        
        return textField;
    }
    
    /**
     * Thiết lập các sự kiện
     */
    private void setupEvents() {
        // Sự kiện cho nút Trở lại (cả 2 nút)
        ActionListener troLaiAction = e -> dispose();
        btnTroLai.addActionListener(troLaiAction);
        btnTroLaiTop.addActionListener(troLaiAction);
        
        // Phím tắt ESC để đóng form
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getRootPane().registerKeyboardAction(troLaiAction, escapeKeyStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        
        // TODO: Sự kiện cho nút Lưu sẽ được thêm sau
        // btnLuu.addActionListener(e -> luuHanhKhach());
        
        // TODO: Sự kiện cho nút Tìm kiếm sẽ được thêm sau
        // btnTimKiem.addActionListener(e -> timKiemTheoCCCD());
    }
    
    // ========== GETTER METHODS ==========
    
    public JTextField getTxtHoTenDem() {
        return txtHoTenDem;
    }
    
    public JTextField getTxtTen() {
        return txtTen;
    }
    
    public JTextField getTxtSDT() {
        return txtSDT;
    }
    
    public JTextField getTxtEmail() {
        return txtEmail;
    }
    
    public JTextField getTxtCCCD() {
        return txtCCCD;
    }
    
    public JDateChooser getDateNgaySinh() {
        return dateNgaySinh;
    }
    
    public JButton getBtnLuu() {
        return btnLuu;
    }
    
    public JButton getBtnTimKiem() {
        return btnTimKiem;
    }
    
    // ========== TEST ==========
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            FormThemHanhKhach form = new FormThemHanhKhach();
            form.setVisible(true);
        });
    }
}