/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Class.*;
import Utils.FileUtils;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

public class ThueMayDao {
    private static final String THUEMAY_FILE_NAME = "src/main/java/XMLFile/ThueMay.xml"; // XML file name to store ThueMay data
    private List<ThueMay> listThueMay;

    public ThueMayDao() {
        this.listThueMay = readListThueMay();
        if (listThueMay == null) {
            listThueMay = new ArrayList<ThueMay>(); // Initialize with an empty list if no data is found
        }
    }

    /**
     * Save a list of ThueMay objects to the thueMay.xml file
     * 
     * @param thueMayList
     */
    public void writeListThueMay(List<ThueMay> thueMayList) {
        ThueMayXML thueMayXML = new ThueMayXML(); // Create a ThueMayXML object to hold the list
        thueMayXML.setThueMay(thueMayList); // Set the list of ThueMay objects
        FileUtils.writeXMLtoFile(THUEMAY_FILE_NAME, thueMayXML); // Save to XML
    }

    /**
     * Read a list of ThueMay objects from the thueMay.xml file
     * 
     * @return list of ThueMay
     */
    public List<ThueMay> readListThueMay() {
        List<ThueMay> list = new ArrayList<ThueMay>();
        ThueMayXML thueMayXML = (ThueMayXML) FileUtils.readXMLFile(THUEMAY_FILE_NAME, ThueMayXML.class); // Read XML file
        if (thueMayXML != null) {
            list = thueMayXML.getThueMay(); // Retrieve the list of ThueMay objects
        }
        return list;
    }

    /**
     * Add a ThueMay to the listThueMay and save listThueMay to file
     * 
     * @param thueMay
     */
    MayDao md = new MayDao();
    public void add(ThueMay thueMay) {
    // Kiểm tra xem MaMay đã có trong danh sách chưa
    for (ThueMay existingThueMay : listThueMay) {
        if (existingThueMay.getMaMay() == thueMay.getMaMay() && existingThueMay.getThoiGianKetThuc() == null) {
            // Nếu đã có bản ghi với cùng MaMay mà ThoiGianKetThuc chưa có, không cho thêm
            System.out.println("Máy này đang được thuê.");
            return; // Kết thúc phương thức
        }
    }

    int id = 1; // ID mặc định
    if (listThueMay != null && !listThueMay.isEmpty()) {
        // Đặt ID thành số tiếp theo
        id = listThueMay.size() + 1;
    }
    thueMay.setMaThueMay(id); // Đặt ID cho ThueMay mới
    listThueMay.add(thueMay); // Thêm vào danh sách
    writeListThueMay(listThueMay); // Lưu danh sách đã cập nhật vào file
    md.updateTrangThaiMay(thueMay.getMaMay(), "Không rảnh");
}


    /**
     * Edit a ThueMay in the listThueMay and save listThueMay to file
     * 
     * @param thueMay
     */
    public void edit(ThueMay thueMay) {
        for (int i = 0; i < listThueMay.size(); i++) {
            if (listThueMay.get(i).getMaThueMay() == thueMay.getMaThueMay()) {
                // Update the ThueMay details
                listThueMay.get(i).setMaKhachHang(thueMay.getMaKhachHang());
               
                listThueMay.get(i).setThoiGianKetThuc(thueMay.getThoiGianKetThuc());
                listThueMay.get(i).setTongChiPhi(thueMay.getTongChiPhi());
               
                writeListThueMay(listThueMay); // Save the updated list to file
                break;
            }
        }
    }

    /**
     * Delete a ThueMay from listThueMay and save listThueMay to file
     * 
     * @param thueMay
     * @return true if the ThueMay was found and deleted, false otherwise
     */
    public boolean delete(ThueMay thueMay) {
        boolean isFound = false;
        for (int i = 0; i < listThueMay.size(); i++) {
            if (listThueMay.get(i).getMaThueMay() == thueMay.getMaThueMay()) {
                listThueMay.remove(i); // Remove the ThueMay from the list
                isFound = true;
                break;
            }
        }
        if (isFound) {
            writeListThueMay(listThueMay); // Save the updated list to file
            return true;
        }
        return false; // ThueMay not found
    }

    public Integer getMaThueMayByMaMay(int maMay) {
        for (ThueMay thueMay : listThueMay) {
            if (thueMay.getMaMay() == maMay && thueMay.getThoiGianKetThuc() == null) {
                return thueMay.getMaThueMay(); // Trả về MaThueMay nếu tìm thấy
            }
        }
        return null; 
    }
   /**
 * Lấy khoảng thời gian (tính bằng giây) từ thời gian bắt đầu đến thời gian hiện tại cho một máy cụ thể.
 *
 * @param maMay Mã máy cần kiểm tra
 * @return Khoảng thời gian tính bằng giây nếu máy đang thuê, ngược lại trả về -1.
 */
public long getKhoangThoiGianByMaMay(int maMay) {
    for (ThueMay thueMay : listThueMay) {
        if (thueMay.getMaMay() == maMay && thueMay.getThoiGianKetThuc() == null) {
            Date thoiGianBatDau = thueMay.getThoiGianBatDau(); // Giả sử thoiGianBatDau là kiểu Date
            Date thoiGianHienTai = new Date(); // Lấy thời gian hiện tại

          
            // Tính khoảng thời gian giữa thời gian bắt đầu và thời gian hiện tại
            long durationInMillis = thoiGianHienTai.getTime() - thoiGianBatDau.getTime();
            return durationInMillis / (1000); 
        }
    }
    return -1; // Trả về -1 nếu không tìm thấy máy thỏa mãn điều kiện
}

/**
 * Lấy danh sách mã máy không có ThoiGianKetThuc.
 *
 * @return Danh sách các mã máy không có thời gian kết thúc.
 */
public List<Integer> getListMaMayTrong() {
    List<Integer> listMaMay = new ArrayList<>();
    for (ThueMay thueMay : listThueMay) {
        if (thueMay.getThoiGianKetThuc() == null) {
            listMaMay.add(thueMay.getMaMay()); // Thêm mã máy vào danh sách nếu không có thời gian kết thúc
        }
    }
    return listMaMay; // Trả về danh sách mã máy không có thời gian kết thúc
}

 /**
     * Lấy thời gian bắt đầu cho một máy cụ thể với điều kiện ThoiGianKetThuc là null.
     *
     * @param maMay Mã máy cần lấy thời gian bắt đầu
     * @return Thời gian bắt đầu dạng "dd/MM/yyyy: hh:mm:ss" nếu máy đang thuê, ngược lại trả về null.
     */
    public String getThoiGianBatDauByMaMay(int maMay) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy: HH:mm:ss");

        for (ThueMay thueMay : listThueMay) {
            // Kiểm tra MaMay và điều kiện ThoiGianKetThuc là null
            if (thueMay.getMaMay() == maMay && thueMay.getThoiGianKetThuc() == null) {
                Date thoiGianBatDau = thueMay.getThoiGianBatDau(); // Lấy thời gian bắt đầu
                return dateFormat.format(thoiGianBatDau); // Trả về thời gian bắt đầu đã được định dạng
            }
        }

        // Nếu không tìm thấy máy hoặc máy không thỏa mãn điều kiện
        return "";
    }
    
      public Map<String, Double> getDoanhThuTheoThang(LocalDate startDate, LocalDate endDate) {
        // Tạo định dạng cho tháng và năm
        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MM/yyyy");

        // Khởi tạo Map để lưu doanh thu theo tháng/năm
        Map<String, Double> doanhThuMap = new HashMap<>();

        // Lọc danh sách ThueMay theo khoảng thời gian từ startDate đến endDate
        List<ThueMay> filteredList = listThueMay.stream()
                .filter(tm -> {
                    LocalDate thoiGianKetThuc = tm.getThoiGianKetThuc().toInstant()
                            .atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    return (thoiGianKetThuc != null && !thoiGianKetThuc.isBefore(startDate) && !thoiGianKetThuc.isAfter(endDate));
                })
                .collect(Collectors.toList());

        // Tính tổng doanh thu theo từng tháng/năm
        for (ThueMay thueMay : filteredList) {
            LocalDate thoiGianKetThuc = thueMay.getThoiGianKetThuc().toInstant()
                    .atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            // Định dạng tháng/năm
            String monthYear = thoiGianKetThuc.format(monthYearFormatter);

            // Tính tổng doanh thu cho từng tháng/năm
            doanhThuMap.put(monthYear, doanhThuMap.getOrDefault(monthYear, 0.0) + thueMay.getTongChiPhi());
        }

        return doanhThuMap;
    }
    public List<ThueMay> getListThueMay() {
        return listThueMay; // Return the list of ThueMay objects
    }

    public void setListThueMay(List<ThueMay> listThueMay) {
        this.listThueMay = listThueMay; // Set the list of ThueMay objects
    }
}
