package Class;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ThueMay_DichVu")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThueMay_DichVu {
    
    @XmlElement(name = "MaThueMay")
    private int maThueMay;

    @XmlElement(name = "MaDichVu")
    private int maDichVu;

    @XmlElement(name = "SoLuong")
    private int soLuong;

    public ThueMay_DichVu() {
    }

    public ThueMay_DichVu(int maThueMay, int maDichVu, int soLuong) {
        this.maThueMay = maThueMay;
        this.maDichVu = maDichVu;
        this.soLuong = soLuong;
    }

    // Getters và Setters
    public int getMaThueMay() {
        return maThueMay;
    }

    public void setMaThueMay(int maThueMay) {
        this.maThueMay = maThueMay;
    }

    public int getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(int maDichVu) {
        this.maDichVu = maDichVu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
