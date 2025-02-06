package org.OS;

import java.io.*;
import java.util.*;

public class Cli {
    static File currentPath = new File(System.getProperty("user.dir"));

    public static void chooseCommandAction(String command, String[] args) {
        //String[] commands = command.split(" ");
        switch (command) {
            case "echo":
                System.out.println(echo(args));
                break;
            case "pwd":
                System.out.println(pwd());
                break;
            case "cd":
                cd(args);
                break;
            case "ls":
                System.out.println(ls(args));
                break;
            case "mkdir":
                mkdir(args);
                break;
            case "rmdir":
                rmdir(args);
                break;
            case "touch":
                touch(args);
                break;
            case "rm":
                rm(args,false);
                break;
            case "mv":
                mv(args);
                break;
            case "help":
                help();
                break;
            case "cat":
                CatCommand(args);
                break;
            case ">":
                OverwriteFile(args);
                break;
            case ">>":
                AppendToFile(args);
                break;
            case "|":
                PipeCommand(args);
                break;
            case "exit":
                break;
            default:
                System.out.println("Unknown command " + "\"" + command + "\"");
                break;
        }
    }
    public static String echo(String[] args) {
        return String.join(" ", args);
    }
    public void setCurrentPath(File newPath) {
        // Update the currentPath to a new File object representing the new path
        if (newPath.exists() && newPath.isDirectory()) {
            this.currentPath = newPath;
        } else {
            throw new IllegalArgumentException("Invalid path: " + newPath);
        }
    }

    public static String pwd() {//Print Working Directory
        return currentPath.getAbsolutePath();
    }

    public static void cd(String[] args) {
        if (args.length == 1) {
            // Handle "cd .." case to move to the parent directory
            if (args[0].equals("..")) {
                File parentPath = currentPath.getParentFile();
                if (parentPath != null && parentPath.exists()) {
                    currentPath = parentPath;
                    System.out.println("Moved to parent directory: " + currentPath.getAbsolutePath());
                } else {
                    System.out.println("ERROR: No parent directory available");
                }
                return;
            }
            // Handle the case where an absolute or relative path is provided
            File newPath;
            if (new File(args[0]).isAbsolute()) {
                // If it's an absolute path, use it directly
                newPath = new File(args[0]);
            } else {
                // If it's a relative path, resolve it from the current path
                newPath = new File(currentPath, args[0]);
            }
            // Check if the path exists and is a directory
            if (newPath.exists() && newPath.isDirectory()) {
                currentPath = newPath;
                System.out.println("Changed directory to " + currentPath.getAbsolutePath());
            } else {
                System.out.println("ERROR: Directory not found");
            }
        } else {
            System.out.println("ERROR: cd takes one argument (directory name or path)");
        }
    }

    public static void touch(String[] args) {
        if (args.length == 0) {
            System.out.println("ERROR: No file name provided. Please specify at least one file to create.");
            return;
        }
        for (String fileName : args) {
            File newFile;
            // Check if the path is absolute
            if (fileName.contains(":") || fileName.startsWith("/")) {
                newFile = new File(fileName); // Absolute path
            } else {
                newFile = new File(currentPath, fileName); // Relative path
            }

            try {
                if (newFile.createNewFile()) {
                    System.out.println("File created: " + fileName);
                } else {
                    System.out.println("ERROR: File already exists: " + fileName);
                }
            } catch (IOException e) {
                System.out.println("ERROR: Could not create file " + fileName);
            }
        }
    }
    public static void rm(String[] args , boolean isTest) {
        boolean force = false; // Flag for -f option
        boolean recursiveDelete = false; // Flag for -r option
        if(args.length==0){
            System.out.println("ERROR: No arguments are enterd , enter the name of the file you want to delete it ");
            return;
        }
        // Parse options
        for (String arg : args) {
            if (arg.equals("-f")) {
                force = true;
            } else if (arg.equals("-r")) {
                recursiveDelete = true;
            } else {
                removeFile(arg, force, recursiveDelete ,isTest); // Call to remove the file
            }
        }
    }

    public static void removeFile(String fileName, boolean force, boolean recursiveDelete , boolean isTest) {
        File file ;
        if (fileName.contains(":") || fileName.startsWith("/")) {
            file = new File(fileName); // Absolute path
        } else {
            file = new File(currentPath , fileName); // Relative path
        }
        // Check if the file exists
        if (!file.exists()) {
            System.out.println("ERROR: File not found: " + fileName);
            return;
        }

        if (!force && !isTest) {
            // Ask for confirmation to delete the file, skip during tests
            System.out.print("Are you sure you want to delete " + fileName + "? (y/n): ");
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine();
            if (!response.toLowerCase().startsWith("y")) {
                System.out.println("Skipping deletion of " + fileName);
                return; // Skip the deletion
            }
        }

        // Handle directories if recursiveDelete is true rm -r
        if (file.isDirectory()) {
            if (recursiveDelete) {
                deleteDirectory(file); // Recursively delete the directory
                System.out.println("Directory deleted: " + fileName);
            } else {
                System.out.println("ERROR: " + fileName + " is a directory. Use -r to remove it.");
            }
            return;
        }

        // Attempt to delete the file
        if (file.delete()) {
            System.out.println("File deleted: " + fileName);
        } else {
            System.out.println("ERROR: Could not delete file " + fileName);
        }
    }

    // Recursive method to delete a directory and its contents
    private static void deleteDirectory(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file); // Recursively delete subdirectory
                }
                file.delete(); // Delete the file or empty directory
            }
        }
        dir.delete(); // Finally delete the empty directory
    }
    public static void mkdir(String[] args) {
        for (int i = 0; i < args.length; i++) {
            File dir = new File(args[i]);
            if (dir.exists()) {
                System.out.println("Directory already exists: " + args[i]);
            } else if (dir.mkdir()) {
                System.out.println("Directory created: " + args[i]);
            } else {
                System.out.println("Failed to create directory: " + args[i]);
            }
        }
        return;
    }

    public static void rmdir(String[] args) {
        for (int i = 0; i < args.length; i++) {
            File dir = new File(args[i]);
            if (!dir.exists()) {
                System.out.println("Directory does not exist: " + args[i]);
            } else if (dir.delete()) {
                System.out.println("Directory deleted: " + args[i]);
            } else {
                if (dir.list().length > 0) {
                    System.out.println("Directory is not empty: " + args[i]);
                } else {
                    System.out.println("Failed to delete directory: " + args[i]);
                }
            }
        }
        return;
    }

    public static void mv(String[] args) {
        if (args.length == 0) {
            System.out.println("mv: missing source file operand");
            return;
        }
        if (args.length == 1) {
            System.out.println("mv: missing destination file operand after '" + args[0] + "'");
            return;
        }
        File dest = new File(args[args.length - 1]);
        if (!dest.exists() && args.length == 2) {
            File source = new File(args[0]);
            if (!source.exists()) {
                System.out.println("Source file does not exist: " + args[0]);
                return;
            } else if (source.exists()) {
                if (dest.exists()) {
                    File newDest = new File(dest, source.getName());
                    if (source.renameTo(newDest)) {
                        System.out.println("File moved successfully: " + source.getPath() + " -> " + newDest.getPath());
                    } else {
                        System.out.println("Failed to move file: " + source.getPath());
                    }
                    return;
                } else {
                    if (source.renameTo(dest)) {
                        System.out.println("File moved successfully");
                    } else {
                        System.out.println("Failed to move file: " + source.getPath());
                    }
                    return;
                }
            }
        }
        if (args.length > 2) {
            for (int i = 0; i < args.length - 1; i++) {
                if (!dest.exists()) {
                    File source = new File(args[i]);
                    if (!source.exists()) {
                        System.out.println("Source file does not exist: " + args[i]);
                        continue;
                    }
                    File newDest = new File(dest, source.getName());
                    if (source.renameTo(newDest)) {
                        System.out.println("File moved successfully: " + source.getPath() + " -> " + newDest.getPath());
                    } else {
                        System.out.println("Failed to move file: " + source.getPath());
                    }
                }
                File source = new File(args[i]);
                if (!source.exists()) {
                    System.out.println("Source file does not exist: " + args[i]);
                    break;
                }
                File newDest = new File(dest, source.getName());
                if (source.renameTo(newDest)) {
                    System.out.println("File moved successfully: " + source.getPath() + " -> " + newDest.getPath());
                }
            }
        }
    }
    public static String[] CatCommand(String[] args) {
        StringBuilder result = new StringBuilder();
        if (args.length == 0) {
            // No file arguments were provided; read from user input
            readFromUserInput(result);
        } else {
            // Concatenate and display contents of provided files

            for (String filePath : args) {
                readFileAndPrint(filePath,result);
            }

        }
        return result.toString().trim().split("\n");
    }
    public static String[] readFromUserInput(StringBuilder result) {
        System.out.println("Enter text (type 'exit' to finish):");
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                String line = scanner.nextLine();
                if ("exit".equalsIgnoreCase(line)) {  // End input on typing 'exit'
                    break;
                }
                System.out.println(line);// Echo input to console
                result.append(line).append("\n");
            }
        }catch (IllegalStateException e) {
            System.out.println("Scanner is closed unexpectedly: " + e.getMessage());
        }
        return result.toString().trim().split("\n");
    }
    private static String[] readFileAndPrint(String filePath,StringBuilder result) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + " - " + e.getMessage());
        }
        return result.toString().trim().split("\n");
    }

//-------------------------------------------------------------------------------------------------------------------//


    public static void OverwriteFile(String[] args) {
        int f = -1;
        String[] first = {};
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(">")) {
                switch (args[0]) {
                    case "echo":
                        first = new String[]{echo(Arrays.copyOfRange(args, 1, i))};
                        break;
                    case "ls":
                        first = new String[]{ls(Arrays.copyOfRange(args,1,i))};
                        break;
                    case "cat":
                        first = CatCommand(Arrays.copyOfRange(args, 1 , i));
                        break;
                    default:
                        System.out.println("Unknown command "+"\""+args[0]+"\"");
                        break;
                }
                f = i;
                break;
            }

        }
        if (f != -1 && f + 1 < args.length) {
            String filePath = args[f + 1];
            String content = String.join(" ", first);
            File file = new File(filePath);
            try {
                // Check if the file exists
                if (!file.exists()) {
                    // Create a new file if it doesn't exist
                    if (file.createNewFile()) {
                        System.out.println("File created: " + filePath);
                    } else {
                        System.out.println("File already exists: " + filePath);
                    }
                }

                // Write content to the file (overwriting)
                try (FileWriter writer = new FileWriter(file, false)) {  // false means overwrite
                    writer.write(content);
                }
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
//--------------------------------------------------------------------------------------------------------------------//


    public static void AppendToFile(String[] args) {
        int f = -1;
        StringBuilder contentBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(">>")) {
                switch (args[0]) {
                    case "echo":
                        contentBuilder.append(echo(Arrays.copyOfRange(args, 1, i))).append("\n");
                        break;
                    case "ls":
                        contentBuilder.append(ls(Arrays.copyOfRange(args, 1, i))).append("\n");
                        break;
                    case "cat":
                        String[] catContent = CatCommand(Arrays.copyOfRange(args, 1, i));
                        for (String line : catContent) {
                            contentBuilder.append(line).append("\n");
                        }
                        break;
                    default:
                        System.out.println("Unknown command "+"\""+args[0]+"\"");
                        break;
                }
                f = i;
                break;
            }

        }
        if (f != -1 && f + 1 < args.length) {
            String filePath = args[f + 1];
            try (FileWriter writer = new FileWriter(filePath, true)) {  // true appends to the file
                writer.write(contentBuilder.toString().trim());
            } catch (IOException e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

//--------------------------------------------------------------------------------------------------------------------//


    public static void PipeCommand(String[] args) {

        for (int i = 0; i < args.length; i++) {
            String[] first={};
            if (args[i].equals("|")) {
                switch (args[0]) {
                    case "echo":
                        first = new String[]{echo(Arrays.copyOfRange(args, 1, i))};
                        break;
                    case "ls":
                        first = new String[]{ls(Arrays.copyOfRange(args, 1, i))};
                        break;
                    case "cat":
                        first = CatCommand(Arrays.copyOfRange(args, 1 , i));
                        break;
                    default:
                        System.out.println("Unknown command "+"\""+args[0]+"\"");
                        break;
                }
                chooseCommandAction(args[args.length-1],first);
            }
        }
    }
    public static void help() {
        String[] commands = {
                "pwd: Displays the current directory.",
                "cd: Changes the current directory.",
                "ls: Lists files and directories in the current directory.",
                "mkdir: Creates a new directory.",
                "rmdir: Removes an empty directory.",
                "touch: Creates a new empty file or updates the timestamp of an existing file.",
                "mv: Moves or renames files or directories.",
                "rm: Deletes a file.",
                "cat: Displays the content of a file in the terminal.",
                ">: Redirects output of a command to a file.",
                ">>: Appends output of a command to the end of a file.",
                "|: Pipes the output of the first command to function as input to the second command."
        };
        for (String command : commands) {
            System.out.println(command);
        }
    }

    public static String ls(String[] args) {
        int aflag = 0, rflag = 0;
        List<String> directories = new ArrayList<>();
        StringBuilder output = new StringBuilder();

        for (String arg : args) {
            if (arg.charAt(0) == '-') {
                for (int i = 1; i < arg.length(); i++) {
                    switch (arg.charAt(i)) {
                        case 'a':
                            aflag = 1;
                            break;
                        case 'r':
                            rflag = 1;
                            break;
                        default:
                            output.append(arg.charAt(i)).append(" is not a valid option.\n");
                            return output.toString();
                    }
                }
            } else {
                directories.add(arg);
            }
        }

        Collections.sort(directories);

        // Handle default directory if none is specified
        if (directories.isEmpty()) {
            directories.add(".");
        }
        if (directories.size() == 1) {
            String dir = directories.get(0);
            File fileDir = new File(dir);
            if (!fileDir.isDirectory() || !fileDir.exists() || fileDir.listFiles() == null) {
                output.append(dir).append(" isn't a directory.\n");
                return output.toString();
            }
            List<String> outputList = new ArrayList<>();
            if (aflag == 1) {
                outputList.add("./");
                outputList.add("../");
            }
            for (File f : fileDir.listFiles()) {
                if (!(f.getName().charAt(0) == '.') || aflag == 1) {
                    outputList.add((f.getName().contains(" ") ? "'" : "") + f.getName() +
                            (f.getName().contains(" ") ? "'" : "") + (f.isDirectory() ? "/" : ""));
                }
            }
            if (rflag == 1) {
                Collections.reverse(outputList);
            }

            // Append each output item to the StringBuilder
            for (String outputItem : outputList) {
                output.append(outputItem).append(" ");
            }
            output.append("\n");
        }
        else {
            for (String dir : directories) {
                File fileDir = new File(dir);
                if (!fileDir.isDirectory() || !fileDir.exists() || fileDir.listFiles() == null) {
                    output.append(dir).append(" isn't a directory.\n");
                    continue;
                }
                output.append(dir).append(":\n");
                List<String> outputList = new ArrayList<>();
                if (aflag == 1) {
                    outputList.add("./");
                    outputList.add("../");
                }
                for (File f : fileDir.listFiles()) {
                    if (!(f.getName().charAt(0) == '.') || aflag == 1) {
                        outputList.add((f.getName().contains(" ") ? "'" : "") + f.getName() +
                                (f.getName().contains(" ") ? "'" : "") + (f.isDirectory() ? "/" : ""));
                    }
                }
                if (rflag == 1) {
                    Collections.reverse(outputList);
                }
                // Append each output item to the StringBuilder
                for (String outputItem : outputList) {
                    output.append(outputItem).append(" ");
                }
                output.append("\n");
            }
        }
        return output.toString().trim();
    }

}

