package transformadores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CSVtoMAP {

    public static String convertirCSVaJSON(String entradaCSV) throws Exception {
        // Crear un objeto ObjectMapper para manejar la conversión
        ObjectMapper objectMapper = new ObjectMapper();

        // Dividir las líneas del CSV
        String[] lineas = entradaCSV.split("\n");

        // Crear el nodo principal JSON
        ObjectNode nodoPrincipal = objectMapper.createObjectNode();

        // Agregar el nodo de solicitud de descarga al nodo principal
        ObjectNode nodoSolicitud = objectMapper.createObjectNode();
        nodoPrincipal.set("SolicitudDescarga", nodoSolicitud);

        // Iterar sobre las líneas del CSV
        ArrayNode contenedoresArray = objectMapper.createArrayNode();
        for (int i = 2; i < lineas.length; i++) {  // Empezamos desde la tercera línea, ya que las dos primeras son encabezados
            String[] campos = lineas[i].split(",");
            ObjectNode nodoContenedor = objectMapper.createObjectNode();
            nodoContenedor.put("id-SNTN-contenedor", campos[0]);
            nodoContenedor.put("tipo-contenedor", campos[1]);
            nodoContenedor.put("peso", campos[2]);
            nodoContenedor.put("precinto", campos[3]);
            nodoContenedor.put("tamaño-contenedor", campos[4]);

            // Verificar si hay información de traslado
            if (campos.length > 5) {
                ObjectNode nodoTraslado = objectMapper.createObjectNode();
                nodoTraslado.put("fecha-maxima-entrega", campos[5]);
                nodoTraslado.put("direccion-entrega", campos[6]);
                nodoTraslado.put("codigo-postal", campos[7]);
                nodoTraslado.put("ciudad", campos[8]);
                nodoTraslado.put("codigo-pais", campos[9]);

                nodoContenedor.set("Traslado", nodoTraslado);
            }

            contenedoresArray.add(nodoContenedor);
        }

        nodoSolicitud.set("Contenedores", contenedoresArray);

        // Convertir el nodo principal a JSON y devolverlo como String
        return objectMapper.writeValueAsString(nodoPrincipal);
    }

    public static void main(String[] args) {
        try {
            // Reemplaza esta cadena con tu CSV de solicitud de descarga
            String csvEntrada = "SNTN:SDC:345678901\nSNTN:PORT:ES46, Puerto de Valencia\nSNTN:AN:WorldPort, WorldPort, ES\nSNTN:DOCKER:8417d8ef-066d-13b86f4dc691, cisterna, 1200, 123456789, std-20\nSNTN:DOCKER:43d4246b-bb8d-4d6f99855a1b, open-top, 800, 123456789, std-20, 10/10/2023 13:42:00,\"Carrer del Marqués de Sant Joan, 32\", 46015, Valencia, ES\n";

            // Convertir CSV a JSON
            String jsonSalida = convertirCSVaJSON(csvEntrada);

            // Imprimir el resultado
            System.out.println(jsonSalida);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
