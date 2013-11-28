package connection;

public class Parser {

	public String addTag(String xml, String tag) {
		xml = "<" + tag + ">" + xml;
		xml += "</" + tag + ">";
		return xml;
	}
	
	public String addField(String xml, String field, String value) {
		xml += "<" + field + ">" + value + "</" + field + ">";
		return xml;
	}
	
	public String removeTag(String xml) {
		int index = xml.indexOf(">") + 1;
		xml = xml.substring(index);
		index = xml.lastIndexOf("<");
		xml = xml.substring(0, index);
		return xml;
	}
	
	public String getFieldValue(String xml) {
		int index = xml.indexOf(">") + 1;
		xml = xml.substring(index);
		index = xml.indexOf("<");
		String field = xml.substring(0, index);
		return field;
	}
	
	public String removeField(String xml) {
		int index = xml.indexOf(">") + 1;
		xml = xml.substring(index);
		index = xml.indexOf(">") + 1;
		xml = xml.substring(index);
		return xml;
	}
	
}