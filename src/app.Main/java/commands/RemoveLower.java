package commands;

import exceptions.InvalidDataException;
import model.Route;
import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;

import java.io.IOException;

public class RemoveLower extends Abstract {
    public RemoveLower() {
        super("remove_lower", "Remove elements less than threshold", "remove_lower", true);
    }
    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        System.out.println("Enter threshold element for comparison:");
        Route threshold = inputReader.readRoute(false);
        int removed = manager.removeLower(threshold);
        System.out.println("Removed " + removed + " element(s).");
    }
}
