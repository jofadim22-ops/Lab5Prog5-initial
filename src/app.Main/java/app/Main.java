package app;

import managers.CollectionManager;
import managers.CommandExecutor;
import utils.ConsoleInputReader;
import commands.CommandRegistry;
import commands.ExecuteScript;

public class Main {

    public static void main(String[] args) {

        String filePath = resolveFilePath(args);
        if (filePath == null) {
            System.exit(1);
        }

        System.out.println("Application starting.Using data file: " + filePath);

        CollectionManager collectionManager;
        try {
            collectionManager = new CollectionManager(filePath);
        } catch (Exception e) {
            System.err.println("Critical error during initialization: " + e.getMessage());
            System.exit(2);
            return;
        }


        CommandRegistry registry = new CommandRegistry();
        registry.registerAll();

        try (ConsoleInputReader inputReader = new ConsoleInputReader()) {
            CommandExecutor commandExecutor = new CommandExecutor (collectionManager, inputReader, registry);

            ExecuteScript scriptCmd = (ExecuteScript) registry.get("execute_script");
            if (scriptCmd != null) {
                scriptCmd.setExecutor(commandExecutor);
            }

            commandExecutor.startInteractiveMode();

        } catch (Exception e) {
            System.err.println("Critical error in command loop" + e.getMessage());
            System.exit(3);
        }


        System.out.println("Application terminated normally.");
    }

    private static String resolveFilePath(String[] args) {
        String path = System.getenv("ROUTE_FILE_PATH");
        if (path == null || path.trim().isEmpty()) {
            if (args.length >= 1 && !args[0].trim().isEmpty()) {
                path = args[0].trim();
            }
        }
        if (path == null || path.trim().isEmpty()) {
            System.err.println("Error: Data file path not provided.");
            System.err.println("Usage: java app.Main <filepath");
            return null;
        }

        return path;
    }
}
