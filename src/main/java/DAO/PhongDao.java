/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.ArrayList;
import java.util.List;

import Class.*;
import Utils.FileUtils;

public class PhongDao {
    private static final String PHONG_FILE_NAME = "src/main/java/XMLFile/Phong.xml"; // XML file name to store Phong data
    private List<Phong> listPhong;

    public PhongDao() {
        this.listPhong = readListPhong();
        if (listPhong == null) {
            listPhong = new ArrayList<Phong>(); // Initialize with an empty list if no data is found
        }
    }

    /**
     * Save a list of Phong objects to the phong.xml file
     * 
     * @param phongList
     */
    public void writeListPhong(List<Phong> phongList) {
        PhongXML phongXML = new PhongXML(); // Create a PhongXML object to hold the list
        phongXML.setPhong(phongList); // Set the list of Phong objects
        FileUtils.writeXMLtoFile(PHONG_FILE_NAME, phongXML); // Save to XML
    }

    /**
     * Read a list of Phong objects from the phong.xml file
     * 
     * @return list of Phong
     */
    public  List<Phong> readListPhong() {
        List<Phong> list = new ArrayList<Phong>();
        PhongXML phongXML = (PhongXML) FileUtils.readXMLFile(PHONG_FILE_NAME, PhongXML.class); // Read XML file
        if (phongXML != null) {
            list = phongXML.getPhong(); // Retrieve the list of Phong objects
        }
        return list;
    }

    /**
     * Add a Phong to the listPhong and save listPhong to file
     * 
     * @param phong
     */
    public boolean add(Phong phong) {
       try{
            int id = 1; // Default ID
        if (listPhong != null && !listPhong.isEmpty()) {
            // Set ID to the next available number
            id = listPhong.size() + 1;
        }
        phong.setMaPhong(id); // Set the ID for the new Phong
        listPhong.add(phong); // Add to the list
        writeListPhong(listPhong); // Save the updated list to file
        return true;
       }catch(Exception ex){
           return false;
       }
    }

    /**
     * Edit a Phong in the listPhong and save listPhong to file
     * 
     * @param phong
     */
  public boolean edit(Phong phong) {
    for (int i = 0; i < listPhong.size(); i++) {
        if (listPhong.get(i).getMaPhong() == phong.getMaPhong()) {
            // Update the Phong details
            listPhong.get(i).setTenPhong(phong.getTenPhong());
            listPhong.get(i).setMoTa(phong.getMoTa());
            writeListPhong(listPhong); // Save the updated list to file
            return true; // Return true if the update was successful
        }
    }
    return false; // Return false if the Phong was not found
}


    /**
     * Delete a Phong from listPhong and save listPhong to file
     * 
     * @param phong
     * @return true if the Phong was found and deleted, false otherwise
     */
    public boolean delete(Phong phong) {
        boolean isFound = false;
        for (int i = 0; i < listPhong.size(); i++) {
            if (listPhong.get(i).getMaPhong() == phong.getMaPhong()) {
                listPhong.remove(i); // Remove the Phong from the list
                isFound = true;
                break;
            }
        }
        if (isFound) {
            writeListPhong(listPhong); // Save the updated list to file
            return true;
        }
        return false; // Phong not found
    }

    public List<Phong> getListPhong() {
        return listPhong; // Return the list of Phong objects
    }

    public void setListPhong(List<Phong> listPhong) {
        this.listPhong = listPhong; // Set the list of Phong objects
    }
    public String getTenPhongById(int maPhong) {
    for (Phong phong : listPhong) {
        if (phong.getMaPhong() == maPhong) {
            return phong.getTenPhong(); // Return tenPhong if found
        }
    }
    return "N/A"; // Return null if the Phong was not found
}
}

