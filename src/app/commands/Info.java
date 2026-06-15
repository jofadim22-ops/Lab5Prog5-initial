package app.commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class Info extends AbstractCommand {
    public Info() {
        super("info", "Display collection information", "info");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        System.out.println(manager.getInfo());
    }
}
