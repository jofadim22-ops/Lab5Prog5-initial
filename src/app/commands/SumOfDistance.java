package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.util.Objects;

public class SumOfDistance extends AbstractCommand {
    public SumOfDistance() {
        super("sum_of_distance", "Calculate the sum of distance", " sum_of_distance");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        float sum = manager.getAll().stream()
                .filter(Objects::nonNull)
                .map(Route::getDistance)
                .filter(Objects::nonNull)
                .reduce(0f, Float::sum);
        System.out.println("Sum of distances: " + sum);
    }
}
