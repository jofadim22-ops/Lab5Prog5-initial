package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class Save extends Abstract {

    public Save() {
        super("save" , "Save collection to file", "save");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException {
        manager.save();
        System.out.println("Collection saved to file.");
    }
}
