import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.OS.Cli;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class PipeTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        // Restore original System.out
        System.setOut(originalOut);
    }

    @Test
    public void testPipe() {
        // First command: `echo Hello world` (mocked to print to System.out)
        String[] command = {"echo","Hello","world", "|", "echo"};

        // Execute pipe
        Cli.PipeCommand(command);

        // Expected result: Only "Hello world" should be printed if "world" is found
        assertEquals("Hello world", outputStream.toString().trim());
    }

}
