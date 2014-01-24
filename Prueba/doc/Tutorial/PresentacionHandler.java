import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.activation.DataHandler;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name = "handler")
@XmlAccessorType(XmlAccessType.FIELD)
public class PresentacionHandler {

	public static PresentacionHandler unmarshal(String xml){
		PresentacionHandler ph;
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(PresentacionHandler.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader("<handler>"+xml+"</handler>");
			ph = (PresentacionHandler) unmarshaller.unmarshal(reader);
			return ph;
		} catch (UnmarshalException ue) {
			// do nothing
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	@XmlElement(name = "rawFile")
	@XmlMimeType("application/octet-stream")
	private DataHandler dataHandler;
	
	public DataHandler getDataHandler() {
		return dataHandler;
	}
	
	public void saveTo(String path) throws IOException {

		OutputStream os = new FileOutputStream(new File(path));

		dataHandler.writeTo(os);
	}

	
}
