package panel_nhanvien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author Nguyễn Nam Trung Nguyên
 */
public class Panel_TraVe extends javax.swing.JPanel {
    
    // Components
    private JTable ticketTable;
    private JTable returnedTicketTable;
    private JTextField txtSearch;
    private JTextField txtGiaVe;
    private JTextField txtPhanTramHoan;
    private JTextField txtTienHoanTra;
    private JButton btnTim;
    private JButton btnTraVe;
    private JButton btnHuyVe;
    private DefaultTableModel ticketTableModel;
    private DefaultTableModel returnedTableModel;
    
    public Panel_TraVe() {
        initComponents();
    }
    
    private void initComponents() {
        setBackground(new Color(250, 250, 250));
        setLayout(new BorderLayout(0, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // PHẦN TRÊN - Panel A: Tìm vé + Bảng thông tin vé
        JPanel panelA = createPanelA();
        
        // PHẦN DƯỚI - Panel chứa B1 và B2
        JPanel panelBottom = new JPanel(new GridLayout(1, 2, 20, 0));
        panelBottom.setBackground(new Color(250, 250, 250));
        
        // Panel B1 - Thông tin giá + Nút
        JPanel panelB1 = createPanelB1();
        
        // Panel B2 - Danh sách đã trả vé
        JPanel panelB2 = createPanelB2();
        
        panelBottom.add(panelB1);
        panelBottom.add(panelB2);
        
        // Thêm các panel vào layout chính
        add(panelA, BorderLayout.NORTH);
        add(panelBottom, BorderLayout.CENTER);
    }
    
    /**
     * Tạo Panel A - Phần tìm vé và bảng thông tin vé
     */
    private JPanel createPanelA() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(new Color(250, 250, 250));
        
        // Panel tiêu đề và tìm kiếm
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(250, 250, 250));
        
        // Label tiêu đề
        JLabel lblTitle = new JLabel("Thông tin vé");
        lblTitle.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 16));
        lblTitle.setForeground(new Color(59, 59, 59));
        
        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setBackground(new Color(250, 250, 250));
        
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(200, 30));
        
        btnTim = new JButton("Tìm");
        btnTim.setFont(new Font("Arial", Font.BOLD, 14));
        btnTim.setPreferredSize(new Dimension(80, 30));
        btnTim.setBackground(new Color(70, 130, 180));
        btnTim.setForeground(Color.WHITE);
        btnTim.setFocusPainted(false);
        btnTim.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        searchPanel.add(new JLabel("Mã vé:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnTim);
        
        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(searchPanel, BorderLayout.EAST);
        
        // Bảng thông tin vé
        String[] columns = {"Mã vé", "Tên", "Ngày giờ", "Ga đi", "Ga đến", "Toa - Loại ghế", "Loại", "Giá"};
        ticketTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        ticketTable = new JTable(ticketTableModel);
        ticketTable.setFont(new Font("Arial", Font.PLAIN, 13));
        ticketTable.setRowHeight(30);
        ticketTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        ticketTable.getTableHeader().setBackground(new Color(70, 130, 180));
        ticketTable.getTableHeader().setForeground(Color.WHITE);
        ticketTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ticketTable.setSelectionBackground(new Color(173, 216, 230));
        
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setPreferredSize(new Dimension(0, 200));
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Tạo Panel B1 - Thông tin giá và nút chức năng
     */
    private JPanel createPanelB1() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("Thông tin giá");
        lblTitle.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 16));
        lblTitle.setForeground(new Color(59, 59, 59));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(20));
        
        // Giá vé
        JPanel pnlGia = createInputRow("Giá vé:");
        txtGiaVe = (JTextField) pnlGia.getComponent(1);
        txtGiaVe.setEditable(false);
        panel.add(pnlGia);
        panel.add(Box.createVerticalStrut(15));
        
        // Phần trăm hoàn
        JPanel pnlPhanTram = createInputRow("Phần trăm hoàn (%):");
        txtPhanTramHoan = (JTextField) pnlPhanTram.getComponent(1);
        txtPhanTramHoan.setEditable(false);
        panel.add(pnlPhanTram);
        panel.add(Box.createVerticalStrut(15));
        
        // Tiền hoàn trả
        JPanel pnlHoanTra = createInputRow("Tiền hoàn trả:");
        txtTienHoanTra = (JTextField) pnlHoanTra.getComponent(1);
        txtTienHoanTra.setEditable(false);
        panel.add(pnlHoanTra);
        panel.add(Box.createVerticalStrut(25));
        
        // Panel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        btnTraVe = new JButton("Trả vé");
        btnTraVe.setFont(new Font("Arial", Font.BOLD, 14));
        btnTraVe.setPreferredSize(new Dimension(120, 40));
        btnTraVe.setBackground(new Color(34, 139, 34));
        btnTraVe.setForeground(Color.WHITE);
        btnTraVe.setFocusPainted(false);
        btnTraVe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnHuyVe = new JButton("Hủy vé");
        btnHuyVe.setFont(new Font("Arial", Font.BOLD, 14));
        btnHuyVe.setPreferredSize(new Dimension(120, 40));
        btnHuyVe.setBackground(new Color(220, 20, 60));
        btnHuyVe.setForeground(Color.WHITE);
        btnHuyVe.setFocusPainted(false);
        btnHuyVe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(btnTraVe);
        buttonPanel.add(btnHuyVe);
        
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    /**
     * Tạo Panel B2 - Danh sách vé đã trả
     */
    private JPanel createPanelB2() {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("Danh sách đã trả vé");
        lblTitle.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 16));
        lblTitle.setForeground(new Color(59, 59, 59));
        
        // Bảng danh sách đã trả
        String[] columns = {"Mã vé", "Ngày giờ", "Ga đi", "Ga đến", "Loại"};
        returnedTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        returnedTicketTable = new JTable(returnedTableModel);
        returnedTicketTable.setFont(new Font("Arial", Font.PLAIN, 13));
        returnedTicketTable.setRowHeight(30);
        returnedTicketTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        returnedTicketTable.getTableHeader().setBackground(new Color(70, 130, 180));
        returnedTicketTable.getTableHeader().setForeground(Color.WHITE);
        returnedTicketTable.setSelectionBackground(new Color(173, 216, 230));
        
        JScrollPane scrollPane = new JScrollPane(returnedTicketTable);
        
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Tạo hàng input với label và textfield
     */
    private JPanel createInputRow(String labelText) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setPreferredSize(new Dimension(150, 30));
        
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setPreferredSize(new Dimension(200, 30));
        
        panel.add(label, BorderLayout.WEST);
        panel.add(textField, BorderLayout.CENTER);
        
        return panel;
    }
    
    // Getter methods để truy cập components từ bên ngoài
    public JTable getTicketTable() {
        return ticketTable;
    }
    
    public JTable getReturnedTicketTable() {
        return returnedTicketTable;
    }
    
    public JTextField getTxtSearch() {
        return txtSearch;
    }
    
    public JTextField getTxtGiaVe() {
        return txtGiaVe;
    }
    
    public JTextField getTxtPhanTramHoan() {
        return txtPhanTramHoan;
    }
    
    public JTextField getTxtTienHoanTra() {
        return txtTienHoanTra;
    }
    
    public JButton getBtnTim() {
        return btnTim;
    }
    
    public JButton getBtnTraVe() {
        return btnTraVe;
    }
    
    public JButton getBtnHuyVe() {
        return btnHuyVe;
    }
    
    public DefaultTableModel getTicketTableModel() {
        return ticketTableModel;
    }
    
    public DefaultTableModel getReturnedTableModel() {
        return returnedTableModel;
    }
}