import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.OS.Cli;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class RemoveTest {
    private static final String TEST_DIR = "testDir";
    private static final String TEST_FILE = "testFile.txt";

    @BeforeEach
    public void setUp() throws IOException {
        Cli cli = new Cli();
        // Setup a temporary test file and directory for testing
        File testFile = new File(TEST_FILE);
        testFile.createNewFile();
        File testDir = new File(TEST_DIR);
        testDir.mkdir();
    }

    @AfterEach
    void tearDown() {
        // Clean up after each test
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
        File dir = new File(TEST_DIR);
        if (dir.exists()) {
            dir.delete();
        }
    }

    @Test
    void testRmRemovesFile() {
        Cli cli = new Cli();
        cli.rm(new String[]{TEST_FILE}, true);
        assertFalse(new File(TEST_FILE).exists(), "File should be deleted.");
    }

    @Test
    void testRmRemovesDirectoryRecursively() {
        Cli cli = new Cli();
        cli.rm(new String[]{"-r", TEST_DIR}, true);
        assertFalse(new File(TEST_DIR).exists(), "Directory should be deleted.");
    }

    @Test
    void testRmWithoutArguments() {
        Cli cli = new Cli();
        cli.rm(new String[]{}, true);
        assertTrue(new File(TEST_DIR).exists(), "Directory should still exist.");
    }

    @Test
    void testRmFileWithForceOption() {
        Cli cli = new Cli();
        cli.rm(new String[]{"-f", TEST_FILE}, true);
        assertFalse(new File(TEST_FILE).exists(), "File should be deleted with force option.");
    }

    @Test
    void testRmNonExistentFile() {
        Cli cli = new Cli();
        String nonExistentFilePath ="nonExistentFile.txt";
        cli.rm(new String[]{nonExistentFilePath},true);
        File nonExistentFile = new File(nonExistentFilePath);
        assertFalse(nonExistentFile.exists(), "Non-existent file should not exist.");
    }
}