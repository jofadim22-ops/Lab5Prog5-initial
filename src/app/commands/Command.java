package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;
import java.util.Map;

public interface Command {
    String getName();
    String getDescription();
    String getUsage();
    boolean requiresInteractiveInput();
    void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
        throws CommandException, IOException, InvalidDataException;

}

