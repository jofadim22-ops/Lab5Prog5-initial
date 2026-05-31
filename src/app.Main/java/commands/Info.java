package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;


public class Info extends Abstract {
    public Info() {
        super("info", "print collection information (type, init date, size)", "info");
    }
    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
             throws CommandException, IOException {
        System.out.println(manager.getInfo());
    }
}
