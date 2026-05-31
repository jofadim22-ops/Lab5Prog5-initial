package commands;

import model.Route;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import managers.CollectionManager;
import utils.ConsoleInputReader;
import java.io.IOException;

public class RemoveGreater extends Abstract {

    public RemoveGreater() {
        super("remove_greater", "Remove elements greater than threshold", "remove_greater", true);
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
             throws CommandException, IOException, InvalidDataException {
        System.out.println("Enter threshold element for comparison:");
        Route threshold = inputReader.readRoute(false);
        int removed = manager.removeGreater(threshold);
        System.out.println("Removed" + removed + " element(s).");
    }
}
