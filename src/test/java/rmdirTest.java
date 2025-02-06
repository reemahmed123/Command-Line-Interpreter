import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class rmdirTest {

    private static final String TEST_DIR = "testDir";

    @BeforeEach
    public void setUp() {
        // Create directory before each test
        File dir = new File(TEST_DIR);
        if (!dir.exists()) {
            dir.mkdir();
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
    public void testRmdirSuccess() {
        File dir = new File(TEST_DIR);
        boolean isDeleted = dir.delete();
        assertTrue(isDeleted, "Directory should be deleted successfully");
        assertFalse(dir.exists(), "Directory should not exist");
    }

    @Test
    public void testRmdirNonExistent() {
        File dir = new File("nonExistentDir");
        boolean isDeleted = dir.delete();
        assertFalse(isDeleted, "Non-existent directory should not be deleted");
    }

    @Test
    public void testRmdirNotEmpty() {
        File dir = new File(TEST_DIR);
        File file = new File(TEST_DIR + "/testFile.txt");
        try {
            file.createNewFile();
        } catch (Exception e) {
            fail("Failed to create test file");
        }
        boolean isDeleted = dir.delete();
        assertFalse(isDeleted, "Non-empty directory should not be deleted");
        assertTrue(dir.exists(), "Directory should still exist");
        file.delete(); // Clean up the test file
    }
}