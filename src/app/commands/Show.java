package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class Show extends AbstractCommand {
    public Show() {
        super("show", "Display all elements", "show");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        if (manager.getAll().isEmpty()) {
            System.out.println("Collection is empty");
            return;
        }

        for (Route route : manager.getSortedRoutes()) {
            System.out.println(route);
        }
    }
}