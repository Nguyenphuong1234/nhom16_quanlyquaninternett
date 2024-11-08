package Class;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ThueMayList")
@XmlAccessorType(XmlAccessType.FIELD)
public class ThueMayXML {

    @XmlElement(name = "ThueMay")
    private List<ThueMay> thueMay;

    public List<ThueMay> getThueMay() {
        return thueMay;
    }

    public void setThueMay(List<ThueMay> thueMay) {
        this.thueMay = thueMay;
    }
}
