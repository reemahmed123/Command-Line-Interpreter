import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class mkdirTest {

    private static final String TEST_DIR = "testDir";

    @BeforeEach
    public void setUp() {
        // Clean up before each test
        File dir = new File(TEST_DIR);
        if (dir.exists()) {
            dir.delete();
        }
    }

    @AfterEach
    public void tearDown() {
        // Clean up after each test
        File dir = new File(TEST_DIR);
        if (dir.exists()) {
            dir.delete();
        }
    }

    @Test
    public void testMkdirSuccess() {
        File dir = new File(TEST_DIR);
        boolean isCreated = dir.mkdir();
        assertTrue(isCreated, "Directory should be created successfully");
        assertTrue(dir.exists(), "Directory should exist");
    }

    @Test
    public void testMkdirAlreadyExists() {
        File dir = new File(TEST_DIR);
        dir.mkdir(); // Create directory first
        boolean isCreated = dir.mkdir(); // Try to create again
        assertFalse(isCreated, "Directory should not be created again");
    }

    @Test
    public void testMkdirInvalidPath() {
        File dir = new File("/invalidPath/testDir");
        boolean isCreated = dir.mkdir();
        assertFalse(isCreated, "Directory should not be created with invalid path");
    }
}