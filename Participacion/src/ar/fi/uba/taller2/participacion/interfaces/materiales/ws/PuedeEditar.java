
package ar.fi.uba.taller2.participacion.interfaces.materiales.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for puedeEditar complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="puedeEditar">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="usuarioId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ambitoId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "puedeEditar", propOrder = {
    "usuarioId",
    "ambitoId"
})
public class PuedeEditar {

    protected String usuarioId;
    protected long ambitoId;

    /**
     * Gets the value of the usuarioId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuarioId() {
        return usuarioId;
    }

    /**
     * Sets the value of the usuarioId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuarioId(String value) {
        this.usuarioId = value;
    }

    /**
     * Gets the value of the ambitoId property.
     * 
     */
    public long getAmbitoId() {
        return ambitoId;
    }

    /**
     * Sets the value of the ambitoId property.
     * 
     */
    public void setAmbitoId(long value) {
        this.ambitoId = value;
    }

}
