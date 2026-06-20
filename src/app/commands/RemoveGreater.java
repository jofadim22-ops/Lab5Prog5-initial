package app.commands;

import exceptions.CommandException;
import exceptions.InvalidDataException;
import managers.CollectionManager;
import model.Coordinates;
import model.Route;
import utils.ConsoleInputReader;

import java.io.IOException;

public class RemoveGreater extends AbstractCommand {
    public RemoveGreater() {
        super("remove_greater", "Remove elements greater than the given one", "remove_greater");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        System.out.println("Enter coordinates x [100]:");
        String xInput = inputReader.readLine().trim();
        int x = xInput.isEmpty() ? 100 : Integer.parseInt(xInput);

        System.out.println("Enter coordinates y [200]:");
        String yInput = inputReader.readLine().trim();
        int y = yInput.isEmpty() ? 200 : Integer.parseInt(yInput);

        System.out.println("Enter distance [50.5]:");
        String distInput = inputReader.readLine().trim();
        float distance = distInput.isEmpty() ? 50.5f : Float.parseFloat(distInput);

        int removedCount = manager.removeGreaterByCriteria(x, y, distance);
        System.out.println("Removed " + removedCount + "elements");
    }
}
