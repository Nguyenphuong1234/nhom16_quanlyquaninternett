package Class;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "KhachHangList")
@XmlAccessorType(XmlAccessType.FIELD)
public class KhachHangXML {

    @XmlElement(name = "KhachHang")
    private List<KhachHang> khachHang;

    public List<KhachHang> getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(List<KhachHang> khachHang) {
        this.khachHang = khachHang;
    }

    
}
