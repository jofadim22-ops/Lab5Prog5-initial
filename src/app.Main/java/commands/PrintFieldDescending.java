package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class PrintFieldDescending extends Abstract {
    public PrintFieldDescending() {
        super("print_field_descending_distance",
                "print distance values in descending order",
                "print_field_descending_distance");
    }
    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException {
        manager.printDistancesDescending();
    }
}
