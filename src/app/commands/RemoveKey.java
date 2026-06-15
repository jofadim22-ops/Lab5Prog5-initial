package app.commands;

import managers.CollectionManager;
import utils.ConsoleInputReader;
import exceptions.CommandException;
import exceptions.InvalidDataException;
import java.io.IOException;

public class RemoveKey extends AbstractCommand {
    public RemoveKey() {
        super("remove_Key", "Remove element by Key", "remove_Key <Key>");
    }

    @Override
    public void execute(String[] args, CollectionManager manager, ConsoleInputReader inputReader)
             throws CommandException, IOException, InvalidDataException {

        Integer Key;

        if (args.length > 0 && !"null".equalsIgnoreCase(args[0].trim())) {
            try {
                Key = Integer.parseInt(args[0].trim());
            } catch (NumberFormatException e) {
                throw new CommandException("Key must be a valid integer");
            }
        } else {
            System.out.println("Enter Key to remove: ");
            String KeyInput = inputReader.readLine();
            try {
                Key = Integer.parseInt((KeyInput.trim()));
            } catch (NumberFormatException e){
                throw new CommandException("Key must be a valid integer");
            }
        }

        if (manager.remove(Key)) {
            System.out.println("Element with Key " + Key + " removed successfully.");
        } else {
            throw new CommandException("No element found with Key: " + Key);
        }
    }
}
