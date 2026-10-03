package managers;

import app.commands.Command;
import app.commands.CommandRegistry;
import exceptions.ScriptRecursionException;
import utils.ConsoleInputReader;

import java.rmi.registry.Registry;
import java.util.Scanner;

public class CommandExecutor {
    private final CommandRegistry registry;
    private final CollectionManager collectionManager;
    private final ConsoleInputReader inputReader;
    private final Scanner scanner;

    public CommandExecutor(CommandRegistry registry,
                           CollectionManager collectionManager,
                           ConsoleInputReader inputReader) {
        this.registry = registry;
        this.collectionManager = collectionManager;
        this.inputReader = inputReader;
        this.scanner = new Scanner(System.in);
    }

    public String executeCommand(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }

        String[] parts = input.trim().split("\\s+", 2);
        String commandName = parts[0];
        String[] commandArgs = parts.length > 1 ? parts[1].split("\\s+") : new String[0];

        Command command = registry.getCommand(commandName);
        if (command == null) {
            return "Unknown command: '" + commandName + "'. Type 'help' for list.";
        }

        try {
            command.execute(commandArgs, collectionManager, inputReader);
            return null;
        } catch (ScriptRecursionException e) {
            return "Command Error: " + e.getMessage();
        } catch (Exception e) {
            return "Command Error: " + e.getMessage();
        }
    }

    public void startInteractiveMode() {
        System.out.println("Route Collection Manager started. Type 'help' for commands.");

        while (scanner.hasNextLine()) {
            System.out.println("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String result = executeCommand(input);

            if (result != null && !result.isEmpty()) {
                System.out.println(result);
            }

            if (input.startsWith("exit")) {
                break;

                }
            }
        }
    }