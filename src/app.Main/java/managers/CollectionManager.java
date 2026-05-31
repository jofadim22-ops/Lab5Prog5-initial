package managers;

import model.Route;
import utils.XmlFileHandler;
import java.io.IOException;
import java.security.Key;
import java.util.*;

public class CollectionManager {
    private final Hashtable<Integer, Route> collection;
    private final Date initializationDate;
    private final String filePath;

    public CollectionManager(String filePath) throws IOException, Exception {
        this.filePath = filePath;
        this.initializationDate = new Date();
        this.collection =new Hashtable<>();

        if (java.nio.file.Files.exists(java.nio.file.Paths.get(filePath))) {
            Hashtable<Integer, Route> loaded = XmlFileHandler.readFromFile(filePath);
            if (loaded != null) {
                collection.putAll(loaded);
                loaded.keySet().stream().max(Integer::compareTo)
                        .ifPresent(maxId -> Route.updateNextId(maxId));
            }
        }
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

    public boolean insert(Integer Key, Route route) {
        if (Key == null || route == null) return false;

        if (route.getId() == null) {
            route.assignNewId();
        }

        if (route.getCreationDate() == null) {
            route.setCreationDate(new Date());
        }

        collection.put(Key, route);
        return true;
    }

    public boolean update(Integer id, Route newRoute) {
        if (!collection.containsKey(id)) return false;

        newRoute.setId(id);

        if (newRoute.getCreationDate() == null) {
            Route original = collection.get(id);
            if (original != null && original.getCreationDate() != null) {
                newRoute.setCreationDate(original.getCreationDate());
            }
        }

        collection.put(id, newRoute);
        return true;
    }

    public boolean removeKey(Integer Key) {
        return collection.remove(Key) != null;
    }

    public int removeGreater(Route threshold) {
        if (threshold == null) return 0;

        List<Integer> toRemove = collection.keySet().stream()
                .filter(Key -> {
                    Route route = collection.get(Key);
                    return  route != null && route.compareTo(threshold) < 0;
                })
                .toList();

        toRemove.forEach(collection::remove);
        return toRemove.size();
    }

    public int removeLower(Route threshold) {
        if (threshold == null) return 0;

        List<Integer> toRemove = collection.keySet().stream()
                .filter(key -> {
                    Route route = collection.get(key);
                    return route != null && route.compareTo(threshold) > 0;
                })
                .toList();

        toRemove.forEach(collection::remove);
        return toRemove.size();
    }

    public int removeGreaterKey(Integer thresholdKey) {
        if (thresholdKey == null) return 0;

        List<Integer> toRemove = collection.keySet().stream()
                .filter(Key -> Key != null && Key > thresholdKey)
                .toList();

        toRemove.forEach(collection::remove);
        return toRemove.size();
    }

    public void clear() {
        collection.clear();
    }

    public void save() throws IOException {
        XmlFileHandler.writeToFile(filePath, collection);
    }

    public float sumOfDistance() {
        return collection.values().stream()
                .filter(Objects::nonNull)
                .map(Route::getDistance)
                .filter(Objects::nonNull)
                .reduce(0f, Float::sum);
    }

    public int countByDistance(Float distance) {
        if (distance == null) return 0;

        return (int) collection.values().stream()
                .filter(Objects::nonNull)
                .map(Route::getDistance)
                .filter(d -> d != null && Float.compare(d, distance) == 0)
                .count();
    }

    public void printDistancesDescending() {
        List<Float> distances = collection.values().stream()
                .filter(Objects::nonNull)
                .map(Route::getDistance)
                .filter(Objects::nonNull)
                .sorted(Comparator.reverseOrder())
                .toList();

        if (distances.isEmpty()) {
            System.out.println(" No distances to display");
            return;
        }

        System.out.println(" Distances (descending):");
        for (int i = 0; i < distances.size(); i++) {
            System.out.printf(" %d. %.2f%n", i + 1, distances.get(i));
        }
    }


    public Hashtable<Integer, Route> getCollection() {
        return collection;
    }
}
