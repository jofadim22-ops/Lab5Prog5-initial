package app.commands;

import model.Route;
import managers.CollectionManager;
import model.Coordinates;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class Update extends AbstractCommand {
    public Update() {
        super("update", "Update element by id", "update <id>", false);
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        validateArgs(args, 1, "update");

        Integer id;
        try {
            id = Integer.parseInt(args[0].trim());
        } catch (NumberFormatException e ) {
            throw new CommandException("ID must be a valid integer");
        }

        Route oldRoute = manager.getRoute(id);
        if (oldRoute == null) {
            throw new CommandException("No element found with ID: " + id);
        }

        System.out.println("Updating Route with ID: " + id);
        System.out.println("(Leave a field blank to keep its current value: " + oldRoute.toString() + ")");

        Route newRoute = inputReader.readRoute(true);

        newRoute.setId(id);
        manager.put(id, newRoute);

        System.out.println("Element with ID " + id + " successfully updated.");
        }
    }
