import static org.junit.jupiter.api.Assertions.*;

import org.OS.Cli;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

class LsTest {

    private final Cli c = new Cli();

    @Test
    void noArgs() {
        assertEquals("f1/ file.txt file3.txt pom.xml src/ target/ Test1.txt Test2.txt", c.ls(new String[]{}));
    }
    @Test
    void singleDirectory() {
        assertEquals("main/ test/", c.ls(new String[]{"src"}));
    }
    @Test
    void multipleDirectories() {
        assertEquals("main isn't a directory.\nsrc:\nmain/ test/ \nsrc/main:\njava/ resources/", c.ls(new String[]{"src", "main", "src/main"}));
    }
    @Test
    void hiddenDirectories() {
        assertEquals("./ ../ .gitignore .idea/ f1/ file.txt file3.txt pom.xml src/ target/ Test1.txt Test2.txt", c.ls(new String[]{"-a"}));
    }
    @Test
    void reverseDirectories() {
        assertEquals("Test2.txt Test1.txt target/ src/ pom.xml file3.txt file.txt f1/", c.ls(new String[]{"-r"}));
    }
    @Test
    void bothOptions() {
        assertEquals("Test2.txt Test1.txt target/ src/ pom.xml file3.txt file.txt f1/ .idea/ .gitignore ../ ./", c.ls(new String[]{"-r", "-a"}));
    }
}