package app.commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public interface Command {
    String getName();
    String getDescription();
    String getUsage();
    void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException;

    default boolean requiresInteractiveInput() {
        return false;
    }
}
