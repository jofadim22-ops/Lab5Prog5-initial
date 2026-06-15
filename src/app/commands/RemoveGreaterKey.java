package app.commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoveGreaterKey extends AbstractCommand {
    public RemoveGreaterKey() {
        super("remove_greater_Key", "Remove elements with Key greater than given", "remove_greater_Key <Key>");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        validateArgs(args, 1, "remove_greater_Key");

        Integer thresholdKey;
        try {
            thresholdKey = Integer.parseInt(args[0].trim());
        } catch (NumberFormatException e) {
            throw new CommandException("Key must be a valid integer");
        }

        List<Integer> toRemove = new ArrayList<>();
        for (Integer Key : manager.getCollection().keySet()) {
            if (Key != null && Key > thresholdKey) {
                toRemove.add(Key);
            }
        }

        System.out.println("Removed " + toRemove.size() + " elements");
    }
}
