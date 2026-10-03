package app.commands;

import managers.CollectionManager;
import managers.CommandExecutor;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class Exit extends AbstractCommand {

    public Exit() {
        super("exit", "Terminate program without saving", "exit", false);
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        System.out.println("Exiting program...");
        System.exit(0);
    }
}
