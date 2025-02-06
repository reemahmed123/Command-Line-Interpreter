package org.OS;

public class Command {
    String commandName;
    String[] Arguments;

    public Command(String commandName, String[] arguments) {
        this.commandName = commandName;
        this.Arguments = arguments;
    }
    public String getCommandName(){
        return commandName;
    }
    public String[] getArguments(){
        return Arguments;
    }
}
