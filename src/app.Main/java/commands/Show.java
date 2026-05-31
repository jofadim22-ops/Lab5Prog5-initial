package commands;

import model.Route;
import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;
import java.util.List;

public class Show extends Abstract {

    public Show() {
        super("show", "print all elements of the collection", "show");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException {
        List<Route> routes = manager.getSortedRoutes();
        if (routes.isEmpty()) {
            System.out.println("Collection is empty.");
            return;
        }

        System.out.println("collection contents (" + routes.size() + " elements):");
        System.out.println("_".repeat(60));
        for (Route route : routes) {
            System.out.println(" " + route);
        }
        System.out.println("_".repeat(60));
    }
}
