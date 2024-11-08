package Class;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PhongList")
@XmlAccessorType(XmlAccessType.FIELD)
public class PhongXML {

    @XmlElement(name = "Phong")
    private List<Phong> phong;

    public List<Phong> getPhong() {
        return phong;
    }

    public void setPhong(List<Phong> phong) {
        this.phong = phong;
    }
}
