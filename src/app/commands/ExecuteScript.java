package app.commands;

import managers.CollectionManager;
import managers.CommandExecutor;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExecuteScript extends AbstractCommand {

    private CommandExecutor executor;
    public ExecuteScript() {
        super("execute_script", "Execute commands from file", "execute_script <file>");
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        if (args.length < 1) {
            throw new CommandException("Usage: execute_script <filename>");
        }

        String filename = args[0];
        File file = new File(filename);

        if (!file.exists()) {
            System.err.println("Error: File not found: " + filename);
            return;
        }

        System.out.println("Executing script: " + filename);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                System.out.println("> " + line);

                String[] parts = line.split("\\s+", 2);
                String commandName = parts[0].toLowerCase();
                String[] commandArgs = parts.length > 1 ? parts[1].split("\\s+") : new String[0];

                System.out.println("  [Executing: " + commandName + "]");
                }
        }
    }
}