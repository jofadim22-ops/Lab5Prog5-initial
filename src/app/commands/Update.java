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
        super("update", "Update element by id", "update <id>");
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

        if (!manager.containsKey(id)) {
            throw new CommandException("No element found with ID " + id);
        }

        Route oldRoute = manager.get(id);
        System.out.println("Enter new route details (leave empty to keep current value):");

        Route newRoute = createUpdateRouteFromInput(inputReader, oldRoute);

        if (oldRoute.getCreationDate() != null) {
            newRoute.setCreationDate(oldRoute.getCreationDate());
        }

        newRoute.setId(0);

        manager.put(id, newRoute);
        System.out.println("Element with ID " + id + " updated successfully");
    }

    private Route createUpdateRouteFromInput(ConsoleInputReader inputReader, Route oldRoute)
            throws IOException, CommandException {
        Route route = new Route();

        System.out.println("Enter name [" + oldRoute.getName() + "]: ");
        String nameInput = inputReader.readLine().trim();
        route.setName(nameInput.isEmpty() ? oldRoute.getName() : nameInput);

        System.out.println("Enter coordinates x [" + oldRoute.getCoordinates().getX() + "]: ");
        String xInput = inputReader.readLine().trim();
        Integer x = xInput.isEmpty() ? oldRoute.getCoordinates().getX() : parseInteger(xInput, "X");

        System.out.println("Enter coordinates y [" + oldRoute.getCoordinates().getY() + "]: ");
        String yInput = inputReader.readLine().trim();
        Integer y = yInput.isEmpty() ? oldRoute.getCoordinates().getY() : parseInteger(yInput, "Y");

        route.setCoordinates(new Coordinates(x, y));

        System.out.println("Enter distance [" + oldRoute.getDistance() + "]: ");
        String distInput = inputReader.readLine().trim();
        route.setDistance(distInput.isEmpty() ? oldRoute.getDistance() : parseFloat(distInput, "Distance"));

        return route;
    }
}
