package Class;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ThueMay_DichVuList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThueMay_DichVuXML {
    
    @XmlElement(name = "ThueMay_DichVu")
    private List<ThueMay_DichVu> thueMayDichVu;

    public List<ThueMay_DichVu> getThueMayDichVu() {
        return thueMayDichVu;
    }

    public void setThueMayDichVu(List<ThueMay_DichVu> thueMayDichVu) {
        this.thueMayDichVu = thueMayDichVu;
    }
}
