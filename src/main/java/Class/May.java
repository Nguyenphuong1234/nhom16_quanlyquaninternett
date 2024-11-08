package Class;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "May")
@XmlAccessorType(XmlAccessType.FIELD)
public class May {

    @XmlElement(name = "MaMay")
    private int maMay;

    @XmlElement(name = "MaPhong")
    private int maPhong;

    @XmlElement(name = "TrangThai")
    private String trangThai;

    @XmlElement(name = "ThongSo")
    private String thongSo;

    @XmlElement(name = "GiaTheoGio")
    private double giaTheoGio;

    public May() {
    }

    public May(int maMay, int maPhong, String trangThai, String thongSo, double giaTheoGio) {
        this.maMay = maMay;
        this.maPhong = maPhong;
        this.trangThai = trangThai;
        this.thongSo = thongSo;
        this.giaTheoGio = giaTheoGio;
    }

    // Getters và Setters
    public int getMaMay() {
        return maMay;
    }

    public void setMaMay(int maMay) {
        this.maMay = maMay;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getThongSo() {
        return thongSo;
    }

    public void setThongSo(String thongSo) {
        this.thongSo = thongSo;
    }

    public double getGiaTheoGio() {
        return giaTheoGio;
    }

    public void setGiaTheoGio(double giaTheoGio) {
        this.giaTheoGio = giaTheoGio;
    }
}
