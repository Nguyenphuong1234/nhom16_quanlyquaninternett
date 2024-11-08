package GUI;

import java.awt.BorderLayout;
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
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.data.category.DefaultCategoryDataset;
import DAO.*;

public class ThongKeDoanhThu extends JFrame {
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel pnl_chart;
    private JDateChooser dateChooserStart; // Date picker for start date
    private JDateChooser dateChooserEnd;   // Date picker for end date
    ThueMayDao thueMayDao = new ThueMayDao();

    public ThongKeDoanhThu() {
        initComponents();
        initChart();
        setTitle("Thống Kê Doanh Thu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
    }

    public void initChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Initialize the dataset with default values
        for (int thang = 1; thang <= 12; thang++) {
            dataset.addValue(0.0, "Số tiền", "Tháng " + thang);
        }

        // Create chart from dataset
        JFreeChart chart = ChartFactory.createBarChart("Biểu Đồ Doanh Thu", // Title
                "Tháng", // X-axis label
                "Số Tiền", // Y-axis label
                dataset);
        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        // Display chart on panel
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(chartPanel.getPreferredSize().width, 700));
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
        LocalDate startDate = null;
        LocalDate endDate = null;

        Date start = dateChooserStart.getDate();
        Date end = dateChooserEnd.getDate();

        if (start == null || end == null) {
            JOptionPane.showMessageDialog(jButton1, "Vui lòng chọn ngày bắt đầu và ngày kết thúc");
            return;
        }

        startDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        endDate = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (startDate.isAfter(endDate)) {
            JOptionPane.showMessageDialog(jButton1, "Ngày bắt đầu phải trước ngày kết thúc");
            return;
        }

        // Check if both dates are in the same year
        if (startDate.getYear() != endDate.getYear()) {
            JOptionPane.showMessageDialog(jButton1, "Ngày bắt đầu và ngày kết thúc phải cùng năm");
            return;
        }



        // Fetch data based on the date range
        Map<String, Double> doanhThuTheoThang = thueMayDao.getDoanhThuTheoThang(startDate, endDate);

        // Create dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populate dataset with revenue per month/year, setting 0 for missing months
        for (int thang = 1; thang <= 12; thang++) {
            String monthYear = String.format("%02d/%d", thang, startDate.getYear());
            double doanhThu = doanhThuTheoThang.getOrDefault(monthYear, 0.0);
            System.out.println("a" + doanhThu);
            dataset.addValue(doanhThu, "Số tiền", "Tháng " + thang);
        }

        // Create chart from dataset
        JFreeChart chart = ChartFactory.createBarChart("Biểu Đồ Doanh Thu Từ " + startDate + " Đến " + endDate,
                "Tháng", "Số Tiền", dataset);
        chart.getCategoryPlot().getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        // Display chart in pnl_chart
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(chartPanel.getPreferredSize().width, 700));
        pnl_chart.removeAll();
        pnl_chart.setLayout(new BorderLayout());
        pnl_chart.add(chartPanel, BorderLayout.CENTER);
        pnl_chart.validate();
    }

    // Main method to run the application
    public static void main(String[] args) {
        // Create and display the form
        java.awt.EventQueue.invokeLater(() -> {
            new ThongKeDoanhThu().setVisible(true);
        });
    }
}
