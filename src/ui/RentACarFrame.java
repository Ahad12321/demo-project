package ui;

import model.Car;
import model.Rental;
import service.CarRentalManager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Rent a Car masaüstü tətbiqinin əsas istifadəçi interfeysi pəncərəsi (JFrame).
 */
public class RentACarFrame extends JFrame {
    private final CarRentalManager manager;

    // Cədvəl komponentləri
    private JTable carTable;
    private DefaultTableModel tableModel;

    // Yeni maşın əlavə etmə xanaları
    private JTextField txtBrand;
    private JTextField txtModel;
    private JTextField txtPrice;

    // Kirayələmə xanaları
    private JTextField txtCustomerName;
    private JTextField txtCustomerPhone;
    private JTextField txtDays;

    public RentACarFrame() {
        this.manager = new CarRentalManager();
        initUI();
    }

    private void initUI() {
        setTitle("Rent a Car - Maşın Kirayəsi Sistemi");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın mərkəzində açılsın
        setLayout(new BorderLayout(10, 10));

        // Əsas panelləri pəncərəyə yerləşdiririk
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        // Cədvəli ilkin məlumatlarla doldururuq
        refreshTable();
    }

    /**
     * ÜST HİSSƏ: Başlıq və yeni maşın əlavə etmək üçün forma paneli.
     */
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel lblTitle = new JLabel("Maşın Kirayəsi ", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(30, 45, 80));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 205)),
                "Yeni Avtomobil Əlavə Et"
        ));

        formPanel.add(new JLabel("Marka:"));
        txtBrand = new JTextField(9);
        formPanel.add(txtBrand);

        formPanel.add(new JLabel("Model:"));
        txtModel = new JTextField(9);
        formPanel.add(txtModel);

        formPanel.add(new JLabel("Günlük Qiymət (AZN):"));
        txtPrice = new JTextField(7);
        formPanel.add(txtPrice);

        JButton btnAddCar = new JButton("Maşın Əlavə Et");
        btnAddCar.setBackground(new Color(41, 128, 185));
        btnAddCar.setForeground(Color.WHITE);
        btnAddCar.setFocusPainted(false);
        btnAddCar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAddCar.addActionListener(e -> handleAddCar());
        formPanel.add(btnAddCar);

        topPanel.add(formPanel, BorderLayout.CENTER);
        return topPanel;
    }

    /**
     * MƏRKƏZİ HİSSƏ: Bütün maşınların siyahısı və statusunu göstərən JTable cədvəli.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createTitledBorder("Mövcud Avtomobillər və Status")
        ));

        String[] columnNames = {"ID", "Marka", "Model", "Günlük Qiymət (AZN)", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Xanaların birbaşa redaktə edilməsinin qarşısını alırıq
            }
        };

        carTable = new JTable(tableModel);
        carTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carTable.setRowHeight(26);
        carTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        carTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Mərkəzləşdirilmiş renderer
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        carTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        carTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        carTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(carTable);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        return centerPanel;
    }

    /**
     * ALT HİSSƏ: Seçilmiş maşını kirayələmək və geri qaytarmaq üçün əməliyyatlar paneli.
     */
    private JPanel createBottomPanel() {
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        actionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(180, 190, 205)),
                "Kirayə və Təhvil Əməliyyatları"
        ));

        actionPanel.add(new JLabel("Müştəri Adı:"));
        txtCustomerName = new JTextField(11);
        actionPanel.add(txtCustomerName);

        actionPanel.add(new JLabel("Telefon:"));
        txtCustomerPhone = new JTextField(9);
        actionPanel.add(txtCustomerPhone);

        actionPanel.add(new JLabel("Gün Sayı:"));
        txtDays = new JTextField(4);
        actionPanel.add(txtDays);

        JButton btnRent = new JButton("Kirayələ");
        btnRent.setBackground(new Color(39, 174, 96));
        btnRent.setForeground(Color.WHITE);
        btnRent.setFocusPainted(false);
        btnRent.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRent.addActionListener(e -> handleRentCar());
        actionPanel.add(btnRent);

        JButton btnReturn = new JButton("Maşını Qaytar");
        btnReturn.setBackground(new Color(231, 76, 60));
        btnReturn.setForeground(Color.WHITE);
        btnReturn.setFocusPainted(false);
        btnReturn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnReturn.addActionListener(e -> handleReturnCar());
        actionPanel.add(btnReturn);

        bottomContainer.add(actionPanel, BorderLayout.CENTER);
        return bottomContainer;
    }

    /**
     * Cədvəli məlumat bazasındakı cari avtomobil siyahısı ilə yeniləyir.
     */
    private void refreshTable() {
        tableModel.setRowCount(0); // Əvvəlki sətrləri təmizləyirik
        for (Car car : manager.getAllCars()) {
            Object[] rowData = {
                    car.getId(),
                    car.getBrand(),
                    car.getModel(),
                    String.format("%.2f", car.getDailyPrice()),
                    car.isAvailable() ? "Boşdur" : "Kirayədədir"
            };
            tableModel.addRow(rowData);
        }
    }

    /**
     * Yeni avtomobil əlavə edilməsi hadisəsi.
     */
    private void handleAddCar() {
        String brand = txtBrand.getText().trim();
        String model = txtModel.getText().trim();
        String priceText = txtPrice.getText().trim();

        if (brand.isEmpty() || model.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Zəhmət olmasa bütün sahələri (Marka, Model, Qiymət) doldurun!",
                    "Xəbərdarlıq", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Günlük qiymət 0-dan böyük müsbət ədəd olmalıdır!",
                        "Xəta", JOptionPane.ERROR_MESSAGE);
                return;
            }

            manager.addCar(brand, model, price);
            refreshTable();

            // Giriş xanalarını təmizləyirik
            txtBrand.setText("");
            txtModel.setText("");
            txtPrice.setText("");

            JOptionPane.showMessageDialog(this,
                    "Avtomobil uğurla siyahıya əlavə edildi!",
                    "Məlumat", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Qiymət düzgün rəqəm formatında daxil edilməlidir (məsələn: 45 və ya 60.5)!",
                    "Format Xətası", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Avtomobilin kirayələnməsi hadisəsi.
     */
    private void handleRentCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Zəhmət olmasa əvvəlcə cədvəldən kirayələmək istədiyiniz maşını seçin!",
                    "Seçim Xətası", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int carId = (int) tableModel.getValueAt(selectedRow, 0);
        String customerName = txtCustomerName.getText().trim();
        String customerPhone = txtCustomerPhone.getText().trim();
        String daysText = txtDays.getText().trim();

        if (customerName.isEmpty() || customerPhone.isEmpty() || daysText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Zəhmət olmasa Müştəri adı, Telefon və Gün sayını daxil edin!",
                    "Xəbərdarlıq", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int days = Integer.parseInt(daysText);
            Rental rental = manager.rentCar(carId, customerName, customerPhone, days);

            refreshTable();

            // Xanaları təmizləyirik
            txtCustomerName.setText("");
            txtCustomerPhone.setText("");
            txtDays.setText("");

            JOptionPane.showMessageDialog(this,
                    "Avtomobil uğurla kirayələndi!\n\n" +
                            "Müştəri: " + rental.getCustomer().getFullName() + "\n" +
                            "Əlaqə: " + rental.getCustomer().getPhone() + "\n" +
                            "Kirayə Müddəti: " + rental.getRentalDays() + " gün\n" +
                            "Ümumi Məbləğ: " + String.format("%.2f", rental.getTotalPrice()) + " AZN",
                    "Uğurlu Əməliyyat", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gün sayı müsbət tam ədəd olmalıdır (məsələn: 3)!",
                    "Format Xətası", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Xəta", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Avtomobilin geri qaytarılması hadisəsi.
     */
    private void handleReturnCar() {
        int selectedRow = carTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                    "Zəhmət olmasa cədvəldən təhvil vermək istədiyiniz maşını seçin!",
                    "Seçim Xətası", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int carId = (int) tableModel.getValueAt(selectedRow, 0);
        boolean returned = manager.returnCar(carId);

        if (returned) {
            refreshTable();
            JOptionPane.showMessageDialog(this,
                    "Avtomobil uğurla təhvil alındı və yenidən istifadəyə açıqdır!",
                    "Məlumat", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Seçilmiş avtomobil hazırda kirayədə deyil (artıq boşdur)!",
                    "Məlumat", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
