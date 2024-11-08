/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;



import java.util.ArrayList;
import java.util.List;

import Class.*;
import Utils.FileUtils;
/**
 * KhachHangDao class
 * 
 */
public class KhachHangDao {
    private static final String KHACH_HANG_FILE_NAME = "src/main/java/XMLFile/KhachHang.xml";
    private List<KhachHang> listKhachHang;

    public KhachHangDao() {
        this.listKhachHang = readListKhachHang();
        if (listKhachHang == null) {
            listKhachHang = new ArrayList<KhachHang>();
        }
    }

    /**
     * Save a list of KhachHang objects to the khachhang.xml file
     * 
     * @param khachHangs
     */
    public void writeListKhachHang(List<KhachHang> khachHangs) {
        KhachHangXML khachHangXML = new KhachHangXML();
        khachHangXML.setKhachHang(khachHangs);
        FileUtils.writeXMLtoFile(KHACH_HANG_FILE_NAME, khachHangXML);
    }

    /**
     * Read a list of KhachHang objects from the khachhang.xml file
     * 
     * @return list of KhachHang
     */
    public List<KhachHang> readListKhachHang() {
        List<KhachHang> list = new ArrayList<KhachHang>();
        KhachHangXML khachHangXML = (KhachHangXML) FileUtils.readXMLFile(
                KHACH_HANG_FILE_NAME, KhachHangXML.class);
        if (khachHangXML != null) {
            list = khachHangXML.getKhachHang();
        }
        return list;
    }

    /**
     * Add a KhachHang to the listKhachHang and save listKhachHang to file
     * 
     * @param khachHang
     */
    public boolean add(KhachHang khachHang) {
       try{
            int id = 1; // Default ID
        if (listKhachHang != null && !listKhachHang.isEmpty()) {
            // Set ID to the next available number
            id = listKhachHang.size() + 1;
        }
        khachHang.setMaKhachHang(id); // Set the ID for the new KhachHang
        listKhachHang.add(khachHang); // Add to the list
        writeListKhachHang(listKhachHang); // Save the list to file
        return true;
       }catch(Exception ex){
           return false;
       }
    }

    /**
     * Edit a KhachHang in the listKhachHang and save listKhachHang to file
     * 
     * @param khachHang
     */
    public boolean edit(KhachHang khachHang) {
        for (int i = 0; i < listKhachHang.size(); i++) {
            if (listKhachHang.get(i).getMaKhachHang() == khachHang.getMaKhachHang()) {
                // Update the customer details
                listKhachHang.get(i).setTenKhachHang(khachHang.getTenKhachHang());
                listKhachHang.get(i).setSoDienThoai(khachHang.getSoDienThoai());
                listKhachHang.get(i).setEmail(khachHang.getEmail());
                listKhachHang.get(i).setDiemTichLuy(khachHang.getDiemTichLuy());
                writeListKhachHang(listKhachHang); // Save the updated list to file
                return true;
            }
        }
        return false;
    }

    /**
     * Delete a KhachHang from listKhachHang and save listKhachHang to file
     * 
     * @param khachHang
     * @return true if the customer was found and deleted, false otherwise
     */
    public boolean delete(KhachHang khachHang) {
        boolean isFound = false;
        for (int i = 0; i < listKhachHang.size(); i++) {
            if (listKhachHang.get(i).getMaKhachHang() == khachHang.getMaKhachHang()) {
                listKhachHang.remove(i); // Remove the customer from the list
                isFound = true;
                break;
            }
        }
        if (isFound) {
            writeListKhachHang(listKhachHang); // Save the updated list to file
            return true;
        }
        return false; // Customer not found
    }

    /**
 * Lấy điểm tích lũy của một khách hàng dựa vào mã khách hàng
 * 
 * @param maKhachHang mã khách hàng
 * @return điểm tích lũy nếu mã khách hàng tồn tại, ngược lại trả về false
 */
public Object getDiemTichLuyByMaKhachHang(int maKhachHang) {
    for (KhachHang khachHang : listKhachHang) {
        if (khachHang.getMaKhachHang() == maKhachHang) {
            return khachHang.getDiemTichLuy(); // Trả về điểm tích lũy nếu tìm thấy
        }
    }
    return false; // Trả về false nếu mã khách hàng không tồn tại
}

/**
 * Cộng điểm tích lũy cho khách hàng dựa vào mã khách hàng
 * 
 * @param maKhachHang mã khách hàng
 * @param diemTichLuyToAdd số điểm tích lũy cần cộng thêm
 * @return true nếu việc cộng điểm thành công, false nếu không tìm thấy khách hàng
 */
public boolean addDiemTichLuy(int maKhachHang, int diemTichLuyToAdd) {
    for (KhachHang khachHang : listKhachHang) {
        if (khachHang.getMaKhachHang() == maKhachHang) {
            // Cộng điểm tích lũy
            khachHang.setDiemTichLuy(khachHang.getDiemTichLuy() + diemTichLuyToAdd);
            writeListKhachHang(listKhachHang); // Lưu danh sách đã cập nhật vào file
            return true; // Cộng điểm thành công
        }
    }
    return false; // Không tìm thấy khách hàng
}

    public List<KhachHang> getListKhachHang() {
        return listKhachHang; // Return the list of customers
    }

    public void setListKhachHang(List<KhachHang> listKhachHang) {
        this.listKhachHang = listKhachHang; // Set the list of customers
    }
}
