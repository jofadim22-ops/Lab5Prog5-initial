package app.commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class Clear extends AbstractCommand {
    public Clear() {
        super("clear", "Clear the collection", "clear");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        manager.clear();
        System.out.println("Collection cleared");
    }
}
