package Class;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "MayList")
@XmlAccessorType(XmlAccessType.FIELD)
public class MayXML {

    @XmlElement(name = "May")
    private List<May> may;

    public List<May> getMay() {
        return may;
    }

    public void setMay(List<May> may) {
        this.may = may;
    }
}
