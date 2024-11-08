/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import Class.*;
import Utils.FileUtils;
import java.util.HashMap;
import java.util.Map;

public class MayDao {
    private static final String MAY_FILE_NAME = "src/main/java/XMLFile/May.xml"; // XML file name to store May data
    private List<May> listMay;

    public MayDao() {
        this.listMay = readListMay();
        if (listMay == null) {
            listMay = new ArrayList<May>(); // Initialize with an empty list if no data is found
        }
    }

    /**
     * Save a list of May objects to the may.xml file
     * 
     * @param mayList
     */
    public void writeListMay(List<May> mayList) {
        MayXML mayXML = new MayXML(); // Create a MayXML object to hold the list
        mayXML.setMay(mayList); // Set the list of May objects
        FileUtils.writeXMLtoFile(MAY_FILE_NAME, mayXML); // Save to XML
    }

    /**
     * Read a list of May objects from the may.xml file
     * 
     * @return list of May
     */
    public List<May> readListMay() {
        List<May> list = new ArrayList<May>();
        MayXML mayXML = (MayXML) FileUtils.readXMLFile(MAY_FILE_NAME, MayXML.class); // Read XML file
        if (mayXML != null) {
            list = mayXML.getMay(); // Retrieve the list of May objects
        }
        return list;
    }

    /**
     * Add a May to the listMay and save listMay to file
     * 
     * @param may
     */
    public boolean add(May may) {
       try{
            int id = 1; // Default ID
        if (listMay != null && !listMay.isEmpty()) {
            // Set ID to the next available number
            id = listMay.size() + 1;
        }
        may.setMaMay(id); // Set the ID for the new May
        listMay.add(may); // Add to the list
        writeListMay(listMay); // Save the updated list to file
        return true;
       }catch(Exception ex){
           return false;
       }
    }

    /**
     * Edit a May in the listMay and save listMay to file
     * 
     * @param may
     */
    public boolean edit(May may) {
        for (int i = 0; i < listMay.size(); i++) {
            if (listMay.get(i).getMaMay() == may.getMaMay()) {
                // Update the May details
                listMay.get(i).setMaPhong(may.getMaPhong());
                listMay.get(i).setTrangThai(may.getTrangThai());
                listMay.get(i).setThongSo(may.getThongSo());
                listMay.get(i).setGiaTheoGio(may.getGiaTheoGio());
                writeListMay(listMay); // Save the updated list to file
                return true;
            }
        }
        return false;
    }

    /**
     * Delete a May from listMay and save listMay to file
     * 
     * @param may
     * @return true if the May was found and deleted, false otherwise
     */
    public boolean delete(May may) {
        boolean isFound = false;
        for (int i = 0; i < listMay.size(); i++) {
            if (listMay.get(i).getMaMay() == may.getMaMay()) {
                listMay.remove(i); // Remove the May from the list
                isFound = true;
                break;
            }
        }
        if (isFound) {
            writeListMay(listMay); // Save the updated list to file
            return true;
        }
        return false; // May not found
    }
    PhongDao dao = new PhongDao();
    public Map<String, List<May>> getMayGroupedByPhong() {
        Map<String, List<May>> groupedMay = new HashMap<>();
        
        for (May may : listMay) {
            String tenPhong = dao.getTenPhongById(may.getMaPhong());
            groupedMay.putIfAbsent(tenPhong, new ArrayList<>()); // Nếu chưa có phòng, khởi tạo danh sách
            groupedMay.get(tenPhong).add(may); // Thêm máy vào danh sách của phòng tương ứng
        }
        
        return groupedMay; // Trả về bản đồ nhóm máy theo phòng
    }
    public void updateTrangThaiMay(int maMay, String trangThai) {
    for (May may : listMay) {
        if (may.getMaMay() == maMay) {
            may.setTrangThai(trangThai); // Update the status of the machine
            writeListMay(listMay); // Save the updated list to file
            break; // Return true if the update was successful
        }
    }
   
}
   public double calculateTotalCostTime(int maMay, long soGiay) {
    for (May may : listMay) {
        if (may.getMaMay() == maMay) {
            // Lấy giá theo giờ
            double giaTheoGio = may.getGiaTheoGio();
            // Chuyển đổi số giây thành giờ
            double soGio = soGiay / 3600.0; // 3600 giây trong 1 giờ
            return giaTheoGio * soGio; // Tính tổng tiền
        }
    }
    return -1; // Trả về -1 nếu không tìm thấy máy
}


    public List<May> getListMay() {
        return listMay; // Return the list of May objects
    }

    public void setListMay(List<May> listMay) {
        this.listMay = listMay; // Set the list of May objects
    }
}