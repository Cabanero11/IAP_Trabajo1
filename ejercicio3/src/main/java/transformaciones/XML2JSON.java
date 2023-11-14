package transformadores;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class XML2JSON extends AbstractMessageTransformer {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
		
		String xml=(String)message.getPayload();
		
		JSONArray jsonList=new JSONArray();
		JSONObject catalogo=new JSONObject();
		
		try{
		    Document document = DocumentHelper.parseText(xml);
		    List<Node> libros = document.selectNodes("//book");

		    for(Node n:libros){
		    	JSONObject obj=new JSONObject();
		    	obj.put("id", n.valueOf("@id"));

		    	Node nombreNode=n.selectSingleNode("author");
		    	obj.put("autor", nombreNode.getStringValue()); 	
		    	
		    	Node tituloNode=n.selectSingleNode("title");
		    	obj.put("titulo", tituloNode.getStringValue());
		    	
		    	Node generoNode=n.selectSingleNode("genre");
		    	obj.put("genero", generoNode.getStringValue());
		    	
		    	Node precioNode=n.selectSingleNode("price");
		    	obj.put("precio", precioNode.getStringValue());
		    	
		    	Node fechaNode=n.selectSingleNode("publish_date");
		    	obj.put("fecha", fechaNode.getStringValue());
		    	
		    	Node descNode=n.selectSingleNode("description");
		    	obj.put("descripcion", descNode.getStringValue());
		    	
		    	jsonList.put(obj);
		    }
		    
		    
		    catalogo.put("catalogo", jsonList);
		}catch(Exception e){ 
			e.printStackTrace(); 
		}

		return catalogo.toString();

	}

}
