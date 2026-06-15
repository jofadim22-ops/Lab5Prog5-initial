package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PrintFieldDescendingDistance extends AbstractCommand {
    public PrintFieldDescendingDistance() {
        super("print_field_descending_distance",
                "print distance field values in descending order",
                "print_field_descending_distance");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        List<Float> distances = new ArrayList<>();
        for (Route route : manager.getAll()) {
            if (route != null && route.getDistance() != null) {
                distances.add(route.getDistance());
            }
        }

        Collections.sort(distances);
        Collections.reverse(distances);

        if (distances.isEmpty()) {
            System.out.println("No distances to display");
            return;
        }

        System.out.println("Distance field values (descending):");
        for (int i = 0; i < distances.size(); i++) {
            System.out.printf("%d. %.2f%n", i + 1, distances.get(i));
        }
    }
}
