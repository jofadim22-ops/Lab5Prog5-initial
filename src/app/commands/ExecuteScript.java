package app.commands;

import managers.CollectionManager;
import managers.CommandExecutor;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class ExecuteScript extends AbstractCommand {
    private CommandExecutor executor;

    public ExecuteScript() {
        super("execute_script", "Execute commands from a file", "execute_script file_name");
    }

    public void setExecutor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {

        validateArgs(args, 1, "execute_script");

        if (executor == null) {
            throw new CommandException("Internal error: CommandExecutor not injected.");
        }

        executor.executeScript(args[0].trim());
    }
}
