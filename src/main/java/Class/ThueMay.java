package Class;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name = "ThueMay")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThueMay {

    @XmlElement(name = "MaThueMay")
    private int maThueMay;

    @XmlElement(name = "MaKhachHang")
    private int maKhachHang;

    @XmlElement(name = "MaMay")
    private int maMay;

    @XmlElement(name = "ThoiGianBatDau")
    private Date thoiGianBatDau;

    @XmlElement(name = "ThoiGianKetThuc")
    private Date thoiGianKetThuc;

    @XmlElement(name = "TongChiPhi")
    private double tongChiPhi;

    @XmlElement(name = "MaPhong")
    private int maPhong;

    public ThueMay() {
    }

    public ThueMay(int maThueMay, int maKhachHang, int maMay, Date thoiGianBatDau, Date thoiGianKetThuc, double tongChiPhi, int maPhong) {
        this.maThueMay = maThueMay;
        this.maKhachHang = maKhachHang;
        this.maMay = maMay;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.tongChiPhi = tongChiPhi;
        this.maPhong = maPhong;
    }

    // Getters và Setters
    public int getMaThueMay() {
        return maThueMay;
    }

    public void setMaThueMay(int maThueMay) {
        this.maThueMay = maThueMay;
    }

    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public int getMaMay() {
        return maMay;
    }

    public void setMaMay(int maMay) {
        this.maMay = maMay;
    }

    public Date getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(Date thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public Date getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(Date thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public double getTongChiPhi() {
        return tongChiPhi;
    }

    public void setTongChiPhi(double tongChiPhi) {
        this.tongChiPhi = tongChiPhi;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }
}
