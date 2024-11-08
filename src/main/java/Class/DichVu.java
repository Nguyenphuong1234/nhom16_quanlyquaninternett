package Class;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "DichVu")
@XmlAccessorType(XmlAccessType.FIELD)
public class DichVu {

    @XmlElement(name = "MaDichVu")
    private int maDichVu;

    @XmlElement(name = "TenDichVu")
    private String tenDichVu;

    @XmlElement(name = "GiaDichVu")
    private double giaDichVu;

    public DichVu() {
    }

    public DichVu(int maDichVu, String tenDichVu, double giaDichVu) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
        this.giaDichVu = giaDichVu;
    }

    // Getters và Setters
    public int getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(int maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public double getGiaDichVu() {
        return giaDichVu;
    }

    public void setGiaDichVu(double giaDichVu) {
        this.giaDichVu = giaDichVu;
    }

    @Override
    public String toString() {
        return tenDichVu;
    }
    
}
