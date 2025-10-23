/**
 * @author Nguyễn Nam Trung Nguyên
 */
package panel_nhanvien;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Connection;
import com.toedter.calendar.JDateChooser;

import connect_database.DatabaseConnection;
import dao.*;
import entity.*;

public class Panel_BanVe extends javax.swing.JPanel {

    private int selectedTrainIndex = -1;
    private int selectedCoachIndex = -1;
    private int selectedSeatIndex = -1;
    private Connection connection;
    private Ga_Dao gaDao;
    private Dao_LichTrinh lichTrinhDao;
    private ChuyenTau_Dao chuyenTauDao;
    private Dao_Toa toaDao;
    private ChoNgoi_Dao choNgoiDao;
    private Ve_Dao veDao;
    private LoaiToa_Dao loaiToaDao;
    private KhoangCachGa_Dao khoangCachGaDao;

    private List<LichTrinh> lichTrinhDi;
    private List<LichTrinh> lichTrinhVe;
    private String selectedMaLichTrinhDi;
    private String selectedMaLichTrinhVe;
    
    private JPanel mainPanel;
    
    public Panel_BanVe() {
        initDatabase();
        initComponents();
        loadGaData();
        setupTrainList();
        setupCoachList();
        setupSeatGrid();
        setupRadioButtonListeners();
    }

    private void initDatabase() {
        try {
            connection = DatabaseConnection.getInstance().getConnection();
            if (connection != null) {
                gaDao = new Ga_Dao(connection);
                lichTrinhDao = new Dao_LichTrinh(connection);
                chuyenTauDao = new ChuyenTau_Dao(connection);
                toaDao = new Dao_Toa(connection);
                choNgoiDao = new ChoNgoi_Dao(connection);
                veDao = new Ve_Dao(connection);
                loaiToaDao = new LoaiToa_Dao(connection);
                khoangCachGaDao = new KhoangCachGa_Dao(connection);
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

    private void loadGaData() {
        try {
            if (gaDao != null) {
                List<String> danhSachGa = gaDao.getTenGaList();
                jComboBox1.removeAllItems();
                jComboBox2.removeAllItems();
                for (String tenGa : danhSachGa) {
                    jComboBox1.addItem(tenGa);
                    jComboBox2.addItem(tenGa);
                }
                if (danhSachGa.size() >= 2) {
                    jComboBox1.setSelectedIndex(0);
                    jComboBox2.setSelectedIndex(1);
                }
                System.out.println("Load danh sách ga thành công: " + danhSachGa.size() + " ga");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi tải dữ liệu ga: " + e.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser1 = new JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        jDateChooser2 = new JDateChooser();
        jButtonSearch = new javax.swing.JButton();
        
        jPanel2 = new javax.swing.JPanel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        
        jPanel9 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        
        jPanel11 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(250, 250, 250));
        setPreferredSize(new java.awt.Dimension(1200, 700));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        jLabel1.setText("Ga Đi");
        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jComboBox1.setPreferredSize(new Dimension(180, 35));

        jLabel2.setText("Ga Đến");
        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jComboBox2.setPreferredSize(new Dimension(180, 35));

        jLabel3.setText("Ngày Đi");
        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jDateChooser1.setDateFormatString("dd/MM/yy");
        jDateChooser1.setPreferredSize(new Dimension(140, 35));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            jDateChooser1.setDate(sdf.parse("10/10/25"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        jLabel4.setText("Ngày Về");
        jLabel4.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jDateChooser2.setDateFormatString("dd/MM/yy");
        jDateChooser2.setPreferredSize(new Dimension(140, 35));
        jDateChooser2.setEnabled(false);

        jButtonSearch.setText("Tra Cứu");
        jButtonSearch.setBackground(new Color(70, 130, 180));
        jButtonSearch.setForeground(Color.WHITE);
        jButtonSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jButtonSearch.setPreferredSize(new Dimension(120, 35));
        jButtonSearch.addActionListener(e -> timKiemChuyenTau());

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(20)
                .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(40)
                .addComponent(jLabel2)
                .addGap(20)
                .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addGap(40)
                .addComponent(jLabel3)
                .addGap(20)
                .addComponent(jDateChooser1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                .addGap(40)
                .addComponent(jLabel4)
                .addGap(20)
                .addComponent(jDateChooser2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addComponent(jButtonSearch, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(jLabel1)
            .addComponent(jComboBox1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel2)
            .addComponent(jComboBox2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel3)
            .addComponent(jDateChooser1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel4)
            .addComponent(jDateChooser2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jButtonSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        buttonGroup1.add(jRadioButton1);
        buttonGroup1.add(jRadioButton2);
        jRadioButton1.setText("Một Chiều");
        jRadioButton1.setSelected(true);
        jRadioButton1.setBackground(Color.WHITE);
        jRadioButton2.setText("Khứ Hồi");
        jRadioButton2.setBackground(Color.WHITE);

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addGap(30)
                .addComponent(jRadioButton2)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(jRadioButton1)
            .addComponent(jRadioButton2)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        jLabel5.setText("■");
        jLabel5.setForeground(new Color(100, 200, 100));
        jLabel5.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel6.setText("Đang chọn");
        jLabel6.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        jLabel7.setText("■");
        jLabel7.setForeground(Color.WHITE);
        jLabel7.setBackground(Color.WHITE);
        jLabel7.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        jLabel7.setOpaque(true); // bật opaque để hiển thị background
        jLabel8.setText("Chỗ trống");
        jLabel8.setFont(new Font("Segoe UI", Font.PLAIN, 8));

        jLabel9.setText("■");
        jLabel9.setForeground(new Color(255, 100, 100));
        jLabel9.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel10.setText("Chỗ đầy");
        jLabel10.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        jLabel11.setText("■");
        jLabel11.setForeground(new Color(0, 0, 139)); // xanh lam đậm cho toa giường nằm
        jLabel11.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel12.setText("Toa giường nằm");
        jLabel12.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        jLabel13.setText("■");
        jLabel13.setForeground(new Color(255, 215, 0)); // vàng cho toa ngồi cứng
        jLabel13.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel14.setText("Toa ngồi cứng");
        jLabel14.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        jLabel15.setText("■");
        jLabel15.setForeground(new Color(144, 238, 144)); // xanh lá nhạt cho toa ngồi mềm
        jLabel15.setFont(new Font("Arial", Font.BOLD, 20));
        jLabel16.setText("Toa ngồi mềm");
        jLabel16.setFont(new Font("Segoe UI", Font.PLAIN, 11));

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addGap(5)
                .addComponent(jLabel6)
                .addGap(20)
                .addComponent(jLabel7)
                .addGap(5)
                .addComponent(jLabel8)
                .addGap(20)
                .addComponent(jLabel9)
                .addGap(5)
                .addComponent(jLabel10)
                .addGap(20)
                .addComponent(jLabel11)
                .addGap(5)
                .addComponent(jLabel12)
                .addGap(20)
                .addComponent(jLabel13)
                .addGap(5)
                .addComponent(jLabel14)
                .addGap(20)
                .addComponent(jLabel15)
                .addGap(5)
                .addComponent(jLabel16)
                .addGap(20)
                .addComponent(jLabel21)
                .addGap(5)
                .addComponent(jLabel22)
                .addGap(20)
                .addComponent(jLabel23)
                .addGap(5)
                .addComponent(jLabel24)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.CENTER)
            .addComponent(jLabel5)
            .addComponent(jLabel6)
            .addComponent(jLabel7)
            .addComponent(jLabel8)
            .addComponent(jLabel9)
            .addComponent(jLabel10)
            .addComponent(jLabel11)
            .addComponent(jLabel12)
            .addComponent(jLabel13)
            .addComponent(jLabel14)
            .addComponent(jLabel15)
            .addComponent(jLabel16)
            .addComponent(jLabel21)
            .addComponent(jLabel22)
            .addComponent(jLabel23)
            .addComponent(jLabel24)
        );

        jPanel4.setBackground(new java.awt.Color(250, 250, 250));

        jPanel5.setBackground(Color.WHITE);
        jPanel5.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        jLabel17.setText("Lượt Đi");
        jLabel17.setFont(new Font("Segoe UI", Font.BOLD, 14));

        jTable1.setRowHeight(50);
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1.setPreferredSize(new Dimension(600, 400));

        jLabel18.setText("Lượt Về");
        jLabel18.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel18.setVisible(false);

        jTable2.setRowHeight(50);
        jScrollPane3.setViewportView(jTable2);
        jScrollPane3.setPreferredSize(new Dimension(600, 200));
        jScrollPane3.setVisible(false);

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jLabel17)
            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addComponent(jLabel18)
            .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createSequentialGroup()
            .addComponent(jLabel17)
            .addGap(10)
            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(10)
            .addComponent(jLabel18)
            .addGap(10)
            .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        jPanel6.setBackground(new Color(250, 250, 250));

        jPanel7.setBackground(Color.WHITE);
        jPanel7.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        jLabel19.setText("Toa Tàu");
        jLabel19.setFont(new Font("Segoe UI", Font.BOLD, 14));

        jPanel8.setBackground(Color.WHITE);
        jPanel8.setLayout(new BoxLayout(jPanel8, BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(jPanel8);
        jScrollPane2.setPreferredSize(new Dimension(200, 400));

        GroupLayout jPanel7Layout = new GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19)
            .addComponent(jScrollPane2)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createSequentialGroup()
            .addComponent(jLabel19)
            .addGap(10)
            .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        jPanel9.setBackground(Color.WHITE);
        jPanel9.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        jLabel20.setText("Ghế Ngồi");
        jLabel20.setFont(new Font("Segoe UI", Font.BOLD, 14));

        jPanel10.setBackground(Color.WHITE);
        jPanel10.setLayout(new GridLayout(6, 5, 5, 5));

        GroupLayout jPanel9Layout = new GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20)
            .addComponent(jPanel10, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createSequentialGroup()
            .addComponent(jLabel20)
            .addGap(10)
            .addComponent(jPanel10, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createSequentialGroup()
            .addComponent(jPanel7, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
            .addGap(10)
            .addComponent(jPanel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        GroupLayout jPanel4Layout = new GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createSequentialGroup()
            .addGap(10)
            .addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
            .addGap(10)
            .addComponent(jPanel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(10)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel11.setBackground(new Color(255, 255, 255));
        jPanel11.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        jButton1.setText("Bán Vé");
        jButton1.setBackground(new Color(70, 130, 180));
        jButton1.setForeground(Color.WHITE);
        jButton1.setFont(new Font("Segoe UI", Font.BOLD, 13));
        jButton1.setPreferredSize(new Dimension(120, 35));

        jButton2.setText("Làm Mới");
        jButton2.setBackground(new Color(200, 200, 200));
        jButton2.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jButton2.setPreferredSize(new Dimension(120, 35));
        jButton2.addActionListener(e -> lamMoi());

        jButton1.addActionListener(e -> chuyenSangThanhToan());
        
        GroupLayout jPanel11Layout = new GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createSequentialGroup()
            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
            .addGap(15)
            .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(0)
            .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(5)
            .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addGap(10)
            .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGap(10)
            .addComponent(jPanel11, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );
    }

    private void chuyenSangThanhToan() {
        if (mainPanel != null) {
            // Tạo panel Thanh Toán mới
            ThanhToan thanhToanPanel = new ThanhToan();
            
            // Xóa panel hiện tại và thêm panel mới
            mainPanel.removeAll();
            mainPanel.add(thanhToanPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
            
            System.out.println("✅ Đã chuyển sang màn hình Thanh Toán!");
        } else {
            JOptionPane.showMessageDialog(this,
                "Lỗi: Chưa thiết lập mainPanel!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }

	private void timKiemChuyenTau() {
        String tenGaDi = (String) jComboBox1.getSelectedItem();
        String tenGaDen = (String) jComboBox2.getSelectedItem();
        
        if (tenGaDi == null || tenGaDen == null) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn ga đi và ga đến!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (tenGaDi.equals(tenGaDen)) {
            JOptionPane.showMessageDialog(this, 
                "Ga đi và ga đến không thể trùng nhau!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String maGaDi = gaDao.getMaGaByTenGa(tenGaDi);
        String maGaDen = gaDao.getMaGaByTenGa(tenGaDen);
        
        java.util.Date ngayDi = jDateChooser1.getDate();
        lichTrinhDi = lichTrinhDao.getLichTrinhByGaAndNgay(maGaDi, maGaDen, ngayDi);
        
        if (jRadioButton2.isSelected()) {
            java.util.Date ngayVe = jDateChooser2.getDate();
            lichTrinhVe = lichTrinhDao.getLichTrinhByGaAndNgay(maGaDen, maGaDi, ngayVe);
        } else {
            lichTrinhVe = null;
        }
        
        populateTrainTable(jTable1, lichTrinhDi, maGaDi, maGaDen);
        if (jRadioButton2.isSelected()) {
            populateTrainTable(jTable2, lichTrinhVe, maGaDen, maGaDi);
            jLabel18.setVisible(true);
            jScrollPane3.setVisible(true);
        } else {
            jLabel18.setVisible(false);
            jScrollPane3.setVisible(false);
        }
        
        setupCoachList();
        setupSeatGrid();
    }

    private void populateTrainTable(JTable table, List<LichTrinh> list, String maGaDi, String maGaDen) {
        if (list == null || list.isEmpty()) {
            table.setModel(new DefaultTableModel(new String[]{"Mã Tàu", "Giờ Đi", "Giờ Đến", "Ga Đi", "Ga Đến", "Chỗ Còn"}, 0));
            return;
        }
        
        DefaultTableModel model = new DefaultTableModel(new String[]{"Mã Tàu", "Giờ Đi", "Giờ Đến", "Ga Đi", "Ga Đến", "Chỗ Còn"}, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
        
        Ga tenGaDi = gaDao.getGaByMa(maGaDi);
        Ga tenGaDen = gaDao.getGaByMa(maGaDen);
        
        for (LichTrinh lt : list) {
            ChuyenTau ct = chuyenTauDao.getByMa(lt.getMaChuyenTau());
            if (ct != null) {
                int totalSeats = toaDao.getTotalSeatsByChuyenTau(lt.getMaChuyenTau());
                int sold = veDao.getSoldByLichTrinh(lt.getMaLichTrinh());
                int available = totalSeats - sold;
                
                model.addRow(new Object[]{
                    ct.getTenChuyenTau(),
                    sdf.format(java.sql.Timestamp.valueOf(lt.getThoiGianKhoiHanh())),
                    sdf.format(java.sql.Timestamp.valueOf(lt.getThoiGianDuKienDen())),
                    tenGaDi,
                    tenGaDen,
                    available
                });
            }
        }
        
        table.setModel(model);
    }

    private void lamMoi() {
        selectedTrainIndex = -1;
        selectedCoachIndex = -1;
        selectedSeatIndex = -1;
        jRadioButton1.setSelected(true);
        jLabel18.setVisible(false);
        jScrollPane3.setVisible(false);
        jDateChooser2.setEnabled(false);
        jScrollPane1.setPreferredSize(new Dimension(600, 400));
        loadGaData();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            jDateChooser1.setDate(sdf.parse("10/10/25"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        lichTrinhDi = null;
        lichTrinhVe = null;
        setupTrainList();
        setupCoachList();
        setupSeatGrid();
        jPanel5.revalidate();
        jPanel5.repaint();
        System.out.println("Đã làm mới form bán vé");
    }

    private void setupRadioButtonListeners() {
        jRadioButton1.addActionListener(e -> {
            jLabel18.setVisible(false);
            jScrollPane3.setVisible(false);
            jDateChooser2.setEnabled(false);
            jScrollPane1.setPreferredSize(new Dimension(600, 400));
            jPanel5.revalidate();
            jPanel5.repaint();
        });

        jRadioButton2.addActionListener(e -> {
            jLabel18.setVisible(true);
            jScrollPane3.setVisible(true);
            jDateChooser2.setEnabled(true);
            jScrollPane1.setPreferredSize(new Dimension(600, 200));
            jPanel5.revalidate();
            jPanel5.repaint();
        });
    }

    private void setupTrainList() {
    	jTable1.setModel(new DefaultTableModel(new String[]{"Mã Tàu", "Giờ Đi", "Giờ Đến", "Ga Đi", "Ga Đến", "Chỗ Còn"}, 0));
    	jTable2.setModel(new DefaultTableModel(new String[]{"Mã Tàu", "Giờ Đi", "Giờ Đến", "Ga Đi", "Ga Đến", "Chỗ Còn"}, 0));

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedTrainIndex = jTable1.getSelectedRow();
                if (selectedTrainIndex >= 0 && lichTrinhDi != null) {
                    selectedMaLichTrinhDi = lichTrinhDi.get(selectedTrainIndex).getMaLichTrinh();
                    selectedCoachIndex = -1;
                    selectedSeatIndex = -1;
                    setupCoachList();
                    setupSeatGrid();
                }
            }
        });

        jTable2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedTrainIndex = jTable2.getSelectedRow();
                if (selectedTrainIndex >= 0 && lichTrinhVe != null) {
                    selectedMaLichTrinhVe = lichTrinhVe.get(selectedTrainIndex).getMaLichTrinh();
                    selectedCoachIndex = -1;
                    selectedSeatIndex = -1;
                    setupCoachList();
                    setupSeatGrid();
                }
            }
        });
    }

    private void setupCoachList() {
        jPanel8.removeAll();
        jPanel8.setLayout(new BoxLayout(jPanel8, BoxLayout.Y_AXIS));

        List<LichTrinh> selectedLichTrinhList = jRadioButton1.isSelected() ? lichTrinhDi : lichTrinhVe;

        if (selectedTrainIndex >= 0 && selectedLichTrinhList != null && toaDao != null) {
            LichTrinh lt = selectedLichTrinhList.get(selectedTrainIndex);
            List<ToaTau> toas = toaDao.getToaByChuyenTau(lt.getMaChuyenTau());

            if (toas != null && !toas.isEmpty()) {
                for (int i = 0; i < toas.size(); i++) {
                    ToaTau toa = toas.get(i);
                    JButton coachBtn = new JButton("Toa " + toa.getSoToa());
                    coachBtn.setMaximumSize(new Dimension(160, 60));
                    coachBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

                    // Set màu theo loại toa
                    coachBtn.setBackground(getCoachColor(toa.getLoaiToa()));
                    coachBtn.setForeground(Color.WHITE);
                    coachBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));

                    final int coachIndex = i;
                    coachBtn.addActionListener(e -> {
                        selectedCoachIndex = coachIndex;
                        selectedSeatIndex = -1;
                        setupSeatGrid();
                    });

                    jPanel8.add(coachBtn);
                    jPanel8.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            } else {
                JLabel noCoach = new JLabel("Chưa có toa cho chuyến tàu này");
                noCoach.setAlignmentX(Component.CENTER_ALIGNMENT);
                jPanel8.add(noCoach);
            }
        } else {
            JLabel noCoach = new JLabel("Vui lòng chọn chuyến tàu");
            noCoach.setAlignmentX(Component.CENTER_ALIGNMENT);
            jPanel8.add(noCoach);
        }

        jPanel8.revalidate();
        jPanel8.repaint();
    }

    private void setupSeatGrid() {
        jPanel10.removeAll();
        jPanel10.setLayout(new GridLayout(6, 5, 5, 5));

        List<LichTrinh> selectedLichTrinhList = jRadioButton1.isSelected() ? lichTrinhDi : lichTrinhVe;

        if (selectedTrainIndex >= 0 && selectedCoachIndex >= 0 
                && selectedLichTrinhList != null && toaDao != null && choNgoiDao != null) {
            LichTrinh lt = selectedLichTrinhList.get(selectedTrainIndex);
            List<ToaTau> toas = toaDao.getToaByChuyenTau(lt.getMaChuyenTau());
            if (toas != null && selectedCoachIndex < toas.size()) {
                ToaTau selectedToa = toas.get(selectedCoachIndex);
                List<ChoNgoi> chos = choNgoiDao.getByToa(selectedToa.getMaToa());

                Color seatColor = getSeatColor(selectedToa.getLoaiToa());

                int totalSeats = chos != null ? chos.size() : 0;
                int rows = (int) Math.ceil(totalSeats / 4.0); // 4 ghế / row (2 bên)

                for (int row = 0; row < rows; row++) {
                    for (int col = 0; col < 2; col++) { // 2 ghế bên trái
                        int seatNumber = row * 4 + col;
                        JButton seatBtn = createSeatButton(seatNumber, lt, chos, seatColor);
                        jPanel10.add(seatBtn);
                    }

                    JLabel space = new JLabel();
                    space.setPreferredSize(new Dimension(40, 40));
                    jPanel10.add(space);

                    for (int col = 2; col < 4; col++) { // 2 ghế bên phải
                        int seatNumber = row * 4 + col;
                        JButton seatBtn = createSeatButton(seatNumber, lt, chos, seatColor);
                        jPanel10.add(seatBtn);
                    }
                }
            }
        } else {
            // Nếu chưa chọn chuyến hoặc toa -> hiển thị ghế vô hiệu hóa
            for (int i = 0; i < 6 * 4; i++) {
                JButton seatBtn = new JButton(String.valueOf(i + 1));
                seatBtn.setPreferredSize(new Dimension(40, 40));
                seatBtn.setEnabled(false);
                jPanel10.add(seatBtn);

                if ((i + 1) % 2 == 0) {
                    JLabel space = new JLabel();
                    space.setPreferredSize(new Dimension(40, 40));
                    jPanel10.add(space);
                }
            }
        }

        jPanel10.revalidate();
        jPanel10.repaint();
    }

    // ------------------ HÀM HỖ TRỢ ------------------
    private JButton createSeatButton(int seatIndex, LichTrinh lt, List<ChoNgoi> chos, Color seatColor) {
        JButton seatBtn = new JButton(String.valueOf(seatIndex + 1));
        seatBtn.setPreferredSize(new Dimension(40, 40));
        boolean isEnabled = seatIndex < chos.size();
        int status = 0;
        if (isEnabled) {
            ChoNgoi cn = chos.get(seatIndex);
            status = veDao.isChoSold(lt.getMaLichTrinh(), cn.getMaChoNgoi()) ? 1 : 0;
        }
        final int index = seatIndex;
        seatBtn.addActionListener(e -> {
            selectedSeatIndex = index;
            setupSeatGrid();
        });
        setSeatColor(seatBtn, status, index == selectedSeatIndex, seatColor);
        seatBtn.setEnabled(isEnabled);
        return seatBtn;
    }

    private Color getCoachColor(String loaiToa) {
        switch (loaiToa) {
            case "GN": return new Color(0, 0, 139);   // giường nằm
            case "GC": return new Color(255, 215, 0); // ngồi cứng
            case "GM": return new Color(144, 238, 144); // ngồi mềm
            default: return new Color(70, 130, 180);
        }
    }

    private Color getSeatColor(String loaiToa) {
        switch (loaiToa) {
            case "GM": return new Color(255, 200, 100);
            case "GC": return new Color(0, 100, 0);
            case "GN4": return new Color(100, 150, 255);
            case "GN6": return new Color(150, 150, 150);
            case "TV": return new Color(128, 0, 128);
            default: return new Color(220, 220, 220);
        }
    }
    

    private void setSeatColor(JButton seatBtn, int status, boolean isSelected, Color seatColor) {
        if (isSelected) {
            seatBtn.setBackground(new Color(100, 200, 100));
            seatBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // viền mỏng cho đang chọn
        } else if (status == 1) {
            seatBtn.setBackground(new Color(255, 100, 100));
            seatBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // viền mỏng cho đầy
        } else {
            seatBtn.setBackground(new Color(255, 255, 255)); // trắng cho chỗ trống
            seatBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // viền đen đậm xung quanh (độ dày 2)
        }
        seatBtn.setForeground(Color.BLACK); // chữ đen để nổi bật trên nền trắng
        seatBtn.setEnabled(! (status == 1)); // disable nếu đầy
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
    
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private JDateChooser jDateChooser1;
    private JDateChooser jDateChooser2;
    
}