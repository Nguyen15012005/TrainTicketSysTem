package panel_nhanvien;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;

/**
 * In vé tàu KHÔNG dùng JasperReports - Dùng Java2D thuần
 * ✓ Không cần file template
 * ✓ Không cần thư viện JasperReports
 * ✓ Chạy ngay lập tức
 */
public class VeTauPrinter {
    
    /**
     * In vé tàu - Phiên bản đơn giản nhất
     */
    public static void inVeTau(String maVe, String gaDi, String gaDen, String maTau,
                                String ngayDi, String gioDi, String toa, String cho, String hangVe) {
        
        System.out.println("=== BẮT ĐẦU IN VÉ ===");
        System.out.println("Mã vé: " + maVe);
        
        try {
            // Tạo panel vé để in
            VePanel vePanel = new VePanel(maVe, gaDi, gaDen, maTau, ngayDi, gioDi, toa, cho, hangVe);
            
            // Tùy chọn 1: Hiển thị preview trước khi in
            hienThiPreview(vePanel, maVe);
            
            // Tùy chọn 2: In trực tiếp (bỏ comment dòng dưới)
            // inTrucTiep(vePanel);
            
            System.out.println("✓✓✓ THÀNH CÔNG ✓✓✓");
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                "Lỗi khi in vé: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Hiển thị preview vé trước khi in
     */
    private static void hienThiPreview(VePanel vePanel, String maVe) {
        JFrame frame = new JFrame("Xem trước vé - " + maVe);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Thêm nút in
        JButton btnIn = new JButton("🖨 IN VÉ");
        btnIn.setFont(new Font("Arial", Font.BOLD, 16));
        btnIn.setPreferredSize(new Dimension(200, 50));
        btnIn.addActionListener(e -> {
            inTrucTiep(vePanel);
            frame.dispose();
        });
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(btnIn);
        
        // Scroll pane cho vé
        JScrollPane scrollPane = new JScrollPane(vePanel);
        scrollPane.setPreferredSize(new Dimension(650, 900));
        
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * In trực tiếp ra máy in
     */
    private static void inTrucTiep(VePanel vePanel) {
        PrinterJob job = PrinterJob.getPrinterJob();
        
        job.setPrintable(new Printable() {
            @Override
            public int print(Graphics g, PageFormat pf, int page) {
                if (page > 0) return NO_SUCH_PAGE;
                
                Graphics2D g2d = (Graphics2D) g;
                g2d.translate(pf.getImageableX(), pf.getImageableY());
                
                // Scale để fit trang in
                double scaleX = pf.getImageableWidth() / vePanel.getWidth();
                double scaleY = pf.getImageableHeight() / vePanel.getHeight();
                double scale = Math.min(scaleX, scaleY);
                g2d.scale(scale, scale);
                
                vePanel.print(g2d);
                return PAGE_EXISTS;
            }
        });
        
        // Hiển thị hộp thoại chọn máy in
        if (job.printDialog()) {
            try {
                job.print();
                JOptionPane.showMessageDialog(null, 
                    "Đã gửi lệnh in!", 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (PrinterException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                    "Lỗi khi in: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Panel chứa nội dung vé tàu
     */
    static class VePanel extends JPanel {
        private String maVe, gaDi, gaDen, maTau, ngayDi, gioDi, toa, cho, hangVe;
        
        public VePanel(String maVe, String gaDi, String gaDen, String maTau,
                       String ngayDi, String gioDi, String toa, String cho, String hangVe) {
            this.maVe = maVe;
            this.gaDi = gaDi;
            this.gaDen = gaDen;
            this.maTau = maTau;
            this.ngayDi = ngayDi;
            this.gioDi = gioDi;
            this.toa = toa;
            this.cho = cho;
            this.hangVe = hangVe;
            
            setPreferredSize(new Dimension(595, 842)); // A4
            setBackground(Color.WHITE);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int margin = 50;
            int y = 50;
            
            // TIÊU ĐỀ
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.setColor(Color.BLACK);
            drawCenteredString(g2, "ĐƯỜNG SẮT VIỆT NAM", 595, y);
            y += 40;
            
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            drawCenteredString(g2, "THẺ LÊN TÀU HỎA", 595, y);
            y += 30;
            
            g2.setFont(new Font("Arial", Font.ITALIC, 14));
            drawCenteredString(g2, "BOARDING PASS", 595, y);
            y += 30;
            
            // Đường kẻ
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(margin, y, 595 - margin, y);
            y += 30;
            
            // MÃ VÉ (nổi bật)
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawString("Mã vé / Ticket ID:", margin, y);
            
            g2.setStroke(new BasicStroke(1));
            g2.drawRect(margin + 160, y - 20, 595 - margin * 2 - 160, 30);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            drawCenteredString(g2, maVe, margin + 160, 595 - margin, y);
            y += 50;
            
            // GA ĐI → GA ĐẾN (layout ngang)
            int boxWidth = 150;
            int boxHeight = 40;
            int startX = margin;
            
            // GA ĐI
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawRect(startX, y - 30, 80, boxHeight);
            drawCenteredString(g2, "GA ĐI", startX, startX + 80, y - 10);
            
            g2.drawRect(startX + 80, y - 30, boxWidth, boxHeight);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            drawCenteredString(g2, gaDi, startX + 80, startX + 80 + boxWidth, y - 5);
            
            // MŨI TÊN
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.drawString("→", startX + 240, y - 5);
            
            // GA ĐẾN
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.drawRect(startX + 270, y - 30, 80, boxHeight);
            drawCenteredString(g2, "GA ĐẾN", startX + 270, startX + 350, y - 10);
            
            g2.drawRect(startX + 350, y - 30, boxWidth, boxHeight);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            drawCenteredString(g2, gaDen, startX + 350, startX + 350 + boxWidth, y - 5);
            
            y += 60;
            
            // THÔNG TIN TÀU
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.drawString("Tàu / Train:", margin, y);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.drawString(maTau, margin + 120, y);
            y += 35;
            
            // NGÀY & GIỜ
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.drawString("Ngày đi / Date:", margin, y);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.drawString(ngayDi, margin + 120, y);
            
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.drawString("Giờ / Time:", margin + 300, y);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.drawString(gioDi, margin + 390, y);
            y += 45;
            
            // TOA & CHỖ (nổi bật)
            int boxY = y - 30;
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.drawRect(margin, boxY, 120, 40);
            drawCenteredString(g2, "Toa / Coach", margin, margin + 120, boxY + 25);
            
            g2.drawRect(margin + 120, boxY, 100, 40);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            drawCenteredString(g2, toa, margin + 120, margin + 220, boxY + 28);
            
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.drawRect(margin + 240, boxY, 120, 40);
            drawCenteredString(g2, "Chỗ / Seat", margin + 240, margin + 360, boxY + 25);
            
            g2.drawRect(margin + 360, boxY, 100, 40);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            drawCenteredString(g2, cho, margin + 360, margin + 460, boxY + 28);
            
            y += 60;
            
            // HẠNG VÉ
            g2.setFont(new Font("Arial", Font.BOLD, 13));
            g2.drawString("Hạng / Class:", margin, y);
            g2.setFont(new Font("Arial", Font.PLAIN, 13));
            g2.drawString(hangVe, margin + 120, y);
            y += 50;
            
            // LƯU Ý
            g2.drawRect(margin, y, 595 - margin * 2, 120);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString("LƯU Ý / NOTES:", margin + 10, y + 20);
            
            g2.setFont(new Font("Arial", Font.PLAIN, 11));
            y += 40;
            g2.drawString("- Vui lòng có mặt tại ga trước giờ tàu chạy 30 phút", margin + 10, y);
            y += 18;
            g2.drawString("- Mang theo CCCD/Hộ chiếu khi lên tàu", margin + 10, y);
            y += 18;
            g2.drawString("- Giữ gìn vé cẩn thận, không bẻ gấp", margin + 10, y);
            y += 18;
            g2.drawString("- Please arrive 30 minutes before departure", margin + 10, y);
            y += 18;
            g2.drawString("- Bring ID/Passport when boarding", margin + 10, y);
            
            // CHÂN TRANG
            y = 780;
            g2.setFont(new Font("Arial", Font.ITALIC, 11));
            drawCenteredString(g2, "Chúc quý khách có chuyến đi vui vẻ!", 595, y);
            y += 20;
            g2.setFont(new Font("Arial", Font.ITALIC, 9));
            drawCenteredString(g2, "Hotline: 1900 1000 | www.dsvn.vn", 595, y);
        }
        
        private void drawCenteredString(Graphics2D g, String text, int width, int y) {
            drawCenteredString(g, text, 0, width, y);
        }
        
        private void drawCenteredString(Graphics2D g, String text, int startX, int endX, int y) {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int x = startX + (endX - startX - textWidth) / 2;
            g.drawString(text, x, y);
        }
    }
    
    /**
     * HÀM TEST
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            inVeTau(
                "VT001234",
                "Hà Nội",
                "Sài Gòn",
                "SE1",
                "25/10/2025",
                "19:30",
                "3",
                "15",
                "Giường nằm khoang 4"
            );
        });
    }
}