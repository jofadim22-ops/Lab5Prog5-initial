package app.commands;

import managers.CollectionManager;
import model.Route;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RemoveLower extends AbstractCommand {
    public RemoveLower() {
        super("remove_lower", "Remove elements lower than the given one", "remove_lower");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        System.out.println("Enter coordinates x [100]:");
        String xInput = inputReader.readLine().trim();
        int x = xInput.isEmpty() ? 100 : Integer.parseInt(xInput);

        System.out.println("Enter coordiantes y [200]:");
        String yInput = inputReader.readLine().trim();
        int y = yInput.isEmpty() ? 200 : Integer.parseInt(yInput);

        System.out.println("Enter diatance [50.5]:");
        String distInput = inputReader.readLine().trim();
        float distance = distInput.isEmpty() ? 50.5f : Float.parseFloat(distInput);

        int removedCount = manager.removeGreaterByCriteria(x, y, distance);
        System.out.println("Removed " + removedCount + " elements");


    }
}
