package app.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandRegistry {

    public final Map<String, Command> commands = new HashMap<>();

    public void register(Command command) {
        if (command == null) throw new IllegalArgumentException("Command cannot be null");
        commands.put(command.getName().toLowerCase(), command);
    }

    public Command get(String name) {
        return commands.get(name.toLowerCase());
    }

    public boolean contains(String name) {
        return commands.containsKey(name.toLowerCase());
    }

    public Set<String> getCommandNames() {
        return commands.keySet();
    }

    public void registerAll() {
        register(new Help());
        register(new Info());
        register(new Show());
        register(new Insert());
        register(new Update());
        register(new Clear());
        register(new Save());
        register(new ExecuteScript());
        register(new Exit());
        register(new RemoveGreater());
        register(new RemoveLower());
        register(new RemoveGreaterKey());
        register(new SumOfDistance());
        register(new CountByDistance());
        register(new PrintFieldDescendingDistance());
        register(new RemoveKey());
    }
}
