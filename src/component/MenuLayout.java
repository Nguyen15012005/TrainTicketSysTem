/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package component;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;

public class MenuLayout extends javax.swing.JPanel {

    private boolean show;
    private float alpha;
    private Menu menu1;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    // Thêm tham số isQuanLy
    public MenuLayout(boolean isQuanLy) {
        initComponents(isQuanLy);
        setOpaque(false);
        setVisible(false);
        menu1.addMouseListener(new MouseAdapter() {
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents(boolean isQuanLy) {

        menu1 = new Menu(isQuanLy); // Truyền cờ để xác định menu

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(menu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 49, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
        );
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setColor(new Color(150, 150, 150));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        super.paintComponent(grphcs);
    }

    public Menu getMenu() {
        return menu1;
    }
}
