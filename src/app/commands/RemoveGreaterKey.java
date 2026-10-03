package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RemoveGreaterKey extends AbstractCommand {
    public RemoveGreaterKey() {
        super("remove_greater_Key", "remove elements with Key greater than given", "remove_greater_Key {Key}", false);
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        validateArgs(args, 1, "remove_greater_Key");

        Integer Key;
        try {
            Key = Integer.parseInt(args[0].trim());
        } catch (NumberFormatException e) {
            throw new CommandException("Key must be a valid integer.");
        }

        int removedCount = 0;
        var iterator = manager.getCollection().entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Integer, Route> entry = iterator.next();
            if (entry.getKey() > Key) {
                iterator.remove();
                removedCount++;
            }
        }

        if (removedCount > 0) {
            System.out.println("Successfully removed " + removedCount + "element(s).");
        } else {
            System.out.println("No elements found with a key greater than " + Key + ".");
        }
    }
}
