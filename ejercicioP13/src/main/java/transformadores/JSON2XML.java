package transformadores;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON2XML {
		private static final String jsonString="[{\"dni\":\"12345678S\",\"nombre\":\"Luis\",\"apellidos\":\"García\"},{\"dni\":\"87654321D\",\"nombre\":\"María\",\"apellidos\":\"Pérez\"}]";

		public static void main(String args[]){
			
			try {
				JSONArray alumnos=new JSONArray(jsonString);
					
				Document document = DocumentHelper.createDocument();
				Element root = document.addElement( "Alumnos" );
				
				for(int i=0;i<alumnos.length();i++){
					JSONObject alumnoJSON=alumnos.getJSONObject(i);
					
					Element alumnoXML = root.addElement("Alumno");
							
					alumnoXML.addAttribute("dni", alumnoJSON.get("dni").toString());
						
					alumnoXML.addElement("Nombre")
								.addText(alumnoJSON.get("nombre").toString());
					alumnoXML.addElement("Apellidos")
								.addText(alumnoJSON.get("apellidos").toString());
				}	
				
				System.out.println(document.asXML()); 
					
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	
}
