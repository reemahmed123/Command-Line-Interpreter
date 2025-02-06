import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.OS.Cli;

public class OverWriteTest {

    private final Path testFilePath = Paths.get("testFile.txt");

    // Mock methods for echo and CatCommand, similar to what you have in your original code.
    private String echo(String[] args) {
        return String.join(" ", args);
    }

    private String[] CatCommand(String[] args) {
        // Simulate concatenation of input files or strings for testing purposes.
        return new String[] {"Sample content from CatCommand"};
    }

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure file does not exist before each test
        Files.deleteIfExists(testFilePath);
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up the test file after each test
        Files.deleteIfExists(testFilePath);
        Files.deleteIfExists(Paths.get("testFile2.txt"));
    }

    @Test
    public void testEchoOverwriteFile() throws IOException {
        Cli.OverwriteFile(new String[]{"echo", "Hello,", "world!", ">", "testFile.txt"});

        String content = Files.readString(testFilePath);
        assertEquals("Hello, world!", content);
    }

    @Test
    public void testCatOverwriteFile() throws IOException {
        Cli.OverwriteFile(new String[]{"cat", "C:\\Users\\hp\\IdeaProjects\\OS-final\\Test2.txt", ">", "testFile.txt"});
        String content = Files.readString(testFilePath);
        assertEquals("My name is mariam", content);
    }

    @Test
    public void testUnknownCommand() {
        // Run OverwriteFile with an unknown command
        Cli.OverwriteFile(new String[]{"unknown", "command", ">", "testFile2.txt"});

        // Test that the file wasn't created and no file was written
        assertTrue(Files.notExists(testFilePath));
    }

    @Test
    public void testFileCreationAndWrite() throws IOException {
        // Assume OverwriteFile method writes "Hello, world!" to testOutput.txt
        Cli.OverwriteFile(new String[]{"echo", "Hello,", "world!", ">", "testFile.txt"});
        // Assert file is created

        assertTrue(Files.exists(testFilePath));

        // Verify file content
        String content = Files.readString(testFilePath);
        assertEquals("Hello, world!", content);
    }

    @Test
    public void testFileOverwrite() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath.toFile(), false)) {
            writer.write("Existing content\n");
        }
        // Assume OverwriteFile method is called and should overwrite the file
        Cli.OverwriteFile(new String[]{"echo", "New content", ">", "testFile.txt"});

        // Verify the file content is overwritten
        String content = Files.readString(testFilePath);
        assertEquals("New content", content);
    }
}
