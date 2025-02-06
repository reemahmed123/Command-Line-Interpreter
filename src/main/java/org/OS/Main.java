package org.OS;
//import java.io.IOException;
import java.util.Scanner;


public class Main{
    public static void main(String[] args)  {
        Parser parser = new Parser();
        Cli cli = new Cli();
        String userInput;
        boolean run = true ;
        System.out.println("Welcome to the CLI! Type 'exit' to quit.");
        Scanner input = new Scanner(System.in);
        while (run){
            System.out.print(">");
            userInput = input.nextLine().trim();
            if (userInput.equals("exit")){
                run = false ;
            }
            Command command = parser.parse(userInput);
            if (command==null) {
                // Invalid command or argument, skip to the next loop iteration
                System.out.print("Invalid Command , Please Try again" );
                continue;
            }
            cli.chooseCommandAction(command.getCommandName(), command.getArguments());
        }
        // Close the Scanner at the end
        input.close();
        System.out.println("Exiting the CLI. Goodbye!");
    }
}