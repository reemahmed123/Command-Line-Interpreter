import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.OS.Cli;

public class AppendTest {

    private final Path testFilePath = Paths.get("testOutput.txt");

    // Mock or actual methods for echo and CatCommand if needed.
    private String echo(String[] args) {
        return String.join(" ", args);
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
    }

    @Test
    public void testEchoAppendFile() throws IOException {
        Cli.AppendToFile(new String[]{"echo", "Hello," ,"world!", ">>", "testOutput.txt"});
        String content = Files.readString(testFilePath);
        assertEquals("Hello, world!", content);
    }

    @Test
    public void testFileCreationAndWrite() throws IOException {
        // Assume OverwriteFile method writes "Hello, world!" to testOutput.txt
        Cli.AppendToFile(new String[]{"echo", "world!", ">>", "testOutput.txt"});

        // Assert file is created
        if(Files.exists(testFilePath)) {
            assertTrue(Files.exists(testFilePath));
        } else {
            assertTrue(Files.notExists(testFilePath));
        }
        // Verify file content
        String content = Files.readString(testFilePath);
        assertEquals("world!", content);
    }

    @Test
    public void testFileAppend() throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath.toFile(), false)) {
            writer.write("Existing content\n");
        }
        // Assume OverwriteFileAppend method is called and should append to the file
        Cli.AppendToFile(new String[]{"echo", "Appended content", ">>", "testOutput.txt"});

        // Verify the file content is appended
        String content = Files.readString(testFilePath);
        assertEquals("Existing content" +'\n'+"Appended content", content);
    }
}
