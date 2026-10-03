package app.commands;

import exceptions.CommandException;
import exceptions.InvalidDataException;
import exceptions.ScriptRecursionException;
import managers.CollectionManager;
import utils.ConsoleInputReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ExecuteScript extends AbstractCommand {

    private CommandRegistry registry;

    private static final ThreadLocal<Set<String>> executingScripts =
            ThreadLocal.withInitial(HashSet::new);

    public ExecuteScript() {
        super("execute_script", "Execute commands from a file",
                "execute_script <file_name>", false);
    }

    public void setRegistry(CommandRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void execute(String[] args, CollectionManager manager,
                        ConsoleInputReader inputReader)
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

            String absolutePath;
            try {
                absolutePath = file.getCanonicalPath();
            } catch (IOException e) {
                absolutePath = file.getAbsolutePath();
            }

            Set<String> currentScripts = executingScripts.get();

            if (currentScripts.contains(absolutePath)) {
                throw new ScriptRecursionException(
                        "Script recursion detected: " + absolutePath);
            }

            currentScripts.add(absolutePath);

            try (Scanner scanner = new Scanner(file)) {
                int lineNumber = 0;
                while (scanner.hasNextLine()) {
                    lineNumber++;
                    String line = scanner.nextLine().trim();

                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }

                    String[] parts = line.split("\\s+", 2);
                    String commandName = parts[0];
                    String[] commandArgs = parts.length > 1 ? parts[1].split("\\s+") : new String[0];

                    Command command = registry.getCommand(commandName);
                    if (command == null) {
                        System.out.println("Unknown command: " + commandName);
                        continue;

                    }

                    command.execute(commandArgs, manager, inputReader);

                }

                    System.out.println("Script execution completed: " + filename);

                } catch(ScriptRecursionException e) {
                    throw e;
                } catch(IOException e) {
                    throw new CommandException("Error reading script file: " + e.getMessage());
                } finally {

                    currentScripts.remove(absolutePath);

                }
            }
        }
