
package ar.fi.uba.taller2.participacion.interfaces.materiales.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ar.fi.uba.taller2.participacion.interfaces.materiales.ws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PuedeEditarResponse_QNAME = new QName("http://ws.materiales.interfaces.participacion.taller2.uba.fi.ar/", "puedeEditarResponse");
    private final static QName _PuedeEditar_QNAME = new QName("http://ws.materiales.interfaces.participacion.taller2.uba.fi.ar/", "puedeEditar");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ar.fi.uba.taller2.participacion.interfaces.materiales.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PuedeEditarResponse }
     * 
     */
    public PuedeEditarResponse createPuedeEditarResponse() {
        return new PuedeEditarResponse();
    }

    /**
     * Create an instance of {@link PuedeEditar }
     * 
     */
    public PuedeEditar createPuedeEditar() {
        return new PuedeEditar();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PuedeEditarResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.materiales.interfaces.participacion.taller2.uba.fi.ar/", name = "puedeEditarResponse")
    public JAXBElement<PuedeEditarResponse> createPuedeEditarResponse(PuedeEditarResponse value) {
        return new JAXBElement<PuedeEditarResponse>(_PuedeEditarResponse_QNAME, PuedeEditarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PuedeEditar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.materiales.interfaces.participacion.taller2.uba.fi.ar/", name = "puedeEditar")
    public JAXBElement<PuedeEditar> createPuedeEditar(PuedeEditar value) {
        return new JAXBElement<PuedeEditar>(_PuedeEditar_QNAME, PuedeEditar.class, null, value);
    }

}
