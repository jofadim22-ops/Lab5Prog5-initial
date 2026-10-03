package app.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandRegistry {

    public final Map<String, Command> commands = new HashMap<>();

    public void register(String name, Command command) {
        commands.put(name, command);
    }

    public Command getCommand(String name) {
        return commands.get(name);
    }

    public boolean contains(String name) {
        return commands.containsKey(name);
    }

    public Set<String> getCommandNames() {
        return commands.keySet();
    }
}
