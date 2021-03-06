
package ar.fiuba.redsocedu.datalayer.ws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.6 in JDK 6
 * Generated source version: 2.1
 * 
 */
@WebService(name = "IData", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IData {


    /**
     * 
     */
    @WebMethod
    @RequestWrapper(localName = "beginTransaction", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.BeginTransaction")
    @ResponseWrapper(localName = "beginTransactionResponse", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.BeginTransactionResponse")
    public void beginTransaction();

    /**
     * 
     * @param object
     * @param entityName
     */
    @WebMethod
    @RequestWrapper(localName = "saveOrUpdate", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.SaveOrUpdate")
    @ResponseWrapper(localName = "saveOrUpdateResponse", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.SaveOrUpdateResponse")
    public void saveOrUpdate(
        @WebParam(name = "entityName", targetNamespace = "")
        String entityName,
        @WebParam(name = "object", targetNamespace = "")
        Object object);

    /**
     * 
     */
    @WebMethod
    @RequestWrapper(localName = "rollback", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.Rollback")
    @ResponseWrapper(localName = "rollbackResponse", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.RollbackResponse")
    public void rollback();

    /**
     * 
     * @param object
     * @param entityName
     */
    @WebMethod
    @RequestWrapper(localName = "delete", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.Delete")
    @ResponseWrapper(localName = "deleteResponse", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.DeleteResponse")
    public void delete(
        @WebParam(name = "entityName", targetNamespace = "")
        String entityName,
        @WebParam(name = "object", targetNamespace = "")
        Object object);

    /**
     * 
     * @param serializedCriteria
     * @return
     *     returns java.util.List<ar.fiuba.redsocedu.datalayer.ws.ReturnedObject>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "query", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.Query")
    @ResponseWrapper(localName = "queryResponse", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.QueryResponse")
    public List<ReturnedObject> query(
        @WebParam(name = "serializedCriteria", targetNamespace = "")
        String serializedCriteria);

    /**
     * 
     */
    @WebMethod
    @RequestWrapper(localName = "commit", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.Commit")
    @ResponseWrapper(localName = "commitResponse", targetNamespace = "http://ws.datalayer.redsocedu.fiuba.ar/", className = "ar.fiuba.redsocedu.datalayer.ws.CommitResponse")
    public void commit();

}
