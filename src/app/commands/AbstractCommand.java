package app.commands;

import exceptions.CommandException;
import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.InvalidDataException;
import java.io.IOException;

public abstract class AbstractCommand implements Command {
    protected String name;
    protected String description;
    protected String usage;
    protected boolean requiresInteractive;

    protected AbstractCommand(String name, String description, String usage, boolean requiresInteractive) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.requiresInteractive = requiresInteractive;
    }

    @Override
    public String getName() { return name; }

    @Override
    public String getDescription() { return description; }

    @Override
    public String getUsage() { return usage; }

    @Override
    public boolean requiresInteractiveInput() { return requiresInteractive; }

    protected void validateArgs(String[] args, int expectedCount, String commandName)
        throws CommandException {
        if (args.length < expectedCount) {
            throw new CommandException(
                    "Insufficient arguments for '" + commandName + "'. Usage: " + getUsage());
        }
    }
}
