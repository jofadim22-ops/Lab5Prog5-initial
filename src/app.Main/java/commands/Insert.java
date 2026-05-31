package commands;

import model.Route;
import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class Insert extends Abstract {
    public Insert() {
        super("insert", "Add a new element with the given Key", "insert null", true);
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        validateArgs(args, 1, "insert");
        if (!"null".equals(args[0].trim()))
            throw new CommandException("Usage: insert null (element fields will be prompted");

        Integer key = inputReader.readInteger("Enter Key for new element", false, 1, null);
        System.out.println("Entering element fields:");
        Route route = inputReader.readRoute(false);
        manager.insert(key, route);
        System.out.println("Element inserted successfully.");
    }
}
