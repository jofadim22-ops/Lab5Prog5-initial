package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoveGreater extends AbstractCommand {
    public RemoveGreater() {
        super("remove_greater", "Remove elements greater than the given one", "remove_greater");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        System.out.println("Enter route to compare against:");
        Route threshold = inputReader.readRoute(false);

        List<Integer> toRemove = new ArrayList<>();
        for (Route route : manager.getAll()) {
            if (route != null && route.compareTo(threshold) > 0) {
                toRemove.add(route.getId());
            }
        }

        for (Integer id : toRemove) {
            manager.remove(id);
        }

        System.out.println("Removed " + toRemove.size() + " elements");
    }
}
