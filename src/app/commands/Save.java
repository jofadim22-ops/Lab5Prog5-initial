package app.commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class Save extends AbstractCommand {
    public Save() {
        super("save", "Save collection to file", "save");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
             throws CommandException, IOException, InvalidDataException {

        manager.save();
        System.out.println("Collection saved to " + manager.getFilePath());
    }
}
