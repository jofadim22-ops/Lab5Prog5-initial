package utils;

import model.Coordinates;
import model.Location;
import model.Route;
import java.io.*;
import java.util.Date;
import java.util.Hashtable;
import javax.xml.stream.*;

public class XmlFileHandler {

    public static Hashtable<Integer, Route> readFromFile(String filename)
             throws IOException, XMLStreamException {

        Hashtable<Integer, Route> collection = new Hashtable<>();
        File file = new File(filename);
        if (!file.exists() || file.length() == 0) return collection;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            factory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, false);
            factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);

            XMLStreamReader xmlReader = factory.createXMLStreamReader(reader);

            Route currentRoute = null;
            Integer currentKey = null;
            String currentParent = "";
            StringBuilder textBuffer = new StringBuilder();

            while (xmlReader.hasNext()) {
                int event = xmlReader.next();

                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        String elementName = xmlReader.getLocalName();

                        if ("route".equals(elementName)) {
                            currentRoute = new Route();
                            String KeyAttr = xmlReader.getAttributeValue(null, "Key");
                            if (KeyAttr == null) {
                                KeyAttr = xmlReader.getAttributeValue(null, "Key");
                            }
                            if (KeyAttr != null) {
                                currentKey = Integer.parseInt(KeyAttr);
                            }
                        } else if ("coordinates".equals(elementName) ||
                                "from".equals(elementName) ||
                                "to".equals(elementName)) {
                            currentParent = elementName;
                        }
                        textBuffer.setLength(0);
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        textBuffer.append(xmlReader.getText());
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        String endElement = xmlReader.getLocalName();
                        String value = textBuffer.toString().trim();

                        if ("coordinates".equals(endElement) ||
                                "from".equals(endElement) ||
                                "to".equals(endElement)) {
                            currentParent = "";
                        }

                        if (currentRoute != null && !value.isEmpty()) {
                            populateRouteField(currentRoute, currentParent, endElement, value);
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

    public static void writeToFile(String filename, Hashtable<Integer, Route> collection)
        throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.println("<collection>");

            for (Integer Key : collection.keySet()) {
                Route route = collection.get(Key);
                if (route == null) continue;

                writer.println("    <route Key=\"" + escapeXml(Key.toString()) + "\">");
                writerRouteFields(writer, route);
                writer.println("  </route>");
            }

            writer.println("</collection>");
            writer.flush();
        }
    }

    private static void populateRouteField(Route route, String parent, String fieldName, String value) {
        try {
            if (parent.isEmpty()) {
                switch (fieldName) {
                    case "id":
                        route.setId(Integer.parseInt(value));
                        break;
                    case "creationDate":
                        route.setCreationDate(new Date(Long.parseLong(value)));
                        break;
                }
            } else if ("coordinates".equals(parent)) {
                if (route.getCoordinates() == null) {
                    route.setCoordinates(new Coordinates(0, 0));
                }
                if ("x".equals(fieldName)) {
                    route.getCoordinates().setX(Integer.parseInt(value));
                } else if ("y".equals(fieldName)) {
                    route.getCoordinates().setY(Integer.parseInt(value));
                }
            } else if ("from".equals(parent) || "to".equals(parent)) {
                Location loc = "from".equals(parent) ? route.getFrom() : route.getTo();
                if (loc == null) {
                    loc = new Location(0.0, 0f, 0, "temp");
                    if ("from".equals(parent)) {
                        route.setFrom(loc);
                    } else {
                        route.setTo(loc);
                    }
                }
                if ("x".equals(fieldName)) {
                    loc.setX(Double.parseDouble(value));
                } else if ("y".equals(fieldName)) {
                    loc.setY(Float.parseFloat(value));
                } else if ("z".equals(fieldName)) {
                    loc.setZ(Integer.parseInt(value));
                } else if ("name".equals(fieldName)) {
                    loc.setName(value);
                }
            }
        } catch (Exception e) {
            System.err.println("Warning: could not parse '" + "." + fieldName + e.getMessage());
        }
    }

    private static void writerRouteFields(PrintWriter writer, Route route) {
        if (route.getId() != null) {
            writer.println("   <id>" + route.getId() + "</id>");
        }
        if (route.getName() != null) {
            writer.println("   <name>" + escapeXml(route.getName()) + "</name>");
        }
        if (route.getCreationDate() != null) {
            writer.println("     <creationDate>" + route.getCreationDate().getTime() + "</creationDate>");
        }
        if (route.getDistance() != null) {
            writer.println("    <distance>" + route.getDistance() + "</distance>");
        }
        if (route.getCoordinates() != null) {
            writer.println("     <coordinates>");
            writer.println("        <x>" + route.getCoordinates().getX() + "</x>");
            writer.println("        <y>" + route.getCoordinates().getY() + "</y>");
            writer.println("     </coordinates>");
        }

        if (route.getFrom() != null) {
            writer.println("    <from>");
            Location from = route.getFrom();
            if (from.getX() != null) {
                writer.println("     <x>" + from.getX() + "</x>");
            }

            writer.println("     <y>" + from.getY() + "</y>");
            writer.println("     <z>" + from.getZ() + "</z>");

            if (from.getName() != null) {
                writer.println("     <name>" + escapeXml(from.getName()) + "</name>");
            }
            writer.println("    </from>");
        }

        if (route.getTo() != null) {
            writer.println("    <to>");
            Location to =route.getTo();
            if (to.getX() != null) {
                writer.println("    <x>" + to.getX() + "</x>");
            }

            writer.println("     <y>" + to.getY() + "</y>");
            writer.println("     <z>" + to.getZ() + "</z>");

            if (to.getName() != null) {
                writer.println("     <name>" + escapeXml(to.getName()) + "</name>");
            }
            writer.println("    </to>");
        }
    }


    private static String escapeXml(String str) {
        if (str == null) return "";
        return str.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}









