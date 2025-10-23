/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package gui;

import entity.TaiKhoan;
import event.EventMenuSelected;

import java.awt.BorderLayout;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;
import panel_menu.Panel_MainForm;
import panel_menu.Panel_MenuLayout;
import panel_nhanvien.Panel_BanVe;
import panel_nhanvien.Panel_DoiVe;
//import panel_nhanvien.Panel_DoiVe;
import panel_nhanvien.Panel_HoaDon;
import panel_nhanvien.Panel_KhachHang;
import panel_nhanvien.Panel_ThongKeNhanVien;
import panel_nhanvien.Panel_TongQuanNhanVien;
import panel_nhanvien.Panel_TraCuuNhanVien;
import panel_nhanvien.Panel_TraVe;
import swing_dashboard.WindowSnapshots;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Gui_NhanVien extends javax.swing.JFrame {

    private final MigLayout layout;
    private final Panel_MainForm main;
    private final Panel_MenuLayout menu;
    private final Animator animator;
    private final WindowSnapshots windowSnapshots;
    private TaiKhoan loggedInUser;

    public Gui_NhanVien(TaiKhoan user) {
        this.loggedInUser = user;
    	setTitle("Hệ Thống Bán Vé Ga Tàu - Nhân Viên");
		setSize(1200, 700); // <- bật lại dòng này
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
        

		// Tạo icon cho ứng dụng
		ImageIcon icon = new ImageIcon(getClass().getResource("/icon/logo.png"));
		if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
			setIconImage(icon.getImage());
		}
        initComponents();
        layout = new MigLayout("fill", "0[fill]0", "0[fill]0");
        main = new Panel_MainForm();
        menu = new Panel_MenuLayout(false, loggedInUser);

        windowSnapshots = new WindowSnapshots(Gui_NhanVien.this);
        menu.getMenu().initMoving(Gui_NhanVien.this);
        main.initMoving(Gui_NhanVien.this);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(main);
        JPanel glassPanel = new JPanel(layout);
        glassPanel.setOpaque(false);
        glassPanel.add(menu, "pos -215 0 100% 100%");
        setGlassPane(glassPanel);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float x = (fraction * 215);
                float alpha;
                if (menu.isShow()) {
                    x = -x;
                    alpha = 0.5f - (fraction / 2);
                } else {
                    x -= 215;
                    alpha = fraction / 2;
                }
                layout.setComponentConstraints(menu, "pos " + (int) x + " 0 100% 100%");
                if (alpha < 0) {
                    alpha = 0;
                }
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
                if (!menu.isShow()) {
                    menu.setVisible(false);
                }
                windowSnapshots.remoVeTauSnapshot();
                getContentPane().setVisible(true);
            }

        };
        animator = new Animator(350, target);
        animator.setDeceleration(0.5f);
        animator.setAcceleration(0.5f);
        animator.setResolution(1);
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    if (!animator.isRunning()) {
                        if (menu.isShow()) {
                            animator.start();
                        }
                    }
                }
            }
        });
        main.addEventMenu(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    if (!menu.isShow()) {
                        menu.setVisible(true);
                        animator.start();
                    }
                }
            }
        });
//        chuyển các form
        menu.getMenu().addEventMenuSelected(new EventMenuSelected() {
            @Override
            public void selected(int index) {
                if (index == 0) {
                    main.show(new Panel_TongQuanNhanVien());
                } else if (index == 1) {
                    main.show(new Panel_BanVe());
                }else if (index == 2) {
                    main.show(new Panel_DoiVe());
                }
                else if (index == 3) {
                    main.show(new Panel_TraVe());
                }
                else if (index == 4) {
                    main.show(new Panel_HoaDon());
                }
                else if (index == 5) {
                    main.show(new Panel_TraCuuNhanVien());
                }
                else if (index == 6) {
                    main.show(new Panel_KhachHang());
                }
                else if (index == 7) {
                    main.show(new Panel_ThongKeNhanVien());
                }
                else {
                	Gui_DangNhap dangNhap = new Gui_DangNhap();
                	dangNhap.setVisible(true);
                	dispose();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
