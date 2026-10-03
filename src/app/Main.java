package app;

import app.commands.*;
import managers.CollectionManager;
import managers.CommandExecutor;
import utils.ConsoleInputReader;

public class Main {
    public static void main(String[] args) {
        String filePath = args.length > 0 ? args[0] : "demo.xml";
        CollectionManager collectionManager = new CollectionManager(filePath);
        ConsoleInputReader inputReader = new ConsoleInputReader();
        CommandRegistry registry = new CommandRegistry();

        registry.register("info", new Info());
        registry.register("help", new Help());
        registry.register("clear", new Clear());
        registry.register("insert", new Insert());
        registry.register("show", new Show());
        registry.register("update", new Update());
        registry.register("sum_of_distance", new SumOfDistance());
        registry.register("count_by_distance", new CountByDistance());
        registry.register("print_field_descending_distance", new PrintFieldDescendingDistance());
        registry.register("remove_greater_key", new RemoveGreaterKey());
        registry.register("remove_key", new RemoveKey());
        registry.register("save", new Save());
        registry.register("execute_script", new ExecuteScript());
        registry.register("exit", new Exit());

        CommandExecutor commandExecutor = new CommandExecutor(
                registry, collectionManager, inputReader);


        ExecuteScript scriptCmd = (ExecuteScript) registry.getCommand("execute_script");
        if (scriptCmd != null) {
            scriptCmd.setRegistry(registry);
        }

        commandExecutor.startInteractiveMode();

    }
}
