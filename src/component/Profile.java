package component;

import common.Role;
import javax.swing.*;
import java.awt.*;
import entity.User;

public class Profile extends JPanel {

    private JLabel lblName;
    private JLabel lblRole;
    private swing_dashboard.ImageAvatar pic;

    // Constructor nhận User (có thể null)
    public Profile(User account) {
        initComponents(account);
        setOpaque(false);
    }

    private void initComponents(User account) {
        pic = new swing_dashboard.ImageAvatar();
        lblName = new JLabel(account != null ? account.getUserName() : "Tên nhân viên");
        lblRole = new JLabel(account != null && account.getRole() != null ? getRoleName(account.getRole()) : "Chức vụ");

        pic.setForeground(new Color(245, 245, 245));
        pic.setBorderSize(2);
        pic.setIcon(new ImageIcon(getClass().getResource("/icon/profile.jpg")));

        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblName.setForeground(Color.WHITE);
        lblName.setHorizontalAlignment(SwingConstants.CENTER);

        lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRole.setForeground(new Color(200, 200, 200));
        lblRole.setHorizontalAlignment(SwingConstants.CENTER);

        // Layout
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(pic, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblName, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                .addComponent(lblRole, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(15)
                .addComponent(pic, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
                .addComponent(lblName)
                .addGap(5)
                .addComponent(lblRole)
                .addGap(15)
        );
    }

    // Chuyển enum Role sang tên hiển thị
    private String getRoleName(Role role) {
        switch (role) {
            case NHANVIEN:
                return "Nhân viên";
            case QUANLY:
                return "Quản lý";
            default:
                return "Không xác định";
        }
    }

    // Cập nhật user sau khi tạo panel
    public void setUser(User account) {
        if (account != null) {
            lblName.setText(account.getUserName());
            lblRole.setText(account.getRole() != null ? getRoleName(account.getRole()) : "Không xác định");
        }
    }
}
