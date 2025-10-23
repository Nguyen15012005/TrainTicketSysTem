/**
 *
 * @author Nguyễn Nam Trung Nguyên
 */
package panel_quanly;

import chart.ModelChartLine;
import chart.ModelChartPie;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class Panel_TongQuanQuanLy extends javax.swing.JPanel {

    public Panel_TongQuanQuanLy() {
        initComponents();
        initData();
    }

    private void initData() {
        // ======= DỮ LIỆU MẪU CHO BẢNG VÉ =======
        DefaultTableModel model = (DefaultTableModel) table1.getModel();
        Random random = new Random();
        String[] gaDi = {"Sài Gòn", "Hà Nội", "Đà Nẵng", "Huế", "Nha Trang", "Vinh"};
        String[] gaDen = {"Hà Nội", "Đà Nẵng", "Huế", "Nha Trang", "Vinh", "Sài Gòn"};
        String[] trangThai = {"Đã thanh toán", "Chờ xác nhận", "Đã hủy"};

        for (int i = 0; i < 15; i++) {
            String maVeTau = "V" + (1000 + i);
            String tenKH = "Khách " + (i + 1);
            String gaDiStr = gaDi[random.nextInt(gaDi.length)];
            String gaDenStr = gaDen[random.nextInt(gaDen.length)];
            double gia = 200000 + random.nextInt(800000);
            String status = trangThai[random.nextInt(trangThai.length)];
            model.addRow(new Object[]{
                new ImageIcon(getClass().getResource("/icon/train.jpg")),
                maVeTau,
                tenKH,
                gaDiStr + " - " + gaDenStr,
                String.format("%,.0f VNĐ", gia),
                status
            });
        }
        table1.fixTable(jScrollPane1);

        // ======= BIỂU ĐỒ TRÒN (PHẦN TRĂM SỐ LƯỢNG VÉ THEO TUẦN) =======
        List<ModelChartPie> pieData = new ArrayList<>();
        pieData.add(new ModelChartPie("Vé đã bán", 120, new Color(21, 202, 87)));
        pieData.add(new ModelChartPie("Vé chờ thanh toán", 40, new Color(238, 167, 35)));
        pieData.add(new ModelChartPie("Vé đã hủy", 15, new Color(245, 79, 99)));
        chartPie.setModel(pieData);

        // ======= BIỂU ĐỒ ĐƯỜNG (DOANH THU THEO NGÀY TRONG TUẦN) =======
        List<ModelChartLine> lineData = new ArrayList<>();
        lineData.add(new ModelChartLine("Thứ 2", 8_000_000));
        lineData.add(new ModelChartLine("Thứ 3", 10_000_000));
        lineData.add(new ModelChartLine("Thứ 4", 12_000_000));
        lineData.add(new ModelChartLine("Thứ 5", 9_500_000));
        lineData.add(new ModelChartLine("Thứ 6", 14_000_000));
        lineData.add(new ModelChartLine("Thứ 7", 18_000_000));
        lineData.add(new ModelChartLine("Chủ nhật", 16_500_000));
        chartLine1.setModel(lineData);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chartPie = new chart.ChartPie();
        chartLine1 = new chart.ChartLine();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table1 = new swing_dashboard.Table();

        setBackground(new java.awt.Color(250, 250, 250));

        jLabel1.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(66, 66, 66));
        jLabel1.setText("Danh sách vé gần đây");

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Biểu tượng", "Mã vé", "Tên khách hàng", "Hành trình", "Giá vé", "Trạng thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(50);
            table1.getColumnModel().getColumn(5).setPreferredWidth(80);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(chartLine1, javax.swing.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chartPie, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chartLine1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chartPie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private chart.ChartLine chartLine1;
    private chart.ChartPie chartPie;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private swing_dashboard.Table table1;
    // End of variables declaration//GEN-END:variables
}
