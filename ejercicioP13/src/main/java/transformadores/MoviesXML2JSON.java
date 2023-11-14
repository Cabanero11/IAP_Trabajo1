package transformadores;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class MoviesXML2JSON extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
		// TODO Auto-generated method stub
		String payload = (String) message.getPayload();
		String moviesJSONString = "";
		
		try {
			Document documentoXML = DocumentHelper.parseText(payload);
			List<? extends Node> movieListXML = documentoXML.selectNodes("//movie");
			
			
			JSONArray movieListJSON = new JSONArray();
			
			for (Node movieXML:movieListXML) {
				
				JSONObject movieJSON = new JSONObject();
				
				String title = movieXML.selectSingleNode("title").getStringValue();
				String director = movieXML.selectSingleNode("director").getStringValue();
				String plot = movieXML.selectSingleNode("plot").getStringValue();
				String rating = movieXML.selectSingleNode("rating").getStringValue();
				
				movieJSON.put("title", title);
				movieJSON.put("director", director);
				movieJSON.put("plot", plot);
				movieJSON.put("rating", rating);
				
				JSONArray characterListJSON = new JSONArray();
				List<? extends Node> charactersListXML = documentoXML.selectNodes(".//character");
				
				
				for(Node characterXML:charactersListXML) {
					JSONObject charactersJSON = new JSONObject();
					
					charactersJSON.put("name", characterXML.selectSingleNode("name").getStringValue());
					charactersJSON.put("actor", characterXML.selectSingleNode("actor").getStringValue());
					
					characterListJSON.put(charactersJSON);
				}
				
				movieJSON.put("characters", characterListJSON);
				
				movieListJSON.put(movieJSON);
			}
			
			moviesJSONString = movieListJSON.toString();
			
		} catch (DocumentException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return moviesJSONString;
	}

}
