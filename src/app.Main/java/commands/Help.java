package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class Help extends Abstract {
    private final CommandRegistry registry;

    public Help(CommandRegistry registry) {
        super("help", "Display help on available commands", "help");
        this.registry = registry;
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException {

        System.out.println("=== Available Commands ===");
        registry.getCommandNames().forEach(name ->
                System.out.printf(" %-30s %s%n", name, registry.get(name).getDescription()));
    }
}
