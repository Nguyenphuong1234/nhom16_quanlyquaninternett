package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.toedter.calendar.JDateChooser; // Import JDateChooser
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import DAO.*;
import java.time.LocalDateTime;

public class ThongKeDichVu extends JFrame {
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnl_chart;
    private JDateChooser dateChooserStart; // Date picker for start date
    private JDateChooser dateChooserEnd;   // Date picker for end date
    DichVuDao thueMayDao = new DichVuDao();

    public ThongKeDichVu() {
        initComponents();
        initChart();
        setTitle("Thống Kê Dịch Vụ");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
    }

    public void initChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Create pie chart with default values (can be updated later)
        JFreeChart chart = ChartFactory.createPieChart("Top Dịch Vụ Được Gọi Nhiều Nhất", // Title
                dataset, // Data
                true, // Show legend
                true, false);

        // Display chart on panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(chartPanel.getPreferredSize().width, 700));
        pnl_chart.removeAll();
        pnl_chart.setLayout(new BorderLayout());
        pnl_chart.add(chartPanel, BorderLayout.CENTER);
        pnl_chart.validate();
    }

    private void initComponents() {
        pnl_chart = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        dateChooserStart = new JDateChooser(); // Initialize date chooser for start date
        dateChooserEnd = new JDateChooser(); // Initialize date chooser for end date

        // Initialize pnl_chart with a default layout
        pnl_chart.setLayout(new BorderLayout());

        jButton1.setText("Thống kê");
        jButton1.addActionListener(evt -> jButton1ActionPerformed(evt));

        jLabel1.setText("Nhập khoảng thời gian:");

        // Main layout using GridBagLayout
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(jLabel1, gbc);

        gbc.gridx = 1;
        inputPanel.add(dateChooserStart, gbc);

        gbc.gridx = 2;
        inputPanel.add(dateChooserEnd, gbc);

        gbc.gridx = 3;
        inputPanel.add(jButton1, gbc);

        // Set layout for the main frame
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(pnl_chart, BorderLayout.CENTER);
    }


private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
    // Validate and parse dates
    LocalDateTime startDateTime = null;
    LocalDateTime endDateTime = null;

    Date start = dateChooserStart.getDate();
    Date end = dateChooserEnd.getDate();

    if (start == null || end == null) {
        JOptionPane.showMessageDialog(jButton1, "Vui lòng chọn ngày bắt đầu và ngày kết thúc");
        return;
    }

    // Convert to LocalDateTime, setting time to start of day for startDate
    startDateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
    // Convert to LocalDateTime, setting time to end of day for endDate
    endDateTime = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(23, 59, 59);

    if (startDateTime.isAfter(endDateTime)) {
        JOptionPane.showMessageDialog(jButton1, "Ngày bắt đầu phải trước ngày kết thúc");
        return;
    }

    // Fetch data based on the date range
    Map<String, Double> serviceStatistics = thueMayDao.getMostPurchasedService(startDateTime, endDateTime);
    
    // Create dataset for pie chart
    DefaultPieDataset dataset = new DefaultPieDataset();
    
    for (Map.Entry<String, Double> entry : serviceStatistics.entrySet()) {
        dataset.setValue(entry.getKey() + ": " + entry.getValue(), entry.getValue());
       
    }

    // Create pie chart from dataset
    JFreeChart chart = ChartFactory.createPieChart("Top Dịch Vụ Được Gọi Nhiều Nhất Từ " + startDateTime + " Đến " + endDateTime,
            dataset, true, true, false);

    // Display chart in pnl_chart
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(chartPanel.getPreferredSize().width, 700));
    pnl_chart.removeAll();
    pnl_chart.setLayout(new BorderLayout());
    pnl_chart.add(chartPanel, BorderLayout.CENTER);
    pnl_chart.validate();
}

    // Main method to run the application
    public static void main(String[] args) {
        // Create and display the form
        java.awt.EventQueue.invokeLater(() -> {
            new ThongKeDichVu().setVisible(true);
        });
    }
}
