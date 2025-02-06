# Command Line Interpreter (CLI)

## Project Overview
This project is a basic Command Line Interpreter (CLI) similar to a Unix/Linux shell, developed as part of the CS341 - Operating Systems 1 course at Cairo University. The CLI supports various system commands such as `pwd`, `cd`, `ls`, `mkdir`, `rmdir`, `touch`, `mv`, `rm`, `cat`, as well as internal commands like `exit` and `help`.

The project is implemented in Java using IntelliJ IDEA and is accompanied by JUnit test cases for verifying the functionality of each command.

## Features
1. **Command Execution**:
    - Supports system commands: `pwd`, `cd`, `ls`, `mkdir`, `rmdir`, `touch`, `mv`, `rm`, `cat`, `>`, `>>`, `|`
2. **Internal Commands**:
    - `exit`: Terminates the CLI.
    - `help`: Displays available commands and their usage.
3. **JUnit Testing**:
    - JUnit tests have been written to verify the functionality of each command. Each command is tested with different test cases to ensure its correctness.

## Requirements
- JDK 11 or higher
- IntelliJ IDEA or any other Java IDE
- JUnit 5 for unit testing
