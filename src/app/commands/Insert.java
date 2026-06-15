package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.util.Date;

public class Insert extends AbstractCommand {
    public Insert() {
        super("insert", "Add a new element to the collection", "insert <Key>");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        validateArgs(args, 1, "insert");

        Integer Key;
        try {
            Key = Integer.parseInt(args[0].trim());
        } catch (NumberFormatException e) {
            throw new CommandException("Key must be a valid integer");
        }

        if (manager.containsKey(Key)) {
            throw new CommandException("An element with Key " + Key + " already exists");
        }

        Route route = inputReader.readRoute(false);

        if (route.getId() == null) route.assignNewId();
        if (route.getCreationDate() == null) route.setCreationDate(new Date());

        manager.put(Key, route);
        System.out.println("Element inserted successfully");
    }
}
