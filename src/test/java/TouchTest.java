import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.OS.Cli;
import java.io.File;
import java.io.IOException;

public class TouchTest {
    private Cli cli;

    @BeforeEach
    public void setUp() {
        cli = new Cli();
    }

    @Test
    public void testTouchCreatesNewFileRelativePath() throws IOException {
        cli.setCurrentPath(new File("C:\\Users\\hp\\IdeaProjects\\OS-final"));
        String[] fileArgs = {"newfile.txt"};
        File expectedFile = new File("C:\\Users\\hp\\IdeaProjects\\OS-final\\newfile.txt");
        cli.touch(fileArgs);
        assertTrue(expectedFile.exists(), "The file 'newfile.txt' should have been created in the current directory.");
        // Clean up: Delete the file after the test
        expectedFile.delete();
    }

    @Test
    public void testTouchFileAlreadyExists() throws IOException {
        cli.setCurrentPath(new File("C:\\Users\\hp\\IdeaProjects\\OS-final"));
        String[] fileArgs = {"existingfile.txt"};
        File existingFile = new File("C:\\Users\\hp\\IdeaProjects\\OS-final\\existingfile.txt");
        existingFile.createNewFile(); // Ensure the file exists
        cli.touch(fileArgs);
        assertTrue(existingFile.exists(), "The file 'existingfile.txt' already exists and should not be created again.");
        existingFile.delete();
    }

    //@Test
    public void testTouchCreatesNewFileAbsolutePath() throws IOException {
        String[] fileArgs = {"C:\\Users\\hp\\Documents\\newfile.txt"};
        File expectedFile = new File("C:\\Users\\hp\\Documents\\newfile.txt");
        cli.touch(fileArgs);
        assertTrue(expectedFile.exists(), "The file 'newfile.txt' should have been created in the absolute path.");
        // Clean up: Delete the file after the test
        expectedFile.delete();
    }

    @Test
    public void testTouchNoArguments() {
        // Act: Call the touch command without any arguments
        cli.touch(new String[]{});
    }
}