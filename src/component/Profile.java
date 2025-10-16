/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package component;

//import entity.User;
import javax.swing.*;
import java.awt.*;

public class Profile extends javax.swing.JPanel {

    private JLabel lblName;
    private JLabel lblRole;
    private swing_dashboard.ImageAvatar pic;

    public Profile() {
        initComponents();
        setOpaque(false);
    }

    private void initComponents() {

        pic = new swing_dashboard.ImageAvatar();
        lblName = new JLabel("Tên nhân viên");
        lblRole = new JLabel("Chức vụ");

        pic.setForeground(new java.awt.Color(245, 245, 245));
        pic.setBorderSize(2);
        pic.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profile.jpg"))); // NOI18N

        // 🎨 Style cho text
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setForeground(new Color(255, 255, 255));
        lblName.setHorizontalAlignment(SwingConstants.CENTER);

        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRole.setForeground(new Color(200, 200, 200));
        lblRole.setHorizontalAlignment(SwingConstants.CENTER);

        // 📐 Layout
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                .addComponent(pic, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(15)
                .addComponent(pic, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addComponent(lblName)
                .addGap(5)
                .addComponent(lblRole)
                .addGap(15)
        );
    }

    // ✅ Phương thức hiển thị thông tin người dùng sau khi đăng nhập
//    public void setUser(User user) {
//        if (user != null) {
//            lblName.setText(user.getUserName());
//            lblRole.setText(user.getRole() != null ? user.getRole().toString() : "Không xác định");
//        }
//    }
}
