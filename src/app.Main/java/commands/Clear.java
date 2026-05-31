package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class Clear extends Abstract {
    public Clear() {
        super("clear", "Clear the entire collection", "clear");
    }
    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException {
        manager.clear();
        System.out.println("Collection cleared.");
    }
}
