/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import java.util.ArrayList;
import java.util.List;

import Class.*;
import Utils.FileUtils;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
   import java.util.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Collectors;

/**
 * DichVuDao class
 * 
 */
public class DichVuDao {
    private static final String DICH_VU_FILE_NAME = "src/main/java/XMLFile/DichVu.xml";
    private List<DichVu> listDichVu;

    public DichVuDao() {
        this.listDichVu = readListDichVu();
        if (listDichVu == null) {
            listDichVu = new ArrayList<DichVu>();
        }
    }

    /**
     * Save a list of DichVu objects to the dichvu.xml file
     * 
     * @param dichVus
     */
    public void writeListDichVu(List<DichVu> dichVus) {
        DichVuXML dichVuXML = new DichVuXML();
        dichVuXML.setDichVu(dichVus);
        FileUtils.writeXMLtoFile(DICH_VU_FILE_NAME, dichVuXML);
    }

    /**
     * Read a list of DichVu objects from the dichvu.xml file
     * 
     * @return list of DichVu
     */
    public List<DichVu> readListDichVu() {
        List<DichVu> list = new ArrayList<DichVu>();
        DichVuXML dichVuXML = (DichVuXML) FileUtils.readXMLFile(
                DICH_VU_FILE_NAME, DichVuXML.class);
        if (dichVuXML != null) {
            list = dichVuXML.getDichVu();
        }
        return list;
    }

    /**
     * Add a DichVu to the listDichVu and save listDichVu to file
     * 
     * @param dichVu
     */
    public boolean add(DichVu dichVu) {
        try{
            int id = 1;
        if (listDichVu != null && listDichVu.size() > 0) {
            id = listDichVu.size() + 1;
        }
        dichVu.setMaDichVu(id);
        listDichVu.add(dichVu);
        writeListDichVu(listDichVu);
        return true;
        }catch(Exception ex){
            return false;
        }
    }

    /**
     * Edit a DichVu in the listDichVu and save listDichVu to file
     * 
     * @param dichVu
     */
    public boolean edit(DichVu dichVu) {
        int size = listDichVu.size();
        for (int i = 0; i < size; i++) {
            if (listDichVu.get(i).getMaDichVu() == dichVu.getMaDichVu()) {
                listDichVu.get(i).setTenDichVu(dichVu.getTenDichVu());
                listDichVu.get(i).setGiaDichVu(dichVu.getGiaDichVu());
                writeListDichVu(listDichVu);
                return true;
            }
        }
        return false;
    }

    /**
     * Delete a DichVu from listDichVu and save listDichVu to file
     * 
     * @param dichVu
     */
    public boolean delete(DichVu dichVu) {
        boolean isFound = false;
        int size = listDichVu.size();
        for (int i = 0; i < size; i++) {
            if (listDichVu.get(i).getMaDichVu() == dichVu.getMaDichVu()) {
                isFound = true;
                listDichVu.remove(i);
                break;
            }
        }
        if (isFound) {
            writeListDichVu(listDichVu);
            return true;
        }
        return false;
    }
    public DichVu findDichVuByMaDichVu(int maDichVu) {
        for (DichVu dichVu : listDichVu) {
            if (dichVu.getMaDichVu() == maDichVu) {
                return dichVu;
            }
        }
        return null;
    }

  



public Map<String, Double> getMostPurchasedService(LocalDateTime startDate, LocalDateTime endDate) {
    Map<String, Double> serviceTotal = new HashMap<>();
    ThueMayDao thueMayDao = new ThueMayDao();
    ThueMay_DichVuDao thueMay_DichVuDao = new ThueMay_DichVuDao();

    // Fetch all ThueMay transactions
    List<ThueMay> thueMayList = thueMayDao.getListThueMay(); 

    for (ThueMay thueMay : thueMayList) {
        // Convert LocalDateTime to java.util.Date
        Date thoiGianKetThuc = thueMay.getThoiGianKetThuc();
        
        // Check if thoiGianKetThuc falls within the specified date range
        if (thoiGianKetThuc.after(Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant())) &&
            thoiGianKetThuc.before(Date.from(endDate.plusDays(1).atZone(ZoneId.systemDefault()).toInstant()))) {

            List<ThueMay_DichVu> dichVuList = thueMay_DichVuDao.getThueMay_DichVuByMaThueMay(thueMay.getMaThueMay());

            for (ThueMay_DichVu thueMay_DichVu : dichVuList) {
                DichVu dichVu = findDichVuByMaDichVu(thueMay_DichVu.getMaDichVu());
                if (dichVu != null) {
                    String tenDichVu = dichVu.getTenDichVu();
                    double giaDichVu = dichVu.getGiaDichVu() * thueMay_DichVu.getSoLuong();

                    // Update total amount spent on the service
                    serviceTotal.put(tenDichVu, serviceTotal.getOrDefault(tenDichVu, 0.0) + giaDichVu);
                }
            }
        }
    }

    // Find the top 5 most purchased services based on total amount
    List<Map.Entry<String, Double>> sortedServices = serviceTotal.entrySet().stream()
        .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // Sort in descending order
        .limit(5) // Limit to top 5
        .collect(Collectors.toList());

    // Prepare the result
    Map<String, Double> result = new HashMap<>();
    for (Map.Entry<String, Double> entry : sortedServices) {
        String serviceName = entry.getKey();
        double totalAmount = entry.getValue();

        // Add to result map
        result.put(serviceName, totalAmount);
    }

    return result;
}



    public List<DichVu> getListDichVu() {
        return listDichVu;
    }

    public void setListDichVu(List<DichVu> listDichVu) {
        this.listDichVu = listDichVu;
    }
}
