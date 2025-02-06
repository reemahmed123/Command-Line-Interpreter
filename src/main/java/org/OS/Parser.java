package org.OS;

import java.util.Arrays;

public class Parser {
    public Command parse(String input) {
        String[] inputSplit = input.split(" ");

        // Check if the input is empty
        if (inputSplit.length == 0) {
            System.out.println("ERROR: No command entered!");
            return null;  // Return null if no command is provided
        }
        // Extract command name
        String commandName = inputSplit[0];
        // Extract arguments (all elements after the command name)
        String[] arguments = Arrays.copyOfRange(inputSplit, 1, inputSplit.length);
        // Extract arguments (all elements after the command name)
        arguments = Arrays.copyOfRange(inputSplit, 1, inputSplit.length);

        for (int i = 0; i < inputSplit.length; i++) {
            if (inputSplit[i].equals("|")) {

                commandName = "|";
                // Extract arguments (all elements after the command name)
                arguments = Arrays.copyOfRange(inputSplit, 0, inputSplit.length);
            } else if (inputSplit[i].equals(">")) {
                commandName = ">";
                // Extract arguments (all elements after the command name)
                arguments = Arrays.copyOfRange(inputSplit, 0, inputSplit.length);
            } else if (inputSplit[i].equals(">>")) {
                commandName = ">>";
                // Extract arguments (all elements after the command name)
                arguments = Arrays.copyOfRange(inputSplit, 0, inputSplit.length);
            }
        }

            // Validate command and arguments
        if (isInvalidCommand(commandName, arguments)) {
            return null;  // Return null if command is invalid
        }

        // Return a new Command object with the parsed name and arguments
        return new Command(commandName, arguments);
    }

    public boolean isInvalidCommand(String commandName, String[] args) {
        // Check for commands that do not take arguments
        if ((commandName.equals("pwd") || commandName.equals("help")) && args.length != 0) {
            System.out.println("ERROR: " + commandName + " does not take any arguments!");
            return true;
        }

        // Check for commands that require at least one argument
        if ( (commandName.equals("rmdir")  || commandName.equals("mkdir") ||
                commandName.equals("touch")||commandName.equals("rm")) && args.length == 0) {
            System.out.println("ERROR: " + commandName + " requires at least one argument!");
            return true;
        }

        // Check for commands that require exactly one argument
        /*if ((commandName.equals("echo") && args.length != 1)){
            System.out.println("ERROR: " + commandName + " requires exactly one argument!");
            return true;
        }*/

        // If no issues found, command is valid
        return false;
    }
}
