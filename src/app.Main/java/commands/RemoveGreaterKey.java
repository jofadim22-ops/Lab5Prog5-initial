package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class RemoveGreaterKey extends Abstract {
    public RemoveGreaterKey() {
        super("remove_greater_Key", "Remove elements with Key > threshold", "remove_greater_Key null", true);
    }
    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException {
        validateArgs(args, 1, "remove_greater_Key");
        if (!"null".equals(args[0].trim()))
            throw new CommandException("Usage: remove_greater_Key null (Key will be prompted)");

        Integer Key = inputReader.readInteger("Enter Key threshold", false, 1, null);
        int removed = manager.removeGreaterKey(Key);
        System.out.println("Removed " + removed + " element(s) with Key > " + Key);
    }
}
