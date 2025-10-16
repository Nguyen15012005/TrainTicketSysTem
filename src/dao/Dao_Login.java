/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.DatabaseConnection;
import entity.Login;
import entity.User;
import common.Role;

/**
 * Xử lý người dùng (DAO)
 * @author TrungNguyen
 */
public class Dao_Login {

    private final Connection con;

    public Dao_Login() {
        con = DatabaseConnection.getInstance().getConnection();
    }

    // ✅ Đăng nhập
    public User login(Login login) throws SQLException {
        if (login == null || login.getEmail() == null || login.getPassword() == null) {
            return null;
        }

        String sql = """
            SELECT TOP 1 [UserID], [UserName], [Email], [Password], [Role]
            FROM [User]
            WHERE [Email] = ? AND [Password] = ?
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login.getEmail());
            ps.setString(2, login.getPassword());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserID(rs.getInt("UserID"));
                    u.setUserName(rs.getString("UserName"));
                    u.setEmail(rs.getString("Email"));
                    u.setPassword(rs.getString("Password"));
                    String roleStr = rs.getString("Role");
                    if (roleStr != null) {
                        try {
                            u.setRole(Role.valueOf(roleStr.toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            u.setRole(null);
                        }
                    }
                    return u;
                }
            }
        }
        return null; // sai email/mật khẩu
    }

    // ✅ Tạo người dùng mới
    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO [User] (UserName, Email, Password, Role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole() != null ? user.getRole().toString() : "CUSTOMER");
            return ps.executeUpdate() > 0;
        }
    }

    // ✅ Kiểm tra trùng email
    public boolean checkDuplicateEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM [User] WHERE Email = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
