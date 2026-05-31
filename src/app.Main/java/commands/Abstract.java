package commands;

import exceptions.CommandException;

public abstract class Abstract implements Command {

    private final String name;
    private final String description;
    private final String usage;
    private final boolean requiresInteractive;

    protected Abstract(String name, String description, String usage) {
        this(name, description, usage, false);
    }

    protected Abstract(String name, String description, String usage, boolean requiresInteractive) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.requiresInteractive = requiresInteractive;
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
        return requiresInteractive;
    }

    protected void validateArgs(String[] args, int expectedCount, String commandName)
            throws CommandException {
        if (args.length < expectedCount) {
            throw new CommandException(
                    "Insufficient arguments for '" + commandName + "'. Usage: " + getUsage());
        }
    }

    protected Integer parseInteger(String arg, String fieldName) throws CommandException {
        try {
            return Integer.parseInt(arg.trim());

        } catch (NumberFormatException e) {
            throw new CommandException(
                    fieldName + "must be a valid integer, got: '" + arg + "'");
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
