
package service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import model.Encuesta2;

@XmlRootElement(name = "getEncuestaResponse", namespace = "http://materials/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getEncuestaResponse", namespace = "http://materials/")
public class GetEncuestaResponse {

    @XmlElement(name = "return", namespace = "")
    private Encuesta2 _return;

    /**
     * 
     * @return
     *     returns Encuesta
     */
    public Encuesta2 getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(Encuesta2 _return) {
        this._return = _return;
    }

}
