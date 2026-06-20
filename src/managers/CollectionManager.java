package managers;

import model.Coordinates;
import model.Route;
import utils.XmlFileHandler;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;
import java.util.*;
import javax.xml.stream.XMLStreamException;

public class CollectionManager {
    private final Hashtable<Integer, Route> collection;
    private final Date initializationDate;
    private final String filePath;

    public CollectionManager(String filePath) {
        this.filePath = filePath;
        this.initializationDate = new Date();
        this.collection = new Hashtable<>();

        if (Files.exists(Paths.get(filePath))) {
            try {
                Hashtable<Integer, Route> loaded = XmlFileHandler.readFromFile(filePath);
                if (loaded != null) {
                    collection.putAll(loaded);
                    loaded.keySet().stream().max(Integer::compareTo)
                            .ifPresent(Route::updateNextId);
                }
            } catch (IOException e) {
                System.err.println("Warning: Could not read file " + filePath + ": " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Warning:Error parsing XML: " + e.getMessage());
            }
        }
    }

    public boolean put(Integer Key, Route route) {
        if (Key == null || route == null) return false;
        collection.put(Key, route);
        return true;
    }

    public Route get(Integer Key) {
        return collection.get(Key);
    }

    public Collection<Route> getAll() {
        return collection.values();
    }

    public boolean remove(Integer Key) {
        return collection.remove(Key) != null;
    }

    public void clear() {
        collection.clear();
    }

    public boolean containsKey(Integer Key) {
        return collection.containsKey(Key);
    }

    public int size() {
        return collection.size();
    }

    public String getInfo() {
        return String.format(
                "Collection Type: %s%n" +
                "Storage: java.util.Hashtable<Integer, Route>%n" +
                "Initialization Date: %s%n" +
                "Number of Elements: %d%n" +
                "File Path: %s",
                collection.getClass().getName(),
                initializationDate,
                collection.size(),
                filePath
        );
    }

    public List<Route> getSortedRoutes() {
        List<Route> list = new ArrayList<>(collection.values());
        Collections.sort(list);
        return list;
    }

    public void save() throws IOException {
        XmlFileHandler.writeToFile(filePath, collection);
    }

    public String getFilePath() {
        return filePath;
    }

    public Date getInitializationDate() {
        return initializationDate;
    }

    public Hashtable<Integer, Route> getCollection() {
        return collection;
    }

    public int removeGreaterByCriteria(int x, int y, float distance) {
        List<Integer> KeysToRemove = new ArrayList<>();

        for (Map.Entry<Integer, Route> entry : collection.entrySet()) {
            Route route = entry.getValue();

            if (route.getDistance() != null && route.getDistance() > distance) {
                KeysToRemove.add(entry.getKey());
            }
        }

        for (Integer Key : KeysToRemove) {
            collection.remove(Key);
        }

        return KeysToRemove.size();
    }

    public int removeLowerByCriteria(int x, int y, float distance) {
        List<Integer> KeysToRemove = new ArrayList<>();

        for (Map.Entry<Integer, Route> entry : collection.entrySet()) {
            Route route = entry.getValue();

            if (route.getDistance() != null && route.getDistance() < distance) {
                KeysToRemove.add(entry.getKey());
            }
        }

        for (Integer Key : KeysToRemove) {
            collection.remove(Key);
        }

        return KeysToRemove.size();
    }

    public int removeGreaterKey(Integer Key) {
    if (Key == null) {
        return 0;
    }

    List<Integer> KeysToRemove = new ArrayList<>();

    for (Integer k : collection.keySet()) {
        if (k > Key) {
            KeysToRemove.add(k);
        }
    }

    for (Integer K : KeysToRemove) {
        collection.remove(K);
    }

    return KeysToRemove.size();
    }

    public int removeLowerKey(Integer Key) {
        if (Key == null) {
            return 0;
        }

        List<Integer> KeysToRemove = new ArrayList<>();

        for (Integer K : collection.keySet()) {
            if (K < Key) {
                KeysToRemove.add(K);
            }
        }

        for (Integer K : KeysToRemove) {
            collection.remove(K);
        }

        return KeysToRemove.size();
    }
}
