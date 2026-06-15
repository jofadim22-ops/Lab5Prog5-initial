package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.util.Objects;

public class CountByDistance extends AbstractCommand {
    public CountByDistance() {
        super("count_by_distance", "Count elements with specific distance", "count_by_distance <distance>");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        validateArgs(args, 1, "count_by_distance");

        Float distance;
        try {
            distance = Float.parseFloat(args[0].trim());
        } catch (NumberFormatException e) {
            throw new CommandException("Distance must be a valid number");
        }

        int count = (int) manager.getAll().stream()
                .filter(Objects::nonNull)
                .map(Route::getDistance)
                .filter(d -> d != null && Float.compare(d, distance) == 0)
                .count();

        System.out.println("Count of elements with distance " + distance + ": " + count);
    }
}
