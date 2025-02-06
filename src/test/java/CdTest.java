import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.OS.Cli;
import java.io.File;
import java.io.IOException;

public class CdTest {
    private Cli cli;

    @BeforeEach
    public void setUp() {
        cli = new Cli(); // Assuming Cli has a default constructor
    }

    @Test
    public void testCdParentDirectory() {
        // Arrange: Set the current path to a known subdirectory
        cli.setCurrentPath(new File("C:\\Users\\hp\\IdeaProjects\\OS-final\\f1"));
        cli.cd(new String[]{".."}); //test function
        assertEquals("C:\\Users\\hp\\IdeaProjects\\OS-final", cli.pwd(), "Failed to move to the parent directory from f1. Expected to be in 'C:\\Users\\hp\\IdeaProjects\\OS");
    }

    @Test
    public void testCdAbsolutePath() {
        cli.setCurrentPath(new File("C:\\Users\\hp\\IdeaProjects\\OS-final"));
        cli.cd(new String[]{"C:\\Users\\hp\\Documents"});
        assertEquals("C:\\Users\\hp\\Documents", cli.pwd(), "Failed to change to the absolute path 'C:\\Users\\hp\\Documents'.");
    }

    @Test
    public void testCdRelativePath() {
        cli.setCurrentPath(new File("C:\\Users\\hp\\IdeaProjects\\OS-final"));
        cli.cd(new String[]{"f1"});
        assertEquals("C:\\Users\\hp\\IdeaProjects\\OS-final\\f1", cli.pwd(), "Failed to change to the relative path 'f1'. Expected to be in 'C:\\Users\\hp\\IdeaProjects\\OS\\f1'.");
    }

    @Test
    public void testCdNonExistentDirectory() {
        cli.setCurrentPath(new File("C:\\Users\\hp\\IdeaProjects\\OS-final"));
        cli.cd(new String[]{"NonExist"});
        assertEquals("C:\\Users\\hp\\IdeaProjects\\OS-final", cli.pwd(), "Directory should not change when trying to navigate to a non-existent directory.");
    }

    @Test
    public void testCdTooManyArguments() {
        cli.setCurrentPath(new File("C:\\Users\\hp\\IdeaProjects\\OS-final"));
        cli.cd(new String[]{"f1", "f2"});
        assertEquals("C:\\Users\\hp\\IdeaProjects\\OS-final", cli.pwd(), "Directory should not change when 'cd' is called with too many arguments.");
    }

    @Test
    public void testCdNoArguments() {
        cli.setCurrentPath(new File("C:\\Users\\hp\\IdeaProjects\\OS-final"));
        cli.cd(new String[]{});
        assertEquals("C:\\Users\\hp\\IdeaProjects\\OS-final", cli.pwd(), "Directory should not change when 'cd' is called without any arguments.");
    }
}