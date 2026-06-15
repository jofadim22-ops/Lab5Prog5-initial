package app.commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;

import java.io.IOException;

public class Help extends AbstractCommand {
    public Help() {
        super("help", "Display help information", "help [command]");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
            throws CommandException, IOException, InvalidDataException {
        System.out.println("Available commands:");
        System.out.println("  help - Display help information");
        System.out.println("  info - Display all elements");
        System.out.println("  show - Display all elements");
        System.out.println("  insert <Key> - Add a new element");
        System.out.println("  update <id> - Update element by id");
        System.out.println("  remove_greater - Remove elements by Key");
        System.out.println("  remove_lower - Remove elements greater than given");
        System.out.println("  remove_greater_Key <Key> - Remove elements with Key greater than given");
        System.out.println("  sum_of_distance - Calculate sum of distances");
        System.out.println("  count_by_distance <distance> - Count elements with specific distance order");
        System.out.println("  print_field_descending_distance - Print distances in descending order");
        System.out.println("  clear - Clear the collection");
        System.out.println("  save - Save collection to file");
        System.out.println(" execute_Script <file> - Execute commands from file");
        System.out.println("  exit - Terminate program");
    }
}
