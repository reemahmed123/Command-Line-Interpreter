import org.OS.Cli;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PwdTest {
    private Cli cli;

    @BeforeEach
    public void setUp() {cli = new Cli(); }
    @AfterEach
    public void tearDown() {
        // Reset the current path after each test (if applicable in your implementation)
        cli.setCurrentPath(new File(System.getProperty("user.dir")));  // Reset to the default path
    }

    @Test
    public void testPwdReturnsCurrentAbsolutePath() {
        String currentPath = cli.pwd();
        // Assert: Dynamically get the expected path
        String expectedPath = System.getProperty("user.dir");
        assertEquals(expectedPath, currentPath, "The current working directory does not match the expected path.");
    }

    @Test
    public void testPwdReturnsCorrectAbsolutePath() {
        String expected = "C:\\Users\\hp\\IdeaProjects";
        cli.setCurrentPath(new File(expected));
        String currentPath = cli.pwd();
        // Assert: Verify the expected and actual paths match
        assertEquals(expected, currentPath, "Expected path: " + expected + " but got: " + currentPath);
    }
}