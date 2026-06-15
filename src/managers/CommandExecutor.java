package managers;

import app.commands.Command;
import app.commands.CommandRegistry;
import utils.ConsoleInputReader;
import utils.XmlFileHandler;
import exceptions.CommandException;
import exceptions.InvalidDataException;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

public class CommandExecutor {

    private final CollectionManager collectionManager;
    private final ConsoleInputReader inputReader;
    private final CommandRegistry commandRegistry;
    private final Stack<String> scriptCallStack = new Stack<>();
    private static final int MAX_SCRIPT_DEPTH = 5;
    private boolean running = true;

    public CommandExecutor(CollectionManager collectionManager,
                           ConsoleInputReader inputReader,
                           CommandRegistry commandRegistry) {
        this.collectionManager = collectionManager;
        this.inputReader = inputReader;
        this.commandRegistry = commandRegistry;
    }

    public void stop() {
        running = false;
    }

    public void startInteractiveMode() {
        System.out.println("Route Collection Manager started. Type 'help' for commands.");
        this.running = true;

        try {
            String line;
            while (running && (line = inputReader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                processCommand(line, false);
            }
        } catch (IOException e) {
            System.err.println("Input error: " + e.getMessage());
        }
    }

    private void processCommand(String commandLine, boolean isScript) {
        try {
            String[] parts = commandLine.trim().split("\\s+", 2);
            String commandName = parts[0].toLowerCase();
            String[] args = parts.length > 1 ? new String[]{parts[1]} : new String[0];

            Command command = commandRegistry.get(commandName);
            if (command == null) {
                System.err.println("Unknown command: " + commandName + "'. Type 'help' for list.");
                return;
            }

            command.execute(args, collectionManager, inputReader);

        } catch (CommandException e) {
            System.err.println("Command Error: " + e.getMessage());
            Command cmd = commandRegistry.get(commandLine.split("\\s+")[0]);
            if (cmd != null) {
                System.err.println("Usage: " + cmd.getUsage());
            }
        } catch (InvalidDataException e) {
            System.err.println("Invalid Data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Number Format Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Argument: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("File Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getClass().getSimpleName() +
                    " - " + e.getMessage());
        }
    }

    public void executeScript(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            System.err.println("Cannot resolve script path: " + filename);
            return;
        }

        String canonicalPath;
        try {
            canonicalPath = new File(filename).getCanonicalPath();
        } catch (IOException e) {
            System.err.println("Cannot resolve script path: " + filename);
            return;
        }

        if (scriptCallStack.contains(canonicalPath)) {
            System.err.println("Circular script reference detected: " + filename);
            return;
        }

        if (scriptCallStack.size() >= MAX_SCRIPT_DEPTH) {
            System.out.println("Executing script: " + filename);

            try(BufferedReader scriptReader =  new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = scriptReader.readLine()) != null) {
                    line = line.trim();

                    if (line.isEmpty() || line.startsWith("#") || line.startsWith("//")) {
                        continue;
                    }

                    processCommand(line, true);
                }
            } catch (IOException e) {
                System.err.println("Scipt file error: " + e.getMessage());
            } finally {
                scriptCallStack.pop();
                System.out.println("Script execution completed: " + filename);
            }
        }
    }
}
