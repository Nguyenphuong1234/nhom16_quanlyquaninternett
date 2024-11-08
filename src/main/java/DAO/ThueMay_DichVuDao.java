/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import java.util.ArrayList;
import java.util.List;

import Class.*;
import Utils.FileUtils;
public class ThueMay_DichVuDao {
    private static final String THUEMAY_DICHVU_FILE_NAME = "src/main/java/XMLFile/ThueMay_DichVu.xml"; // XML file name to store ThueMay_DichVu data
    private List<ThueMay_DichVu> listThueMay_DichVu;

    public ThueMay_DichVuDao() {
        this.listThueMay_DichVu = readListThueMay_DichVu();
        if (listThueMay_DichVu == null) {
            listThueMay_DichVu = new ArrayList<ThueMay_DichVu>(); // Initialize with an empty list if no data is found
        }
    }

    /**
     * Save a list of ThueMay_DichVu objects to the thueMay_DichVu.xml file
     * 
     * @param thueMay_DichVuList
     */
    public void writeListThueMay_DichVu(List<ThueMay_DichVu> thueMay_DichVuList) {
        ThueMay_DichVuXML thueMay_DichVuXML = new ThueMay_DichVuXML(); // Create a ThueMay_DichVuXML object to hold the list
        thueMay_DichVuXML.setThueMayDichVu(thueMay_DichVuList); // Set the list of ThueMay_DichVu objects
        FileUtils.writeXMLtoFile(THUEMAY_DICHVU_FILE_NAME, thueMay_DichVuXML); // Save to XML
    }

    /**
     * Read a list of ThueMay_DichVu objects from the thueMay_DichVu.xml file
     * 
     * @return list of ThueMay_DichVu
     */
    public List<ThueMay_DichVu> readListThueMay_DichVu() {
        List<ThueMay_DichVu> list = new ArrayList<ThueMay_DichVu>();
        ThueMay_DichVuXML thueMay_DichVuXML = (ThueMay_DichVuXML) FileUtils.readXMLFile(THUEMAY_DICHVU_FILE_NAME, ThueMay_DichVuXML.class); // Read XML file
        if (thueMay_DichVuXML != null) {
            list = thueMay_DichVuXML.getThueMayDichVu(); // Retrieve the list of ThueMay_DichVu objects
        }
        return list;
    }

    /**
     * Add a ThueMay_DichVu to the listThueMay_DichVu and save listThueMay_DichVu to file
     * 
     * @param thueMay_DichVu
     */
    public boolean add(ThueMay_DichVu thueMay_DichVu) {
        try{
            
            listThueMay_DichVu.add(thueMay_DichVu); // Add to the list
            writeListThueMay_DichVu(listThueMay_DichVu); // Save the updated list to file
            return true;
            }catch(Exception ex){
                return false;
        }
    }

    /**
     * Edit a ThueMay_DichVu in the listThueMay_DichVu and save listThueMay_DichVu to file
     * 
     * @param thueMay_DichVu
     */
    public void edit(ThueMay_DichVu thueMay_DichVu) {
        for (int i = 0; i < listThueMay_DichVu.size(); i++) {
            if (listThueMay_DichVu.get(i).getMaThueMay() == thueMay_DichVu.getMaThueMay()) {
                // Update the ThueMay_DichVu details
                listThueMay_DichVu.get(i).setMaDichVu(thueMay_DichVu.getMaDichVu());
                listThueMay_DichVu.get(i).setSoLuong(thueMay_DichVu.getSoLuong());
                writeListThueMay_DichVu(listThueMay_DichVu); // Save the updated list to file
                break;
            }
        }
    }

    /**
     * Delete a ThueMay_DichVu from listThueMay_DichVu and save listThueMay_DichVu to file
     * 
     * @param thueMay_DichVu
     * @return true if the ThueMay_DichVu was found and deleted, false otherwise
     */
    public boolean delete(ThueMay_DichVu thueMay_DichVu) {
        boolean isFound = false;
        for (int i = 0; i < listThueMay_DichVu.size(); i++) {
            if (listThueMay_DichVu.get(i).getMaThueMay() == thueMay_DichVu.getMaThueMay()) {
                listThueMay_DichVu.remove(i); // Remove the ThueMay_DichVu from the list
                isFound = true;
                break;
            }
        }
        if (isFound) {
            writeListThueMay_DichVu(listThueMay_DichVu); // Save the updated list to file
            return true;
        }
        return false; // ThueMay_DichVu not found
    }
    DichVuDao dichVuDao = new DichVuDao();
 public List<ServiceDetails> getServiceDetailsByMaThueMay(int maThueMay) {
    List<ServiceDetails> serviceDetailsList = new ArrayList<>(); // Tạo danh sách để lưu kết quả

    // Duyệt qua danh sách ThueMay_DichVu để tìm các dịch vụ theo mã thuê máy
    for (ThueMay_DichVu thueMayDichVu : listThueMay_DichVu) {
        if (thueMayDichVu.getMaThueMay() == maThueMay) {
            int maDichVu = thueMayDichVu.getMaDichVu();
            DichVu dichVu = dichVuDao.findDichVuByMaDichVu(maDichVu);

            if (dichVu != null) {
                int soLuong = thueMayDichVu.getSoLuong();
                double donGia = dichVu.getGiaDichVu();
                double thanhTien = soLuong * donGia;

                // Tạo đối tượng ServiceDetails và thêm vào danh sách
                serviceDetailsList.add(new ServiceDetails(dichVu.getTenDichVu(), soLuong, donGia, thanhTien));
            }
        }
    }

    // Trả về danh sách chứa các thông tin dịch vụ
    return serviceDetailsList;
}
 public double calculateTotalServiceCost(List<ServiceDetails> serviceDetailsList) {
    double totalCost = 0.0; // Khởi tạo tổng chi phí

    // Duyệt qua danh sách dịch vụ và cộng dồn tổng chi phí
    for (ServiceDetails serviceDetail : serviceDetailsList) {
        totalCost += serviceDetail.getThanhTien(); // Cộng dồn giá trị thành tiền
    }

    return totalCost; 
}

/**
 * Get a list of ThueMay_DichVu objects based on MaThueMay
 * 
 * @param maThueMay the rental machine ID to filter by
 * @return a list of ThueMay_DichVu objects
 */
public List<ThueMay_DichVu> getThueMay_DichVuByMaThueMay(int maThueMay) {
    List<ThueMay_DichVu> resultList = new ArrayList<>(); // Create a list to hold results

    // Iterate through the list to find matches
    for (ThueMay_DichVu thueMay_DichVu : listThueMay_DichVu) {
        if (thueMay_DichVu.getMaThueMay() == maThueMay) {
            resultList.add(thueMay_DichVu); // Add matching items to the result list
        }
    }
    
    return resultList; // Return the filtered list
}

    public List<ThueMay_DichVu> getListThueMay_DichVu() {
        return listThueMay_DichVu; // Return the list of ThueMay_DichVu objects
    }

    public void setListThueMay_DichVu(List<ThueMay_DichVu> listThueMay_DichVu) {
        this.listThueMay_DichVu = listThueMay_DichVu; // Set the list of ThueMay_DichVu objects
    }
}
