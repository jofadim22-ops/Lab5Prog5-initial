package utils;

import model.Route;
import java.io.*;
import java.util.Date;
import java.util.Hashtable;
import javax.xml.stream.*;

public class  XmlFileHandler {

    public static Hashtable<Integer, Route> readFromFile(String filename)
            throws IOException, XMLStreamException {

        Hashtable<Integer, Route> collection = new Hashtable<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            XMLInputFactory factory = XMLInputFactory.newInstance();

            factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);

            XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);

            Route currentRoute = null;
            Integer currentKey = null;
            String elementName = "";
            StringBuilder textBuffer = new StringBuilder();

            while (xmlReader.hasNext()) {
                int event = xmlReader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        elementName = xmlReader.getLocalName();

                        if ("route".equals(elementName)) {
                            currentRoute = new Route();
                            String KeyAttr = xmlReader.getAttributeValue(null, "Key");
                            if (KeyAttr != null) {
                                currentKey = Integer.parseInt(KeyAttr);
                            }
                        }
                        textBuffer.setLength(0);
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        textBuffer.append(xmlReader.getText());
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        String endElement = xmlReader.getLocalName();
                        String value = textBuffer.toString().trim();

                        if (currentRoute != null && !value.isEmpty()) {
                            populateRouteField(currentRoute, endElement, value);
                        }

                        if ("route".equals(endElement) && currentRoute != null && currentKey != null) {
                            collection.put(currentKey, currentRoute);
                            currentRoute = null;
                            currentKey = null;
                        }
                        break;
                }
            }
            xmlReader.close();
        }
        return collection;
    }


        public static Route createEmpty() {
            return new Route();
        }

        public static void writeToFile(String filename, Hashtable<Integer, Route> collection)
                 throws IOException {

             try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
                 writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                 writer.println("<collection>");

                 for (Integer key : collection.keySet()) {
                     Route route = collection.get(key);
                     if (route == null) continue;

                     writer.println(" <route Key=\"" + escapeXml(key.toString()) + "\">");
                     writerRouteFields(writer, route);
                     writer.println(" </route>");
                 }

                 writer.println("</collection>");
                 writer.flush();
             }
        }


        private static void populateRouteField(Route route, String fieldName, String value) {
            if (value == null || value.trim().isEmpty()) return;

            try {
                switch (fieldName) {
                    case "id" -> {
                        int parsedId = Integer.parseInt(value.trim());
                        if (parsedId > 0) route.setId(parsedId);
                    }
                    case "name" -> route.setName(value.trim());
                    case "distance" -> route.setDistance(Float.parseFloat(value.trim()));
                    case "creationDate" -> route.setCreationDate(new Date(Long.parseLong(value.trim())));
                }
            } catch (Exception e) {
                System.err.println("Warning: Could not parse '" + fieldName + e.getMessage());
            }
        }

        private static void writerRouteFields(PrintWriter writer, Route route) {
            writer.println("    <id>" + route.getId() + "</id>");
            writer.println("    <name>" + escapeXml(route.getName()) + "</name>");
            writer.println("    <creationDate>" + route.getCreationDate().getTime() + "</creationDate>");
            writer.println("    <distance>" + route.getDistance() + "</distance>");
        }

        private static String escapeXml(String str) {
            if (str == null) return "";
            return str.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;");
       }
    }





