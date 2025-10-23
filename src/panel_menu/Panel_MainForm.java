/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package panel_menu;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import panel_nhanvien.Panel_TongQuanNhanVien;

public class Panel_MainForm extends javax.swing.JPanel {

    public Panel_MainForm() {
        initComponents();
        show(new Panel_TongQuanNhanVien());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        header1 = new panel_menu.Panel_Header();
        body = new javax.swing.JPanel();

        setBackground(new java.awt.Color(250, 250, 250));

        body.setOpaque(false);
        body.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(header1, javax.swing.GroupLayout.DEFAULT_SIZE, 809, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(header1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(body, javax.swing.GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public void addEventMenu(ActionListener Event) {
        header1.addEventMenu(Event);
    }

    public void initMoving(JFrame fram) {
        header1.initMoving(fram);
    }

    public void show(Component com) {
        body.removeAll();
        body.add(com);
        body.repaint();
        body.revalidate();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel body;
    private panel_menu.Panel_Header header1;
    // End of variables declaration//GEN-END:variables
}
