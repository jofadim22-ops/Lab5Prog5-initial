package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class Exit extends Abstract {
    public Exit() {
        super("exit", "Terminate program Without saving", "exit");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException {
        System.out.println("Exiting program...");
        System.exit(0);
    }
}
