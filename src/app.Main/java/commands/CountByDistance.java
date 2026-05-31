package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class CountByDistance extends Abstract {
    public CountByDistance() {
        super("count_by_distance", "Count elements with exact distance", "count_by_distance");
    }
    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException {
        validateArgs(args, 1, "count_by_distance");
        Float distance = parseFloat(args[0], "Distance");
        if (distance == null || distance <= 1)
            throw new CommandException("Distance must be > 1");

        int count = manager.countByDistance(distance);
        System.out.println("count: " + count);
    }
}
