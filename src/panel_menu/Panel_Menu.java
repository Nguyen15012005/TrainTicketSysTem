package panel_menu;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;

import javax.swing.JFrame;

import entity.Menu;
import entity.TaiKhoan;
import event.EventMenuSelected;

public class Panel_Menu extends javax.swing.JPanel {

    private EventMenuSelected event;
    private TaiKhoan loggedInUser;

    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
        listMenu1.addEventMenuSelected(event);
    }

    // Thêm tham số isQuanLy
    public Panel_Menu(boolean isQuanLy, TaiKhoan loggedInUser) {
        this.loggedInUser = loggedInUser;
        initComponents(isQuanLy, loggedInUser);
        listMenu1.setOpaque(false);
        initMenu(isQuanLy);
    }

    private void initMenu(boolean isQuanLy) {
        if (isQuanLy) {
            listMenu1.addItem(new Menu("1", "Trang Tổng Quan", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("2", "Lịch Trình", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("3", "Khuyến Mãi", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("4", "Nhân Viên", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("5", "Tài Khoản", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("6", "Thống Kê", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("7", "Tra Cứu", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("8", "Hỗ Trợ", Menu.MenuType.MENU));
            // Dòng trống
            listMenu1.addItem(new Menu("", "", Menu.MenuType.EMPTY));
            listMenu1.addItem(new Menu("", "", Menu.MenuType.EMPTY));
            listMenu1.addItem(new Menu("", "", Menu.MenuType.EMPTY));
            // Logout
            listMenu1.addItem(new Menu("10", "Đăng Xuất", Menu.MenuType.MENU));
        } else {
            listMenu1.addItem(new Menu("1", "Trang Tổng Quan", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("2", "Bán Vé", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("3", "Đổi Vé", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("4", "Trả Vé", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("5", "Hóa Đơn", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("6", "Khách Hàng", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("7", "Thống Kê", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("8", "Tra Cứu", Menu.MenuType.MENU));
            listMenu1.addItem(new Menu("9", "Hỗ Trợ", Menu.MenuType.MENU));
            // Dòng trống
            listMenu1.addItem(new Menu("", "", Menu.MenuType.EMPTY));
            listMenu1.addItem(new Menu("", "", Menu.MenuType.EMPTY));
            // Logout
            listMenu1.addItem(new Menu("10", "Đăng Xuất", Menu.MenuType.MENU));
        }
    }

    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#1E90FF"), 0, getHeight(), Color.decode("#0000CD"));
        int height = 140;
        Path2D.Float f = new Path2D.Float();
        f.moveTo(0, 0);
        f.curveTo(0, 0, 0, 70, 100, 70);
        f.curveTo(100, 70, getWidth(), 70, getWidth(), height);
        f.lineTo(getWidth(), getHeight());
        f.lineTo(0, getHeight());
        g2.setColor(new Color(0, 0, 255));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(g);
        g2.fill(f);
        super.paintChildren(grphcs);
    }

    private int x;
    private int y;

    public void initMoving(JFrame fram) {
        profile1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                x = me.getX();
                y = me.getY();
            }
        });
        profile1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                fram.setLocation(me.getXOnScreen() - x, me.getYOnScreen() - y);
            }
        });
    }

    // Variables
    private swing_dashboard.ListMenu<String> listMenu1;
    private panel_menu.Panel_Profile profile1;

    private void initComponents(boolean isQuanLy, TaiKhoan loggedInUser) {
        profile1 = new Panel_Profile(loggedInUser);
        listMenu1 = new swing_dashboard.ListMenu<>();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(profile1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE))
        );
    }
}
