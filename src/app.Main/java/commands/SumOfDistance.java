package commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import java.io.IOException;

public class SumOfDistance extends Abstract {

    public SumOfDistance() {
        super("sum_of_distance", "Sum of all distance field values", "sum_of_distance");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
             throws CommandException, IOException {

        System.out.println("Sum Of distances: " + manager.sumOfDistance());
    }
}
