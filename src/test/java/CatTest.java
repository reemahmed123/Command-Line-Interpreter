import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.OS.Cli;

public class CatTest {

    private final Path filePath1 = Paths.get("testFile1.txt");
    private final Path filePath2 = Paths.get("testFile2.txt");
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out; // Save original System.out

    @BeforeEach
    public void setUp() throws IOException {
        // Set up test files
        try (FileWriter writer = new FileWriter(filePath1.toFile())) {
            writer.write("Content of file 1."); // Ensure newline for consistency
        }
        try (FileWriter writer = new FileWriter(filePath2.toFile())) {
            writer.write("Content of file 2."); // Ensure newline for consistency
        }

        // Redirect system output to capture printed results
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() throws IOException {
        // Clean up the test files
        Files.deleteIfExists(filePath1);
        Files.deleteIfExists(filePath2);

        // Restore original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testConcatenateFiles() {
        // Call the CatCommand method with file arguments
        Cli.CatCommand(new String[]{"testFile1.txt", "testFile2.txt"});

        // Check the captured output, including newline characters
        String expectedOutput = "Content of file 1." + System.lineSeparator() + "Content of file 2.";
        assertEquals(expectedOutput.trim(), outputStream.toString().trim());
    }

    @Test
    public void testNoArgumentsUserInput() {
        // Redirect output stream
        System.setOut(new PrintStream(outputStream));

        // Simulate user input
        String simulatedInput = "This is user input.\nexit\n"; // Simulate user input followed by 'exit'
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        // Prepare a StringBuilder to capture the results
        StringBuilder result = new StringBuilder();

        // Call the readFromUser Input method
        String[] output = Cli.readFromUserInput(result);

        // Check the captured output
        assertEquals("This is user input.", result.toString().trim()); // Compare only the actual content
        assertEquals(1, output.length); // There should be one line of output
        assertEquals("This is user input.", output[0]); // Check the content of the output array

        // Restore original output stream
        System.setOut(originalOut);
    }

}