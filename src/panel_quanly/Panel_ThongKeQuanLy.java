/**
 * @author Nguyễn Thành Long
 */
package panel_quanly;

import dao.Dao_ThongKe;
import entity.LichTrinh;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.List;

public class Panel_ThongKeQuanLy extends javax.swing.JPanel {

    private static final Color COLOR_PRIMARY = new Color(67, 56, 202);
    private static final Color COLOR_SUCCESS = new Color(34, 197, 94);
    private static final Color COLOR_WARNING = new Color(251, 146, 60);
    private static final Color COLOR_BACKGROUND = new Color(249, 250, 251);
    private static final Color COLOR_WHITE = new Color(255, 255, 255);
    private static final Color COLOR_DARK_TEXT = new Color(17, 24, 39);
    private static final Color COLOR_LIGHT_TEXT = new Color(107, 114, 128);
    private static final Color COLOR_BORDER = new Color(209, 213, 219);
    
    // KHAI BÁO: Labels để hiển thị dữ liệu KPI
    private JLabel revenueLabel, passengerLabel, monthlyRevenueLabel;
    private JPanel scheduleContentPanel, notificationPanel, chartPanel;
    
    // KHAI BÁO: Biến cho biểu đồ tuần
    private int currentWeekOffset = 0; // 0 = tuần hiện tại, -1 = tuần trước, +1 = tuần sau
    private JLabel weekLabel;
    
    private final Panel_ThongKeQuanLy dashboardDAO;

    public Panel_ThongKeQuanLy() {
        this.dashboardDAO = new Dao_ThongKe();
        initComponents();
        loadAllData();
    }

    private void initComponents() {
        setBackground(COLOR_BACKGROUND);
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        add(createHeaderPanel(), BorderLayout.NORTH);

        // TẠO BỐ CỤC 2 CỘT CHO PHẦN THÂN
        JPanel mainContentPanel = new JPanel(new GridBagLayout());
        mainContentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // CỘT TRÁI: Panel chứa Thông báo + Biểu đồ
        gbc.gridx = 0;
        gbc.weightx = 0.62;
        gbc.insets = new Insets(0, 0, 0, 16);
        mainContentPanel.add(createLeftPanel(), gbc);

        // CỘT PHẢI: Panel Lịch trình
        gbc.gridx = 1;
        gbc.weightx = 0.38;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainContentPanel.add(createSchedulePanel(), gbc);

        add(mainContentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerWrapper = new JPanel(new BorderLayout(0, 20));
        headerWrapper.setOpaque(false);
        
        JPanel greetingPanel = new JPanel(new BorderLayout());
        greetingPanel.setOpaque(false);
        
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        
        JLabel greeting = new JLabel("XIN CHÀO NGUYỄN THÀNH LONG");
        greeting.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        greeting.setForeground(COLOR_LIGHT_TEXT);
        JLabel title = new JLabel("Dashboard Vận Hành Ga");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(COLOR_DARK_TEXT);
        textPanel.add(greeting);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(title);
        greetingPanel.add(textPanel, BorderLayout.WEST);
        
        // TẠO PANEL KPI: 3 thẻ KPI
        JPanel kpiPanel = new JPanel(new GridLayout(1, 3, 16, 0));
        kpiPanel.setOpaque(false);
        kpiPanel.add(createKPICard("💰", "Doanh thu hôm nay", "Đang tải...", "revenue", COLOR_PRIMARY));
        kpiPanel.add(createKPICard("👥", "Số Lượng Vé đã bán hôm nay", "Đang tải...", "passenger", COLOR_SUCCESS));
        kpiPanel.add(createKPICard("📊", "Doanh thu tháng", "Đang tải...", "monthlyRevenue", COLOR_WARNING));
        
        headerWrapper.add(greetingPanel, BorderLayout.NORTH);
        headerWrapper.add(kpiPanel, BorderLayout.CENTER);
        return headerWrapper;
    }

    private JPanel createKPICard(String icon, String title, String initialValue, String type, Color accentColor) {
        RoundedPanel card = new RoundedPanel(10);
        card.setBackground(COLOR_WHITE);
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JPanel accentLine = new JPanel();
        accentLine.setBackground(accentColor);
        accentLine.setPreferredSize(new Dimension(4, 0));
        card.add(accentLine, BorderLayout.WEST);
        
        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(0, 16, 0, 0));
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        topPanel.setOpaque(false);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(COLOR_LIGHT_TEXT);
        topPanel.add(iconLabel);
        topPanel.add(titleLabel);
        
        JLabel valueLabel = new JLabel(initialValue);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(COLOR_DARK_TEXT);
        
        switch (type) {
            case "revenue": revenueLabel = valueLabel; break;
            case "passenger": passengerLabel = valueLabel; break;
            case "monthlyRevenue": monthlyRevenueLabel = valueLabel; break;
        }
        
        content.add(topPanel);
        content.add(Box.createVerticalStrut(12));
        content.add(valueLabel);
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    // THÊM MỚI: TẠO PANEL BÊN TRÁI (Thông báo + Biểu đồ)
    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        // Panel Thông báo (trên)
        JPanel notificationSection = createNotificationPanel();
        notificationSection.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        // Panel Biểu đồ (dưới)
        JPanel chartSection = createChartPanel();
        
        leftPanel.add(notificationSection);
        leftPanel.add(Box.createVerticalStrut(16));
        leftPanel.add(chartSection);
        
        return leftPanel;
    }

    // THÊM MỚI: TẠO PANEL THÔNG BÁO
    private JPanel createNotificationPanel() {
        RoundedPanel panel = new RoundedPanel(10);
        panel.setBackground(COLOR_WHITE);
        panel.setLayout(new BorderLayout(0, 12));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("🔔 Thông báo nội bộ & Phân công");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(COLOR_DARK_TEXT);

        notificationPanel = new JPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));
        notificationPanel.setOpaque(false);
        notificationPanel.add(new JLabel("Đang tải thông báo..."));

        JScrollPane scrollPane = new JScrollPane(notificationPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // THÊM MỚI: TẠO PANEL BIỂU ĐỒ CỘT THEO TUẦN
    private JPanel createChartPanel() {
        RoundedPanel panel = new RoundedPanel(10);
        panel.setBackground(COLOR_WHITE);
        panel.setLayout(new BorderLayout(0, 16));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header: Tiêu đề + Điều khiển
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("📊 Doanh thu theo tuần");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(COLOR_DARK_TEXT);

        // Panel điều khiển tuần
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        controlPanel.setOpaque(false);

        // Nút Tuần trước
        JButton prevBtn = createControlButton("<-");
        prevBtn.addActionListener(e -> {
            currentWeekOffset--;
            loadWeeklyChart();
        });

        // Label hiển thị tuần hiện tại
        weekLabel = new JLabel("Tuần hiện tại");
        weekLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        weekLabel.setForeground(COLOR_DARK_TEXT);

        // Nút Tuần sau
        JButton nextBtn = createControlButton("->");
        nextBtn.addActionListener(e -> {
            currentWeekOffset++;
            loadWeeklyChart();
        });

        // Nút Hôm nay (quay về tuần hiện tại)
        JButton todayBtn = createControlButton("Hôm nay");
        todayBtn.addActionListener(e -> {
            currentWeekOffset = 0;
            loadWeeklyChart();
        });

        // Nút Calendar (chọn tuần)
        JButton calendarBtn = createControlButton("Chọn Tuần");
        calendarBtn.addActionListener(e -> showWeekPicker());

        controlPanel.add(prevBtn);
        controlPanel.add(weekLabel);
        controlPanel.add(nextBtn);
        controlPanel.add(todayBtn);
        controlPanel.add(calendarBtn);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(controlPanel, BorderLayout.EAST);

        // Panel chứa biểu đồ cột
        chartPanel = new JPanel();
        chartPanel.setOpaque(false);
        chartPanel.setLayout(new BorderLayout());

        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);

        return panel;
    }

    // THÊM MỚI: TẠO NÚT ĐIỀU KHIỂN
    private JButton createControlButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(COLOR_PRIMARY);
        btn.setBackground(COLOR_BACKGROUND);
        btn.setBorder(new EmptyBorder(6, 12, 6, 12));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_PRIMARY);
                btn.setForeground(COLOR_WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(COLOR_BACKGROUND);
                btn.setForeground(COLOR_PRIMARY);
            }
        });
        
        return btn;
    }

    // THÊM MỚI: HIỂN THỊ DIALOG CHỌN TUẦN
    private void showWeekPicker() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn tuần", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(this);

        JPanel content = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        content.setBackground(COLOR_WHITE);

        JLabel label = new JLabel("Nhập số tuần lùi/tiến:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        SpinnerModel model = new SpinnerNumberModel(currentWeekOffset, -52, 52, 1);
        JSpinner spinner = new JSpinner(model);
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setColumns(5);

        JButton confirmBtn = new JButton("Xác nhận");
        confirmBtn.setBackground(COLOR_PRIMARY);
        confirmBtn.setForeground(COLOR_WHITE);
        confirmBtn.setFocusPainted(false);
        confirmBtn.addActionListener(e -> {
            currentWeekOffset = (int) spinner.getValue();
            loadWeeklyChart();
            dialog.dispose();
        });

        content.add(label);
        content.add(spinner);
        content.add(confirmBtn);

        dialog.add(content, BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    // THÊM MỚI: VẼ BIỂU ĐỒ CỘT
    private JPanel createBarChart(double[] data) {
        JPanel chartCanvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                int padding = 40;
                int chartHeight = height - padding - 30;
                int chartWidth = width - padding * 2;

                double maxValue = 0;
                for (double value : data) {
                    if (value > maxValue) maxValue = value;
                }
                if (maxValue == 0) maxValue = 1000000;
                
                String[] days = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
                int barWidth = chartWidth / 7 - 10;

              for (int i = 0; i < 7; i++) {
                    int x = padding + i * (chartWidth / 7) + 5;
                    int barHeight = (int) ((data[i] / maxValue) * chartHeight);
                    int y = padding + chartHeight - barHeight;

                    // Vẽ cột
                    g2.setColor(COLOR_PRIMARY);
                    g2.fillRoundRect(x, y, barWidth, barHeight, 8, 8);

                    // Vẽ nhãn ngày
                    g2.setColor(COLOR_DARK_TEXT);
                    g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
                    FontMetrics fm = g2.getFontMetrics();
                    int labelX = x + (barWidth - fm.stringWidth(days[i])) / 2;
                    g2.drawString(days[i], labelX, height - 10);

                    // Vẽ giá trị
                    if (data[i] > 0) {
                        String valueText = new DecimalFormat("#,###").format(data[i] / 1000) + "k";
                        g2.setFont(new Font("Segoe UI", Font.PLAIN, 10));
                        fm = g2.getFontMetrics();
                        int valueX = x + (barWidth - fm.stringWidth(valueText)) / 2;
                        g2.drawString(valueText, valueX, y - 5);
                    }
                }

                g2.dispose();
            }
        };
        chartCanvas.setOpaque(false);
        chartCanvas.setPreferredSize(new Dimension(0, 280));
        return chartCanvas;
    }

    private JPanel createSchedulePanel() {
        RoundedPanel panel = new RoundedPanel(10);
        panel.setBackground(COLOR_WHITE);
        panel.setLayout(new BorderLayout(0, 16));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Lịch trình sắp khởi hành");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(COLOR_DARK_TEXT);
        title.setBorder(new EmptyBorder(0, 0, 4, 0));

        scheduleContentPanel = new JPanel();
        scheduleContentPanel.setLayout(new BoxLayout(scheduleContentPanel, BoxLayout.Y_AXIS));
        scheduleContentPanel.setOpaque(false);
        scheduleContentPanel.add(new JLabel("Đang tải lịch trình..."));

        JScrollPane scrollPane = new JScrollPane(scheduleContentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createTrainCard(LichTrinh schedule) {
        RoundedPanel card = new RoundedPanel(8);
        card.setBackground(COLOR_BACKGROUND);
        card.setLayout(new BorderLayout(12, 0));
        card.setBorder(new EmptyBorder(12, 12, 12, 12));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        JLabel trainName = new JLabel(schedule.getTrainName());
        trainName.setFont(new Font("Segoe UI", Font.BOLD, 15));
        trainName.setForeground(COLOR_DARK_TEXT);

        JLabel route = new JLabel(schedule.getDeparture() + " → " + schedule.getDestination());
        route.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        route.setForeground(COLOR_LIGHT_TEXT);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        JLabel dateTimeLabel = new JLabel(schedule.getDate().format(dateFormatter) + " - " + schedule.getTime().format(timeFormatter));
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateTimeLabel.setForeground(COLOR_LIGHT_TEXT);

        content.add(trainName);
        content.add(Box.createVerticalStrut(5));
        content.add(route);
        content.add(Box.createVerticalStrut(5));
        content.add(dateTimeLabel);
        
        card.add(content, BorderLayout.CENTER);
        return card;
    }

    // THÊM MỚI: TẠO THẺ THÔNG BÁO
    private JPanel createNotificationCard(String message) {
        JPanel card = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        card.setOpaque(false);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel icon = new JLabel("•");
        icon.setFont(new Font("Segoe UI", Font.BOLD, 16));
        icon.setForeground(COLOR_PRIMARY);

        JLabel text = new JLabel(message);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        text.setForeground(COLOR_DARK_TEXT);

        card.add(icon);
        card.add(text);

        return card;
    }

    // TẢI DỮ LIỆU: Load tất cả dữ liệu
    private void loadAllData() {
        new SwingWorker<Map<String, Object>, Void>() {
            @Override
            protected Map<String, Object> doInBackground() throws Exception {
                Map<String, Object> results = new HashMap<>();
                results.put("revenue", dashboardDAO.getDailyRevenue());
                results.put("passengers", dashboardDAO.getPassengerCount());
                results.put("monthlyRevenue", dashboardDAO.getMonthlyRevenue());
                results.put("schedules", dashboardDAO.getUpcomingSchedules(5));
                results.put("notifications", dashboardDAO.getRecentNotifications(5));
                return results;
            }

            @Override
            protected void done() {
                try {
                    Map<String, Object> results = get();
                    
                    revenueLabel.setText(new DecimalFormat("#,### VNĐ").format(results.get("revenue")));
                    passengerLabel.setText(results.get("passengers") + " vé");
                    monthlyRevenueLabel.setText(new DecimalFormat("#,### VNĐ").format(results.get("monthlyRevenue")));
                    
                    // Cập nhật lịch trình
                    List<LichTrinh> schedules = (List<LichTrinh>) results.get("schedules");
                    scheduleContentPanel.removeAll();
                    if (schedules.isEmpty()) {
                        scheduleContentPanel.add(new JLabel("Không có lịch trình mới."));
                    } else {
                        for (LichTrinh schedule : schedules) {
                            scheduleContentPanel.add(createTrainCard(schedule));
                            scheduleContentPanel.add(Box.createVerticalStrut(10));
                        }
                    }
                    scheduleContentPanel.revalidate();
                    scheduleContentPanel.repaint();

                    // THÊM MỚI: Cập nhật thông báo
                    List<String> notifications = (List<String>) results.get("notifications");
                    notificationPanel.removeAll();
                    if (notifications.isEmpty()) {
                        notificationPanel.add(new JLabel("Không có thông báo mới."));
                    } else {
                        for (String notification : notifications) {
                            notificationPanel.add(createNotificationCard(notification));
                        }
                    }
                    notificationPanel.revalidate();
                    notificationPanel.repaint();

                    // THÊM MỚI: Load biểu đồ tuần
                    loadWeeklyChart();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    // THÊM MỚI: TẢI DỮ LIỆU BIỂU ĐỒ TUẦN
    private void loadWeeklyChart() {
        new SwingWorker<double[], Void>() {
            @Override
            protected double[] doInBackground() throws Exception {
                return dashboardDAO.getWeeklyRevenue(currentWeekOffset);
            }

            @Override
            protected void done() {
                try {
                    double[] weekData = get();
                    
                    // Cập nhật label tuần
                    if (currentWeekOffset == 0) {
                        weekLabel.setText("Tuần hiện tại");
                    } else if (currentWeekOffset < 0) {
                        weekLabel.setText("Tuần trước (" + Math.abs(currentWeekOffset) + ")");
                    } else {
                        weekLabel.setText("Tuần sau (" + currentWeekOffset + ")");
                    }
                    
                    // Vẽ lại biểu đồ
                    chartPanel.removeAll();
                    chartPanel.add(createBarChart(weekData), BorderLayout.CENTER);
                    chartPanel.revalidate();
                    chartPanel.repaint();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }

    private class RoundedPanel extends JPanel {
        private int cornerRadius;
        public RoundedPanel(int radius) { super(); this.cornerRadius = radius; setOpaque(false); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
            g2.setColor(COLOR_BORDER);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);
            g2.dispose();
        }
    }
}