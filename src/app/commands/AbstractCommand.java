package app.commands;

import exceptions.CommandException;
import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.InvalidDataException;
import java.io.IOException;

public abstract class AbstractCommand implements Command {

    private final String name;
    private final String description;
    private final String usage;
    private final boolean requiresIntercative;

    protected AbstractCommand(String name, String description, String usage) {
        this(name, description, usage, false);
    }

    protected AbstractCommand(String name, String description, String usage, boolean requiresIntercative) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.requiresIntercative = requiresIntercative;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public boolean requiresInteractiveInput() {
        return requiresIntercative;
    }

    protected void validateArgs(String[] args, int expectedCount, String commandName)
        throws CommandException {
        if (args.length < expectedCount){
            throw new CommandException(
                    "Insufficient arguments for '" + commandName + "'. Usage: " + getUsage());
        }
    }

    protected Integer parseInteger(String arg, String fieldName) throws CommandException {
        try {
            return Integer.parseInt(arg.trim());
        } catch (NumberFormatException e) {
            throw new CommandException(
                    fieldName + " must be a valid Integer, got: '" + arg + "'");
        }
    }

    protected Float parseFloat(String arg, String fieldName) throws CommandException {
        try {
            return Float.parseFloat(arg.trim());
        } catch (NumberFormatException e) {
            throw new CommandException(
                    fieldName + " must be a valid number, got: '" + arg + "'");
        }
    }
}
