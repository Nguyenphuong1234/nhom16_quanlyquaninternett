package Class;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Phong")
@XmlAccessorType(XmlAccessType.FIELD)
public class Phong {

    @XmlElement(name = "MaPhong")
    private int maPhong;

    @XmlElement(name = "TenPhong")
    private String tenPhong;

    @XmlElement(name = "MoTa")
    private String moTa;

    public Phong() {
    }

    public Phong(int maPhong, String tenPhong, String moTa) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.moTa = moTa;
    }

    // Getters và Setters
    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    @Override
    public String toString() {
        return tenPhong;
    }
    
}
