/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package main;

import gui.Gui_DangNhap;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Thiết lập giao diện Nimbus (nếu có)
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Gui_DangNhap.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Mở form đăng nhập
        java.awt.EventQueue.invokeLater(() -> new Gui_DangNhap().setVisible(true));
    }
}
