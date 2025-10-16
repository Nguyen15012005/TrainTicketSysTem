/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package gui;

import component.MenuLayout;
import entity.User;
import event.EventMenuSelected;

import java.awt.BorderLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;
import panelNhanVien.BanVe;
import panelNhanVien.DoiVe;
import panelNhanVien.HoaDon;
import panelNhanVien.KhachHang;
import panelNhanVien.MainForm;
import panelNhanVien.ThongKeNhanVien;
import panelNhanVien.TongQuanNhanVien;
import panelNhanVien.TraCuuNhanVien;
import panelNhanVien.TraVe;
import swing_dashboard.WindowSnapshots;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import panelQuanLy.KhuyenMai;
import panelQuanLy.LichTrinh;
import panelQuanLy.NhanVien;
import panelQuanLy.TaiKhoan;
import panelQuanLy.ThongKeQuanLy;
import panelQuanLy.TongQuanQuanLy;
import panelQuanLy.TraCuuQuanLy;

public class Gui_QuanLy extends javax.swing.JFrame {

    private final MigLayout layout;
    private final MainForm main;
    private final MenuLayout menu;
    private final Animator animator;
    private final WindowSnapshots windowSnapshots;
    private User loggedInUser;

    public Gui_QuanLy(User user) {
        setTitle("Hệ Thống Bán Vé Ga Tàu - Quản Lý");
        setSize(1200, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon/logo.png"));
        if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            setIconImage(icon.getImage());
        }

        initComponents();
        this.loggedInUser = user;
        layout = new MigLayout("fill", "0[fill]0", "0[fill]0");
        main = new MainForm();
        menu = new MenuLayout(true, loggedInUser); // true = Menu Quản Lý
        windowSnapshots = new WindowSnapshots(Gui_QuanLy.this);

        menu.getMenu().initMoving(Gui_QuanLy.this);
        main.initMoving(Gui_QuanLy.this);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(main);

        JPanel glassPanel = new JPanel(layout);
        glassPanel.setOpaque(false);
        glassPanel.add(menu, "pos -215 0 100% 100%");
        setGlassPane(glassPanel);

        animator = new Animator(350, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float x = fraction * 215;
                float alpha;
                if (menu.isShow()) {
                    x = -x;
                    alpha = 0.5f - fraction / 2;
                } else {
                    x -= 215;
                    alpha = fraction / 2;
                }
                layout.setComponentConstraints(menu, "pos " + (int) x + " 0 100% 100%");
                if (alpha < 0) alpha = 0;
                menu.setAlpha(alpha);
                menu.revalidate();
            }

            @Override
            public void begin() {
                getGlassPane().setVisible(true);
                windowSnapshots.createSnapshot();
                getContentPane().setVisible(false);
            }

            @Override
            public void end() {
                menu.setShow(!menu.isShow());
                if (!menu.isShow()) menu.setVisible(false);
                windowSnapshots.removeSnapshot();
                getContentPane().setVisible(true);
            }
        });

        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me) && !animator.isRunning() && menu.isShow()) {
                    animator.start();
                }
            }
        });

        main.addEventMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning() && !menu.isShow()) {
                    menu.setVisible(true);
                    animator.start();
                }
            }
        });

        // Chuyển form theo menu
        menu.getMenu().addEventMenuSelected(index -> {
            switch (index) {
                case 0 -> main.show(new TongQuanQuanLy());
                case 1 -> main.show(new LichTrinh());
                case 2 -> main.show(new KhuyenMai());
                case 3 -> main.show(new NhanVien());
                case 4 -> main.show(new TaiKhoan());
                case 5 -> main.show(new ThongKeQuanLy());
                case 6 -> main.show(new TraCuuQuanLy());
                default -> {
                    Gui_DangNhap dangNhap = new Gui_DangNhap();
                    dangNhap.setVisible(true);
                    dispose();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        mainPanel = new javax.swing.JLayeredPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainPanel.setBackground(new java.awt.Color(250, 250, 250));
        mainPanel.setOpaque(true);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1100, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
                mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 645, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(mainPanel)
        );

        pack();
        setLocationRelativeTo(null);
    }

    private javax.swing.JLayeredPane mainPanel;
}
