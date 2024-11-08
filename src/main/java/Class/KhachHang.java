package Class;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KhachHang")
@XmlAccessorType(XmlAccessType.FIELD)
public class KhachHang {

    @XmlElement(name = "MaKhachHang")
    private int maKhachHang;

    @XmlElement(name = "TenKhachHang")
    private String tenKhachHang;

    @XmlElement(name = "SoDienThoai")
    private String soDienThoai;

    @XmlElement(name = "Email")
    private String email;

    @XmlElement(name = "DiemTichLuy")
    private int diemTichLuy;

    public KhachHang() {
    }

    public KhachHang(int maKhachHang, String tenKhachHang, String soDienThoai, String email, int diemTichLuy) {
        this.maKhachHang = maKhachHang;
        this.tenKhachHang = tenKhachHang;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diemTichLuy = diemTichLuy;
    }

    // Getters và Setters
    public int getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(int maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDiemTichLuy() {
        return diemTichLuy;
    }

    public void setDiemTichLuy(int diemTichLuy) {
        this.diemTichLuy = diemTichLuy;
    }
}
