/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package component;

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


import entity.Model_Menu;
import entity.User;
import event.EventMenuSelected;

public class Menu extends javax.swing.JPanel {

    private EventMenuSelected event;
    private User loggedInUser;

    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
        listMenu1.addEventMenuSelected(event);
    }

    // Thêm tham số isQuanLy
   public Menu(boolean isQuanLy, User loggedInUser) {
    this.loggedInUser = loggedInUser;
    initComponents(isQuanLy, loggedInUser);
    listMenu1.setOpaque(false);
    initMenu(isQuanLy);
}



    private void initMenu(boolean isQuanLy) {
        
        if (isQuanLy) {
        	listMenu1.addItem(new Model_Menu("1", "Trang Tổng Quan", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("2", "Lịch Trình", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("3", "Khuyến Mãi", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("4", "Nhân Viên", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("5", "Tài Khoản", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("6", "Thống Kê", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("7", "Tra Cứu", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("8", "Hỗ Trợ", Model_Menu.MenuType.MENU));
         // Dòng trống
            listMenu1.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
            listMenu1.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
            listMenu1.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
            // Logout
            listMenu1.addItem(new Model_Menu("10", "Đăng Xuất", Model_Menu.MenuType.MENU));
        } else {
        	listMenu1.addItem(new Model_Menu("1", "Trang Tổng Quan", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("2", "Bán Vé", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("3", "Đổi Vé", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("4", "Trả Vé", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("5", "Hóa Đơn", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("6", "Khách Hàng", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("7", "Thống Kê", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("8", "Tra Cứu", Model_Menu.MenuType.MENU));
            listMenu1.addItem(new Model_Menu("9", "Hỗ Trợ", Model_Menu.MenuType.MENU));
            // Dòng trống
            listMenu1.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
            listMenu1.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
            
            listMenu1.addItem(new Model_Menu("10", "Đăng Xuất", Model_Menu.MenuType.MENU));
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
        g2.setColor(new Color(0,0,255) );
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
    private component.Profile profile1;

    private void initComponents(boolean isQuanLy, User loggedInUser) {
    profile1 = new Profile(loggedInUser);
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
