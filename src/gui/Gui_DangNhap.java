/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */

package gui;

import java.awt.Color;
import java.awt.MediaTracker;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

import dao.Dao_Login;
import entity.Login;
import entity.User;

public class Gui_DangNhap extends javax.swing.JFrame {

    private Animator animatorLogin;
    private boolean signIn;

    private swing_login.Background background1;
    private swing_login.Button cmdSignIn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private swing_login.PasswordField txtPass;
    private swing_login.TextField txtUser;
    private javax.swing.JPanel panelLogin;
    private Dao_Login dao;
    private User loggedInUser;


    public Gui_DangNhap() {
    	setTitle("Hệ Thống Bán Vé Ga Tàu");
		setSize(1200, 700); // <- bật lại dòng này
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		// Tạo icon cho ứng dụng
		ImageIcon icon = new ImageIcon(getClass().getResource("/icon/logo.png"));
		if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			setIconImage(icon.getImage());
		}
        initComponents();
        getContentPane().setBackground(new Color(245, 245, 245));
        initAnimation();
        initEnterKey(); // ✅ Thêm xử lý Enter
        dao = new Dao_Login(); // ✅ Khởi tạo DAO ở đây
    }

    // -------------------------------
    // Hiệu ứng chuyển cảnh login
    // -------------------------------
    private void initAnimation() {
        TimingTarget targetLogin = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (signIn) {
                    background1.setAnimate(fraction);
                } else {
                    background1.setAnimate(1f - fraction);
                }
            }

            @Override
            public void end() {
                if (signIn) {
                    // ✅ Sau khi hiệu ứng login xong → mở giao diện bán vé ngay
                    SwingUtilities.invokeLater(() -> {
//                        new TicketAppFrame().setVisible(true);
                        dispose(); // đóng login
                    });
                } else {
                    enableLogin(true);
                    txtUser.grabFocus();
                }
            }
        };

        animatorLogin = new Animator(1500, targetLogin);
        animatorLogin.setResolution(0);
    }

    // ✅ Thêm xử lý phím Enter
    private void initEnterKey() {
        txtUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtPass.requestFocus(); // chuyển xuống ô mật khẩu
                }
            }
        });

        txtPass.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cmdSignIn.doClick(); // tự động đăng nhập
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        background1 = new swing_login.Background();
        panelLogin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmdSignIn = new swing_login.Button();
        txtUser = new swing_login.TextField();
        txtPass = new swing_login.PasswordField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        background1.setLayout(new java.awt.CardLayout());

        panelLogin.setOpaque(false);
        jPanel1.setOpaque(false);
//Icon
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo.png")));

//        Nút đăng nhập
        cmdSignIn.setBackground(new java.awt.Color(0,0,255));
        cmdSignIn.setForeground(Color.WHITE);
        cmdSignIn.setText("Sign In");
        cmdSignIn.setEffectColor(new java.awt.Color(199, 196, 255));
        cmdSignIn.addActionListener(evt -> cmdSignInActionPerformed(evt));

        txtUser.setBackground(new Color(245, 245, 245));
        txtUser.setLabelText("User Name");
        txtUser.setLineColor(new Color(0,0,255));
        txtUser.setSelectionColor(new Color(30,144,255));

        txtPass.setBackground(new Color(245, 245, 245));
        txtPass.setLabelText("Password");
        txtPass.setLineColor(new Color(0,0,255));
        txtPass.setSelectionColor(new Color(30,144,255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdSignIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
                    .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(20)
                    .addComponent(jLabel1)
                    .addGap(20)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30)
                    .addComponent(cmdSignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLoginLayout.createSequentialGroup()
                    .addContainerGap(427, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(428, Short.MAX_VALUE))
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelLoginLayout.createSequentialGroup()
                    .addContainerGap(63, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(233, Short.MAX_VALUE))
        );

        background1.add(panelLogin, "card2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void cmdSignInActionPerformed(java.awt.event.ActionEvent evt) {
        if (!animatorLogin.isRunning()) {
            signIn = true;
            String user = txtUser.getText().trim();
            String pass = String.valueOf(txtPass.getPassword());
            boolean action = true;

            if (user.isEmpty()) {
                txtUser.setHelperText("Vui lòng nhập email đã đăng ký!");
                txtUser.grabFocus();
                action = false;
            }

            if (pass.isEmpty()) {
                txtPass.setHelperText("Vui lòng nhập mật khẩu!");
                if (action) txtPass.grabFocus();
                action = false;
            }

            if (action) {
                try {
                    // ✅ Gọi DAO để kiểm tra đăng nhập
                    Login loginData = new Login(user, pass);
                    User account = dao.login(loginData);

                    if (account != null) {
                        enableLogin(false);
                        animatorLogin.start(); // hiệu ứng login

                        System.out.println("✅ Đăng nhập thành công! Xin chào " + account.getUserName());

                        // ✅ Phân quyền theo Role
                        SwingUtilities.invokeLater(() -> {
                            if (account.getRole() != null) {
                                switch (account.getRole()) {
                                    case QUANLY -> {
                                    	java.awt.EventQueue.invokeLater(() -> new Gui_QuanLy(account).setVisible(true));
//                                        new Gui_QuanLy().setVisible(true);
                                        dispose(); // đóng màn hình đăng nhập
                                    }
                                    case NHANVIEN -> {
                                    	java.awt.EventQueue.invokeLater(() -> new Gui_NhanVien(account).setVisible(true));
                                        dispose();
                                    }
                                    default -> {
                                        javax.swing.JOptionPane.showMessageDialog(this,
                                                "Tài khoản không có quyền hợp lệ!",
                                                "Thông báo",
                                                javax.swing.JOptionPane.WARNING_MESSAGE);
                                    }
                                }
                            } else {
                                javax.swing.JOptionPane.showMessageDialog(this,
                                        "Không xác định được vai trò người dùng!",
                                        "Lỗi",
                                        javax.swing.JOptionPane.ERROR_MESSAGE);
                            }
                        });
                    } else {
                        txtUser.setHelperText("Sai email hoặc mật khẩu!");
                        txtPass.setHelperText("Vui lòng thử lại.");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    txtUser.setHelperText("Lỗi kết nối cơ sở dữ liệu!");
                    txtPass.setHelperText(ex.getMessage());
                }
            }

        }
    }


    private void enableLogin(boolean action) {
        txtUser.setEditable(action);
        txtPass.setEditable(action);
        cmdSignIn.setEnabled(action);
    }

//    public static void main(String args[]) {
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(Gui_DangNhap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//
//        java.awt.EventQueue.invokeLater(() -> new Gui_DangNhap().setVisible(true));
//    }
}
