package panel_quanly;

import dao.Dao_KhuyenMai;
import entity.KhuyenMai;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;

import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Panel_KhuyenMai extends JPanel {

	private JTable tableKM;
	private DefaultTableModel modelKM;
	private JComboBox<String> cbLoaiKM, cbTrangThai;
	private JLabel lblSoBanGhi;
	private JTextField txtSearch;
	private Dao_KhuyenMai dao = new Dao_KhuyenMai();
	private List<KhuyenMai> danhSachKM;
	private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public Panel_KhuyenMai() {
		initComponents();
		loadData();
	}

	private void initComponents() {
		setLayout(new BorderLayout(10, 10));
		setBackground(new Color(245, 247, 250));

		JPanel pLeft = new JPanel(new GridBagLayout());
		pLeft.setPreferredSize(new Dimension(300, 0));
		pLeft.setBackground(Color.WHITE);
		pLeft.setBorder(
				new CompoundBorder(new EmptyBorder(15, 15, 15, 15), new LineBorder(new Color(230, 230, 230), 1, true)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 6, 8, 6);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;

		JLabel lblSearch = new JLabel("Tìm kiếm khuyến mãi");
		lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		pLeft.add(lblSearch, gbc);

		txtSearch = new RoundedTextField(20);
		txtSearch.setToolTipText("Nhập mã hoặc mô tả (vd: KM2025101975 / 'Giảm 10% vé')");
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.weightx = 0.7;
		pLeft.add(txtSearch, gbc);

		JButton btnSearch = createButton("Tìm", new Color(0, 123, 255));
		gbc.gridx = 1;
		pLeft.add(btnSearch, gbc);

		JButton btnRefresh = createButton("Làm mới", new Color(108, 117, 125));
		gbc.gridx = 2;
		pLeft.add(btnRefresh, gbc);

		JLabel lblLoai = new JLabel("Loại khuyến mãi");
		lblLoai.setFont(new Font("Segoe UI", Font.BOLD, 13));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 3;
		pLeft.add(lblLoai, gbc);

		cbLoaiKM = new JComboBox<>(new String[] { "Tất cả", "Khuyến mãi cố định", "Khuyến mãi theo sự kiện" });
		cbLoaiKM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		gbc.gridy = 3;
		pLeft.add(cbLoaiKM, gbc);

		JLabel lblTrangThai = new JLabel("Trạng thái");
		lblTrangThai.setFont(new Font("Segoe UI", Font.BOLD, 13));
		gbc.gridy = 4;
		pLeft.add(lblTrangThai, gbc);

		cbTrangThai = new JComboBox<>(new String[] { "Tất cả", "Kích hoạt", "Ngừng kích hoạt" });
		cbTrangThai.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		gbc.gridy = 5;
		pLeft.add(cbTrangThai, gbc);

		lblSoBanGhi = new JLabel("Số bản ghi: 0");
		lblSoBanGhi.setFont(new Font("Segoe UI", Font.ITALIC, 12));
		lblSoBanGhi.setForeground(new Color(120, 120, 120));
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.PAGE_END;
		pLeft.add(lblSoBanGhi, gbc);

		cbLoaiKM.addActionListener(e -> filterData());
		cbTrangThai.addActionListener(e -> filterData());
		btnSearch.addActionListener(e -> filterData());
		btnRefresh.addActionListener(e -> loadData());
		txtSearch.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				filterData();
			}
		});

		JPanel pCenter = new JPanel(new BorderLayout(10, 10));
		pCenter.setBackground(Color.WHITE);
		pCenter.setBorder(new EmptyBorder(10, 10, 10, 10));

		JPanel pTop = new JPanel(new BorderLayout());
		pTop.setBackground(new Color(0, 123, 255));
		pTop.setBorder(new EmptyBorder(10, 15, 10, 15));

		JLabel lblTitle = new JLabel("Quản lý khuyến mãi vé tàu");
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblTitle.setForeground(Color.WHITE);

		JButton btnAdd = createButton("Thêm mới", new Color(40, 167, 69));
		btnAdd.setPreferredSize(new Dimension(150, 38));
		pTop.add(lblTitle, BorderLayout.WEST);
		pTop.add(btnAdd, BorderLayout.EAST);

		String[] cols = { "Mã KM", "Tên chương trình", "Loại khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc", "Mức KM",
				"Trạng thái" };
		modelKM = new DefaultTableModel(cols, 0);
		tableKM = new JTable(modelKM);
		tableKM.setRowHeight(28);
		tableKM.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		tableKM.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		tableKM.setSelectionBackground(new Color(230, 240, 255));

		JScrollPane scroll = new JScrollPane(tableKM);
		scroll.setBorder(new LineBorder(new Color(230, 230, 230), 1, true));

		tableKM.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tableKM.getSelectedRow();
				if (row >= 0)
					showDetailForm(row);
			}
		});

		pCenter.add(pTop, BorderLayout.NORTH);
		pCenter.add(scroll, BorderLayout.CENTER);

		add(pLeft, BorderLayout.WEST);
		add(pCenter, BorderLayout.CENTER);

		btnAdd.addActionListener(e -> openAddForm());
	}

	private void loadData() {
		modelKM.setRowCount(0);
		danhSachKM = dao.getAll();
		if (danhSachKM == null)
			return;

		for (KhuyenMai km : danhSachKM) {
			modelKM.addRow(new Object[] { km.getMaKM(), km.getMoTa(), km.getLoaiKM(), km.getNgayApDungStr(),
					km.getNgayKetThucStr(), String.format("%.1f%%", km.getMucKM() * 100),
					km.isTrangThai() ? "Kích hoạt" : "Ngừng kích hoạt" });
		}
		lblSoBanGhi.setText("Số bản ghi: " + danhSachKM.size());
	}

	// ===========================
	// ADD FORM (placeholder + inline validation)
	// ===========================
	private void openAddForm() {
		JDialog d = createFormDialog("Thêm khuyến mãi mới");

		PlaceholderTextField txtMa = new PlaceholderTextField(20, "Tự sinh (vd: KM2025101975)");
		txtMa.setText(KhuyenMai.taoMaTuDong());
		txtMa.setEditable(false);

		PlaceholderTextField txtMoTa = new PlaceholderTextField(20, "Ví dụ: Giảm 10% vé cuối tuần");

		// ✅ Dùng JDateChooser để chọn ngày
		JDateChooser dateNgayBD = new JDateChooser();
		dateNgayBD.setDateFormatString("dd/MM/yyyy");

		JDateChooser dateNgayKT = new JDateChooser();
		dateNgayKT.setDateFormatString("dd/MM/yyyy");

		PlaceholderTextField txtMuc = new PlaceholderTextField(20, "Giá trị 0–1 (vd: 0.1)");

		JComboBox<String> cbLoai = new JComboBox<>(new String[] { "Khuyến mãi cố định", "Khuyến mãi theo sự kiện" });
		JCheckBox chkTrangThai = new JCheckBox("Kích hoạt", true);

		JLabel errMoTa = errorLabel();
		JLabel errNgayBD = errorLabel();
		JLabel errNgayKT = errorLabel();
		JLabel errMuc = errorLabel();

		JPanel pFields = new JPanel();
		pFields.setLayout(new BoxLayout(pFields, BoxLayout.Y_AXIS));
		pFields.setBackground(Color.WHITE);

		addFormField(pFields, "Mã khuyến mãi:", txtMa, null);
		addFormField(pFields, "Mô tả:", txtMoTa, errMoTa);
		addFormField(pFields, "Loại khuyến mãi:", cbLoai, null);
		addFormField(pFields, "Ngày bắt đầu:", dateNgayBD, errNgayBD);
		addFormField(pFields, "Ngày kết thúc:", dateNgayKT, errNgayKT);

		JPanel mucPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
		mucPanel.setOpaque(false);
		mucPanel.add(txtMuc);
		JButton btnDec10 = smallButton("-10%");
		JButton btnInc10 = smallButton("+10%");
		mucPanel.add(btnDec10);
		mucPanel.add(btnInc10);
		addFormField(pFields, "Mức KM (0-1):", mucPanel, errMuc);
		addFormField(pFields, "Trạng thái:", chkTrangThai, null);

		JButton btnSaVeTau = createButton("Lưu", new Color(40, 167, 69));
		JButton btnCancel = createButton("Hủy", new Color(108, 117, 125));
		JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pButton.add(btnSaVeTau);
		pButton.add(btnCancel);
		pButton.setBackground(Color.WHITE);

		d.add(pFields, BorderLayout.CENTER);
		d.add(pButton, BorderLayout.SOUTH);

		btnCancel.addActionListener(e -> d.dispose());

		btnDec10.addActionListener(e -> adjustMucKM(txtMuc, -0.1f, errMuc));
		btnInc10.addActionListener(e -> adjustMucKM(txtMuc, 0.1f, errMuc));

		btnSaVeTau.addActionListener(e -> {
			try {
				String moTa = txtMoTa.getText().trim();
				LocalDate ngayBD = dateNgayBD.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate ngayKT = dateNgayKT.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				float muc = Float.parseFloat(txtMuc.getText().trim());

				if (ngayKT.isBefore(ngayBD)) {
					JOptionPane.showMessageDialog(d, "Ngày kết thúc phải sau ngày bắt đầu!");
					return;
				}

				KhuyenMai km = new KhuyenMai(txtMa.getText(), moTa, cbLoai.getSelectedItem().toString(), ngayBD, ngayKT,
						muc, chkTrangThai.isSelected());

				if (dao.insert(km)) {
					JOptionPane.showMessageDialog(d, "Thêm thành công!");
					loadData();
					d.dispose();
				} else {
					JOptionPane.showMessageDialog(d, "Lỗi khi thêm khuyến mãi!");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(d, "Vui lòng nhập đủ thông tin hợp lệ!");
			}
		});

		d.setVisible(true);
	}

	private void showDetailForm(int row) {
		KhuyenMai km = danhSachKM.get(row);
		JDialog d = createFormDialog("Chi tiết khuyến mãi");

		PlaceholderTextField txtMa = new PlaceholderTextField(20, "");
		txtMa.setText(km.getMaKM());
		txtMa.setEditable(false);

		PlaceholderTextField txtMoTa = new PlaceholderTextField(20, "Ví dụ: Giảm 10% vé cuối tuần");
		txtMoTa.setText(km.getMoTa());

		// ✅ Dùng JDateChooser thay vì nhập tay
		JDateChooser dateNgayBD = new JDateChooser();
		dateNgayBD.setDate(java.sql.Date.valueOf(km.getNgayApDung()));
		dateNgayBD.setDateFormatString("dd/MM/yyyy");

		JDateChooser dateNgayKT = new JDateChooser();
		dateNgayKT.setDate(java.sql.Date.valueOf(km.getNgayKetThuc()));
		dateNgayKT.setDateFormatString("dd/MM/yyyy");

		PlaceholderTextField txtMuc = new PlaceholderTextField(20, "Giá trị 0–1 (vd: 0.1)");
		txtMuc.setText(String.valueOf(km.getMucKM()));

		JCheckBox chkTrangThai = new JCheckBox("Kích hoạt", km.isTrangThai());

		JLabel errMoTa = errorLabel();
		JLabel errMuc = errorLabel();

		JPanel pFields = new JPanel();
		pFields.setLayout(new BoxLayout(pFields, BoxLayout.Y_AXIS));
		pFields.setBackground(Color.WHITE);

		addFormField(pFields, "Mã khuyến mãi:", txtMa, null);
		addFormField(pFields, "Mô tả:", txtMoTa, errMoTa);
		addFormField(pFields, "Ngày bắt đầu:", dateNgayBD, null);
		addFormField(pFields, "Ngày kết thúc:", dateNgayKT, null);

		// Mức KM + trợ năng +/-10%
		JPanel mucPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
		mucPanel.setOpaque(false);
		mucPanel.add(txtMuc);
		JButton btnDec10 = smallButton("-10%");
		JButton btnInc10 = smallButton("+10%");
		mucPanel.add(btnDec10);
		mucPanel.add(btnInc10);
		addFormField(pFields, "Mức KM:", mucPanel, errMuc);
		addFormField(pFields, "Trạng thái:", chkTrangThai, null);

		JButton btnUpdateOrSaVeTau = createButton("Cập nhật", new Color(255, 193, 7));
		JButton btnDelete = createButton("Xóa", new Color(220, 53, 69));
		JButton btnClose = createButton("Đóng", new Color(108, 117, 125));

		JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		pButton.add(btnUpdateOrSaVeTau);
		pButton.add(btnDelete);
		pButton.add(btnClose);
		pButton.setBackground(Color.WHITE);

		d.add(pFields, BorderLayout.CENTER);
		d.add(pButton, BorderLayout.SOUTH);

		// 🔄 Nút Lưu / Cập nhật
		btnUpdateOrSaVeTau.addActionListener(e -> {
			if ("Cập nhật".equals(btnUpdateOrSaVeTau.getText())) {
				setEditEnabled(true, txtMoTa, txtMuc, chkTrangThai);
				dateNgayBD.setEnabled(true);
				dateNgayKT.setEnabled(true);
				btnUpdateOrSaVeTau.setText("Lưu");
			} else {
				try {
					km.setMoTa(txtMoTa.getText().trim());
					km.setNgayApDung(dateNgayBD.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					km.setNgayKetThuc(dateNgayKT.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
					km.setMucKM(Float.parseFloat(txtMuc.getText().trim()));
					km.setTrangThai(chkTrangThai.isSelected());

					if (dao.update(km)) {
						JOptionPane.showMessageDialog(d, "Cập nhật thành công!");
						loadData();
						d.dispose();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(d, "Vui lòng chọn ngày hợp lệ!");
				}
			}
		});

		btnClose.addActionListener(e -> d.dispose());
		btnDelete.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(d, "Bạn có chắc chắn muốn xóa khuyến mãi này không?",
					"Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (confirm == JOptionPane.YES_OPTION && dao.delete(km.getMaKM())) {
				JOptionPane.showMessageDialog(d, "Đã xóa khuyến mãi!");
				loadData();
				d.dispose();
			}
		});

		d.setVisible(true);
	}

	private void setEditEnabled(boolean enabled, PlaceholderTextField txtMoTa, PlaceholderTextField txtMuc,
			JCheckBox chkTrangThai) {
		txtMoTa.setEditable(enabled);
		txtMuc.setEditable(enabled);
		chkTrangThai.setEnabled(enabled);
	}

	// ======= Buttons, Helpers =======
	private JButton createButton(String text, Color bg) {
		JButton b = new JButton(text);
		b.setBackground(bg);
		b.setForeground(Color.WHITE);
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 13));
		b.setBorder(new LineBorder(bg.darker(), 1, true));
		b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		b.setPreferredSize(new Dimension(130, 34));
		b.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				b.setBackground(bg.darker());
			}

			public void mouseExited(MouseEvent e) {
				b.setBackground(bg);
			}
		});
		return b;
	}

	private JButton smallButton(String text) {
		JButton b = new JButton(text);
		b.setMargin(new Insets(4, 8, 4, 8));
		b.setFocusPainted(false);
		b.setFont(new Font("Segoe UI", Font.BOLD, 12));
		b.setBackground(new Color(240, 240, 240));
		b.setBorder(new LineBorder(new Color(200, 200, 200), 1, true));
		return b;
	}

	private JLabel errorLabel() {
		JLabel lbl = new JLabel(" ");
		lbl.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		lbl.setForeground(new Color(200, 0, 0));
		return lbl;
	}

	private void addFormField(JPanel parent, String label, JComponent field, JLabel err) {
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.setBackground(Color.WHITE);
		p.setBorder(new EmptyBorder(10, 20, 5, 20));
		JLabel lbl = new JLabel(label);
		lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
		field.setAlignmentX(Component.LEFT_ALIGNMENT);
		p.add(lbl);
		p.add(Box.createVerticalStrut(3));
		p.add(field);
		if (err != null) {
			p.add(Box.createVerticalStrut(2));
			p.add(err);
		}
		parent.add(p);
	}

	private JDialog createFormDialog(String title) {
		JDialog d = new JDialog((Frame) null, title, true);
		d.getContentPane().setLayout(new BorderLayout());
		d.setSize(480, 610);
		d.setLocationRelativeTo(null);
		d.getContentPane().setBackground(Color.WHITE);
		d.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
		return d;
	}

	// ---- Placeholder textfields
	static class PlaceholderTextField extends JTextField {
		private String placeholder;

		public PlaceholderTextField(int columns, String placeholder) {
			super(columns);
			this.placeholder = placeholder;
			setBorder(new CompoundBorder(new LineBorder(new Color(200, 200, 200), 1, true),
					new EmptyBorder(6, 10, 6, 10)));
			setBackground(Color.WHITE);
			setFont(new Font("Segoe UI", Font.PLAIN, 13));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (getText().isEmpty() && placeholder != null && !isFocusOwner()) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setFont(getFont().deriveFont(Font.ITALIC));
				g2.setColor(new Color(150, 150, 150));
				Insets ins = getInsets();
				g2.drawString(placeholder, ins.left + 2, getHeight() / 2 + getFont().getSize() / 2 - 2);
				g2.dispose();
			}
		}
	}

	static class PlaceholderFormattedTextField extends JformattedTextFieldFixed {
		public PlaceholderFormattedTextField(DateFormatter df, String placeholder) {
			super(df, placeholder);
		}
	}

	// JFormattedTextField với placeholder
	static class JformattedTextFieldFixed extends JFormattedTextField {
		private String placeholder;

		public JformattedTextFieldFixed(DateFormatter df, String placeholder) {
			super(df);
			this.placeholder = placeholder;
			setColumns(20);
			setFont(new Font("Segoe UI", Font.PLAIN, 13));
			setBorder(new CompoundBorder(new LineBorder(new Color(200, 200, 200), 1, true),
					new EmptyBorder(6, 10, 6, 10)));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (getText().isEmpty() && placeholder != null && !isFocusOwner()) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setFont(getFont().deriveFont(Font.ITALIC));
				g2.setColor(new Color(150, 150, 150));
				Insets ins = getInsets();
				g2.drawString(placeholder, ins.left + 2, getHeight() / 2 + getFont().getSize() / 2 - 2);
				g2.dispose();
			}
		}
	}

	private PlaceholderFormattedTextField createDateField(String placeholder) {
		DateFormatter df = new DateFormatter(new SimpleDateFormat("dd/MM/yyyy"));
		PlaceholderFormattedTextField txtDate = new PlaceholderFormattedTextField(df, placeholder);
		txtDate.setToolTipText("Nhập ngày theo định dạng dd/MM/yyyy");
		return txtDate;
	}

	// ---- VALIDATION helpers
	private boolean validateMoTa(JTextField tf, JLabel err) {
		String s = tf.getText().trim();
		if (s.isEmpty()) {
			err.setText("Mô tả không được trống.");
			return false;
		}
		if (s.length() < 5) {
			err.setText("Mô tả tối thiểu 5 ký tự.");
			return false;
		}
		err.setText(" ");
		return true;
	}

	private boolean validateDate(JTextField tf, JLabel err) {
		String val = tf.getText().trim();
		try {
			LocalDate.parse(val, FMT);
			err.setText(" ");
			return true;
		} catch (Exception ex) {
			err.setText("Ngày không hợp lệ (định dạng dd/MM/yyyy).");
			return false;
		}
	}

	private boolean validateDateRange(JTextField from, JTextField to, JLabel errTo) {
		try {
			LocalDate f = LocalDate.parse(from.getText().trim(), FMT);
			LocalDate t = LocalDate.parse(to.getText().trim(), FMT);
			if (t.isBefore(f)) {
				errTo.setText("Ngày kết thúc phải sau ngày bắt đầu.");
				return false;
			}
			errTo.setText(" ");
			return true;
		} catch (Exception ignore) {
			return false;
		}
	}

	private boolean validateMucKM(JTextField tf, JLabel err) {
		String s = tf.getText().trim();
		try {
			float v = Float.parseFloat(s);
			if (v < 0 || v > 1) {
				err.setText("Mức KM phải trong khoảng 0–1.");
				return false;
			}
			err.setText(" ");
			return true;
		} catch (Exception ex) {
			err.setText("Mức KM phải là số (vd: 0.1).");
			return false;
		}
	}

	// tăng/giảm 10%
	private void adjustMucKM(JTextField tf, float delta, JLabel err) {
		try {
			float v = tf.getText().trim().isEmpty() ? 0f : Float.parseFloat(tf.getText().trim());
			v = Math.max(0f, Math.min(1f, v + delta));
			tf.setText(String.format("%.1f", v)); // hiển thị 1 chữ số thập phân
			validateMucKM(tf, err);
		} catch (Exception ex) {
			tf.setText("0.0");
			validateMucKM(tf, err);
		}
	}

	// bật/tắt edit mode cho các field
	private void setEditEnabled(boolean enabled, JTextField txtMoTa, JTextField txtNgayBD, JTextField txtNgayKT,
			JTextField txtMuc, JComboBox<String> cbLoai, JCheckBox chkTrangThai, JButton btnDec10, JButton btnInc10) {
		txtMoTa.setEditable(enabled);
		txtNgayBD.setEditable(enabled);
		txtNgayKT.setEditable(enabled);
		txtMuc.setEditable(enabled);
		cbLoai.setEnabled(enabled);
		chkTrangThai.setEnabled(enabled);
		btnDec10.setEnabled(enabled);
		btnInc10.setEnabled(enabled);
	}

	// Doc listener tiện dụng
	static class SimpleDoc implements DocumentListener {
	    private final Runnable r;

	    SimpleDoc(Runnable r) {
	        this.r = r;
	    }

	    @Override
	    public void insertUpdate(DocumentEvent e) {
	        r.run();
	    }

	    @Override
	    public void removeUpdate(DocumentEvent e) { // ✅ sửa chính tả
	        r.run();
	    }

	    @Override
	    public void changedUpdate(DocumentEvent e) {
	        r.run();
	    }
	}

	private void filterData() {
		if (danhSachKM == null)
			return;
		String keyword = txtSearch.getText().toLowerCase().trim();
		String loai = cbLoaiKM.getSelectedItem().toString();
		String tt = cbTrangThai.getSelectedItem().toString();

		List<KhuyenMai> filtered = danhSachKM.stream()
				.filter(km -> km.getMoTa().toLowerCase().contains(keyword)
						|| km.getMaKM().toLowerCase().contains(keyword))
				.filter(km -> loai.equals("Tất cả") || km.getLoaiKM().equalsIgnoreCase(loai))
				.filter(km -> tt.equals("Tất cả") || (tt.equals("Kích hoạt") && km.isTrangThai())
						|| (tt.equals("Ngừng kích hoạt") && !km.isTrangThai()))
				.collect(Collectors.toList());

		modelKM.setRowCount(0);
		for (KhuyenMai km : filtered) {
			modelKM.addRow(new Object[] { km.getMaKM(), km.getMoTa(), km.getLoaiKM(), km.getNgayApDungStr(),
					km.getNgayKetThucStr(), String.format("%.1f%%", km.getMucKM() * 100),
					km.isTrangThai() ? "Kích hoạt" : "Ngừng kích hoạt" });
		}
		lblSoBanGhi.setText("Số bản ghi: " + filtered.size());
	}

	// Giao diện textfield mặc định (bên ngoài form)
	static class RoundedTextField extends JTextField {
		public RoundedTextField(int columns) {
			super(columns);
			setBorder(new CompoundBorder(new LineBorder(new Color(200, 200, 200), 1, true),
					new EmptyBorder(6, 10, 6, 10)));
			setBackground(Color.WHITE);
			setFont(new Font("Segoe UI", Font.PLAIN, 13));
		}
	}

}
