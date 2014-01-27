package connection;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface Serializable {
	
	String getInfo();
	
	void updateFields(Serializable s);

}
