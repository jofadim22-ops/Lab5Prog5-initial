package commands;

import model.Route;
import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class Update extends Abstract {
    public Update() {
        super("update", "Update element by ID", "update id", true);
    }
    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
             throws CommandException, IOException, InvalidDataException {
        validateArgs(args, 1, "update");
        if (!"id".equalsIgnoreCase(args[0].trim()))
            throw new CommandException("Usage: update id (element fields will be prompted)");
        Integer id = inputReader.readInteger("Enter ID to update", false, 1, null);
        System.out.println("Entering updated element fields:");
        Route route = inputReader.readRoute(true);
        if (manager.update(id, route)) System.out.println("Element updated.");
        else throw new CommandException("No element found with ID: " + id);
    }
}
