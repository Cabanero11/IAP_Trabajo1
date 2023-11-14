package transformadores;

import org.apache.xmlbeans.impl.common.DocumentHelper;
import org.dom4j.Document;
import org.dom4j.Element;
import org.json.JSONArray;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class MoviesJSON2XML extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		// TODO Auto-generated method stub
		String payload = (String) message.getPayload();
		String actorListXML = "";
		
		JSONArray movieListJSON = new JSONArray(payload);
		Document documentoXML = (Document) DocumentHelper.createDocument();
		Element root = documentoXML.addElement("actors");
		
		for(int i = 0; i < movieListJSON.length(); i++) {
			JSONArray characterListJSON = movieListJSON.getJSONObject(i).getJSONArray("characters");
			
			for(int j = 0; j < characterListJSON.length(); j++) {			
				String actorName = characterListJSON.getJSONObject(j).getString("actor");
				
				Element actorXML = root.addElement("Actor");
				actorXML.addText(actorName);
			}
		}
		
		actorListXML = documentoXML.asXML();
		
		return actorListXML;
	}

}
