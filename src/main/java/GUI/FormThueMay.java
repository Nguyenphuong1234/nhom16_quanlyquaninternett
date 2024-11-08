package GUI;

import DAO.*;
import Class.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.*;

public class FormThueMay extends javax.swing.JFrame {
    private MayDao mayDao;
    private ThueMayDao thueMayDao = new ThueMayDao();
    private ThueMay_DichVuDao thueMayDichVuDao = new ThueMay_DichVuDao();
    private void loadDichVuForMay(int maMay) {
        // Lấy danh sách dịch vụ theo mã thuê máy
        List<ServiceDetails> listDichVu = thueMayDichVuDao.getServiceDetailsByMaThueMay(thueMayDao.getMaThueMayByMaMay(maMay));

        // Tạo model cho bảng dịch vụ
        DefaultTableModel model = (DefaultTableModel) tableDichVu.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ
    if (listDichVu != null && !listDichVu.isEmpty()) { 
            for (ServiceDetails dichVu : listDichVu) {
                String tenDichVu = dichVu.getTenDichVu();
                int soLuong = dichVu.getSoLuong();
                double thanhTien = dichVu.getThanhTien(); // Tính thành tiền

                // Thêm dịch vụ vào bảng
                model.addRow(new Object[]{tenDichVu, soLuong, thanhTien});
            }
    }
    }
    
private void handleMayButtonClick(May may) {
    if (may.getTrangThai().equals("Rảnh")) {
        // Hỏi người dùng có muốn thuê máy này không
        int result = JOptionPane.showConfirmDialog(this, 
            "Bạn có muốn thuê máy " + may.getMaMay() + " không?", 
            "Xác nhận", 
            JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            // Tạo đối tượng ThueMay và thêm vào danh sách
            ThueMay thueMay = new ThueMay();
            thueMay.setMaMay(may.getMaMay());
            thueMay.setMaPhong(may.getMaPhong());
            thueMay.setThoiGianBatDau(new Timestamp(System.currentTimeMillis()));

            // Gọi phương thức add để thêm thuê máy vào danh sách
            thueMayDao.add(thueMay);
            loadMayGroupedByPhong(); 
        }
    } else {
        // Lấy danh sách dịch vụ của máy và hiển thị vào bảng
        loadDichVuForMay(may.getMaMay());
    }
}

    public FormThueMay() {
        initComponents();
        mayDao = new MayDao(); // Khởi tạo mayDao
        loadMayGroupedByPhong(); // Tải máy theo phòng
        
    }


   private void loadMayGroupedByPhong() {
    // Xóa hết các component hiện có trong jPanel1
    jPanel1.removeAll(); 
    Map<String, List<May>> groupedMay = mayDao.getMayGroupedByPhong();

    // Thiết lập layout cho jPanel1
    jPanel1.setLayout(new GridLayout(groupedMay.size(), 1)); // Thay đổi để hiển thị theo hàng

    // Duyệt qua từng phòng và thêm máy vào jPanel1
    for (Map.Entry<String, List<May>> entry : groupedMay.entrySet()) {
        String tenPhong = entry.getKey();
        List<May> mayList = entry.getValue();

        // Tạo một JPanel cho từng phòng
        JPanel phongPanel = new JPanel();
        phongPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10)); // Sử dụng WrapLayout để xuống dòng
        phongPanel.setBorder(BorderFactory.createTitledBorder(tenPhong)); // Tiêu đề phòng

        for (May may : mayList) {
            JButton button = new JButton("<html>Máy: " + may.getMaMay() + "<br/>" + thueMayDao.getThoiGianBatDauByMaMay(may.getMaMay()) + "</html>");
            button.setPreferredSize(new Dimension(150, 80)); // Kích thước nút

            // Thay đổi màu nền của JButton dựa trên trạng thái
            if (may.getTrangThai().equals("Rảnh")) {
                button.setBackground(Color.GREEN); // Màu xanh cho máy rảnh
            } else {
                button.setBackground(Color.RED); // Màu đỏ cho máy không rảnh
            }

            button.setOpaque(true);
            button.setBorderPainted(false);
            button.addActionListener(e -> handleMayButtonClick(may));
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) { // Kiểm tra double click
                        // Gọi phương thức để hiển thị tổng chi phí
                        double tongChiPhi = calculateTotalCost(thueMayDao.getMaThueMayByMaMay(may.getMaMay()), may.getMaMay()); // Tính tổng chi phí với mã thuê máy

                        System.out.println(thueMayDao.getMaThueMayByMaMay(may.getMaMay()));
                        // Hiển thị tổng chi phí
                        FormPayment frm = new FormPayment(thueMayDao.getMaThueMayByMaMay(may.getMaMay()), tongChiPhi, may.getMaMay());
                        frm.setVisible(true);
                    }
                }
            });
            phongPanel.add(button); // Thêm nút vào panel phòng
        }

        jPanel1.add(phongPanel); // Thêm panel phòng vào jPanel1
    }

    jPanel1.revalidate(); // Cập nhật lại giao diện
    jPanel1.repaint();
}

    private int calculateTotalCost(int maThueMay, int maMay) {
    double tongChiPhi = 0;

    long timeDifference = thueMayDao.getKhoangThoiGianByMaMay(maMay);
    System.out.println(timeDifference);
    
    double chiPhiThueMay = getRentalCost(maMay, timeDifference);
    tongChiPhi += chiPhiThueMay; // Cộng chi phí thuê máy

    List<ServiceDetails> listDichVu = thueMayDichVuDao.getServiceDetailsByMaThueMay(maThueMay);
    double anuong = thueMayDichVuDao.calculateTotalServiceCost(listDichVu);
    tongChiPhi += anuong; // Cộng chi phí dịch vụ
        System.out.println((int) Math.round(tongChiPhi));
    return (int) Math.round(tongChiPhi); // Làm tròn và trả về kiểu int
}


    private long calculateTimeDifference(String start, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
            Date startDate = sdf.parse(start);
            Date endDate = sdf.parse(end);
            long diffInMillies = endDate.getTime() - startDate.getTime();
            return TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS); // Chuyển đổi sang giờ
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private double getRentalCost(int maMay, long timeInHours) {
        return mayDao.calculateTotalCostTime(maMay, timeInHours);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        JPanel jPanelDichVu = new JPanel(); // Tạo JPanel cho dịch vụ
        jPanelDichVu.setLayout(new BorderLayout()); // Sử dụng BorderLayout

        // Tiêu đề cho danh sách dịch vụ
        JLabel lblDanhSachDichVu = new JLabel("Danh sách dịch vụ", SwingConstants.CENTER);
        lblDanhSachDichVu.setFont(new Font("Arial", Font.BOLD, 22)); // Đặt font cho tiêu đề
        jPanelDichVu.add(lblDanhSachDichVu, BorderLayout.NORTH); // Thêm tiêu đề vào đầu panel

        // Tạo bảng dịch vụ
         String[] columnNames = {"Dịch vụ", "Số lượng", "Thành tiền"}; // Tên các cột của bảng

        // Khởi tạo DefaultTableModel
        DefaultTableModel model = new DefaultTableModel(columnNames, 0); // Tạo mô hình bảng với tiêu đề
        tableDichVu = new JTable(model); // Tạo bảng với mô hình
        JScrollPane scrollPaneTable = new JScrollPane(tableDichVu); // Thêm bảng vào JScrollPane

        // Đặt chiều cao cho bảng chiếm 80% chiều cao của jPanelDichVu
        scrollPaneTable.setPreferredSize(new Dimension(400, 640)); // 80% của chiều cao 800 là 640

        // Thêm bảng vào panel dịch vụ
        jPanelDichVu.add(scrollPaneTable, BorderLayout.CENTER); // Thêm bảng vào giữa panel

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý thuê máy");

        // Thiết lập layout cho JFrame với GridLayout 1 hàng và 2 cột
        setLayout(new GridLayout(1, 2)); // Chia màn hình thành 2 cột đều

        // Thiết lập layout cho jPanel1
        jPanel1.setLayout(new BoxLayout(jPanel1, BoxLayout.Y_AXIS));

        // Thêm jPanel1 vào JScrollPane
        JScrollPane scrollPane = new JScrollPane(jPanel1);
        
        // Thêm JScrollPane vào JFrame
        add(scrollPane); // jPanel1 chiếm 50%

        // Thêm jPanelDichVu vào JFrame
        add(jPanelDichVu); // jPanelDichVu chiếm 50%

        setSize(800, 800); // Đặt kích thước cho JFrame
        setLocationRelativeTo(null); // Đặt vị trí giữa màn hình
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormThueMay().setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel jPanel1;
    private JTable tableDichVu;
    // End of variables declaration  
}
