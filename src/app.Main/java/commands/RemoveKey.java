package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class RemoveKey extends Abstract {
    public RemoveKey() {
        super("remove_Key", "Remove element by Key", "remove_Key null", true);
    }
    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader  inputReader)
            throws CommandException, IOException {
        validateArgs(args, 1, "remove_Key");
        if (!"null".equals(args[0].trim()))
            throw new CommandException("Usage: remove_Key null (Key will be prompted)");

        Integer Key = inputReader.readInteger("Enter Key to remove", false, 1, null);
        if (manager.removeKey(Key)) System.out.println("Element removed.");
        else throw new CommandException("No element found with Key: " + Key);
    }
}
